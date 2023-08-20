package de.tobfal.infundere.client;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OreInfuserBlockModel extends GeoModel<OreInfuserBlockEntity> {
    @Override
    public ResourceLocation getModelResource(OreInfuserBlockEntity animatable) {
        return new ResourceLocation(Infundere.MODID, "geo/ore_infuser.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OreInfuserBlockEntity animatable) {
        return new ResourceLocation(Infundere.MODID, "textures/block/ore_infuser.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OreInfuserBlockEntity animatable) {
        return new ResourceLocation(Infundere.MODID, "animations/ore_infuser.animation.json");
    }
}
