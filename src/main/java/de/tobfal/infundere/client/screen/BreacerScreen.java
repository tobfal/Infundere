package de.tobfal.infundere.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.menu.BreacerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BreacerScreen extends AbstractContainerScreen<BreacerMenu> {

    //<editor-fold desc="Constants">
    private static final ResourceLocation TEXTURE = new ResourceLocation(Infundere.MODID, "textures/gui/breacer_gui.png");
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BreacerScreen(BreacerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int progress = menu.getScaledProgress();

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        pGuiGraphics.blit(TEXTURE, x + 84, y + 11 + 64 - progress, 176, 64 - progress, 8, progress);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int x = pMouseX - (width - imageWidth) / 2;
        int y = pMouseY - (height - imageHeight) / 2;
    }
    //</editor-fold>
}
