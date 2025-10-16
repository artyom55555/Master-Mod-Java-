package com.master.mastermod.common.item.healing;

import java.util.Locale;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import com.master.mastermod.common.item.healing.config.HealingSwordConfig.StageSettings;

public final class HealingSwordState {

    private static final String TAG_ROOT = "HealingSword";
    private static final String TAG_STAGE = "Stage";
    private static final String TAG_ENERGY = "Energy";
    private static final String TAG_KILLS = "Kills";
    private static final String TAG_UNSTABLE = "Unstable";
    private static final String TAG_IMMUNITY = "Immunity";
    private static final String TAG_AURA = "Aura";
    private static final String TAG_CLEANSE_COOLDOWN = "CleanseCooldown";
    private static final String TAG_SOUL_BURST_COOLDOWN = "SoulBurstCooldown";
    private static final String TAG_HIT_PROGRESS = "HitProgress";

    private HealingSwordState() {
    }

    public static int getStage(ItemStack stack) {
        CompoundNBT tag = getOrCreateRoot(stack);
        if (!tag.contains(TAG_STAGE)) {
            tag.putInt(TAG_STAGE, 1);
        }
        return tag.getInt(TAG_STAGE);
    }

    public static void setStage(ItemStack stack, int stage) {
        getOrCreateRoot(stack).putInt(TAG_STAGE, stage);
    }

    public static int getEnergy(ItemStack stack) {
        return getOrCreateRoot(stack).getInt(TAG_ENERGY);
    }

    public static void setEnergy(ItemStack stack, int energy, StageSettings settings) {
        int clamped = Math.max(0, Math.min(settings.getMaxEnergy(), energy));
        getOrCreateRoot(stack).putInt(TAG_ENERGY, clamped);
    }

    public static void addEnergy(ItemStack stack, int delta, StageSettings settings) {
        if (delta == 0) {
            return;
        }
        int current = getEnergy(stack);
        setEnergy(stack, current + delta, settings);
    }

    public static boolean tryConsumeEnergy(ItemStack stack, int amount, StageSettings settings) {
        if (amount <= 0) {
            return true;
        }
        int current = getEnergy(stack);
        if (current < amount) {
            return false;
        }
        setEnergy(stack, current - amount, settings);
        return true;
    }

    public static int getKills(ItemStack stack) {
        return getOrCreateRoot(stack).getInt(TAG_KILLS);
    }

    public static void addKill(ItemStack stack) {
        CompoundNBT tag = getOrCreateRoot(stack);
        tag.putInt(TAG_KILLS, tag.getInt(TAG_KILLS) + 1);
    }

    public static float getUnstable(ItemStack stack) {
        return getOrCreateRoot(stack).getFloat(TAG_UNSTABLE);
    }

    public static void setUnstable(ItemStack stack, float value) {
        getOrCreateRoot(stack).putFloat(TAG_UNSTABLE, value);
    }

    public static int getAuraTicks(ItemStack stack) {
        return getOrCreateRoot(stack).getInt(TAG_AURA);
    }

    public static void setAuraTicks(ItemStack stack, int ticks) {
        getOrCreateRoot(stack).putInt(TAG_AURA, Math.max(0, ticks));
    }

    public static void tickAura(ItemStack stack) {
        CompoundNBT tag = getOrCreateRoot(stack);
        int ticks = Math.max(0, tag.getInt(TAG_AURA) - 1);
        tag.putInt(TAG_AURA, ticks);
    }

    public static int getCleanseCooldown(ItemStack stack) {
        return getOrCreateRoot(stack).getInt(TAG_CLEANSE_COOLDOWN);
    }

    public static void setCleanseCooldown(ItemStack stack, int ticks) {
        getOrCreateRoot(stack).putInt(TAG_CLEANSE_COOLDOWN, Math.max(0, ticks));
    }

    public static void tickCleanseCooldown(ItemStack stack) {
        CompoundNBT tag = getOrCreateRoot(stack);
        int cooldown = Math.max(0, tag.getInt(TAG_CLEANSE_COOLDOWN) - 1);
        tag.putInt(TAG_CLEANSE_COOLDOWN, cooldown);
    }

    public static int getSoulBurstCooldown(ItemStack stack) {
        return getOrCreateRoot(stack).getInt(TAG_SOUL_BURST_COOLDOWN);
    }

    public static void setSoulBurstCooldown(ItemStack stack, int ticks) {
        getOrCreateRoot(stack).putInt(TAG_SOUL_BURST_COOLDOWN, Math.max(0, ticks));
    }

    public static void tickSoulBurstCooldown(ItemStack stack) {
        CompoundNBT tag = getOrCreateRoot(stack);
        int cooldown = Math.max(0, tag.getInt(TAG_SOUL_BURST_COOLDOWN) - 1);
        tag.putInt(TAG_SOUL_BURST_COOLDOWN, cooldown);
    }

    public static int getHitProgress(ItemStack stack) {
        return getOrCreateRoot(stack).getInt(TAG_HIT_PROGRESS);
    }

    public static void incrementHitProgress(ItemStack stack, StageSettings settings) {
        if (settings.getHitEnergyGain() <= 0) {
            return;
        }
        CompoundNBT tag = getOrCreateRoot(stack);
        int progress = tag.getInt(TAG_HIT_PROGRESS) + 1;
        int interval = Math.max(1, settings.getHitEnergyInterval());
        if (progress >= interval) {
            addEnergy(stack, settings.getHitEnergyGain(), settings);
            progress = 0;
        }
        tag.putInt(TAG_HIT_PROGRESS, progress);
    }

    public static void resetHitProgress(ItemStack stack) {
        getOrCreateRoot(stack).putInt(TAG_HIT_PROGRESS, 0);
    }

    public static int getImmunityTicks(ItemStack stack) {
        return getOrCreateRoot(stack).getInt(TAG_IMMUNITY);
    }

    public static void setImmunityTicks(ItemStack stack, int ticks) {
        getOrCreateRoot(stack).putInt(TAG_IMMUNITY, Math.max(0, ticks));
    }

    public static void tickImmunity(ItemStack stack) {
        CompoundNBT tag = getOrCreateRoot(stack);
        int ticks = Math.max(0, tag.getInt(TAG_IMMUNITY) - 1);
        tag.putInt(TAG_IMMUNITY, ticks);
    }

    public static void tickCooldowns(ItemStack stack) {
        tickAura(stack);
        tickCleanseCooldown(stack);
        tickSoulBurstCooldown(stack);
        tickImmunity(stack);
    }

    public static String describe(ItemStack stack, StageSettings settings) {
        return String.format(Locale.ROOT, "Stage %d | Energy %d/%d | Kills %d", getStage(stack), getEnergy(stack),
                settings.getMaxEnergy(), getKills(stack));
    }

    private static CompoundNBT getOrCreateRoot(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains(TAG_ROOT, 10)) {
            tag.put(TAG_ROOT, new CompoundNBT());
        }
        return tag.getCompound(TAG_ROOT);
    }

    public static void ensureStageBounds(ItemStack stack, StageSettings settings) {
        int stage = Math.max(1, Math.min(5, getStage(stack)));
        setStage(stack, stage);
        int energy = Math.min(settings.getMaxEnergy(), getEnergy(stack));
        setEnergy(stack, energy, settings);
    }

    public static void healOwner(LivingEntity owner, float amount) {
        if (amount <= 0) {
            return;
        }
        owner.heal(amount);
    }
}
