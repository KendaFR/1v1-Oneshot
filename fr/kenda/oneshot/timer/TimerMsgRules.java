package fr.kenda.oneshot.timer;

import fr.kenda.oneshot.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerMsgRules extends BukkitRunnable {

    int timer = 3;
    Player player;
    String prefix = Main.prefix;

    public TimerMsgRules(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if(timer == 0){
            player.sendMessage("§m§7========== " + prefix + "§m§7==========");
            player.sendMessage("§bLe but du jeu est simple, chaque joueur reçois une épée, un arc et des flèches. Le joueur à effectué 10 kills d'affilé gagne.");
            player.sendMessage("§c§nAttention, si vous mourrez, votre killstreak est remis à 0");
            player.sendMessage("§4§lBonne chance !");
            player.sendMessage("§m§7========== " + prefix + "§m§7==========");
            cancel();
        }
        timer--;
    }
}

