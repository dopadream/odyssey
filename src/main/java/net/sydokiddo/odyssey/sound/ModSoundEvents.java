package net.sydokiddo.odyssey.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;

public class ModSoundEvents {

// Sound Registry:

// - Item Sounds:

    public static final SoundEvent ITEM_ALLAY_BOOK_CAPTURE = registerSoundEvent("item.allay_bound_book.capture");
    public static final SoundEvent ITEM_ALLAY_BOOK_RELEASE = registerSoundEvent("item.allay_bound_book.release");

// Sound Groups:

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Odyssey.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerSounds() {
        System.out.println("Registering Sounds for " + Odyssey.MOD_ID);
    }
}
