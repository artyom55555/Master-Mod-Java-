package com.master.mastermod.core.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import org.lwjgl.glfw.GLFW;

// Нужен для отображения дополнительной надписи предмета, когда нажат shift
public class Forge16MasterKeyboardUtil {

    private static final long MINECRAFT_WINDOW = Minecraft.getInstance().getWindow().getWindow();

    public static boolean isHoldingLeftShift() {
        return InputMappings.isKeyDown(MINECRAFT_WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT);
    }
}

