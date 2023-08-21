package de.tobfal.infundere.client;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class OreInfuserBlockModel extends DefaultedBlockGeoModel<OreInfuserBlockEntity> {
    public OreInfuserBlockModel() {
        super(new ResourceLocation(Infundere.MODID, "ore_infuser"));
    }

    @Override
    public ResourceLocation getModelResource(OreInfuserBlockEntity animatable) {
        return new ResourceLocation(Infundere.MODID, "geo/block/ore_infuser.geo.json");
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
