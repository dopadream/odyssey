package net.sydokiddo.odyssey.item.custom_items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
// import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.sound.ModSoundEvents;

public class AmethystDaggerItem extends ToolItem implements Vanishable {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public AmethystDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, settings);
        float attackDamage1 = (float) attackDamage + toolMaterial.getAttackDamage();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage1, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
//        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier("Attack range", -1.0, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();

    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        // Cancels knockback to balance it out

        Vec3d vec3d = attacker.getRotationVector();
        target.velocityModified = true;
        target.addVelocity(-vec3d.x * 0.5, -0.25, -vec3d.z * 0.5);

        // Backstabbing Bonus Damage

        boolean isBehind = attacker.getRotationVector().dotProduct(target.getRotationVector()) > 0.8;

        if (isBehind) {
            target.timeUntilRegen = 0;
            target.damage(new Odyssey.BackstabDamageSource(attacker), (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
            target.playSound(ModSoundEvents.ITEM_AMETHYST_DAGGER_BACKSTAB, 1.0F, 0.5F);
        }

        // Amethyst Dagger only has half the invulnerability time of a Sword

        target.timeUntilRegen = 10;

        // Damages the Amethyst Dagger upon use

        stack.damage(1, attacker, (player) -> player.sendToolBreakStatus(Hand.MAIN_HAND));
        return true;
    }

        // Damages the Amethyst Sickle by a value of 2 if attempted to mine with it

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0F) {
            stack.damage(2, miner, (player) -> player.sendToolBreakStatus(Hand.MAIN_HAND));
        }

        return true;
    }

        // Lets players mine Cobwebs quickly with the Amethyst Dagger

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }
}