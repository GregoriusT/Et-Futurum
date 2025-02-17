package ganymedes01.etfuturum.core.utils;

import java.util.ArrayList;

import net.minecraft.block.Block;

public class HoeHelper {
	
	private static ArrayList<Block> hoeBlocks = new ArrayList<Block>();
	
	public static void addToHoeArray(Block block) {
		hoeBlocks.add(block);
	}
	
	public static boolean hoeArrayHas(Block block) {
		return hoeBlocks.contains(block);
	}
	
	public static ArrayList<Block> getHoeArray() {
		return hoeBlocks;
	}
}
