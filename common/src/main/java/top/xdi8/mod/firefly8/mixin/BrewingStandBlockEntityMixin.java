package top.xdi8.mod.firefly8.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xdi8.mod.firefly8.item.tint.ItemTinting;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewingRecipe;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {
    @Inject(method = "isBrewable", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionBrewing;hasMix(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"),
            cancellable = true)
    private static void isBrewable(PotionBrewing potionBrewing, NonNullList<ItemStack> items, CallbackInfoReturnable<Boolean> cir,
                                   @Local(name = "ingredient") ItemStack ingredient, @Local(name = "itemStack") ItemStack itemStack) {
        if (!itemStack.isEmpty() && TintedPotionBrewingRecipe.isInput(itemStack) &&
                potionBrewing.hasMix(ItemTinting.unTint(itemStack), ingredient)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "doBrew", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionBrewing;mix(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;"))
    private static void doBrew(Level level, BlockPos pos, NonNullList<ItemStack> items, CallbackInfo ci,
                               @Local(name = "ingredient") ItemStack ingredient, @Local(name = "dest") int dest) {
        ItemStack input = items.get(dest);
        if (TintedPotionBrewingRecipe.isInput(input)) {
            items.set(dest, ItemTinting.tint(level.potionBrewing().mix(ingredient, ItemTinting.unTint(input))));
        }
    }
}
