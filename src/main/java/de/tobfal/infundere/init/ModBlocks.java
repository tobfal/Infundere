package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.BreacerBlock;
import de.tobfal.infundere.block.OreInfuserBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Infundere.MODID);

    public static final RegistryObject<Block> ORE_INFUSER = BLOCKS.register("ore_infuser",
            () -> new OreInfuserBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> BREACER = registerBlock("breacer",
            () -> new BreacerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f).requiresCorrectToolForDrops()));

    private static <T extends Block> void registerBlockItem(String name, boolean addToTab, RegistryObject<T> block) {
        if (addToTab) {
            ModCreativeTabs.addToTab(ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())));
        } else {
            ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, true, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, boolean addToTab, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, addToTab, toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
