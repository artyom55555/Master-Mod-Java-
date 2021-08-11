package com.master.mastermod.core.network;

import com.master.mastermod.MasterMod;
import com.master.mastermod.core.network.message.InputMessage;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

// Для работы нажатия клавиши на серверах
public class MasterModNetwork {
	public static final String NETWORK_VERSION = "0.1.0";

	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MasterMod.MOD_ID, "network"), () -> NETWORK_VERSION,
			version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));
	
	public static void init(){
		
		CHANNEL.registerMessage(0, InputMessage.class, InputMessage::encode, InputMessage::decode, InputMessage::handle);
		//new message
		//CHANNEL.registerMessage(1, InputMessage.class, InputMessage::encode, InputMessage::decode, InputMessage::handle);
	}
}
