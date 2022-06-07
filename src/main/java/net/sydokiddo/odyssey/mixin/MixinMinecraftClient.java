package net.sydokiddo.odyssey.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.MinecraftClient;

// Uncaps the 60 FPS Menu Limit

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(at=@At("HEAD"), method="getFramerateLimit()I", cancellable=true)
    private void getFramerateLimit(CallbackInfoReturnable<Integer> ci) {
            ci.setReturnValue(((MinecraftClient)(Object)this).getWindow().getFramerateLimit());
        }
    }