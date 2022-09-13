package net.sydokiddo.odyssey.item.custom_items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.sound.ModSoundEvents;

public class AmethystDaggerItem extends TieredItem implements Vanishable {
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public AmethystDaggerItem(Tier toolMaterial, int attackDamage, float attackSpeed, Item.Properties settings) {
        super(toolMaterial, settings);
        float attackDamage1 = (float) attackDamage + toolMaterial.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage1, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
//        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier("Attack range", -1.0, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();

    }

    // Prevents the player from breaking blocks with the Amethyst Dagger while in Creative Mode

    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player miner) {
        return !miner.isCreative();
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    // Cancels knockback for balancing purposes

        Vec3 vec3d = attacker.getLookAngle();
        target.hurtMarked = true;
        target.push(-vec3d.x * 0.5, -0.25, -vec3d.z * 0.5);

    // Backstabbing Bonus Damage

        boolean isBehind = attacker.getLookAngle().dot(target.getLookAngle()) > 0.8;

        if (isBehind) {
            target.invulnerableTime = 0;
            target.hurt(new Odyssey.BackstabDamageSource(attacker), (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
            target.playSound(ModSoundEvents.ITEM_AMETHYST_DAGGER_BACKSTAB, 1.0F, 0.8F);
        }

    // Amethyst Dagger only has half the invulnerability time of a Sword

        target.invulnerableTime = 10;

    // Damages the Amethyst Dagger upon use

        stack.hurtAndBreak(1, attacker, (player) -> player.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        return true;
    }

    // Damages the Amethyst Dagger by a value of 2 if attempted to mine with it

    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(2, miner, (player) -> player.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }

        return true;
    }

    // Lets players mine Cobwebs quickly with the Amethyst Dagger

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}