package net.sydokiddo.odyssey.mixin.allay_tweaks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

// This is still a work in progress, trying to fix the booked Allays not being able to detect note blocks bug
// I'm pretty sure it has to do with the 'accepts' line

@Mixin(AllayEntity.class)
public class AllayVibrationMixin extends PathAwareEntity {

    protected AllayVibrationMixin(EntityType<? extends AllayEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "accepts", cancellable = true)
    public void accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, GameEvent.Emitter emitter, CallbackInfoReturnable<Boolean> cir) {
        if (this.world == world) {
                cir.setReturnValue(true);
            } else {
                Optional<GlobalPos> optional = this.brain.getOptionalMemory(MemoryModuleType.LIKED_NOTEBLOCK);
                cir.setReturnValue(optional.isPresent() && optional.get().getDimension() == world.getRegistryKey() && optional.get().getPos().equals(pos));
            }
        }
    }
