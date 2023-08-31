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

    public static final RegistryObject<Item> ALUMINUM_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("aluminum_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"geckolib"});
    public static final RegistryObject<Item> LEAD_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("lead_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    public static final RegistryObject<Item> NICKEL_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("nickel_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    public static final RegistryObject<Item> OSMIUM_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("osmium_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    public static final RegistryObject<Item> PLATINUM_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("platinum_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    public static final RegistryObject<Item> SILVER_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("silver_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    public static final RegistryObject<Item> TIN_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("tin_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    public static final RegistryObject<Item> URANIUM_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("uranium_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    public static final RegistryObject<Item> ZINC_INFUSION_POWDER = ModCreativeTabs.addToTab(ITEMS.register("zinc_infusion_powder",
            () -> new Item(new Item.Properties())), new String[]{"alltheores"});
    //</editor-fold>

    //<editor-fold desc="Register">
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    //</editor-fold>
}
