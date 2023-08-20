package de.tobfal.infundere.client;

import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class ClientAccess {
    public static boolean updateOreInfuserResourceLocations(BlockPos pos, @Nullable ResourceLocation processBackgroundResourceLocation, @Nullable ResourceLocation processResourceLocation, boolean playAnimation){
        Level level = Minecraft.getInstance().level;
        assert level != null;
        final BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof final OreInfuserBlockEntity oreInfuserBlockEntity)) {
            return false;
        }

        oreInfuserBlockEntity.processBackgroundResourceLocation = processBackgroundResourceLocation;
        oreInfuserBlockEntity.processResourceLocation = processResourceLocation;
        if (playAnimation) {
            oreInfuserBlockEntity.playAnimation = true;
        }
        return true;
    }
}
