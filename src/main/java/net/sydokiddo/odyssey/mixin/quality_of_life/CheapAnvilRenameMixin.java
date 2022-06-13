package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Mixin to make renaming items always cost only 1 XP

@Mixin(value = AnvilScreenHandler.class)
public abstract class CheapAnvilRenameMixin {
    @Shadow @Final private Property levelCost;
    @Inject(method = "updateResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/Property;get()I",
                    ordinal = 1
            ),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void odyssey$makeRenamingCheap(CallbackInfo ci, ItemStack itemStack, int i, int j, int k) {
        if (k > 0 && k == i) {
            levelCost.set(1);
        }
    }
}