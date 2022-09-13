package net.sydokiddo.odyssey.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PhasingEffect extends MobEffect {

    public PhasingEffect(MobEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    // Phasing Effect (Highly WIP!)

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Player) {
            //pLivingEntity.noClip = pLivingEntity.hasStatusEffect(ModEffects.PHASING);
            //pLivingEntity.setOnGround(true);
            //pLivingEntity.verticalCollision = true;
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
