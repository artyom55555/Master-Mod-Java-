package com.master.mastermod.common.item.healing.stage;

import net.minecraft.entity.LivingEntity;

import com.master.mastermod.common.item.healing.aura.HealingSwordAuraManager;
import com.master.mastermod.common.item.healing.config.HealingSwordConfig;

public class StageTwoBehavior implements HealingSwordStageBehavior {

    @Override
    public void onDamage(HealingSwordStageContext context, LivingEntity target, float amount, boolean critical) {
        HealingSwordStageBehavior.super.onDamage(context, target, amount, critical);
        if (critical && !context.level().isClientSide()) {
            HealingSwordAuraManager.startCriticalAura(context.player(), HealingSwordConfig.STAGE_II);
        }
    }
}
