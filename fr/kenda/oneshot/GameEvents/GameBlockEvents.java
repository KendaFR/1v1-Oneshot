package fr.kenda.oneshot.GameEvents;

import fr.kenda.oneshot.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GameBlockEvents implements Listener {

    String prefix = Main.prefix;
    Main main = Main.getInstance();

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player player = e.getPlayer();

        if(!player.hasPermission("oneshot.block")) {
            e.setCancelled(true);
        player.sendMessage(prefix + "§cVous ne pouvez pas casser de blocs.");
        }
        else{
            e.setCancelled(false);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();

        if(!player.hasPermission("oneshot.block")) {
            e.setCancelled(true);
            player.sendMessage(prefix + "§cVous ne pouvez pas poser de blocs.");
        }
        else{
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e){
        e.setCancelled(true);
    }
}
