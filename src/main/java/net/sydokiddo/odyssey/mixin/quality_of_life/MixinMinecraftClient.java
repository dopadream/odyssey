package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Uncaps the 60 FPS Menu Limit

@Mixin(Minecraft.class)
public class MixinMinecraftClient {

    @Inject(at=@At("HEAD"), method="getFramerateLimit()I", cancellable=true)
    private void getFramerateLimit(CallbackInfoReturnable<Integer> ci) {
            ci.setReturnValue(((Minecraft)(Object)this).getWindow().getFramerateLimit());
        }
    }