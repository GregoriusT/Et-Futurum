package ganymedes01.etfuturum.items.block;

import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.items.ItemUninflammable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockUninflammable extends ItemBlock {

	public ItemBlockUninflammable(Block block) {
		super(block);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return !ConfigFunctions.enableNetheriteFlammable;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return ItemUninflammable.createUninflammableItem(world, location);
	}

}
