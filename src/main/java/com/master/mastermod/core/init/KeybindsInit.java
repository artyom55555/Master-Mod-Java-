package com.master.mastermod.core.init;

import java.awt.event.KeyEvent;
import com.master.mastermod.MasterMod;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

// Класс для создания привязок клавиш
@OnlyIn(Dist.CLIENT)
public class KeybindsInit {
	public static KeyBinding exampleKey;
	
	public static void register(final FMLClientSetupEvent event) {
		// Регистрация привязки клавиши к кнопке G
		exampleKey = create("example_key", KeyEvent.VK_G);
		
		ClientRegistry.registerKeyBinding(exampleKey);
	}
	
	private static KeyBinding create(String name, int key) {
		return new KeyBinding("key." + MasterMod.MOD_ID + "." + name, key, "key.category." + MasterMod.MOD_ID);
	}
}
