package de.tobfal.infundere.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.menu.OreInfuserMenu;
import de.tobfal.infundere.client.data.ClientOreInfuserData;
import de.tobfal.infundere.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.searchtree.ResourceLocationSearchTree;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.lang.management.MonitorInfo;
import java.util.Optional;
import java.util.Random;

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

        ResourceLocation resourceLocation = ClientOreInfuserData.getResourceLocation();
        if (resourceLocation != null) {
            pGuiGraphics.blit(resourceLocation, x + 64, y + 6, 0, 0, 48, 48, 48,48);
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
