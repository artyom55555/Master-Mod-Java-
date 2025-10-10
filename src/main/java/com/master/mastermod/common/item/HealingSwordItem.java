package com.master.mastermod.common.item;

import java.util.List;
2
import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class HealingSwordItem extends SwordItem {

        private final float healAmount;

        public HealingSwordItem(IItemTier tier, int attackDamageModifier, float attackSpeedModifier, float healAmount,
                        Properties properties) {
                super(tier, attackDamageModifier, attackSpeedModifier, properties);
                this.healAmount = healAmount;
        }

        @Override
        public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                boolean damaged = super.hurtEnemy(stack, target, attacker);
                if (damaged && !attacker.level.isClientSide) {
                        attacker.heal(this.healAmount);
                }
                return damaged;
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> tooltip,
                        ITooltipFlag flag) {
                super.appendHoverText(stack, level, tooltip, flag);
                tooltip.add(new TranslationTextComponent("item.mastermod.healing_sword.desc"));
        }
}
