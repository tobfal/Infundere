package de.tobfal.infundere.recipe;

import com.google.gson.JsonObject;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.init.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OreInfuserRecipe implements Recipe<SimpleContainer> {

    public final ResourceLocation id;
    public final ItemStack output;
    private final Ingredient containerIngredient;

    private final ItemStack blockItemIngredient;

    public OreInfuserRecipe(ResourceLocation id, ItemStack output, Ingredient containerIngredient, ItemStack blockItemIngredient) {
        this.id = id;
        this.output = output;
        this.containerIngredient = containerIngredient;
        this.blockItemIngredient = blockItemIngredient;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return this.containerIngredient.test(pContainer.getItem(0));
    }

    public boolean hasBlockIngredient(Block block, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return block == getIngredientBlock();
    }

    public boolean hasBlockOutput(Block block, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return block == getOutputBlock();
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ORE_INFUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ORE_INFUSER_TYPE.get();
    }

    public Ingredient getContainerIngredient() {
        return this.containerIngredient;
    }

    public ItemStack getBlockItemIngredient() {
        return this.blockItemIngredient;
    }

    public Block getIngredientBlock() {
        if (!(this.blockItemIngredient.getItem() instanceof BlockItem)) {
            return null;
        }

        return ((BlockItem) this.blockItemIngredient.getItem()).getBlock();
    }

    public Block getOutputBlock() {
        if (!(this.output.getItem() instanceof BlockItem)) {
            return null;
        }

        return ((BlockItem) this.output.getItem()).getBlock();
    }

    public static class Type implements RecipeType<OreInfuserRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "ore_infuser";
    }

    public static class Serializer implements RecipeSerializer<OreInfuserRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Infundere.MODID, "ore_infuser");

        @Override
        public OreInfuserRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            Ingredient containerIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "containerIngredient"));
            ItemStack blockItemIngredient = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "blockItemIngredient"));

            if (!(output.getItem() instanceof BlockItem)) {
                output = ItemStack.EMPTY;
            }

            if (!(blockItemIngredient.getItem() instanceof BlockItem)) {
                blockItemIngredient = ItemStack.EMPTY;
            }

            return new OreInfuserRecipe(pRecipeId, output, containerIngredient, blockItemIngredient);
        }

        @Override
        public @Nullable OreInfuserRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack output = pBuffer.readItem();
            Ingredient containerIngredient = Ingredient.fromNetwork(pBuffer);
            ItemStack blockItemIngredient = pBuffer.readItem();

            return new OreInfuserRecipe(pRecipeId, output, containerIngredient, blockItemIngredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, OreInfuserRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.output);
            pRecipe.getContainerIngredient().toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.blockItemIngredient);
        }
    }
}
