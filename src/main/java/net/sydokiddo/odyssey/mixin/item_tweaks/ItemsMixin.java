package net.sydokiddo.odyssey.mixin.item_tweaks;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.sydokiddo.odyssey.util.ItemMaxCount;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Steph here,
// Made potions stack to 64, as Jeb's combat update does.
// Feel free to remove this, I know you didn't want it here

@Mixin(Items.class)
abstract class ItemsMixin implements ItemMaxCount {
    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0),
            slice = @Slice( from = @At(value = "NEW", target = "Lnet/minecraft/world/item/PotionItem;")))
    private static int onPotion(int old) {
        return 64;
    }
    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0),
            slice = @Slice( from = @At(value = "NEW", target = "Lnet/minecraft/world/item/SplashPotionItem;")))
    private static int onSplashPotion(int old) {
        return 64;
    }
    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0),
            slice = @Slice( from = @At(value = "NEW", target = "Lnet/minecraft/world/item/LingeringPotionItem;")))
    private static int onLingeringPotion(int old) {
        return 64;
    }
}