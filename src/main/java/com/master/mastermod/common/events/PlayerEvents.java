package com.master.mastermod.common.events;

import com.master.mastermod.MasterMod;
import com.master.mastermod.core.init.BlocksInit;
import com.master.mastermod.core.init.ItemsInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

// Класс событий, происходящих с игроком
// ВСе методы должны быть public static void и иметь один параметр ...Event
@EventBusSubscriber(modid = MasterMod.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	private static boolean isFisrtBrokeBlock = true;

	// События, выполняющиеся каждый тик
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		World world = player.getCommandSenderWorld();
		BlockState state = world.getBlockState(player.blockPosition().below());
		
		// Накладывать замедленное падение, если держишь парашют
		if (player.getItemInHand(Hand.MAIN_HAND).getItem() == ItemsInit.PARACHUTE.get()) {
			player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 3, 1));
		}
		
		// Накладывать слепоту, если стоишь на тёмной руде
		if (state.getBlock() == BlocksInit.DARK_ORE.get()) {
			player.addEffect(new EffectInstance(Effects.BLINDNESS, 100, 1));
		}
		
		// Уничтожать блок облака, если ходишь по нему без шифта
		if (state.getBlock() == BlocksInit.CLOUD_BLOCK.get()) {
			if(!player.isShiftKeyDown())
				world.destroyBlock(player.blockPosition().below(), false);
		}
		
		// Накладывать скорость и прыгучесть, если надеты сапоги путешественника
		if (player.inventory.armor.get(0).getItem() == ItemsInit.TRAVELER_DARK_BOOTS.get()) {
			player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 3, 1));
			player.addEffect(new EffectInstance(Effects.JUMP, 3, 1));
		}
	}

	// При выбрасывании предмета, стоя на блоке игрок получает предмет (свой вид крафтов)
	@SubscribeEvent
	public static void onPlayerToss(ItemTossEvent event) {
		PlayerEntity player = event.getPlayer();
		World world = player.getCommandSenderWorld();
		BlockState state = world.getBlockState(player.blockPosition().below());
		Item item = event.getEntityItem().getItem().getItem();

		if (state.getBlock() == Blocks.MAGMA_BLOCK && item == Items.SNOW_BLOCK) {
			player.addItem(ItemsInit.CLOUD_BLOCK.get().getDefaultInstance());
			event.getEntity().remove();
		}
	}

	// Событие при ломании определённого блока
	@SubscribeEvent
	public static void onBlockBreak(final BlockEvent.BreakEvent event) {
		PlayerEntity player = event.getPlayer();
		ITextComponent msg = new StringTextComponent("Вы прикоснулись ко тьме, " + player.getName().getContents() + ".")
				.withStyle(TextFormatting.DARK_PURPLE);
		
		// Если сломал блок тёмной руды в первй раз, то вывести сообщение
		if (isFisrtBrokeBlock && event.getState().getBlock() == BlocksInit.DARK_ORE.get()) {
			player.sendMessage(msg, player.getUUID());
			isFisrtBrokeBlock = false;
		}
	}
}
