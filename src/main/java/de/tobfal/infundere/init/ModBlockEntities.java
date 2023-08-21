package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.block.entity.BreacerBlockEntity;
import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Infundere.MODID);
    public static final RegistryObject<BlockEntityType<OreInfuserBlockEntity>> ORE_INFUSER = BLOCK_ENTITIES.register("ore_infuser",
            () -> BlockEntityType.Builder.of(OreInfuserBlockEntity::new, ModBlocks.ORE_INFUSER.get()).build(null));
    public static final RegistryObject<BlockEntityType<BreacerBlockEntity>> BREACER = BLOCK_ENTITIES.register("breacer",
            () -> BlockEntityType.Builder.of(BreacerBlockEntity::new, ModBlocks.BREACER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
