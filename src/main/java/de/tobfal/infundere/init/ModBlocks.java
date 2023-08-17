package de.tobfal.infundere.init;

import de.tobfal.infundere.Infundere;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Infundere.MODID);

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
