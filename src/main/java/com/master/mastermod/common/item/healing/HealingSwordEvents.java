package com.master.mastermod.common.item.healing;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.item.HealingSwordItem;
import com.master.mastermod.common.item.healing.HealingSwordState;
import com.master.mastermod.common.item.healing.aura.HealingSwordAuraManager;
import com.master.mastermod.common.item.healing.config.HealingSwordConfig.StageSettings;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageBehavior;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageContext;
import com.master.mastermod.common.item.healing.stage.HealingSwordStageData;
import com.master.mastermod.common.item.healing.stage.HealingSwordStages;

@Mod.EventBusSubscriber(modid = MasterMod.MOD_ID, bus = Bus.FORGE)
public final class HealingSwordEvents {

    private static final Set<UUID> CRITICAL_CACHE = new HashSet<>();

    private HealingSwordEvents() {
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntityLiving();
        if (!(event.getSource().getEntity() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
        if (player.level.isClientSide()) {
            return;
        }
        ItemStack stack = getHeldSword(player);
        if (stack.isEmpty()) {
            return;
        }
        HealingSwordItem item = (HealingSwordItem) stack.getItem();
        HealingSwordStages.Stage stage = HealingSwordStages.fromId(HealingSwordState.getStage(stack));
        StageSettings settings = HealingSwordStageData.settings(stage);
        HealingSwordStageContext context = new HealingSwordStageContext(player.level, player, stack, item, settings);
        HealingSwordStageBehavior behavior = HealingSwordStages.behavior(stage);
        boolean critical = CRITICAL_CACHE.remove(player.getUUID());
        behavior.onDamage(context, target, event.getAmount(), critical);
        context.ensureBounds();
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntityLiving();
        if (!(event.getSource().getEntity() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
        if (player.level.isClientSide()) {
            return;
        }
        ItemStack stack = getHeldSword(player);
        if (stack.isEmpty()) {
            return;
        }
        HealingSwordItem item = (HealingSwordItem) stack.getItem();
        HealingSwordStages.Stage stage = HealingSwordStages.fromId(HealingSwordState.getStage(stack));
        StageSettings settings = HealingSwordStageData.settings(stage);
        HealingSwordStageContext context = new HealingSwordStageContext(player.level, player, stack, item, settings);
        HealingSwordStageBehavior behavior = HealingSwordStages.behavior(stage);
        behavior.onKill(context, victim);
        if (settings.getKillEnergyGain() > 0) {
            context.addEnergy(settings.getKillEnergyGain());
            context.ensureBounds();
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        PlayerEntity player = event.player;
        if (player.level.isClientSide()) {
            return;
        }
        ItemStack stack = getHeldSword(player);
        if (stack.isEmpty()) {
            HealingSwordAuraManager.clear(player);
            return;
        }
        HealingSwordItem item = (HealingSwordItem) stack.getItem();
        HealingSwordStages.Stage stage = HealingSwordStages.fromId(HealingSwordState.getStage(stack));
        StageSettings settings = HealingSwordStageData.settings(stage);
        HealingSwordStageContext context = new HealingSwordStageContext(player.level, player, stack, item, settings);
        HealingSwordStages.behavior(stage).tick(context);
        HealingSwordAuraManager.tick(player);
    }

    @SubscribeEvent
    public static void onCritical(CriticalHitEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack stack = getHeldSword(player);
        if (stack.isEmpty()) {
            return;
        }
        if (event.isCriticalHit()) {
            CRITICAL_CACHE.add(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPotionApplicable(PotionEvent.PotionApplicableEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        ItemStack stack = getHeldSword(player);
        if (stack.isEmpty()) {
            return;
        }
        if (HealingSwordState.getImmunityTicks(stack) <= 0) {
            return;
        }
        if (event.getPotionEffect().getEffect() == Effects.POISON
                || event.getPotionEffect().getEffect() == Effects.BLINDNESS) {
            event.setResult(net.minecraftforge.eventbus.api.Event.Result.DENY);
        }
    }

    private static ItemStack getHeldSword(PlayerEntity player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof HealingSwordItem) {
            return main;
        }
        ItemStack off = player.getOffhandItem();
        if (off.getItem() instanceof HealingSwordItem) {
            return off;
        }
        return ItemStack.EMPTY;
    }
}
