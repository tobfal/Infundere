package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.menu.BreacerMenu;
import de.tobfal.infundere.block.menu.OreInfuserMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Infundere.MODID);

    public static final RegistryObject<MenuType<OreInfuserMenu>> ORE_INFUSER_MENU = registerMenuType(OreInfuserMenu::new, "ore_infuser_menu");
    public static final RegistryObject<MenuType<BreacerMenu>> BREACER_MENU = registerMenuType(BreacerMenu::new, "breacer_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
