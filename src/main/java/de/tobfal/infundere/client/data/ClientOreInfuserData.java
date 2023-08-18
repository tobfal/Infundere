package de.tobfal.infundere.client.data;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ClientOreInfuserData {

    private static ResourceLocation resourceLocation;

    public static boolean set(@Nullable ResourceLocation resourceLocation) {
        ClientOreInfuserData.resourceLocation = resourceLocation;
        return true;
    }

    @Nullable
    public static ResourceLocation getResourceLocation() {
        return resourceLocation;
    }
}
