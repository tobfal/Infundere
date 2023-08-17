package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.recipe.OreInfuserRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Infundere.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Infundere.MODID);

    public static final RegistryObject<RecipeSerializer<OreInfuserRecipe>> ORE_INFUSER_SERIALIZER = RECIPE_SERIALIZERS.register("ore_infuser",
            () -> OreInfuserRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeType<OreInfuserRecipe>> ORE_INFUSER_TYPE = RECIPE_TYPES.register("ore_infuser",
            () -> OreInfuserRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
