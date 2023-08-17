package de.tobfal.infundere;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Infundere.MODID)
public class Infundere
{
    public static final String MODID = "infundere";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Infundere()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLCommonSetupEvent event)
    {
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("[Infundere] PreInit");
    }
}
