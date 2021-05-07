package fr.kenda.oneshot.timer;

import fr.kenda.oneshot.GameEvents.PlayerInventory;
import fr.kenda.oneshot.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerKilled extends BukkitRunnable {

    String prefix = Main.prefix;
    Main main = Main.getInstance();
    Player player;

    private int timer = 4;

    public PlayerKilled(Player player) {
        this.player = player;
    }

    @Override
    public void run(){
            if (timer == 3 || timer == 2 || timer == 1) {
                player.sendMessage(prefix + "§cRéapparition dans §6" + timer + " §6secondes.");
            }
            if (timer == 0) {
                Location spawnGame = main.getRandomLocation(main.getSpawns());
                player.teleport(spawnGame);
                player.setGameMode(GameMode.ADVENTURE);
                player.setHealth(20);
                if (main.getDeath().contains(player)) main.getDeath().remove(player);

                PlayerInventory pi = new PlayerInventory();
                pi.GiveInventoryPlay(player);
                cancel();
            }
        timer--;
    }
}
