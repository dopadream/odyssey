package net.sydokiddo.odyssey.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.util.MobBottleHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract PlayerInventory getInventory();
    @Shadow @Nullable public abstract ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var player = this;
        var held = player.getStackInHand(hand);
        var mobBottle = new ItemStack(Odyssey.bottlesMap.get(entity.getType()));
        if ((held.getItem() == Items.GLASS_BOTTLE) && this.isSneaking()) {

            var nbt = new NbtCompound();
            MobBottleHelper.setCompound(mobBottle, "", entity.writeNbt(nbt));

            if (held.getItem() == Items.GLASS_BOTTLE){
                world.playSound(null, player.getBlockPos(), ModSoundEvents.ITEM_ALLAY_BOTTLE_CAPTURE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                player.setStackInHand(hand, mobBottle);
            } else {
                held.decrement(1);
                addOrDropStack(player, mobBottle);
            }

            player.swingHand(hand);
            entity.discard();
        }
    }

    public void addOrDropStack(PlayerEntityMixin player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack)) {
            player.dropItem(stack, true);
        }
    }
}