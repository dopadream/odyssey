package net.sydokiddo.odyssey.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;

public class ModEffects {

    public static StatusEffect PHASING;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Odyssey.MOD_ID, name),
                new PhasingEffect(StatusEffectCategory.BENEFICIAL, 55807));
    }

    public static void registerEffects() {
        PHASING = registerStatusEffect("phasing");
    }
}
