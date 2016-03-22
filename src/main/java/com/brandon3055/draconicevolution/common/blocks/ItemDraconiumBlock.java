package com.brandon3055.draconicevolution.common.blocks;

import cofh.api.energy.IEnergyContainerItem;
import com.brandon3055.brandonscore.common.blocks.ItemBlockBasic;
import com.brandon3055.brandonscore.common.config.FeatureWrapper;
import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
import com.brandon3055.brandonscore.common.utills.Utills;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by brandon3055 on 22/3/2016.
 */
public class ItemDraconiumBlock extends ItemBlockBasic implements IEnergyContainerItem{

	public ItemDraconiumBlock(Block block) {
		super(block);
	}

	public ItemDraconiumBlock(Block block, FeatureWrapper featureWrapper){
		super(block, featureWrapper);
	}

	//region IEnergyContainerItem
	protected final int capacity = 100000000;
	protected final int maxReceive = 10000000;

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if(container.getItemDamage() != 0 || container.stackSize <= 0) return 0;
		maxReceive /= container.stackSize;

		int energy = ItemNBTHelper.getInteger(container, "Energy", 0);
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			ItemNBTHelper.setInteger(container, "Energy", energy);
		}
		if (getEnergyStored(container) == getMaxEnergyStored(container)) {
			container.setItemDamage(1);
			container.setTagCompound(null);
		}
		return energyReceived * container.stackSize;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return ItemNBTHelper.getInteger(container, "Energy", 0);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}

	@Override
	public void onUpdate(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
		if (stack.getItemDamage() == 0 && getEnergyStored(stack) == getMaxEnergyStored(stack)) stack.setItemDamage(1);
		super.onUpdate(stack, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		if (stack.hasTagCompound()) list.add(Utills.addCommas(getEnergyStored(stack)) + " / " + Utills.addCommas(getMaxEnergyStored(stack)) + "RF");
	}

	//endregion
}
