package com.master.mastermod.common.item.healing.stage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;

public interface HealingSwordStageBehavior {

    default void onDamage(HealingSwordStageContext context, LivingEntity target, float amount, boolean critical) {
        float heal = amount * context.vampirism();
        if (heal > 0.0f) {
            context.healOwner(heal);
            context.spawnHearts((int) Math.max(1, Math.round(heal)));
        }
        context.incrementHitProgress();
    }

    default void onKill(HealingSwordStageContext context, LivingEntity victim) {
        context.addKill();
    }

    default ActionResultType onUse(HealingSwordStageContext context) {
        return ActionResultType.PASS;
    }

    default void tick(HealingSwordStageContext context) {
        context.tickCooldowns();
    }

    default void onEquipped(HealingSwordStageContext context) {
        context.ensureBounds();
    }

    default void onUnequipped(HealingSwordStageContext context) {
    }

    default boolean canStartAura(HealingSwordStageContext context) {
        return false;
    }
}
