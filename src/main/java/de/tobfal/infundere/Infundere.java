package de.tobfal.infundere;

import de.tobfal.infundere.client.OreInfuserBlockRenderer;
import de.tobfal.infundere.client.screen.OreInfuserScreen;
import de.tobfal.infundere.init.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

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
        ModRecipes.register(eventBus);

        GeckoLib.initialize();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLCommonSetupEvent event) {

        MenuScreens.register(ModMenuTypes.ORE_INFUSER_MENU.get(), OreInfuserScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("PreInit");
        event.enqueueWork(InfunderePacketHandler::init);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModeEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(ModBlockEntities.ORE_INFUSER.get(), OreInfuserBlockRenderer::new);
        }
    }
}
