package com.master.mastermod.common.events;

import java.util.List;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.entity.projectile.HealingFireballEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = MasterMod.MOD_ID, bus = Bus.FORGE)
public class HealingFireballEvents {

        private HealingFireballEvents() {
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
                LivingEntity living = event.getEntityLiving();
                if (living.level.isClientSide) {
                        return;
                }

                List<HealingFireballEntity> fireballs = living.level.getEntitiesOfClass(HealingFireballEntity.class,
                                living.getBoundingBox().inflate(4.0D), entity -> entity.isOwnedBy(living));
                if (fireballs.isEmpty()) {
                        return;
                }

                HealingFireballEntity fireball = fireballs.get(0);
                fireball.consume();
                if (living.getCommandSenderWorld().getServer() != null) {
                        living.getCommandSenderWorld().getServer().execute(() -> {
                                if (living.isAlive()) {
                                        living.heal(fireball.getHealAmount());
                                }
                        });
                } else {
                        living.heal(fireball.getHealAmount());
                }
        }
}
