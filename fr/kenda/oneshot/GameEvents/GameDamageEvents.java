package fr.kenda.oneshot.GameEvents;

import fr.kenda.oneshot.GameStatus.GameStatus;
import fr.kenda.oneshot.Main;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameDamageEvents implements Listener {

    String prefix = Main.prefix;
    Main main = Main.getInstance();
    int maxpoints = main.maxpoints;

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (main.isStatus(GameStatus.WAITING) || main.isStatus(GameStatus.STARTING)) {
            e.setCancelled(true);
        }
        else{
            e.setDamage(0);
        }
    }


    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        if (main.isStatus(GameStatus.WAITING) || main.isStatus(GameStatus.STARTING)) {
            e.setCancelled(true);
            return;
        }
        Entity victimEntity = e.getEntity();
        Entity damager = e.getDamager();
        Player killer = null;

        if (damager instanceof Player && victimEntity instanceof Player) {

            killer = (Player) damager;
            Player victim = (Player) victimEntity;

            if (killer.getItemInHand().getType() == Material.IRON_SWORD) {

                main.getPoints().put(killer, (main.getPoints().get(killer) + 1));
                int points = main.getPoints().get(killer);
                killer.sendMessage(prefix + "§cTu viens de tuer §6" + victim.getName());
                killer.sendMessage(prefix + "§cTu as un killStreak de §6" + points + "§7/§c" + maxpoints);
                main.killed(victim);
                main.CheckWin(killer);
            } else {
                e.setCancelled(true);
                killer.sendMessage(prefix + "Vous ne pouvez pas tuer le joueur à la main");
                victim.setHealth(victim.getMaxHealth());
            }
        } else if (damager instanceof Arrow && victimEntity instanceof Player) {
            Arrow arrow = (Arrow) damager;
            Player victim = (Player) victimEntity;
            killer = (Player) arrow.getShooter();

            main.getPoints().put(killer, (main.getPoints().get(killer) + 1));
            int points = main.getPoints().get(killer);
            killer.sendMessage(prefix + "§cTu viens de tuer §6" + victim.getName());
            killer.sendMessage(prefix + "§cTu as un killStreak de §6" + points + "§7/§c " + maxpoints);
            main.killed(victim);
            main.CheckWin(killer);
        }
    }
}

