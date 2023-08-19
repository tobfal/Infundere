package de.tobfal.infundere.client.data;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ClientOreInfuserData {

    private static ResourceLocation processBackgroundResourceLocation;
    private static ResourceLocation processResourceLocation;

    public static boolean set(@Nullable ResourceLocation processBackgroundResourceLocation, @Nullable ResourceLocation processResourceLocation) {
        ClientOreInfuserData.processBackgroundResourceLocation = processBackgroundResourceLocation;
        ClientOreInfuserData.processResourceLocation = processResourceLocation;
        return true;
    }

    @Nullable
    public static ResourceLocation getProcessBackgroundResourceLocation() {
        return processBackgroundResourceLocation;
    }

    @Nullable
    public static ResourceLocation getProcessResourceLocation() {
        return processResourceLocation;
    }
}
