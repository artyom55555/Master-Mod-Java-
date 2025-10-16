package com.master.mastermod.common.item.healing.aura;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;

import com.master.mastermod.common.item.healing.config.HealingSwordConfig.StageSettings;

public final class HealingSwordAuraManager {

    private static final Map<UUID, AuraInstance> AURAS = new HashMap<>();

    private HealingSwordAuraManager() {
    }

    public static void startCriticalAura(PlayerEntity owner, StageSettings settings) {
        startAura(owner, settings.getAuraRadius(), settings.getAuraDurationTicks(), 20, 0);
    }

    public static void startActiveAura(PlayerEntity owner, StageSettings settings) {
        startAura(owner, settings.getActiveAuraRadius(), settings.getActiveAuraDurationTicks(), 10, 1);
    }

    public static void startAura(PlayerEntity owner, double radius, int durationTicks, int tickInterval,
            int amplifier) {
        AURAS.put(owner.getUUID(), new AuraInstance(radius, durationTicks, tickInterval, amplifier));
    }

    public static void tick(PlayerEntity owner) {
        AuraInstance aura = AURAS.get(owner.getUUID());
        if (aura == null) {
            return;
        }
        aura.remainingTicks--;
        if (aura.remainingTicks <= 0) {
            AURAS.remove(owner.getUUID());
            return;
        }
        if (owner.tickCount % aura.tickInterval != 0) {
            return;
        }
        AxisAlignedBB box = owner.getBoundingBox().inflate(aura.radius);
        for (LivingEntity entity : owner.level.getEntitiesOfClass(LivingEntity.class, box,
                target -> target.isAlive() && (target == owner || target.isAlliedTo(owner)))) {
            entity.addEffect(new EffectInstance(Effects.REGENERATION, 60, aura.amplifier, true, true));
        }
    }

    public static void clear(PlayerEntity owner) {
        AURAS.remove(owner.getUUID());
    }

    private static final class AuraInstance {
        private final double radius;
        private final int tickInterval;
        private final int amplifier;
        private int remainingTicks;

        private AuraInstance(double radius, int durationTicks, int tickInterval, int amplifier) {
            this.radius = radius;
            this.remainingTicks = durationTicks;
            this.tickInterval = Math.max(1, tickInterval);
            this.amplifier = amplifier;
        }
    }
}
