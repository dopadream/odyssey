package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Fixes the hardcoded 6 tick mining cooldown and sets it to 0

// Steph here,
// Why?

@Mixin(MultiPlayerGameMode.class)
public abstract class MiningCooldownFixMixin {
    @ModifyConstant(method = "continueDestroyBlock", constant = @Constant(intValue = 5))
    private int MiningCooldownFix(int value) {
        return 0;
    }
}