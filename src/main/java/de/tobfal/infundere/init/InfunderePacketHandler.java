package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.network.ClientboundOreInfuserResourcesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class InfunderePacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Infundere.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        INSTANCE.messageBuilder(ClientboundOreInfuserResourcesPacket.class, 0, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundOreInfuserResourcesPacket::encode)
                .decoder(ClientboundOreInfuserResourcesPacket::new)
                .consumerNetworkThread(ClientboundOreInfuserResourcesPacket::handle)
                .add();
    }
}
