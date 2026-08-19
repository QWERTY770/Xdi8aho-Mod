package top.xdi8.mod.firefly8;

import dev.architectury.registry.client.gui.MenuScreenRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;
import top.xdi8.mod.firefly8.client.TakeOnlyContainerScreen;
import top.xdi8.mod.firefly8.client.Xdi8TableScreen;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.screen.FireflyMenus;

public class Firefly8Client implements Runnable {
    public static final ModelLayerLocation CEDAR_BOAT_LAYER =
            new ModelLayerLocation(ResourceLocationTool.create("firefly8:boat/cedar"), "main");
    public static final ModelLayerLocation CEDAR_CHEST_BOAT_LAYER =
            new ModelLayerLocation(ResourceLocationTool.create("firefly8:chest_boat/cedar"), "main");

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        BlockEntityRendererRegistry.register((BlockEntityType) FireflyBlockEntityTypes.REDWOOD_SIGN.get(), StandingSignRenderer::new);
        ColorHandlerRegistry.registerBlockColors(BlockTintSources.foliage(), FireflyBlocks.CEDAR_LEAVES);  // Use BlockTintSources in Minecraft 26.2
        EntityRendererRegistry.register(FireflyEntityTypes.CEDAR_BOAT, context -> new BoatRenderer(context, CEDAR_BOAT_LAYER));
        EntityRendererRegistry.register(FireflyEntityTypes.CEDAR_CHEST_BOAT, context -> new BoatRenderer(context, CEDAR_CHEST_BOAT_LAYER));
        EntityRendererRegistry.register(FireflyEntityTypes.FIREFLY, NoopRenderer::new);
        MenuScreenRegistry.registerScreenFactory(FireflyMenus.TAKE_ONLY_CHEST.get(), TakeOnlyContainerScreen::new);
        MenuScreenRegistry.registerScreenFactory(FireflyMenus.XDI8_TABLE.get(), Xdi8TableScreen::new);
    }
}
