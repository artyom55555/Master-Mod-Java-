package com.master.mastermod.common.item.healing;

import java.util.List;

import com.master.mastermod.common.entity.projectile.HealingFireballEntity;
import com.master.mastermod.common.item.HealingSwordItem;
import com.master.mastermod.common.item.healing.HealingSwordState;
import com.master.mastermod.common.item.healing.config.HealingSwordConfig.StageSettings;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageBehavior;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageContext;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageData;
import com.master.mastermod.common.item.healing.stage.HealingSwordStages;
import com.master.mastermod.core.init.EntityTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.util.text.TranslationTextComponent;

public final class HealingSwordAbility {

        private static final int COOLDOWN_TICKS = 15 * 20;
        private static final int LEVEL_COST = 1;

        private HealingSwordAbility() {
        }

        public static ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand, HealingSwordItem item) {
                ItemStack stack = player.getItemInHand(hand);

                if (player.getCooldowns().isOnCooldown(item)) {
                        return new ActionResult<>(ActionResultType.FAIL, stack);
                }

                HealingSwordStages.Stage stage = HealingSwordStages.fromId(HealingSwordState.getStage(stack));
                StageSettings settings = HealingSwordStageData.settings(stage);
                HealingSwordStageBehavior behavior = HealingSwordStages.behavior(stage);
                HealingSwordStageContext context = new HealingSwordStageContext(level, player, stack, item, settings);

                if (player.isShiftKeyDown()) {
                        if (!level.isClientSide && tryUpgradeStage(player, stack, stage)) {
                                return new ActionResult<>(ActionResultType.SUCCESS, stack);
                        }
                        return new ActionResult<>(ActionResultType.PASS, stack);
                }

                ActionResultType result = behavior.onUse(context);
                if (result == ActionResultType.SUCCESS || result == ActionResultType.CONSUME) {
                        return new ActionResult<>(result, stack);
                }

                if (stage.id() >= HealingSwordStages.Stage.STAGE_III.id()) {
                        return new ActionResult<>(result, stack);
                }

                return summonFireball(level, player, stack, item);
        }

        private static ActionResult<ItemStack> summonFireball(World level, PlayerEntity player, ItemStack stack,
                        HealingSwordItem item) {
                if (!player.isCreative() && player.experienceLevel < LEVEL_COST) {
                        return new ActionResult<>(ActionResultType.FAIL, stack);
                }

                if (!level.isClientSide) {

                        List<HealingFireballEntity> existing = level.getEntitiesOfClass(HealingFireballEntity.class,
                                        player.getBoundingBox().inflate(32.0D), entity -> entity.isOwnedBy(player));
                        if (!existing.isEmpty()) {
                                HealingFireballEntity fireball = existing.get(0);
                                fireball.refreshLifetime();
                                fireball.refreshOrbit();
                                player.getCooldowns().addCooldown(item, COOLDOWN_TICKS);
                                if (!player.isCreative()) {
                                        player.giveExperienceLevels(-LEVEL_COST);
                                }
                                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                                SoundEvents.BEACON_AMBIENT, SoundCategory.PLAYERS, 0.6F, 1.1F);
                                return ActionResult.sidedSuccess(stack, level.isClientSide);
                        }

                        HealingFireballEntity fireball = EntityTypesInit.HEALING_FIREBALL.get().create(level);
                        if (fireball == null) {
                                return new ActionResult<>(ActionResultType.FAIL, stack);
                        }

                        fireball.moveTo(player.getX(), player.getY() + player.getBbHeight() * 0.6D, player.getZ(),
                                        player.yRot, player.xRot);
                        fireball.setOwner(player);
                        level.addFreshEntity(fireball);
                        if (!player.isCreative()) {
                                player.giveExperienceLevels(-LEVEL_COST);
                        }
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                        SoundEvents.BEACON_POWER_SELECT, SoundCategory.PLAYERS, 0.7F, 1.2F);
                }

                player.getCooldowns().addCooldown(item, COOLDOWN_TICKS);
                return ActionResult.sidedSuccess(stack, level.isClientSide);
        }

        private static boolean tryUpgradeStage(PlayerEntity player, ItemStack stack, HealingSwordStages.Stage stage) {
                UpgradeRequirement requirement = UpgradeRequirement.forStage(stage);
                if (requirement == null) {
                        return false;
                }
                int currentKills = HealingSwordState.getKills(stack);
                int currentEnergy = HealingSwordState.getEnergy(stack);
                if (currentKills < requirement.requiredKills || currentEnergy < requirement.requiredEnergy) {
                        player.displayClientMessage(new TranslationTextComponent("message.mastermod.healing_sword.requirements",
                                        requirement.requiredKills, requirement.requiredEnergy), true);
                        return false;
                }

                HealingSwordStages.Stage nextStage = HealingSwordStages.fromId(stage.id() + 1);
                StageSettings nextSettings = HealingSwordStageData.settings(nextStage);
                HealingSwordState.setStage(stack, nextStage.id());
                HealingSwordState.setEnergy(stack, currentEnergy - requirement.requiredEnergy, nextSettings);
                player.displayClientMessage(new TranslationTextComponent("message.mastermod.healing_sword.upgraded",
                                nextStage.id()), true);
                return true;
        }

        private static final class UpgradeRequirement {
                private final int requiredKills;
                private final int requiredEnergy;

                private UpgradeRequirement(int requiredKills, int requiredEnergy) {
                        this.requiredKills = requiredKills;
                        this.requiredEnergy = requiredEnergy;
                }

                private static UpgradeRequirement forStage(HealingSwordStages.Stage stage) {
                        switch (stage) {
                        case STAGE_I:
                                return new UpgradeRequirement(10, 15);
                        case STAGE_II:
                                return new UpgradeRequirement(30, 25);
                        case STAGE_III:
                                return new UpgradeRequirement(60, 40);
                        case STAGE_IV:
                                return new UpgradeRequirement(100, 60);
                        default:
                                return null;
                        }
                }
        }
}
