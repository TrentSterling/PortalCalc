package me.psanker.portalcalc.regions;

import java.util.Iterator;
import java.util.LinkedList;

import org.bukkit.entity.Player;

public class RegionProvider implements Iterator<Region>, Iterable<Region> {
	
	protected int index;
	protected LinkedList<Region> queue;
	
	public static final int SCAN_RADIUS=3;
	
	protected Region zero;

	public RegionProvider(Player player) {
		queue = new LinkedList<Region>();
		index=0;
		zero = new Region(player);
		queueSet();
	}
	
	public boolean hasNext(){
		return !queue.isEmpty();
	}
	
	public Region next(){
		Region r = queue.remove();
		if(queue.isEmpty())
			queueSet();
		return r;
	}
	
	public void remove(){
		throw new UnsupportedOperationException("I don't do that!");
	}

	public Iterator<Region> iterator() {
		return this;
	}
	
	protected void queueSet(){
		if(index==0){
			queue.add(zero);
			index++;
			return;
		}
		
		if(index>=SCAN_RADIUS)
			return;
		
		for(int i=-index;i<=index;i++){
			queue.add(zero.regionByOfsettingRegion(new int[]{index, 0, i}));
			queue.add(zero.regionByOfsettingRegion(new int[]{-index, 0, i}));
		}
		for(int j=-index+1;j<index;j++){
			queue.add(zero.regionByOfsettingRegion(new int[]{j, 0, index}));
			queue.add(zero.regionByOfsettingRegion(new int[]{j, 0, -index}));
		}
		
		index++;
	}

}
