package de.tobfal.infundere.network;

import de.tobfal.infundere.client.data.ClientOreInfuserData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientboundOreInfuserResourcesPacket {
    public @Nullable ResourceLocation processBackgroundResourceLocation;

    public ClientboundOreInfuserResourcesPacket(@Nullable ResourceLocation processBackgroundResourceLocation) {
        this.processBackgroundResourceLocation = processBackgroundResourceLocation;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNullable(this.processBackgroundResourceLocation, FriendlyByteBuf::writeResourceLocation);
    }

    public ClientboundOreInfuserResourcesPacket(FriendlyByteBuf buffer) {
        this(buffer.readNullable(FriendlyByteBuf::readResourceLocation));
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        final var success = new AtomicBoolean(false);
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                success.set(ClientOreInfuserData.set(this.processBackgroundResourceLocation));
            });
        });
        context.get().setPacketHandled(true);
        return success.get();
    }
}
