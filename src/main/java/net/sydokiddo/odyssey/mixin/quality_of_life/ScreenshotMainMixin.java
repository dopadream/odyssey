package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Additional mixin for screenshot to clipboard

@Mixin(Main.class)
public class ScreenshotMainMixin {
    @Inject(method = "main", at = @At("HEAD"), remap = false)
    private static void makeHeadless(CallbackInfo ci) {
        System.setProperty("java.awt.headless", "false");
    }
}