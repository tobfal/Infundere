package de.tobfal.infundere;

import de.tobfal.infundere.init.*;
import de.tobfal.infundere.screen.OreInfuserScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Infundere.MODID)
public class Infundere {
    public static final String MODID = "infundere";
    public static final Logger LOGGER = LoggerFactory.getLogger(Infundere.class + "/INFUNDERE");

    public Infundere() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Config.init();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModCreativeTabs.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLCommonSetupEvent event) {

        MenuScreens.register(ModMenuTypes.ORE_INFUSER_MENU.get(), OreInfuserScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("PreInit");
    }
}
