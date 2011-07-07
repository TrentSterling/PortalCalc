package me.psanker.portalcalc;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockFadeEvent;


public class PCBlockListener extends BlockListener {
	@Override
	public void onBlockFade(BlockFadeEvent e){
		PCLog.log("fade "+e.getBlock().getTypeId(), 0);
	}
}
