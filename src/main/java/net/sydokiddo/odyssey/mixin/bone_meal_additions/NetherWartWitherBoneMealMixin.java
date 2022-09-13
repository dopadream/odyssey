package net.sydokiddo.odyssey.mixin.bone_meal_additions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import org.spongepowered.asm.mixin.Mixin;

// Mixin to allow Nether Warts to be fertilized using Withered Bone Meal

@Mixin(NetherWartBlock.class)
public abstract class NetherWartWitherBoneMealMixin extends BushBlock
{
    protected NetherWartWitherBoneMealMixin(Properties settings)
    {
        super(settings);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        ItemStack stack = player.getItemInHand(hand);
        int age = state.getValue(NetherWartBlock.AGE);
        if (stack.getItem() == ModItems.WITHER_BONE_MEAL && age < 3)
        {
            world.setBlock(pos, this.defaultBlockState().setValue(NetherWartBlock.AGE, age + 1), 2);
            world.playSound(null, pos, ModSoundEvents.ITEM_WITHER_BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 1.0f);
            world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            if (!player.isCreative()) stack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }
}