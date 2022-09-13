package net.sydokiddo.odyssey.mixin.item_tweaks;

import net.minecraft.world.item.Item;
import net.sydokiddo.odyssey.util.ItemMaxCount;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Steph here,
// Potions don't seem to stack. Not sure if this is unfinished or if I broke something.

@Mixin(Item.class)
public abstract class ItemMixin implements ItemMaxCount {
    @Mutable
    @Final
    private int maxCount;
    private int vanillaMaxCount;

    @Override
    public void setMaxCount(int i) {
        this.maxCount = i;
    }

    @Override
    public int getVanillaMaxCount() {
        return vanillaMaxCount;
    }

    @Override
    public void setVanillaMaxCount(int vanillaMaxCount) {
        this.vanillaMaxCount = vanillaMaxCount;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void setVanillaMaxCount(Item.Properties settings, CallbackInfo ci) {
        setVanillaMaxCount(this.maxCount);
    }
}