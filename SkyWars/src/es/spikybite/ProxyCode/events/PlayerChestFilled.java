package es.spikybite.ProxyCode.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;

public class PlayerChestFilled implements Listener{
    private static ArenaManager am = new ArenaManager();
	public static HashMap<Location, Arena> ARENA_OPEN_CHESTS = new HashMap<Location, Arena>();
	@EventHandler
	public void onPlayerOpenChest(PlayerInteractEvent event){
		if(event.getClickedBlock()  == null)return;
		Block clicked = event.getClickedBlock();
		Location blockClicked = event.getClickedBlock().getLocation();
		Player p = event.getPlayer();
		Arena a =  am.getArena(p);
		if(a == null)return;
		if(clicked.getState() instanceof Chest){
		Chest chest = (Chest)clicked.getState();
		if(!ARENA_OPEN_CHESTS.containsKey(blockClicked)){
			ARENA_OPEN_CHESTS.put(blockClicked, a);
			Skywars.getCC().populateChest(a, chest);
		}
		
		}
		
		
	}
	
	@EventHandler
	public void placedChestCleared(BlockPlaceEvent event){
		Block block = event.getBlock();
		Location loc = block.getLocation();
		Player p = event.getPlayer();
		Arena a = am.getArena(p);
		if(a!= null){
		ARENA_OPEN_CHESTS.put(loc, a);
		}
	}
	
	@EventHandler
	public void removedChestCleared(BlockBreakEvent event){
		Block block = event.getBlock();
		Location loc = block.getLocation();
		Player p = event.getPlayer();
		Arena a = am.getArena(p);
		if(a!= null){
		ARENA_OPEN_CHESTS.remove(loc);
		}
	}
	
	public static void clearDataChest(Arena a){
		Iterator<Location> loc = ARENA_OPEN_CHESTS.keySet().iterator();
		while (loc.hasNext()) {
			Location locs = loc.next();
			if(ARENA_OPEN_CHESTS.get(locs).arena().equalsIgnoreCase(a.arena())){
				ARENA_OPEN_CHESTS.remove(locs);
			}
			
		}
	}
}
