package com.master.mastermod.common.item.healing.stage;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;

import com.master.mastermod.common.item.healing.config.HealingSwordConfig;

public class StageFiveBehavior implements HealingSwordStageBehavior {

    private static final int SOUL_BURST_COOLDOWN = 20 * 16;

    @Override
    public void onDamage(HealingSwordStageContext context, LivingEntity target, float amount, boolean critical) {
        HealingSwordStageBehavior.super.onDamage(context, target, amount, critical);
        if (context.level().isClientSide()) {
            return;
        }
        float converted = amount * 0.5f;
        if (converted <= 0.0f) {
            return;
        }
        if (context.player().getRandom().nextBoolean()) {
            context.healOwner(converted);
            context.spawnHearts((int) Math.max(1, Math.round(converted * 0.5f)));
        } else {
            context.player().hurt(DamageSource.MAGIC, converted);
        }
    }

    @Override
    public ActionResultType onUse(HealingSwordStageContext context) {
        if (context.level().isClientSide()) {
            return ActionResultType.SUCCESS;
        }

        if (context.getSoulBurstCooldown() > 0) {
            return ActionResultType.FAIL;
        }

        int energyCost = HealingSwordConfig.STAGE_V.getSoulBurstEnergyCost();
        if (!context.consumeEnergy(energyCost)) {
            return ActionResultType.FAIL;
        }

        double radius = HealingSwordConfig.STAGE_V.getSoulBurstRadius();
        float damage = HealingSwordConfig.STAGE_V.getSoulBurstBaseDamage();
        context.level().explode(context.player(), context.player().getX(), context.player().getY(),
                context.player().getZ(), (float) radius, Explosion.Mode.NONE);

        List<LivingEntity> entities = context.level().getEntitiesOfClass(LivingEntity.class,
                context.player().getBoundingBox().inflate(radius), entity -> entity.isAlive());
        for (LivingEntity entity : entities) {
            if (entity == context.player()) {
                entity.hurt(DamageSource.explosion(context.player()), damage);
            } else if (entity.isAlliedTo(context.player())) {
                entity.addEffect(new EffectInstance(Effects.REGENERATION,
                        HealingSwordConfig.STAGE_V.getSoulBurstRegenDuration(), 1));
            } else {
                entity.hurt(DamageSource.explosion(context.player()), damage);
            }
        }

        context.setSoulBurstCooldown(SOUL_BURST_COOLDOWN);

        if (context.player().getRandom().nextFloat() < HealingSwordConfig.STAGE_V.getOverloadChance()) {
            triggerOverload(context, entities);
        }

        context.player().getCooldowns().addCooldown(context.item(), SOUL_BURST_COOLDOWN);

        return ActionResultType.SUCCESS;
    }

    private void triggerOverload(HealingSwordStageContext context, List<LivingEntity> entities) {
        float overloadDamage = HealingSwordConfig.STAGE_V.getOverloadDamage();
        float overloadHeal = HealingSwordConfig.STAGE_V.getOverloadHeal();
        context.player().hurt(DamageSource.MAGIC, overloadDamage);
        for (LivingEntity entity : entities) {
            if (entity == context.player()) {
                continue;
            }
            if (entity.isAlliedTo(context.player())) {
                entity.heal(overloadHeal);
            } else {
                entity.hurt(DamageSource.MAGIC, overloadDamage);
            }
        }
    }
}
