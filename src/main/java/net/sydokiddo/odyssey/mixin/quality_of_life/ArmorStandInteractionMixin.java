package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows the player to right-click on an Armor Stand with a Copper Wrench to give it arms

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandInteractionMixin {

    @Shadow
    protected abstract void setShowArms(boolean showArms);

    @Shadow
    public abstract boolean shouldShowArms();

    @Inject(at = @At("HEAD"), method = "interactAt", cancellable = true)
    private void interactAt(final PlayerEntity player, final Vec3d vec3d, final Hand hand, final CallbackInfoReturnable<ActionResult> info) {
        ItemStack heldStack = player.getMainHandStack();
        Item heldStackItem = heldStack.getItem();

        if (!this.shouldShowArms() && hand == Hand.MAIN_HAND && heldStackItem == ModItems.COPPER_WRENCH) {
            this.setShowArms(!this.shouldShowArms());
            player.world.playSound(null, player.getBlockPos(), ModSoundEvents.ITEM_COPPER_WRENCH_USE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            player.swingHand(hand);
            info.setReturnValue(ActionResult.FAIL);
        }
    }
}