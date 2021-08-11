package com.master.mastermod.common.events;

import com.master.mastermod.MasterMod;
import com.master.mastermod.core.init.KeybindsInit;
import com.master.mastermod.core.network.MasterModNetwork;
import com.master.mastermod.core.network.message.InputMessage;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

// Класс для события по нажатию клавиши
@EventBusSubscriber(modid = MasterMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {
	
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.level == null) return;
		onInput(mc, event.getKey(), event.getAction());
	}
	
	@SubscribeEvent
	public static void onMouseClick(InputEvent.MouseInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.level == null) return;
		onInput(mc, event.getButton(), event.getAction());
	}
	
	private static void onInput(Minecraft mc, int key, int action) {
		if (mc.screen == null && KeybindsInit.exampleKey.consumeClick()) {
			MasterModNetwork.CHANNEL.sendToServer(new InputMessage(key));
		}
	}
}
