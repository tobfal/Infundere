package de.tobfal.infundere.network;

import de.tobfal.infundere.client.ClientAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientboundOreInfuserResourcesPacket {

    public BlockPos blockPos;
    public @Nullable ResourceLocation processBackgroundResourceLocation;
    public @Nullable ResourceLocation processResourceLocation;
    public boolean playAnimation;

    public ClientboundOreInfuserResourcesPacket(BlockPos blockPos, @Nullable ResourceLocation processBackgroundResourceLocation, @Nullable ResourceLocation processResourceLocation, boolean playAnimation) {
        this.blockPos = blockPos;
        this.processBackgroundResourceLocation = processBackgroundResourceLocation;
        this.processResourceLocation = processResourceLocation;
        this.playAnimation = playAnimation;
    }

    public ClientboundOreInfuserResourcesPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readNullable(FriendlyByteBuf::readResourceLocation), buffer.readNullable(FriendlyByteBuf::readResourceLocation), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockPos);
        buffer.writeNullable(this.processBackgroundResourceLocation, FriendlyByteBuf::writeResourceLocation);
        buffer.writeNullable(this.processResourceLocation, FriendlyByteBuf::writeResourceLocation);
        buffer.writeBoolean(this.playAnimation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        final var success = new AtomicBoolean(false);
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                success.set(ClientAccess.updateOreInfuserResourceLocations(this.blockPos, this.processBackgroundResourceLocation, this.processResourceLocation, this.playAnimation));
            });
        });
        context.get().setPacketHandled(true);
        return success.get();
    }
}
