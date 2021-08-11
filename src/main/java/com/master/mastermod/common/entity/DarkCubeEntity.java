package com.master.mastermod.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

// класс для куба-моба
public class DarkCubeEntity extends SlimeEntity {
	public DarkCubeEntity(EntityType<? extends SlimeEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute setAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.ATTACK_DAMAGE, 5.0f)
				.add(Attributes.ATTACK_SPEED, 1.4f).add(Attributes.MOVEMENT_SPEED, 0.5f);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		p_213281_1_.putInt("Size", this.getSize() - 3);
		super.addAdditionalSaveData(p_213281_1_);
	}

	@Override
	protected int getExperienceReward(PlayerEntity player) {
		return 10;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SLIME_DEATH;
	}

}
