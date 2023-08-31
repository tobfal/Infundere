package de.tobfal.infundere.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

public class ItemTagUtils {

    public static Item getFirstItem(TagKey<Item> tagKey) {
        return ForgeRegistries.ITEMS.tags().getTag(tagKey).stream().findFirst().orElse(Items.AIR);
    }
}
