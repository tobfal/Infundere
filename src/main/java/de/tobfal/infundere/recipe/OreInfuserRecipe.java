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

    //<editor-fold desc="Properties">
    public final ResourceLocation id;
    public final ItemStack result;
    private final Ingredient itemIngredient;
    private final Ingredient blockIngredient;
    private final int processTime;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public OreInfuserRecipe(ResourceLocation id, ItemStack result, Ingredient itemIngredient, Ingredient blockIngredient, int processTime) {
        this.id = id;
        this.result = result;
        this.itemIngredient = itemIngredient;
        this.blockIngredient = blockIngredient;
        this.processTime = processTime;
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return this.itemIngredient.test(pContainer.getItem(0));
    }

    public boolean hasBlockAsIngredient(Block block, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return this.blockIngredient.test(new ItemStack(block.asItem()));
    }

    public boolean hasBlockAsResult(Block block, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return block == getResultBlock();
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    public int getProcessTime() {
        return processTime;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ORE_INFUSER_SERIALIZER.get();
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ORE_INFUSER_TYPE.get();
    }

    public Ingredient getItemIngredient() {
        return this.itemIngredient;
    }

    public Ingredient getBlockIngredient() {
        return this.blockIngredient;
    }

    public Block getIngredientBlock() {
        // TODO: Temporarily returns only first matching block
        return ((BlockItem) this.blockIngredient.getItems()[0].getItem()).getBlock();
    }

    public Block getResultBlock() {
        if (!(this.result.getItem() instanceof BlockItem)) {
            return null;
        }

        return ((BlockItem) this.result.getItem()).getBlock();
    }

    public static class Type implements RecipeType<OreInfuserRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "ore_infuser";

        private Type() {
        }
    }

    public static class Serializer implements RecipeSerializer<OreInfuserRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Infundere.MODID, "ore_infuser");

        @NotNull
        @Override
        public OreInfuserRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            if (!(result.getItem() instanceof BlockItem)) {
                result = ItemStack.EMPTY;
            }

            int processTime = GsonHelper.getAsInt(pSerializedRecipe, "processTime");

            Ingredient itemIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "itemIngredient"));
            Ingredient blockIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "blockIngredient"));

            /** TODO: Check if blockIngredient only contains BlockItems
                This does not work:
                if (Arrays.stream(blockIngredient.getItems()).anyMatch((itemStack) -> !(itemStack.getItem() instanceof BlockItem))) {
                    return new OreInfuserRecipe(pRecipeId, result, itemIngredient, Ingredient.EMPTY, processTime);
                }
            **/

            return new OreInfuserRecipe(pRecipeId, result, itemIngredient, blockIngredient, processTime);
        }

        @Override
        public @Nullable OreInfuserRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack result = pBuffer.readItem();
            Ingredient itemIngredient = Ingredient.fromNetwork(pBuffer);
            Ingredient blockIngredient = Ingredient.fromNetwork(pBuffer);
            int processTime = pBuffer.readInt();

            return new OreInfuserRecipe(pRecipeId, result, itemIngredient, blockIngredient, processTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, OreInfuserRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.result);
            pRecipe.getItemIngredient().toNetwork(pBuffer);
            pRecipe.getBlockIngredient().toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.processTime);
        }
    }
    //</editor-fold>
}
