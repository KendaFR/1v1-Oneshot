package fr.kenda.oneshot;

import fr.kenda.oneshot.GameEvents.*;
import fr.kenda.oneshot.GameStatus.GameStatus;
import fr.kenda.oneshot.timer.PlayerKilled;
import fr.kenda.oneshot.timer.StopServer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin {

    private static Main instance;
    private GameStatus status;
    public static String prefix = "§7[§a1v1-OneShot§7] ";
    public List<Player> players = new ArrayList<>();
    public Map<Player, Integer> points = new HashMap<>();
    private List<Location> spawns = new ArrayList<>();
    public List<Player> death = new ArrayList<>();
    public int maxpoints = 10;

    @Override
    public void onEnable() {
        instance = this;
        registerEvents();
        getLocation();

        getServer().getConsoleSender().sendMessage(prefix + "§aPlugin started");
        this.setStatus(GameStatus.WAITING);
    }

    private void getLocation(){
        //Ajout des spawns de la map
        World world = Bukkit.getWorld("world");
        spawns.add(new Location(world, 1024.500, 76, 1108.500, 180, 0));
        spawns.add(new Location(world, 1063.500, 66, 1106.500, 90, 0));
        spawns.add(new Location(world, 1066.500, 66, 1041.500, 90, 0));
        spawns.add(new Location(world, 1041.500, 66, 1003.500, 0, 0));
        spawns.add(new Location(world, 1010.500, 66, 1004.500, -90, 0));
        spawns.add(new Location(world, 1010.500, 64, 1045.500, 0, 0));
        spawns.add(new Location(world, 1034.500, 68, 1034.500, -180, 0));
        spawns.add(new Location(world, 1043.500, 68, 1066.500, -90, 0));
        spawns.add(new Location(world, 1028.500, 68, 1036.500, 0, 0));
        spawns.add(new Location(world, 1033.500, 73, 1033.500, 0, 0));
        spawns.add(new Location(world, 1011.500, 70, 1038.500, 0, 0));
        spawns.add(new Location(world, 1017.500, 74, 1083.500, -90, 0));
        spawns.add(new Location(world, 1053.500, 105, 1034.500, -180, 0));
        spawns.add(new Location(world, 1053.500, 154, 1065.500, -180, 0));
        spawns.add(new Location(world, 1043.500, 138, 1068.500, -90, 0));
        spawns.add(new Location(world, 1043.500, 138, 1068.500, -90, 0));
        spawns.add(new Location(world, 1020.500, 133, 1037.500, -180, 0));
        world.setAutoSave(false);

    }


    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new GameJoinLeftEvents(), this);
        Bukkit.getPluginManager().registerEvents(new GameDamageEvents(), this);
        Bukkit.getPluginManager().registerEvents(new GameBlockEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public boolean isStatus(GameStatus status) {
        return this.status == status;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        World world = Bukkit.getWorld("world");
        Bukkit.getServer().unloadWorld(world, true);
        Bukkit.getServer().reload();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public Location getRandomLocation(List<Location> spawns) {
        Random random = new Random();
        int number = random.nextInt(spawns.size());
        System.out.println("Valeur retourné : " + number);
        return spawns.get(number);
    }

    public List<Player> getDeath() {
        return death;
    }

    public void killed(Player player) {
        player.sendMessage(prefix + "§7Tu viens de perdre ton killStreak");
        this.getPoints().put(player, 0);
        this.getDeath().add(player);
        player.setGameMode(GameMode.SPECTATOR);
        PlayerInventory pi = new PlayerInventory();
        pi.GiveInventorySpectator(player);

        PlayerKilled killed = new PlayerKilled(player);
        killed.runTaskTimer(this, 0, 20);
    }

    public void CheckWin(Player player){
        if(getPoints().get(player) >= maxpoints){
            Bukkit.broadcastMessage(prefix + "§aLe joueur §6" + player.getName() + " §aviens de gagner la partie.");
            for(Player pls : getPlayers()){
                if(getPoints().get(pls) <= (maxpoints-1)){
                    pls.sendMessage(prefix + "§cTu as perdu la partie.");
                }
            }
            player.sendMessage(prefix + "§bTu viens de gagner la partie !");

            StopServer stop = new StopServer();
            stop.runTaskTimer(getInstance(), 0, 20);
        }
        else if (this.getPlayers().size() == 1) {
            Player winner = players.get(0);
            winner.sendMessage(prefix + "§aTu as gagner la partie !");

            StopServer stop = new StopServer();
            stop.runTaskTimer(getInstance(), 0, 20);
        }
    }

    public Map<Player, Integer> getPoints() {
        return points;
    }
}
