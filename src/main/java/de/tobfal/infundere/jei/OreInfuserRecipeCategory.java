package de.tobfal.infundere.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.init.ModBlocks;
import de.tobfal.infundere.recipe.OreInfuserRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.model.EmptyModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fml.earlydisplay.ElementShader;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class OreInfuserRecipeCategory implements IRecipeCategory<OreInfuserRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Infundere.MODID, "ore_infuser");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Infundere.MODID, "textures/gui/ore_infuser_recipe_gui.png");
    protected final IDrawable oreInfuserBlock;
    private final IGuiHelper guiHelper;

    public OreInfuserRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        oreInfuserBlock = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.ORE_INFUSER.get().asItem()));
    }

    @NotNull
    @Override
    public RecipeType<OreInfuserRecipe> getRecipeType() {
        return InfundereJEIPlugin.ORE_INFUSER_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.literal("Ore Infuser");
    }

    @NotNull
    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(TEXTURE, 0, -15, 128, 55);
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ORE_INFUSER.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OreInfuserRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 11 + 1, 19 + 1).addIngredients(recipe.getContainerIngredient());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStack(recipe.getBlockItemIngredient());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(recipe.result);
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addItemStack(new ItemStack(ModBlocks.ORE_INFUSER.get()));
    }

    @Override
    public void draw(OreInfuserRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX, double mouseY) {
        PoseStack poseStack = stack.pose();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

        Quaternionf quaternion = new Quaternionf();
        quaternion.rotationXYZ(30 * Mth.DEG_TO_RAD, 45 * Mth.DEG_TO_RAD, 0);

        // OreInfuserBlock
        poseStack.pushPose();
        poseStack.translate(53.5, 43, 128 - 32);
        poseStack.scale(16, -16, 16);
        poseStack.mulPose(quaternion);
        dispatcher.renderSingleBlock(ModBlocks.ORE_INFUSER.get().defaultBlockState(), poseStack, bufferSource,
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.cutoutMipped());
        poseStack.popPose();

        // IngredientBlock
        poseStack.pushPose();
        poseStack.translate(53.5, 29.1, 128);
        poseStack.scale(16,-16,-16);
        poseStack.mulPose(quaternion);
        dispatcher.renderSingleBlock(recipe.getIngredientBlock().defaultBlockState(), poseStack, bufferSource,
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
        poseStack.popPose();

        // OreInfuserBlock
        poseStack.pushPose();
        poseStack.translate(98.5, 43, 128 - 32);
        poseStack.scale(16, -16, 16);
        poseStack.mulPose(quaternion);
        dispatcher.renderSingleBlock(ModBlocks.ORE_INFUSER.get().defaultBlockState(), poseStack, bufferSource,
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.cutoutMipped());
        poseStack.popPose();

        // IngredientBlock
        poseStack.pushPose();
        poseStack.translate(98.5, 29.1, 128);
        poseStack.scale(16,-16,-16);
        poseStack.mulPose(quaternion);
        dispatcher.renderSingleBlock(recipe.getResultBlock().defaultBlockState(), poseStack, bufferSource,
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
        poseStack.popPose();

        bufferSource.endBatch();
    }

}
