package net.sydokiddo.odyssey.item.custom_items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.util.MobBookHelper;

public class AllayBookItem extends Item {
    public final EntityType<Allay> animalType;
    public final String storedMobString;
    public Level world;

    public AllayBookItem(EntityType<Allay> type, Properties settings, String storedMobString) {
        super(settings);
        this.animalType = type;
        this.storedMobString = storedMobString;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null || context.getLevel().isClientSide)
            return InteractionResult.FAIL;

        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        InteractionHand hand = context.getHand();
        ItemStack held = player.getItemInHand(hand);

        world.playSound(null, player.blockPosition(), ModSoundEvents.ITEM_ALLAY_BOOK_RELEASE, SoundSource.NEUTRAL, 1.0F, 1.0F);
        player.gameEvent(GameEvent.BLOCK_PLACE);

        if (!world.isClientSide) {
            double x = pos.getX() + 0.5F + facing.getStepX();
            double y = pos.getY() + 0.25F + (world.random.nextFloat() / 2.0F) + facing.getStepY();
            double z = pos.getZ() + 0.5F + facing.getStepZ();
            BlockPos spawnPos = new BlockPos(x, y, z);

            // Spawns Allay

            Allay mob = MobBookHelper.spawn(animalType, (ServerLevel)world, spawnPos, MobSpawnType.MOB_SUMMONED);
            if (mob != null) {

                CompoundTag data = MobBookHelper.getCompound(held, storedMobString);

                mob.readAdditionalSaveData(data);
                }

                if (context.getItemInHand().hasCustomHoverName()) {
                    assert mob != null;
                    mob.setCustomName(context.getItemInHand().getHoverName());
                }

                world.addFreshEntity(mob);
                player.getCooldowns().addCooldown(this, 30);
            }
        player.swing(hand);

        if (!player.isCreative())
            player.setItemInHand(hand, new ItemStack(Items.BOOK));

        return InteractionResult.SUCCESS;
    }
}
