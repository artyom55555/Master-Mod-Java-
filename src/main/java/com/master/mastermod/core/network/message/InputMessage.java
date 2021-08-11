package com.master.mastermod.core.network.message;

import java.util.function.Supplier;

import com.master.mastermod.core.init.ItemsInit;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

// При нажатии клавиши игрок умирает, спавнится лава и даётся гриб
public class InputMessage {
	public int key;

	public InputMessage() {

	}

	public InputMessage(int key) {

	}

	public static void encode(InputMessage message, PacketBuffer buffer) {
		buffer.writeInt(message.key);
	}

	public static InputMessage decode(PacketBuffer buffer) {
		return new InputMessage(buffer.readInt());
	}

	public static void handle(InputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			player.addItem(new ItemStack(ItemsInit.DARK_MUSHROOM.get()));

			World world = player.getCommandSenderWorld();
			world.setBlock(player.blockPosition().below(), Blocks.LAVA.defaultBlockState(), 3);
			player.die(null);
			System.out.println(message.key);
		});
		context.setPacketHandled(true);
	}
}
