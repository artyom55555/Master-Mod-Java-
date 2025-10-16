package com.master.mastermod.common.item.healing.stage;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import com.master.mastermod.common.item.healing.config.HealingSwordConfig;

public class StageFourBehavior implements HealingSwordStageBehavior {

    @Override
    public void onDamage(HealingSwordStageContext context, LivingEntity target, float amount, boolean critical) {
        HealingSwordStageBehavior.super.onDamage(context, target, amount, critical);
    }

    @Override
    public ActionResultType onUse(HealingSwordStageContext context) {
        if (context.level().isClientSide()) {
            return ActionResultType.SUCCESS;
        }

        if (context.getCleanseCooldown() > 0) {
            return ActionResultType.FAIL;
        }

        int energyCost = HealingSwordConfig.STAGE_IV.getCleanseEnergyCost();
        if (!context.consumeEnergy(energyCost)) {
            return ActionResultType.FAIL;
        }

        float healthCost = HealingSwordConfig.STAGE_IV.getCleanseHealthCost();
        if (context.player().getHealth() <= healthCost) {
            context.addEnergy(energyCost);
            return ActionResultType.FAIL;
        }

        context.player().hurt(DamageSource.MAGIC, healthCost);

        List<EffectInstance> toRemove = new ArrayList<>();
        for (EffectInstance effect : context.player().getActiveEffects()) {
            if (!effect.getEffect().isBeneficial()) {
                toRemove.add(effect);
            }
        }
        for (EffectInstance effect : toRemove) {
            context.player().removeEffect(effect.getEffect());
        }

        context.setCleanseCooldown(HealingSwordConfig.STAGE_IV.getCleanseCooldownTicks());
        context.setImmunityTicks(HealingSwordConfig.STAGE_IV.getImmunityDurationTicks());
        context.level().playSound(null, context.player().getX(), context.player().getY(), context.player().getZ(),
                SoundEvents.BEACON_DEACTIVATE, SoundCategory.PLAYERS, 0.8f, 1.2f);
        context.player().addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 0));
        context.player().getCooldowns().addCooldown(context.item(), HealingSwordConfig.STAGE_IV.getCleanseCooldownTicks());

        return ActionResultType.SUCCESS;
    }
}
