package com.master.mastermod.common.entity.projectile;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.master.mastermod.core.init.EntityTypesInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class HealingFireballEntity extends Entity {

        private static final DataParameter<Optional<UUID>> OWNER_UUID = EntityDataManager
                        .defineId(HealingFireballEntity.class, DataSerializers.OPTIONAL_UUID);
        private static final DataParameter<Float> ORBIT_OFFSET = EntityDataManager.defineId(HealingFireballEntity.class,
                        DataSerializers.FLOAT);
        private static final int MAX_LIFETIME_TICKS = 120 * 20;
        private static final float ORBIT_RADIUS = 1.8F;
        private static final float HEAL_AMOUNT = 10.0F;

        private int lifetime;

        public HealingFireballEntity(EntityType<? extends HealingFireballEntity> type, World level) {
                super(type, level);
                this.noPhysics = true;
                this.setNoGravity(true);
                this.lifetime = MAX_LIFETIME_TICKS;
        }

        public HealingFireballEntity(World level) {
                this(EntityTypesInit.HEALING_FIREBALL.get(), level);
        }

        @Override
        protected void defineSynchedData() {
                this.entityData.define(OWNER_UUID, Optional.empty());
                this.entityData.define(ORBIT_OFFSET, this.random.nextFloat() * 360.0F);
        }

        @Override
        public void tick() {
                super.tick();

                if (!this.level.isClientSide) {
                        if (--this.lifetime <= 0) {
                                this.remove();
                                return;
                        }
                }

                LivingEntity owner = this.getOwner();
                if (owner == null || !owner.isAlive()) {
                        if (!this.level.isClientSide) {
                                this.remove();
                        }
                        return;
                }

                float angle = this.entityData.get(ORBIT_OFFSET) + this.tickCount * 4.0F;
                double radians = Math.toRadians(angle % 360.0F);
                double centerY = owner.getY() + owner.getBbHeight() * 0.65D;
                double offsetX = MathHelper.cos((float) radians) * ORBIT_RADIUS;
                double offsetZ = MathHelper.sin((float) radians) * ORBIT_RADIUS;

                this.setPos(owner.getX() + offsetX, centerY, owner.getZ() + offsetZ);
                this.setDeltaMovement(0.0D, 0.0D, 0.0D);
        }

        @Override
        protected void readAdditionalSaveData(CompoundNBT tag) {
                if (tag.hasUUID("Owner")) {
                        this.entityData.set(OWNER_UUID, Optional.of(tag.getUUID("Owner")));
                }
                this.lifetime = tag.contains("Lifetime") ? tag.getInt("Lifetime") : MAX_LIFETIME_TICKS;
                this.entityData.set(ORBIT_OFFSET,
                                tag.contains("Offset") ? tag.getFloat("Offset") : this.random.nextFloat() * 360.0F);
        }

        @Override
        protected void addAdditionalSaveData(CompoundNBT tag) {
                this.getOwnerUUID().ifPresent(uuid -> tag.putUUID("Owner", uuid));
                tag.putInt("Lifetime", this.lifetime);
                tag.putFloat("Offset", this.entityData.get(ORBIT_OFFSET));
        }

        @Override
        public boolean hurt(DamageSource source, float amount) {
                return false;
        }

        @Override
        public boolean isPickable() {
                return false;
        }

        public void refreshLifetime() {
                this.lifetime = MAX_LIFETIME_TICKS;
        }

        public void refreshOrbit() {
                this.entityData.set(ORBIT_OFFSET, this.random.nextFloat() * 360.0F);
        }

        public float getHealAmount() {
                return HEAL_AMOUNT;
        }

        public boolean isOwnedBy(LivingEntity entity) {
                Optional<UUID> uuid = this.getOwnerUUID();
                return uuid.isPresent() && uuid.get().equals(entity.getUUID());
        }

        public void setOwner(@Nullable LivingEntity owner) {
                this.entityData.set(OWNER_UUID, owner == null ? Optional.empty() : Optional.of(owner.getUUID()));
        }

        @Nullable
        public LivingEntity getOwner() {
                Optional<UUID> uuid = this.getOwnerUUID();
                if (uuid.isPresent()) {
                        return this.level.getPlayerByUUID(uuid.get());
                }
                return null;
        }

        private Optional<UUID> getOwnerUUID() {
                return this.entityData.get(OWNER_UUID);
        }

        public void consume() {
                if (!this.level.isClientSide) {
                        this.remove();
                }
        }

        @Override
        public IPacket<?> getAddEntityPacket() {
                return NetworkHooks.getEntitySpawningPacket(this);
        }
}
