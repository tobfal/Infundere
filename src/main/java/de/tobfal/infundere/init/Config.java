package de.tobfal.infundere.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue ORE_INFUSER_PROCESS_TIME;

    public static void init() {
        initCommon();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }

    private static void initCommon() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Ore Infuser").push("ore_infuser");
        ORE_INFUSER_PROCESS_TIME = builder
                .comment("Ticks needed to infuse ore")
                .defineInRange("processTime", 40, 1, Integer.MAX_VALUE);
        builder.pop();

        COMMON_CONFIG = builder.build();
    }
}