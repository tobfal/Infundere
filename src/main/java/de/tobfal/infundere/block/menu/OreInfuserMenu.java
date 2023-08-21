package de.tobfal.infundere.block.menu;

import de.tobfal.infundere.block.entity.OreInfuserBlockEntity;
import de.tobfal.infundere.init.ModMenuTypes;
import de.tobfal.infundere.recipe.OreInfuserRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
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

    //<editor-fold desc="Properties">
    private final OreInfuserBlockEntity blockEntity;
    private final Inventory inventory;
    private final Level level;
    private final ContainerData data;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public OreInfuserMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public OreInfuserMenu(int pContainerId, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ORE_INFUSER_MENU.get(), pContainerId);
        checkContainerSize(inventory, 1);

        blockEntity = (OreInfuserBlockEntity) entity;
        this.level = inventory.player.level();
        this.inventory = inventory;
        this.data = data;

        int box = 18;
        // Create player inventory slots
        for (int height = 0; height < 3; ++height) {
            for (int width = 0; width < 9; ++width) {
                addSlot(new Slot(inventory, 9 + height * 9 + width, 8 + box * width, 84 + box * height));
            }
        }
        for (int width = 0; width < 9; ++width) {
            addSlot(new Slot(inventory, width, 8 + box * width, 142));
        }

        // Create container slots
        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 44, 34));
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
            SimpleContainer inventory = new SimpleContainer(this.inventory.getContainerSize());
            inventory.setItem(0, sourceStack);
            if (level.getRecipeManager().getRecipeFor(OreInfuserRecipe.Type.INSTANCE, inventory, level).isEmpty()) {
                return ItemStack.EMPTY;
            }
            if (!moveItemStackTo(sourceStack, 36, 37, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < 37) {
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
        int barSize = 48;

        return maxProcessTime == 0 ? 0 : Math.round((float) barSize * (float) processTime / (float) maxProcessTime);
    }

    public OreInfuserBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
    //</editor-fold>
}
