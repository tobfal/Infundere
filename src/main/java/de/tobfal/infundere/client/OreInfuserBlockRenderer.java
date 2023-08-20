package de.tobfal.infundere.client;

import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class OreInfuserBlockRenderer extends GeoBlockRenderer<OreInfuserBlockEntity> {
    public OreInfuserBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new OreInfuserBlockModel());
    }
}
