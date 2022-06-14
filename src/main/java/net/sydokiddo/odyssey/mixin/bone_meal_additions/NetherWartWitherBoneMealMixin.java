package net.sydokiddo.odyssey.mixin.bone_meal_additions;

import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import org.spongepowered.asm.mixin.Mixin;

// Mixin to allow Nether Warts to be fertilized using Withered Bone Meal

@Mixin(NetherWartBlock.class)
public abstract class NetherWartWitherBoneMealMixin extends PlantBlock
{
    protected NetherWartWitherBoneMealMixin(Settings settings)
    {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        ItemStack stack = player.getStackInHand(hand);
        int age = state.get(NetherWartBlock.AGE);
        if (stack.getItem() == ModItems.WITHER_BONE_MEAL && age < 3)
        {
            world.setBlockState(pos, this.getDefaultState().with(NetherWartBlock.AGE, age + 1), 2);
            world.playSound(null, pos, ModSoundEvents.ITEM_WITHER_BONE_MEAL_USE, SoundCategory.BLOCKS, 1f, 1.0f);
            world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            if (!player.isCreative()) stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}