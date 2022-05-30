package net.sydokiddo.odyssey.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class PhasingEffect extends StatusEffect {

    public PhasingEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    // Phasing Effect (Highly WIP!)

    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof PlayerEntity) {
            //pLivingEntity.noClip = pLivingEntity.hasStatusEffect(ModEffects.PHASING);
            //pLivingEntity.setOnGround(true);
            //pLivingEntity.verticalCollision = true;
        }

        super.applyUpdateEffect(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
        return true;
    }
}
