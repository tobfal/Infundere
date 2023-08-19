package de.tobfal.infundere.datagen;

import de.tobfal.infundere.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        // TODO: Implement OreInfuseRecipes

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COAL_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER)
                .requires(Items.GUNPOWDER)
                .requires(Items.CHARCOAL)
                .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                .save(pWriter);
    }
}
