package de.tobfal.infundere.recipe;

import com.google.gson.JsonObject;
import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.init.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class OreInfuserRecipe implements Recipe<Container> {

    public static final String ID = "ore-infuser";
    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack output;
    private final int processTime;

    public OreInfuserRecipe(ResourceLocation id, Ingredient input, ItemStack output, int processTime) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if (!pLevel.isClientSide()){
            return false;
        }

        return this.input.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return getResultItem(pRegistryAccess);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    public Block getResultBlock(RegistryAccess pRegistryAccess) {
        if (!(output.getItem() instanceof BlockItem)) {
            return null;
        }

        return ((BlockItem)output.getItem()).getBlock();
    }

    public int getProcessTime(){
        return processTime;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ORE_INFUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ORE_INFUSER_TYPE.get();
    }

    public static final class Type implements RecipeType<OreInfuserRecipe> {
        public static final Type INSTANCE = new Type();

        private Type() {};

        @Override
        public String toString()  {
            return OreInfuserRecipe.ID;
        }
    }

    public static final class Serializer implements RecipeSerializer<OreInfuserRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Infundere.MODID, OreInfuserRecipe.ID);

        private Serializer() {};

        @Override
        public OreInfuserRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            int processTime = GsonHelper.getAsInt(pSerializedRecipe, "processTime");

            if (!(output.getItem() instanceof BlockItem)) {
                Infundere.LOGGER.error(output.getItem().toString() + " is not a BlockItem.");
                return null;
            }

            return new OreInfuserRecipe(pRecipeId, input, output, processTime);
        }

        @Override
        public OreInfuserRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer)  {
            ItemStack output = pBuffer.readItem();
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            int processTime = pBuffer.readInt();

            if (!(output.getItem() instanceof BlockItem)) {
                Infundere.LOGGER.error(output.getItem().toString() + " is not a BlockItem.");
                return null;
            }

            return new OreInfuserRecipe(pRecipeId, input, output, processTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, OreInfuserRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.output);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.processTime);
        }
    }
}
