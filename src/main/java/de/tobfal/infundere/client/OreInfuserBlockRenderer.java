package de.tobfal.infundere.client;

import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class OreInfuserBlockRenderer extends GeoBlockRenderer<OreInfuserBlockEntity> {
    public OreInfuserBlockRenderer() {
        super(new OreInfuserBlockModel());
    }
}
