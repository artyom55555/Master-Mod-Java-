package com.master.mastermod.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import com.master.mastermod.common.item.healing.HealingSwordAbility;
import com.master.mastermod.common.item.healing.HealingSwordState;
import com.master.mastermod.common.item.healing.config.HealingSwordConfig.StageSettings;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageContext;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageData;
import com.master.mastermod.common.item.healing.stage.HealingSwordStages;

public class HealingSwordItem extends SwordItem {

        public HealingSwordItem(IItemTier tier, int attackDamageModifier, float attackSpeedModifier,
                        Properties properties) {
                super(tier, attackDamageModifier, attackSpeedModifier, properties);
        }

        @Override
        public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                return super.hurtEnemy(stack, target, attacker);
        }

        @Override
        public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
                return HealingSwordAbility.use(level, player, hand, this);
        }

        @Override
        public void inventoryTick(ItemStack stack, World level, Entity entity, int slot, boolean selected) {
                super.inventoryTick(stack, level, entity, slot, selected);
                if (level.isClientSide || !(entity instanceof PlayerEntity)) {
                        return;
                }
                PlayerEntity player = (PlayerEntity) entity;
                if (!selected && player.getMainHandItem() != stack && player.getOffhandItem() != stack) {
                        return;
                }
                HealingSwordStages.Stage stage = HealingSwordStages
                                .fromId(HealingSwordState.getStage(stack));
                StageSettings settings = HealingSwordStageData.settings(stage);
                HealingSwordStageContext context = new HealingSwordStageContext(level, player, stack, this, settings);
                HealingSwordStages.behavior(stage).onEquipped(context);
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> tooltip,
                        ITooltipFlag flag) {
                super.appendHoverText(stack, level, tooltip, flag);
                HealingSwordStages.Stage stage = HealingSwordStages.fromId(HealingSwordState.getStage(stack));
                StageSettings settings = HealingSwordStageData.settings(stage);
                tooltip.add(new TranslationTextComponent("item.mastermod.healing_sword.desc")
                                .withStyle(TextFormatting.LIGHT_PURPLE));
                tooltip.add(new TranslationTextComponent("tooltip.mastermod.healing_sword.stage", stage.id())
                                .withStyle(TextFormatting.GOLD));
                tooltip.add(new TranslationTextComponent("tooltip.mastermod.healing_sword.energy",
                                HealingSwordState.getEnergy(stack), settings.getMaxEnergy()).withStyle(TextFormatting.AQUA));
                tooltip.add(new TranslationTextComponent("tooltip.mastermod.healing_sword.kills",
                                HealingSwordState.getKills(stack)).withStyle(TextFormatting.DARK_RED));
        }
}
