package de.tobfal.infundere.datagen;

import com.google.gson.JsonObject;
import de.tobfal.infundere.init.ModRecipes;
import de.tobfal.infundere.recipe.OreInfuserRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class OreInfuserRecipeBuilder implements RecipeBuilder {

    //<editor-fold desc="Properties">
    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final Item result;
    private final Ingredient itemIngredient;
    private final Ingredient blockIngredient;
    private final int processTime;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<OreInfuserRecipe> serializer;
    private String group;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    private OreInfuserRecipeBuilder(RecipeCategory pCategory, CookingBookCategory pBookCategory, ItemLike pResult, Ingredient pItemIngredient, Ingredient pBlockIngredient, int pProcessTime, RecipeSerializer<OreInfuserRecipe> pSerializer) {
        this.category = pCategory;
        this.bookCategory = pBookCategory;
        this.result = pResult.asItem();
        this.itemIngredient = pItemIngredient;
        this.blockIngredient = pBlockIngredient;
        this.processTime = pProcessTime;
        this.serializer = pSerializer;
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    public static OreInfuserRecipeBuilder infusing(ItemLike pItemIngredient, Block pBlockIngredient, Block pResultBlock, RecipeCategory pCategory, int pProcessTime) {
        return new OreInfuserRecipeBuilder(pCategory, CookingBookCategory.MISC, pResultBlock.asItem(), Ingredient.of(pItemIngredient), Ingredient.of(pBlockIngredient), pProcessTime, ModRecipes.ORE_INFUSER_SERIALIZER.get());
    }

    @NotNull
    @Override
    public RecipeBuilder unlockedBy(@NotNull String pCriterionName, @NotNull CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @NotNull
    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @NotNull
    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new OreInfuserRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.bookCategory, this.itemIngredient, this.blockIngredient, this.result, this.processTime, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Inner Classes">
    static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final CookingBookCategory category;
        private final Ingredient itemIngredient;
        private final Ingredient blockIngredient;
        private final Item result;
        private final int processTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<OreInfuserRecipe> serializer;

        public Result(ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pItemIngredient, Ingredient pBlockIngredient, Item pResult, int pProcessTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<OreInfuserRecipe> pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.category = pCategory;
            this.itemIngredient = pItemIngredient;
            this.blockIngredient = pBlockIngredient;
            this.result = pResult;
            this.processTime = pProcessTime;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.serializer = pSerializer;
        }

        public void serializeRecipeData(@NotNull JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            pJson.addProperty("category", this.category.getSerializedName());
            pJson.add("itemIngredient", this.itemIngredient.toJson());
            pJson.add("blockIngredient", this.blockIngredient.toJson());

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            pJson.add("result", resultObject);

            pJson.addProperty("processTime", this.processTime);
        }

        @NotNull
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @NotNull
        public ResourceLocation getId() {
            return this.id;
        }

        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
    //</editor-fold>
}
