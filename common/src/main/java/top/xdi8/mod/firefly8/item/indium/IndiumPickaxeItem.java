package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IndiumPickaxeItem extends Item {
    public IndiumPickaxeItem(Properties pProperties) {
        super(IndiumToolMaterial.INDIUM.applyToolProperties(pProperties.durability(54), BlockTags.MINEABLE_WITH_PICKAXE, 1.0F, -2.8F, 0.0F));
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        super.hurtEnemy(pStack, pTarget, pAttacker);
        IndiumToolMaterial.dropNuggets(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (!super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving)) return false;
        IndiumToolMaterial.dropNuggets(pStack, pLevel, pState, pPos, pEntityLiving);
        return true;
    }
}
