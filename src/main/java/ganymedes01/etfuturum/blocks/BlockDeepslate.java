package ganymedes01.etfuturum.blocks;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.EtFuturumLateWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class BlockDeepslate extends BlockRotatedPillar implements IConfigurable {

	public BlockDeepslate() {
		super(Material.rock);
		this.setHardness(3);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate"));
		this.setBlockTextureName("deepslate");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		this.setStepSound(ConfigWorld.enableNewBlocksSounds ? ModSounds.soundDeepslate : soundTypeStone);
	}

	@Override
	protected IIcon getSideIcon(int p_150163_1_) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getTopIcon(int p_150161_1_)
	{
		return field_150164_N;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		blockIcon = p_149651_1_.registerIcon(getTextureName());
		field_150164_N = p_149651_1_.registerIcon(getTextureName() + "_top");
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(ModBlocks.cobbled_deepslate);
	}

	@Override
	/**
	 * We need to do this in order to make sure deepslate generates, as veins crossing a chunk border may not get replaced fully.
	 * This is used to detect those ores since they'd generate over deepslate, not the other way around.
	 */
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		boolean flag = target == Blocks.stone || this == target;
		if(flag) {
			doDeepslateRedoCheck(world, x, y, z);
		}
		return flag;
	}
	
	public static void doDeepslateRedoCheck(World world, int x, int y, int z) {
		if(!EtFuturumLateWorldGenerator.stopRecording && !world.getChunkFromBlockCoords(x, z).sendUpdates) {
			Map<Long, Set<Integer>> map;
			if(!EtFuturumLateWorldGenerator.deepslateRedoCache.containsKey(world.provider.dimensionId)) {
				EtFuturumLateWorldGenerator.deepslateRedoCache.put(world.provider.dimensionId, map = Maps.newConcurrentMap());
			} else {
				map = EtFuturumLateWorldGenerator.deepslateRedoCache.get(world.provider.dimensionId);
			}

			long coords = ChunkCoordIntPair.chunkXZ2Int(x >> 4, z >> 4);
			
			Set<Integer> posSet;
			if(!map.containsKey(coords)) {
				map.put(coords, posSet = new HashSet());
			} else {
				posSet = map.get(coords);
			}
			
			posSet.add((int)((x & 0xF) << 12 | (y & 0xFF) << 4 | (z & 0xF)));
		}
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableDeepslate;
	}
}
