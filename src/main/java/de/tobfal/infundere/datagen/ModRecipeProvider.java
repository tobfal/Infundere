package de.tobfal.infundere.datagen;

import de.tobfal.infundere.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {

        //<editor-fold desc="OreInfuser">
        OreInfuserRecipeBuilder.infusing(ModItems.COAL_INFUSION_POWDER.get(), Blocks.STONE, Blocks.COAL_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.COAL_INFUSION_POWDER.get()), has(ModItems.COAL_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.COAL_INFUSION_POWDER.get(), Blocks.DEEPSLATE, Blocks.DEEPSLATE_COAL_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.COAL_INFUSION_POWDER.get()), has(ModItems.COAL_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.IRON_INFUSION_POWDER.get(), Blocks.STONE, Blocks.IRON_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.IRON_INFUSION_POWDER.get()), has(ModItems.IRON_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.IRON_INFUSION_POWDER.get(), Blocks.DEEPSLATE, Blocks.DEEPSLATE_IRON_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.IRON_INFUSION_POWDER.get()), has(ModItems.IRON_INFUSION_POWDER.get()))
                .save(pWriter);
        //</editor-fold>

        //<editor-fold desc="Shapeless">
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COAL_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER)
                .requires(Items.GUNPOWDER)
                .requires(Items.CHARCOAL)
                .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IRON_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER)
                .requires(Items.GUNPOWDER)
                .requires(Items.COAL)
                .requires(Items.BONE_MEAL)
                .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
                .save(pWriter);
        //</editor-fold>
    }
}
