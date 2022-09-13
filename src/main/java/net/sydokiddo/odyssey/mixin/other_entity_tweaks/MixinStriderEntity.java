package net.sydokiddo.odyssey.mixin.other_entity_tweaks;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Saddles can be un-equipped from Pigs

@Mixin(Strider.class)
public abstract class MixinStriderEntity implements Saddleable {
    @Final
    @Shadow
    private ItemBasedSteering steering;

    @Inject(method = "mobInteract",
            at = @At("HEAD"), cancellable = true)
    public void interactMob(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.isSaddled() && player.isDiscrete() && player.getItemInHand(hand).isEmpty()) {
            steering.setSaddle(false);
            player.setItemInHand(hand, Items.SADDLE.getDefaultInstance());
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}