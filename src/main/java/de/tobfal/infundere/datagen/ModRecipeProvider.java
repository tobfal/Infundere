package de.tobfal.infundere.datagen;

import de.tobfal.infundere.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

        OreInfuserRecipeBuilder.infusing(ModItems.COPPER_INFUSION_POWDER.get(), Blocks.STONE, Blocks.COPPER_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.COPPER_INFUSION_POWDER.get()), has(ModItems.COPPER_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.COPPER_INFUSION_POWDER.get(), Blocks.DEEPSLATE, Blocks.DEEPSLATE_COPPER_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.COPPER_INFUSION_POWDER.get()), has(ModItems.COPPER_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.IRON_INFUSION_POWDER.get(), Blocks.STONE, Blocks.IRON_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.IRON_INFUSION_POWDER.get()), has(ModItems.IRON_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.IRON_INFUSION_POWDER.get(), Blocks.DEEPSLATE, Blocks.DEEPSLATE_IRON_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.IRON_INFUSION_POWDER.get()), has(ModItems.IRON_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.GOLD_INFUSION_POWDER.get(), Blocks.STONE, Blocks.GOLD_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.GOLD_INFUSION_POWDER.get()), has(ModItems.GOLD_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.GOLD_INFUSION_POWDER.get(), Blocks.DEEPSLATE, Blocks.DEEPSLATE_GOLD_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.GOLD_INFUSION_POWDER.get()), has(ModItems.GOLD_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.GOLD_INFUSION_POWDER.get(), Blocks.NETHERRACK, Blocks.NETHER_GOLD_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.GOLD_INFUSION_POWDER.get()), has(ModItems.GOLD_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.GLOWSTONE_INFUSION_POWDER.get(), Blocks.GLASS, Blocks.GLOWSTONE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.GLOWSTONE_INFUSION_POWDER.get()), has(ModItems.GLOWSTONE_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.REDSTONE_INFUSION_POWDER.get(), Blocks.STONE, Blocks.REDSTONE_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.REDSTONE_INFUSION_POWDER.get()), has(ModItems.REDSTONE_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.REDSTONE_INFUSION_POWDER.get(), Blocks.STONE, Blocks.DEEPSLATE_REDSTONE_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.REDSTONE_INFUSION_POWDER.get()), has(ModItems.REDSTONE_INFUSION_POWDER.get()))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(ModItems.NETHER_QUARTZ_INFUSION_POWDER.get(), Blocks.NETHERRACK, Blocks.NETHER_QUARTZ_ORE, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(ModItems.NETHER_QUARTZ_INFUSION_POWDER.get()), has(ModItems.NETHER_QUARTZ_INFUSION_POWDER.get()))
                .save(pWriter);


        OreInfuserRecipeBuilder.infusing(Items.COAL, Blocks.STONE, Blocks.NETHERRACK, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(Items.BONE_MEAL, Blocks.STONE, Blocks.GRAVEL, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(Items.BONE_MEAL), has(Items.BONE_MEAL))
                .save(pWriter);

        OreInfuserRecipeBuilder.infusing(Items.BONE_MEAL, Blocks.GRAVEL, Blocks.SAND, RecipeCategory.MISC, 100)
                .unlockedBy(getHasName(Items.GRAVEL), has(Items.GRAVEL))
                .save(pWriter);
        //</editor-fold>

        //<editor-fold desc="Shaped">
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ORE_INFUSER.get())
                .define('S', Items.STONE)
                .define('s', Items.SMOOTH_STONE_SLAB)
                .define('t', Items.STICK)
                .pattern("SSS")
                .pattern("SsS")
                .pattern("StS")
                .unlockedBy(getHasName(Items.STONE), has(Items.STONE))
                .save(pWriter);
        //</editor-fold>

        //<editor-fold desc="Shapeless">
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COAL_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER,2)
                .requires(Items.CHARCOAL)
                .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER,2)
                .requires(Items.COAL)
                .requires(Items.ROTTEN_FLESH)
                .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IRON_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER,2)
                .requires(Items.COAL)
                .requires(Items.BONE_MEAL)
                .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLOWSTONE_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER,2)
                .requires(Items.YELLOW_DYE)
                .requires(Items.COAL)
                .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GOLD_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER,2)
                .requires(Items.IRON_INGOT,2)
                .requires(Items.GLOWSTONE_DUST)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.REDSTONE_INFUSION_POWDER.get(), 3)
                .requires(Items.GUNPOWDER,2)
                .requires(Items.GLOWSTONE_DUST)
                .requires(Items.RED_DYE)
                .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Items.GLOWSTONE_DUST))
                .save(pWriter);
        //</editor-fold>
    }
}
