package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.network.chat.Component;
import net.sydokiddo.odyssey.util.ClipboardImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import javax.swing.*;
import com.mojang.blaze3d.platform.NativeImage;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;

// Copies screenshot to clipboard upon taking a screenshot

@Mixin(Screenshot.class)
public abstract class ScreenshotRecorderMixin {
    @Inject(method = "method_1661", at = @At("TAIL"))
    private static void onScreenshot(NativeImage image, File file, Consumer<Component> messageReceiver, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        try {
            File path = new File(mc.gameDirectory.getAbsolutePath() + "\\screenshots\\");
            Optional<Path> lastFilePath = Files.list(path.toPath())
            .filter(f -> !Files.isDirectory(f))
            .max(Comparator.comparingLong(f -> f.toFile().lastModified()));
            Image lastScreen = new ImageIcon(lastFilePath.get().toString()).getImage();
            ClipboardImage newImage = new ClipboardImage(lastScreen);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(newImage, null);

            mc.gui.getChat().addMessage(Component.nullToEmpty("Screenshot copied to clipboard"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}