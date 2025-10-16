package com.master.mastermod.common.item.healing.stage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;

import com.master.mastermod.common.item.healing.aura.HealingSwordAuraManager;
import com.master.mastermod.common.item.healing.config.HealingSwordConfig;

public class StageThreeBehavior implements HealingSwordStageBehavior {

    private static final int HIT_ENERGY_COST = 2;
    private static final int KILL_REGEN_DURATION = 5 * 20;

    @Override
    public void onDamage(HealingSwordStageContext context, LivingEntity target, float amount, boolean critical) {
        HealingSwordStageBehavior.super.onDamage(context, target, amount, critical);
        if (!context.level().isClientSide()) {
            context.consumeEnergy(HIT_ENERGY_COST);
        }
    }

    @Override
    public void onKill(HealingSwordStageContext context, LivingEntity victim) {
        HealingSwordStageBehavior.super.onKill(context, victim);
        if (!context.level().isClientSide()) {
            context.player().addEffect(new EffectInstance(Effects.REGENERATION, KILL_REGEN_DURATION, 0));
        }
    }

    @Override
    public ActionResultType onUse(HealingSwordStageContext context) {
        if (context.level().isClientSide()) {
            return ActionResultType.SUCCESS;
        }
        int cost = HealingSwordConfig.STAGE_III.getActiveAuraEnergyCost();
        if (!context.consumeEnergy(cost)) {
            return ActionResultType.FAIL;
        }
        HealingSwordAuraManager.startActiveAura(context.player(), HealingSwordConfig.STAGE_III);
        context.player().getCooldowns().addCooldown(context.item(), 20 * 12);
        return ActionResultType.SUCCESS;
    }
}
