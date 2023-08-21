package de.tobfal.infundere.block.menu;

import de.tobfal.infundere.block.entity.BreacerBlockEntity;
import de.tobfal.infundere.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BreacerMenu extends AbstractContainerMenu {

    //<editor-fold desc="Properties">
    private final BreacerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BreacerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public BreacerMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.BREACER_MENU.get(), pContainerId);
        checkContainerSize(inv, 12);

        this.blockEntity = (BreacerBlockEntity) entity;
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
                int slotIndex = 0;
                // Input slots
                for (int height = 0; height < 3; ++height) {
                    for (int width = 0; width < 2; ++width) {
                        addSlot(new SlotItemHandler(h, slotIndex++, 26 + box * width, 17 + box * height));
                    }
                }

                // Output slots
                for (int height = 0; height < 3; ++height) {
                    for (int width = 0; width < 2; ++width) {
                        addSlot(new SlotItemHandler(h, slotIndex++, 116 + box * width, 17 + box * height));
                    }
                }
            });
        }

        addDataSlots(data);
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < 36) {
            if (!(sourceStack.getItem() instanceof BlockItem)) {
                return ItemStack.EMPTY;
            }
            if (!moveItemStackTo(sourceStack, 36, 42, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < 48) {
            if (!moveItemStackTo(sourceStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, blockEntity.getBlockState().getBlock());
    }

    public int getScaledProgress() {
        int processTime = this.data.get(0);
        int maxProcessTime = this.data.get(1);
        int barSize = 64;

        return maxProcessTime == 0 ? 0 : Math.round((float) barSize * (float) processTime / (float) maxProcessTime);
    }
    //</editor-fold>
}
