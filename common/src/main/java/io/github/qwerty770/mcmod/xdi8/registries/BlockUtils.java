package io.github.qwerty770.mcmod.xdi8.registries;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.annotation.StableApi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

@StableApi(since = "3.0.0-beta1")
public class BlockUtils {
    public static RegistrySupplier<Block> createPotted(String id, RegistrySupplier<Block> inside) {
        return RegistryHelper.block(id, (properties) -> new FlowerPotBlock(inside.get(), properties),
                BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    }

    public static RegistrySupplier<Block> createLeaves(String id) {
        return RegistryHelper.block(id, p -> new TintedParticleLeavesBlock(0.01F, p),
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.PLANT)
                        .strength(0.2f)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isValidSpawn(BlockUtils::ocelotOrParrot)
                        .isSuffocating(BlockUtils::never)
                        .isViewBlocking(BlockUtils::never)
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
                        .isRedstoneConductor(BlockUtils::never));
    }

    public static BlockBehaviour.Properties woodenBlock() {
        return BlockBehaviour.Properties.of().mapColor(FireflyBlocks.redwoodColor).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD);
    }

    // private methods from net.minecraft.world.level.block.Blocks
    private static Boolean ocelotOrParrot(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return entityType == EntityTypes.OCELOT || entityType == EntityTypes.PARROT;
    }

    private static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
}
