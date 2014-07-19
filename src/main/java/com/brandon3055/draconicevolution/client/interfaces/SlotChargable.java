package com.brandon3055.draconicevolution.client.interfaces;

import com.brandon3055.draconicevolution.common.core.utills.EnergyHelper;
import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotChargable extends Slot {


	public SlotChargable(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);

	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (super.isItemValid(stack))
		{
			return EnergyHelper.isEnergyContainerItem(stack);
		}
		return false;
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}


	@Override
	public boolean func_111238_b() {
		return !((TileEnergyInfuser)inventory).running;
	}
}
