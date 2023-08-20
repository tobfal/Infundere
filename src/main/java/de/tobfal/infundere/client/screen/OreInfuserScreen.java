package de.tobfal.infundere.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import de.tobfal.infundere.block.menu.OreInfuserMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class OreInfuserScreen extends AbstractContainerScreen<OreInfuserMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Infundere.MODID, "textures/gui/ore_infuser_gui.png");

    public OreInfuserScreen(OreInfuserMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        OreInfuserBlockEntity blockEntity = menu.getBlockEntity();
        int processBarX = 91;
        int processBarY = 18;
        int processBarWidth = 48;
        int processBarHeight = 48;

        ResourceLocation processBackgroundResourceLocation = blockEntity.processBackgroundResourceLocation;
        if (processBackgroundResourceLocation != null) {
            pGuiGraphics.blit(processBackgroundResourceLocation, x + processBarX, y + processBarY, 0, 0, processBarWidth, processBarHeight, 48, 48);
        }

        int progress = menu.getScaledProgress();
        ResourceLocation processResourceLocation = blockEntity.processResourceLocation;
        if (processResourceLocation != null) {
            pGuiGraphics.blit(processResourceLocation, x + processBarX, y + processBarY + 48 - progress, 0, 48 - progress, processBarWidth, progress, processBarWidth, processBarHeight);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int x = pMouseX - (width - imageWidth) / 2;
        int y = pMouseY - (height - imageHeight) / 2;
    }
}
