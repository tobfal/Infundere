package de.tobfal.infundere.block.menu;

import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import de.tobfal.infundere.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class OreInfuserMenu extends AbstractContainerMenu {

    private final OreInfuserBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public OreInfuserMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public OreInfuserMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ORE_INFUSER_MENU.get(), pContainerId);
        checkContainerSize(inv, 2);

        blockEntity = (OreInfuserBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        int box = 18;
        // Create player inventory slots
        for (int height = 0; height < 3; ++height) {
            for (int width = 0; width < 9; ++width) {
                addSlot(new Slot(inv, 9 + height * 9 + width, 8 + box * width, 84 + box * height));
            }
        }
        for (int width = 0; width < 9; ++width) {
            addSlot(new Slot(inv, width, 8 + box * width, 142));
        }

        // Create container slots
        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 98, 35));

                addSlot(new SlotItemHandler(h, 1, 26, 35));
            });
        }

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        // TODO: Implement quick move
        return null;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, blockEntity.getBlockState().getBlock());
    }
}
