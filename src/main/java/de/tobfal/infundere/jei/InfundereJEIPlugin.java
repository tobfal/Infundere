package de.tobfal.infundere.jei;

import de.tobfal.infundere.Infundere;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

@JeiPlugin
public class InfundereJEIPlugin implements IModPlugin {

    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(Infundere.MODID, "jei_plugin");

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @SuppressWarnings("resource")
    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) {
            return;
        }

        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
    }

    private static Collection<?> getRecipes(RecipeManager manager, RecipeType<?> type) {
        return manager.getRecipes().parallelStream().filter(recipe -> recipe.getType() == type).collect(Collectors.toList());
    }
}
