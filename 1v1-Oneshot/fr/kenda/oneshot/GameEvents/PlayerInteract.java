package fr.kenda.oneshot.GameEvents;

import fr.kenda.oneshot.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerInteract implements Listener {

    String prefix = Main.prefix;

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e){
        e.setCancelled(e.getSlot() == 8);
        e.getWhoClicked().sendMessage(prefix + "§cTu ne peux pas déplacer cette flèche.");
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        e.setCancelled(true);
    }

}
