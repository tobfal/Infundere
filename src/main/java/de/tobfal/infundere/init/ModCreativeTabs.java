package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> INFUNDERE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Infundere.MODID);

    public static final List<Supplier<? extends ItemLike>> INFUNDERE_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> INFUNDERE_TAB = INFUNDERE_TABS.register("infunderetab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.ORE_INFUSER.get()))
            .title(Component.translatable("creativetab.infundere.infunderetab"))
            .displayItems((pParameters, pOutput) -> {
                INFUNDERE_TAB_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get()));
            })
            .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        INFUNDERE_TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike, String[] modTargets) {
        if (Arrays.stream(modTargets).anyMatch(modTarget -> ModList.get().isLoaded(modTarget))) {
            INFUNDERE_TAB_ITEMS.add(itemLike);
        }
        return itemLike;
    }

    public static void register(IEventBus eventBus) {
        INFUNDERE_TABS.register(eventBus);
    }
}
