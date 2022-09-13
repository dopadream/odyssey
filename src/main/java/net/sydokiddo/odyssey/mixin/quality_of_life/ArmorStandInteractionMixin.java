package net.sydokiddo.odyssey.mixin.quality_of_life;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows the player to right-click on an Armor Stand with a Copper Wrench to give it arms

@Mixin(ArmorStand.class)
public abstract class ArmorStandInteractionMixin {

    @Shadow
    protected abstract void setShowArms(boolean showArms);

    @Shadow
    public abstract boolean isShowArms();

    @Inject(at = @At("HEAD"), method = "interactAt", cancellable = true)
    private void interactAt(final Player player, final Vec3 vec3d, final InteractionHand hand, final CallbackInfoReturnable<InteractionResult> info) {
        ItemStack heldStack = player.getMainHandItem();
        Item heldStackItem = heldStack.getItem();

        if (!this.isShowArms() && hand == InteractionHand.MAIN_HAND && heldStackItem == ModItems.COPPER_WRENCH) {
            this.setShowArms(!this.isShowArms());
            player.level.playSound(null, player.blockPosition(), ModSoundEvents.ITEM_COPPER_WRENCH_USE, SoundSource.NEUTRAL, 1.0F, 1.0F);
            player.swing(hand);
            player.gameEvent(GameEvent.ENTITY_INTERACT);
            heldStack.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            info.setReturnValue(InteractionResult.FAIL);
        }
    }
}