package net.sydokiddo.odyssey.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;

@Environment(EnvType.CLIENT)
@Mixin(InGameOverlayRenderer.class)

public class MixinInGameOverlayRenderer {

    // Hides the Fire overlay in the first person view if an entity or the player has Fire Resistance or is immune to fire

    @Inject(at=@At("HEAD"), method="renderFireOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V", cancellable=true)
    private static void renderFireOverlayHead(MinecraftClient client, MatrixStack stack, CallbackInfo ci) {
        assert client.player != null;
        if (client.player.isInvulnerableTo(DamageSource.ON_FIRE) || client.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            ci.cancel();
        } else {

            // Lowers the fire overlay by a small amount to be less obstructive

            stack.push();
            stack.translate(0, -0.2, 0);
        }
    }
}