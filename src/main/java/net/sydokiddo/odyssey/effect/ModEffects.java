package net.sydokiddo.odyssey.effect;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.sydokiddo.odyssey.Odyssey;

public class ModEffects {

    public static MobEffect PHASING;

    public static MobEffect registerStatusEffect(String name) {
        return Registry.register(Registry.MOB_EFFECT, new ResourceLocation(Odyssey.MOD_ID, name),
                new PhasingEffect(MobEffectCategory.BENEFICIAL, 55807));
    }

    public static void registerEffects() {
        PHASING = registerStatusEffect("phasing");
    }
}
