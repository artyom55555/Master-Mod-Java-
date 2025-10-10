package com.master.mastermod.common.item.healing;

import java.util.List;

import com.master.mastermod.common.entity.projectile.HealingFireballEntity;
import com.master.mastermod.core.init.EntityTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public final class HealingSwordAbility {

        private static final int COOLDOWN_TICKS = 15 * 20;
        private static final int LEVEL_COST = 1;

        private HealingSwordAbility() {
        }

        public static ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand, Item item) {
                ItemStack stack = player.getItemInHand(hand);

                if (player.getCooldowns().isOnCooldown(item)) {
                        return new ActionResult<>(ActionResultType.FAIL, stack);
                }

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
}
