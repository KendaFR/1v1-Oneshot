package fr.kenda.oneshot.GameEvents;

import fr.kenda.oneshot.GameStatus.GameStatus;
import fr.kenda.oneshot.Main;
import fr.kenda.oneshot.timer.GAutoStart;
import fr.kenda.oneshot.timer.TimerMsgRules;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameJoinLeftEvents implements Listener {

    String prefix = Main.prefix;
    Main main = Main.getInstance();

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        PlayerLoginEvent.Result kicked = PlayerLoginEvent.Result.KICK_OTHER;

        if(main.isStatus(GameStatus.GAME) || main.isStatus(GameStatus.FINISH)){
            e.disallow(kicked, prefix + "§cLa partie à déjà commencé.");
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.getInventory().clear();
        player.setFoodLevel(20);

        int connected = getConnected();
        int maxplayers = Bukkit.getMaxPlayers();

        if(main.isStatus(GameStatus.WAITING)) {
            String size = "§7(§a" + connected + "§7/§c" + maxplayers + "§7)";
            e.setJoinMessage(prefix + "§6" + player.getName() + " §7à rejoint la partie. " + size);
            Location spawnWorld = new Location(Bukkit.getWorld("world"), 0.500, 50.5, 0.500, 180, 0);
            player.teleport(spawnWorld);
        }

        if (!main.getPlayers().contains(player)) {
            main.getPlayers().add(player);
        }
        TimerMsgRules msgrules = new TimerMsgRules(player);
        msgrules.runTaskTimer(main, 0,20);

        if(main.isStatus(GameStatus.GAME) || main.isStatus(GameStatus.FINISH)){
            e.setJoinMessage(null);
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(prefix + "§cLa partie est déjà commencé.");
            Location mapgame = new Location(Bukkit.getWorld("world"), 1060.500, 78, 1016.500, 45, 15);
            player.teleport(mapgame);
        }
        else if (main.isStatus(GameStatus.WAITING) && connected == 2) {
            GAutoStart start = new GAutoStart();
            start.runTaskTimer(main, 0, 20);
            main.setStatus(GameStatus.STARTING);
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        int connected = getConnected();
        int maxplayers = Bukkit.getMaxPlayers();

        if(main.getPlayers().contains(player)) main.getPlayers().remove(player);

        String size = "§7(§a" + (connected -1) + "§7/§c" + maxplayers + "§7)";
        e.setQuitMessage(prefix + "§6"+ player.getName() + "§7 à quitté la partie. " + size);
        if(main.isStatus(GameStatus.GAME)) {
            Player winner = main.getPlayers().get(0);
            main.CheckWin(winner);
        }
    }


    public int getConnected(){
        return Bukkit.getOnlinePlayers().size();
    }



}
