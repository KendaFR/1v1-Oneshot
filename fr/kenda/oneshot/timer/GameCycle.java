package fr.kenda.oneshot.timer;

import fr.kenda.oneshot.Main;
import fr.kenda.oneshot.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCycle extends BukkitRunnable {


    private int timer = 61;
    String prefix = Main.prefix;

    @Override
    public void run() {
        if (timer == 30 || timer == 10) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage(prefix + "§7Nouvelle flèche dans §6" + timer + " §6secondes.");
            }
        }
        if (timer == 0) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                ItemStack arrow = new ItemBuilder(Material.ARROW).setName("§6Flèche du OneShot").toItemStack();

                if(players.getGameMode() == GameMode.SPECTATOR){
                    players.sendMessage(prefix +  "Tu es mort, tu n'as donc pas eu ta flèche.");
                } else {
                    if (players.getInventory().getItem(8) == null) {
                        players.getInventory().setItem(8, arrow);
                        players.sendMessage(prefix + "§7Vous avez reçu §6une nouvelle flèche ! §o§7(Faites-en bonne usage :3)");
                    }
                    else if (players.getInventory().getItem(8).getAmount() >= 5) {
                        players.sendMessage(prefix + "§cTu devrais utilisé un peu tes flèches avant d'en recevoir d'autres :3");
                    } else {
                        players.getInventory().addItem(arrow);
                        players.sendMessage(prefix + "§7Vous avez reçu §6une nouvelle flèche ! §o§7(Faites-en bonne usage :3)");
                    }
                }
            }
            timer = 61;
        }
        timer--;
    }
}




