package de.tobfal.infundere.jei;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.recipe.OreInfuserRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class InfundereJEIPlugin implements IModPlugin {

    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(Infundere.MODID, "jei_plugin");

    public static final RecipeType<OreInfuserRecipe> ORE_INFUSER_TYPE = new RecipeType<>(OreInfuserRecipeCategory.UID, OreInfuserRecipe.class);

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) {
            return;
        }

        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        List<OreInfuserRecipe> recipes = manager.getAllRecipesFor(OreInfuserRecipe.Type.INSTANCE);
        registration.addRecipes(ORE_INFUSER_TYPE, recipes);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new OreInfuserRecipeCategory(helper));
    }
}
