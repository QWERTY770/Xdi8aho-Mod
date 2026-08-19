package top.xdi8.mod.firefly8.client;

import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.screen.Xdi8TableMenu;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class Xdi8TableScreen extends AbstractContainerScreen<Xdi8TableMenu> {
    public Xdi8TableScreen(Xdi8TableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, 176, 166);
        inventoryLabelY = imageHeight - 94;
    }
    // Button Size: (34, 16)

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
        if (isOnButton(mouseX, mouseY)) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos + 129, topPos + 36, 176, 16, 34, 16, 256, 256);
        } else {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos + 129, topPos + 36, 176, 0, 34, 16, 256, 256);
        }
        FormattedCharSequence cs = Component.translatable("gui.ok").getVisualOrderText();
        final int width1 = (font.width(cs)) >> 1;
        final int start = leftPos + 129 + 17 - width1;
        guiGraphics.text(font, cs, start, topPos + 40, 0xffffff);
        super.extractContents(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
        if (isOnButton(mouseButtonEvent.x(), mouseButtonEvent.y())) {
            this.menu.clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), 0);
            Objects.requireNonNull(this.minecraft.gameMode)
                    .handleInventoryButtonClick(menu.containerId, 0);
            return true;
        }
        return super.mouseClicked(mouseButtonEvent, doubleClick);
    }

    private static final Identifier BACKGROUND = ResourceLocationTool.create("firefly8",
            "textures/menu/xdi8_table.png");

    private boolean isOnButton(double mouseX, double mouseY) {
        final int startX = leftPos + 129, startY = topPos + 36;
        final int endX = startX + 34, endY = startY + 16;
        return startX <= mouseX && mouseX <= endX &&
               startY <= mouseY && startY <= endY;
    }
}
