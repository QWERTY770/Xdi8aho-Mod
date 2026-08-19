package top.xdi8.mod.firefly8.client;

import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.screen.TakeOnlyChestMenu;

@Environment(EnvType.CLIENT)
public class TakeOnlyContainerScreen extends AbstractContainerScreen<TakeOnlyChestMenu> {
    public TakeOnlyContainerScreen(TakeOnlyChestMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, 176, 114 + 6 * 18);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j, 0, 0, this.imageWidth, 6 * 18 + 17, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j + 6 * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
        super.extractContents(guiGraphics, mouseX, mouseY, partialTick);
    }

    private static final Identifier BACKGROUND = ResourceLocationTool.create("textures/gui/container/generic_54.png");
}
