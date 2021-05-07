package fr.kenda.oneshot.timer;

import fr.kenda.oneshot.GameEvents.PlayerInventory;
import fr.kenda.oneshot.GameStatus.GameStatus;
import fr.kenda.oneshot.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GAutoStart extends BukkitRunnable {

    private int timer = 16;
    String prefix = Main.prefix;
    Main main = Main.getInstance();

    @Override
    public void run() {
        for(Player player : main.getPlayers()){
            player.setLevel(timer);
        }

        if(timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage(prefix + "§7Lancement du jeu dans §6" + timer + " §7secondes.");
            for(Player player : Bukkit.getOnlinePlayers()){
                player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 5, 1);
            }
        }

        if(timer == 0){
            Bukkit.broadcastMessage(prefix + "§7Lancement de la partie !");
            main.setStatus(GameStatus.GAME);

            for(int i = 0; i < main.getPlayers().size(); i++){
                Player player = main.getPlayers().get(i);
                System.out.println(player.getName());
                Location spawnGame = main.getRandomLocation(main.getSpawns());
                System.out.println(spawnGame);
                player.teleport(spawnGame);
                main.getPoints().put(player, 0);

                PlayerInventory pi = new PlayerInventory();
                pi.GiveInventoryPlay(player);
            }

            GameCycle gamecycle = new GameCycle();
            gamecycle.runTaskTimer(main, 0,20);
        }
        timer--;

    }
}
