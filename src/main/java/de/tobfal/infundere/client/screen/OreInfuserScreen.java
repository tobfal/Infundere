package de.tobfal.infundere.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.menu.OreInfuserMenu;
import de.tobfal.infundere.client.data.ClientOreInfuserData;
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

        ResourceLocation processBackgroundResourceLocation = ClientOreInfuserData.getProcessBackgroundResourceLocation();
        if (processBackgroundResourceLocation != null) {
            pGuiGraphics.blit(processBackgroundResourceLocation, x + 64, y + 6, 0, 0, 48, 48, 48, 48);
        }

        int progress = menu.getScaledProgress();
        ResourceLocation processResourceLocation = ClientOreInfuserData.getProcessResourceLocation();
        if (processResourceLocation != null) {
            pGuiGraphics.blit(processResourceLocation, x + 64, y + 6 + 48 - progress, 0, 48 - progress, 48, progress, 48, 48);
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
