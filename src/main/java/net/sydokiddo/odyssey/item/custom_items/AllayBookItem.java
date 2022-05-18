package net.sydokiddo.odyssey.item.custom_items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.util.MobBookHelper;
import org.jetbrains.annotations.Nullable;

public class AllayBookItem extends BucketItem {
    public final EntityType<?> animalType;
    public final String storedMobString;
    public final Fluid fluid;

    public AllayBookItem(EntityType<?> type, Fluid fluid, Settings settings, String storedMobString) {
        super(fluid, settings);
        this.animalType = type;
        this.fluid = fluid;
        this.storedMobString = storedMobString;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null || context.getWorld().isClient)
            return ActionResult.FAIL;

        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Direction facing = context.getSide();
        Hand hand = context.getHand();
        ItemStack held = player.getStackInHand(hand);
        BlockHitResult hitResult = raycast(world, player, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);

        world.playSound(null, player.getBlockPos(), ModSoundEvents.ITEM_ALLAY_BOOK_RELEASE, SoundCategory.NEUTRAL, 1.0F, 1.0F);

        if (!world.isClient) {
            double x = pos.getX() + 0.5F + facing.getOffsetX();
            double y = pos.getY() + 0.25F + (world.random.nextFloat() / 2.0F) + facing.getOffsetY();
            double z = pos.getZ() + 0.5F + facing.getOffsetZ();
            BlockPos spawnPos = new BlockPos(x, y, z);

            // Spawns Allay

            Entity mob = MobBookHelper.spawn(animalType, (ServerWorld)world, spawnPos, SpawnReason.BUCKET);
            if (mob != null) {

                NbtCompound data = MobBookHelper.getCompound(held, storedMobString);

                if (!data.isEmpty())
                    mob.readCustomDataFromNbt(data);

                if (data.isEmpty())
                    mob.readCustomDataFromNbt(data);

                if (context.getStack().hasCustomName()) {
                    mob.setCustomName(context.getStack().getName());
                }

                this.placeFluid(player, world, pos, hitResult);
                world.spawnEntity(mob);
                player.getItemCooldownManager().set(this, 30);
            }
        }
        player.swingHand(hand);

        if (!player.isCreative())
            player.setStackInHand(hand, new ItemStack(Items.BOOK));

        return ActionResult.SUCCESS;
    }

    @Override
    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        world.playSound(player, pos, ModSoundEvents.ITEM_ALLAY_BOOK_RELEASE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }
}