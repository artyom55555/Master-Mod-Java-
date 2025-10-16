package com.master.mastermod.common.item.healing.stage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import com.master.mastermod.common.item.HealingSwordItem;
import com.master.mastermod.common.item.healing.HealingSwordState;
import com.master.mastermod.common.item.healing.config.HealingSwordConfig.StageSettings;

public final class HealingSwordStageContext {
    private final World level;
    private final PlayerEntity player;
    private final ItemStack stack;
    private final HealingSwordItem item;
    private final StageSettings settings;

    public HealingSwordStageContext(World level, PlayerEntity player, ItemStack stack, HealingSwordItem item,
            StageSettings settings) {
        this.level = level;
        this.player = player;
        this.stack = stack;
        this.item = item;
        this.settings = settings;
    }

    public World level() {
        return this.level;
    }

    public PlayerEntity player() {
        return this.player;
    }

    public ItemStack stack() {
        return this.stack;
    }

    public HealingSwordItem item() {
        return this.item;
    }

    public StageSettings settings() {
        return this.settings;
    }

    public void addEnergy(int value) {
        HealingSwordState.addEnergy(this.stack, value, this.settings);
    }

    public boolean consumeEnergy(int value) {
        return HealingSwordState.tryConsumeEnergy(this.stack, value, this.settings);
    }

    public void addKill() {
        HealingSwordState.addKill(this.stack);
    }

    public void healOwner(float amount) {
        HealingSwordState.healOwner(this.player, amount);
    }

    public void spawnHearts(int count) {
        if (!(this.level instanceof ServerWorld)) {
            return;
        }
        ServerWorld server = (ServerWorld) this.level;
        double x = this.player.getX();
        double y = this.player.getY() + 1.0D;
        double z = this.player.getZ();
        server.sendParticles(ParticleTypes.HEART, x, y, z, Math.max(1, count), 0.3D, 0.5D, 0.3D, 0.1D);
    }

    public void ensureBounds() {
        HealingSwordState.ensureStageBounds(this.stack, this.settings);
    }

    public void setAuraTicks(int ticks) {
        HealingSwordState.setAuraTicks(this.stack, ticks);
    }

    public int getAuraTicks() {
        return HealingSwordState.getAuraTicks(this.stack);
    }

    public void setCleanseCooldown(int ticks) {
        HealingSwordState.setCleanseCooldown(this.stack, ticks);
    }

    public int getCleanseCooldown() {
        return HealingSwordState.getCleanseCooldown(this.stack);
    }

    public void setSoulBurstCooldown(int ticks) {
        HealingSwordState.setSoulBurstCooldown(this.stack, ticks);
    }

    public int getSoulBurstCooldown() {
        return HealingSwordState.getSoulBurstCooldown(this.stack);
    }

    public void setImmunityTicks(int ticks) {
        HealingSwordState.setImmunityTicks(this.stack, ticks);
    }

    public int getImmunityTicks() {
        return HealingSwordState.getImmunityTicks(this.stack);
    }

    public void incrementHitProgress() {
        HealingSwordState.incrementHitProgress(this.stack, this.settings);
    }

    public void resetHitProgress() {
        HealingSwordState.resetHitProgress(this.stack);
    }

    public float vampirism() {
        return this.settings.getVampirism();
    }

    public int maxEnergy() {
        return this.settings.getMaxEnergy();
    }

    public int kills() {
        return HealingSwordState.getKills(this.stack);
    }

    public float unstable() {
        return HealingSwordState.getUnstable(this.stack);
    }

    public void setUnstable(float value) {
        HealingSwordState.setUnstable(this.stack, value);
    }

    public void tickCooldowns() {
        HealingSwordState.tickCooldowns(this.stack);
    }

    public void healOrHurtOwner(float healAmount, float hurtAmount) {
        if (healAmount > 0.0f) {
            this.player.heal(healAmount);
        }
        if (hurtAmount > 0.0f) {
            this.player.hurt(net.minecraft.util.DamageSource.MAGIC, hurtAmount);
        }
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.player;
    }
}
