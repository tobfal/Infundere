package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.item.OreInfuserItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Infundere.MODID);

    public static final RegistryObject<BlockItem> ORE_INFUSER = ModCreativeTabs.addToTab(ITEMS.register("ore_infuser",
            () -> new OreInfuserItem(ModBlocks.ORE_INFUSER.get(), new Item.Properties())));

    //<editor-fold desc="Infusion Powder">
    public static final RegistryObject<Item> COAL_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("coal_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> COPPER_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("copper_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> DIAMOND_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("diamond_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> EMERALD_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("emerald_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> GOLD_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("gold_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> IRON_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("iron_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> LAPIS_LAZULI_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("lapis_lazuli_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> REDSTONE_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("redstone_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> GLOWSTONE_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("glowstone_infusion_powder",
            () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> NETHER_QUARTZ_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("nether_quartz_infusion_powder",
            () -> new Item(new Item.Properties())));
    //</editor-fold>

    //<editor-fold desc="Register">
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    //</editor-fold>
}
