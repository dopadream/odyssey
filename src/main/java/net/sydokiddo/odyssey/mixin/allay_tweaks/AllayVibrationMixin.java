package net.sydokiddo.odyssey.mixin.allay_tweaks;

import net.minecraft.world.entity.ai.Brain;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;

// This is still a work in progress, trying to fix the booked Allays not being able to detect note blocks bug
// I'm pretty sure it has to do with the 'accepts' line

@Mixin(Allay.AllayVibrationListenerConfig.class)
public class AllayVibrationMixin {

    @Shadow @Final private Allay field_39476;
    private Allay allay = this.field_39476;
    private Brain brain = this.allay.getBrain();

    @Inject(at = @At("HEAD"), method = "shouldListen", cancellable = true)
    public void shouldListen(ServerLevel world, GameEventListener listener, BlockPos pos, GameEvent event, GameEvent.Context emitter, CallbackInfoReturnable<Boolean> cir) {
        if (this.allay.level == world) {
                cir.setReturnValue(true);
            } else {
                Optional<GlobalPos> optional = this.brain.getMemory(MemoryModuleType.LIKED_NOTEBLOCK_POSITION);
                cir.setReturnValue(optional.isPresent() && optional.get().dimension() == world.dimension() && optional.get().pos().equals(pos));
            }
        }
    }
