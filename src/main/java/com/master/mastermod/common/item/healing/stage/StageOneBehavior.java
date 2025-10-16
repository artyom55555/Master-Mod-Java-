package com.master.mastermod.common.item.healing.stage;

import net.minecraft.entity.LivingEntity;

public class StageOneBehavior implements HealingSwordStageBehavior {

    @Override
    public void onDamage(HealingSwordStageContext context, LivingEntity target, float amount, boolean critical) {
        HealingSwordStageBehavior.super.onDamage(context, target, amount, critical);
    }
}
