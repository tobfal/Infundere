package de.tobfal.infundere.datagen;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {

        ResourceLocation ID = new ResourceLocation(Infundere.MODID, "conditional");

        TagKey<Item> ALUMINUM_TAG = ItemTags.create(new ResourceLocation("forge:ores/aluminum"));
        ConditionalRecipe.builder()
                .addCondition(not(tagEmpty(ALUMINUM_TAG)))
                .addRecipe(
                        OreInfuserRecipeBuilder.infusing(ModItems.ALUMINUM_INFUSION_POWDER.get(), Blocks.STONE, ALUMINUM_TAG, RecipeCategory.MISC, 100)
                                .unlockedBy(getHasName(ModItems.ALUMINUM_INFUSION_POWDER.get()), has(ModItems.ALUMINUM_INFUSION_POWDER.get()))::save
                ).build(pWriter, ID);

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
                .requires(Items.GLOWSTONE)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);
        //</editor-fold>
    }
}
