package de.tobfal.infundere;

import com.mojang.authlib.GameProfile;
import de.tobfal.infundere.client.OreInfuserBlockRenderer;
import de.tobfal.infundere.client.screen.BreacerScreen;
import de.tobfal.infundere.client.screen.OreInfuserScreen;
import de.tobfal.infundere.init.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

import java.util.HashMap;
import java.util.UUID;

@Mod(Infundere.MODID)
public class Infundere {
    public static final String MODID = "infundere";
    public static final Logger LOGGER = LoggerFactory.getLogger(Infundere.class + "/INFUNDERE");

    private static HashMap<Integer, FakePlayer> levelFakePlayer = new HashMap<>();

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

    public static FakePlayer getFakePlayer(Level level) {
        if (levelFakePlayer.containsKey(level.hashCode()))
            return levelFakePlayer.get(level.hashCode());
        if (level instanceof ServerLevel) {
            FakePlayer fakePlayer = new FakePlayer((ServerLevel) level, new GameProfile(UUID.nameUUIDFromBytes("infundere".getBytes()), "[Infundere]"));
            levelFakePlayer.put(level.hashCode(), fakePlayer);
            return fakePlayer;
        }
        return null;
    }

    public static FakePlayer getFakePlayer(Level level, BlockPos pos) {
        FakePlayer player = getFakePlayer(level);
        if (player != null) player.absMoveTo(pos.getX(), pos.getY(), pos.getZ(), 90, 90);
        return player;
    }

    private void clientSetup(final FMLCommonSetupEvent event) {
        MenuScreens.register(ModMenuTypes.ORE_INFUSER_MENU.get(), OreInfuserScreen::new);
        MenuScreens.register(ModMenuTypes.BREACER_MENU.get(), BreacerScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("PreInit");
        event.enqueueWork(InfunderePacketHandler::init);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModeEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(ModBlockEntities.ORE_INFUSER.get(), pContext -> new OreInfuserBlockRenderer());
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
    public static class ServerModeEvents {
        @SubscribeEvent
        public static void onServerStarting(ServerStartingEvent event) {
            levelFakePlayer.clear();
        }
    }
}
