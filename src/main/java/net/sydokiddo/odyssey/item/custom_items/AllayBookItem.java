package net.sydokiddo.odyssey.item.custom_items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.util.MobBookHelper;

public class AllayBookItem extends Item {
    public final EntityType<AllayEntity> animalType;
    public final String storedMobString;
    public World world;

    public AllayBookItem(EntityType<AllayEntity> type, Settings settings, String storedMobString) {
        super(settings);
        this.animalType = type;
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

        world.playSound(null, player.getBlockPos(), ModSoundEvents.ITEM_ALLAY_BOOK_RELEASE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        player.emitGameEvent(GameEvent.BLOCK_PLACE);

        if (!world.isClient) {
            double x = pos.getX() + 0.5F + facing.getOffsetX();
            double y = pos.getY() + 0.25F + (world.random.nextFloat() / 2.0F) + facing.getOffsetY();
            double z = pos.getZ() + 0.5F + facing.getOffsetZ();
            BlockPos spawnPos = new BlockPos(x, y, z);

            // Spawns Allay

            AllayEntity mob = MobBookHelper.spawn(animalType, (ServerWorld)world, spawnPos, SpawnReason.MOB_SUMMONED);
            if (mob != null) {

                NbtCompound data = MobBookHelper.getCompound(held, storedMobString);

                mob.readCustomDataFromNbt(data);
                }

                if (context.getStack().hasCustomName()) {
                    assert mob != null;
                    mob.setCustomName(context.getStack().getName());
                }

                world.spawnEntity(mob);
                player.getItemCooldownManager().set(this, 30);
            }
        player.swingHand(hand);

        if (!player.isCreative())
            player.setStackInHand(hand, new ItemStack(Items.BOOK));

        return ActionResult.SUCCESS;
    }
}
