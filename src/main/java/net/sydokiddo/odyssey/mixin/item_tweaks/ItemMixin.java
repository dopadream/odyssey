package net.sydokiddo.odyssey.mixin.item_tweaks;

import net.minecraft.world.item.Item;
import net.sydokiddo.odyssey.util.ItemMaxCount;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Item.class)
public class ItemMixin implements ItemMaxCount {

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
