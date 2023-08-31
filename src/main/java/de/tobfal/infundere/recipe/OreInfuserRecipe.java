package de.tobfal.infundere.recipe;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.init.ModRecipes;
import de.tobfal.infundere.utils.ItemTagUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OreInfuserRecipe implements Recipe<SimpleContainer> {

    //<editor-fold desc="Properties">
    public final ResourceLocation id;
    public final Either<ItemStack, TagKey<Item>> result;
    private final Ingredient itemIngredient;
    private final Ingredient blockIngredient;
    private final int processTime;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public OreInfuserRecipe(ResourceLocation id, ItemStack result, Ingredient itemIngredient, Ingredient blockIngredient, int processTime) {
        this.id = id;
        this.result = Either.left(result);
        this.itemIngredient = itemIngredient;
        this.blockIngredient = blockIngredient;
        this.processTime = processTime;
    }

    public OreInfuserRecipe(ResourceLocation id, TagKey<Item> resultTag, Ingredient itemIngredient, Ingredient blockIngredient, int processTime) {
        this.id = id;
        this.result = Either.right(resultTag);
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
        if (result.left().isPresent()) {
            return result.left().get();
        } else if (result.right().isPresent()) {
            new ItemStack(ItemTagUtils.getFirstItem(result.right().get()));
        }
        throw new RuntimeException("TODO");
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return getResultItem();
    }

    public ItemStack getResultItem() {
        if (result.left().isPresent()) {
            return result.left().get().copy();
        } else if (result.right().isPresent()) {
            new ItemStack(ItemTagUtils.getFirstItem(result.right().get()));
        }
        throw new RuntimeException("TODO");
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
        Item resultItem;
        if (result.left().isPresent()) {
            resultItem = result.left().get().getItem();
        } else if (result.right().isPresent()) {
            resultItem = ItemTagUtils.getFirstItem(result.right().get());
        } else {
            throw new RuntimeException("TODO");
        }

        if (!(resultItem instanceof BlockItem)) {
            return Blocks.AIR;
        }

        return ((BlockItem) resultItem).getBlock();
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

            int processTime = GsonHelper.getAsInt(pSerializedRecipe, "processTime");

            Ingredient itemIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "itemIngredient"));
            Ingredient blockIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "blockIngredient"));

            /** TODO: Check if blockIngredient only contains BlockItems
                This does not work:
                if (Arrays.stream(blockIngredient.getItems()).anyMatch((itemStack) -> !(itemStack.getItem() instanceof BlockItem))) {
                    return new OreInfuserRecipe(pRecipeId, result, itemIngredient, Ingredient.EMPTY, processTime);
                }
            **/

            JsonObject resultObject = GsonHelper.getAsJsonObject(pSerializedRecipe, "result");
            if (resultObject.has("item")) {
                ItemStack result = ShapedRecipe.itemStackFromJson(resultObject);
                if (!(result.getItem() instanceof BlockItem)) {
                    result = ItemStack.EMPTY;
                }
                return new OreInfuserRecipe(pRecipeId, result, itemIngredient, blockIngredient, processTime);
            } else if (resultObject.has("tag")) {
                ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(resultObject, "tag"));
                TagKey<Item> resultTag = TagKey.create(Registries.ITEM, resourcelocation);
                return new OreInfuserRecipe(pRecipeId, resultTag, itemIngredient, blockIngredient, processTime);
            }

            throw new RuntimeException("TODO");
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
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, OreInfuserRecipe pRecipe) {
            if (pRecipe.result.left().isPresent()) {
                pBuffer.writeItem(pRecipe.result.left().get());
            } else if (pRecipe.result.right().isPresent()) {
                pBuffer.writeItem(new ItemStack(ItemTagUtils.getFirstItem(pRecipe.result.right().get())));
            } else {
                throw new RuntimeException("TODO");
            }
            pRecipe.getItemIngredient().toNetwork(pBuffer);
            pRecipe.getBlockIngredient().toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.processTime);
        }
    }
    //</editor-fold>
}
