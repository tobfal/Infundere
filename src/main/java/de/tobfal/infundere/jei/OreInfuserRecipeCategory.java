package de.tobfal.infundere.jei;

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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OreInfuserRecipeCategory implements IRecipeCategory<OreInfuserRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Infundere.MODID, "ore_infuser");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Infundere.MODID, "textures/gui/ore_infuser_gui.png");
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
        return guiHelper.createDrawable(TEXTURE, 16, 3, 108, 80);
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ORE_INFUSER.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OreInfuserRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 82, 32).addIngredients(recipe.getContainerIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 24, 0).addItemStack(recipe.getBlockItemIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 68, 0).addItemStack(recipe.output);
    }

    @Override
    public void draw(OreInfuserRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX, double mouseY) {
        oreInfuserBlock.draw(stack, 24, 12);
        oreInfuserBlock.draw(stack, 68, 12);
    }
}
