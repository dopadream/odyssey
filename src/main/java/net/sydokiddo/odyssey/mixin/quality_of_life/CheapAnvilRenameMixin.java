package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Mixin to make renaming items always cost only 1 XP

@Mixin(value = AnvilMenu.class)
public abstract class CheapAnvilRenameMixin {
    @Shadow @Final private DataSlot cost;
    @Inject(method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/DataSlot;set(I)V",
                    ordinal = 1
            ),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void odyssey$makeRenamingCheap(CallbackInfo ci, ItemStack itemStack, int i, int j, int k) {
        if (k > 0 && k == i) {
            cost.set(1);
        }
    }
}