package de.tobfal.infundere.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BlockUtils {
    public static List<ItemStack> getBlockDrops(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        NonNullList<ItemStack> stacks = NonNullList.create();
        stacks.addAll(Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos)));
        return stacks;
    }
}
