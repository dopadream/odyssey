package net.sydokiddo.odyssey.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;

public class ModSoundEvents {

// Sound Registry:

// - Item Sounds:

    public static final SoundEvent ITEM_ALLAY_BOOK_CAPTURE = registerSoundEvent("item.allay_bound_book.capture");
    public static final SoundEvent ITEM_ALLAY_BOOK_RELEASE = registerSoundEvent("item.allay_bound_book.release");

    public static final SoundEvent ITEM_AMETHYST_DAGGER_BACKSTAB = registerSoundEvent("item.amethyst_dagger.backstab");

    public static final SoundEvent ITEM_COPPER_WRENCH_USE = registerSoundEvent("item.copper_wrench.use");

    public static final SoundEvent ITEM_WITHER_BONE_MEAL_USE = registerSoundEvent("item.wither_bone_meal.use");

    public static final SoundEvent ITEM_FLINTLOCK_LOADING_START = registerSoundEvent("item.flintlock.loading_start");
    public static final SoundEvent ITEM_FLINTLOCK_LOADING_MIDDLE = registerSoundEvent("item.flintlock.loading_middle");
    public static final SoundEvent ITEM_FLINTLOCK_LOADING_END = registerSoundEvent("item.flintlock.loading_end");
    public static final SoundEvent ITEM_FLINTLOCK_SHOOT = registerSoundEvent("item.flintlock.shoot");

// - Block Sounds:

    public static final SoundEvent BLOCK_PAPER_BLOCK_BREAK = registerSoundEvent("block.paper_block.break");
    public static final SoundEvent BLOCK_PAPER_BLOCK_STEP = registerSoundEvent("block.paper_block.step");
    public static final SoundEvent BLOCK_PAPER_BLOCK_PLACE = registerSoundEvent("block.paper_block.place");
    public static final SoundEvent BLOCK_PAPER_BLOCK_HIT = registerSoundEvent("block.paper_block.hit");
    public static final SoundEvent BLOCK_PAPER_BLOCK_FALL = registerSoundEvent("block.paper_block.fall");

    public static final SoundEvent BLOCK_FIREFLY_LANTERN_BREAK = registerSoundEvent("block.firefly_lantern.break");
    public static final SoundEvent BLOCK_FIREFLY_LANTERN_STEP = registerSoundEvent("block.firefly_lantern.step");
    public static final SoundEvent BLOCK_FIREFLY_LANTERN_PLACE = registerSoundEvent("block.firefly_lantern.place");
    public static final SoundEvent BLOCK_FIREFLY_LANTERN_HIT = registerSoundEvent("block.firefly_lantern.hit");
    public static final SoundEvent BLOCK_FIREFLY_LANTERN_FALL = registerSoundEvent("block.firefly_lantern.fall");

// Sound Groups:

    public static final BlockSoundGroup PAPER_BLOCK = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_PAPER_BLOCK_BREAK, ModSoundEvents.BLOCK_PAPER_BLOCK_STEP, ModSoundEvents.BLOCK_PAPER_BLOCK_PLACE,
            ModSoundEvents.BLOCK_PAPER_BLOCK_HIT, ModSoundEvents.BLOCK_PAPER_BLOCK_FALL);

    public static final BlockSoundGroup FIREFLY_LANTERN = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_FIREFLY_LANTERN_BREAK, ModSoundEvents.BLOCK_FIREFLY_LANTERN_STEP, ModSoundEvents.BLOCK_FIREFLY_LANTERN_PLACE,
            ModSoundEvents.BLOCK_FIREFLY_LANTERN_HIT, ModSoundEvents.BLOCK_FIREFLY_LANTERN_FALL);

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Odyssey.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerSounds() {
        System.out.println("Registering Sounds for " + Odyssey.MOD_ID);
    }
}
