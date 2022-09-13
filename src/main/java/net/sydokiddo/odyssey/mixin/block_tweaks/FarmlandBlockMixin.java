package net.sydokiddo.odyssey.mixin.block_tweaks;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Mixin to disable trampling of crops when the player is wearing boots enchanted with Feather Falling

@Mixin(FarmBlock.class)
public class FarmlandBlockMixin extends Block {

    public FarmlandBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FarmBlock;turnToDirt(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"), method="fallOn", cancellable = true)
    public void checkFeatherFallingOnLanding(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo info) {
        if (entity != null) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, (LivingEntity) entity) > 0) {
                FabricLoader.getInstance().isDevelopmentEnvironment();
                super.fallOn(world, state, pos, entity, fallDistance);
                info.cancel();
            }
        }
    }
}