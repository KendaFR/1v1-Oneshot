package fr.kenda.oneshot.timer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class StopServer extends BukkitRunnable {

    private int timer = 6;

    @Override
    public void run() {
        if(timer == 0){
            Bukkit.getServer().unloadWorld(Bukkit.getWorld("world"), true);
            Bukkit.getServer().reload();
            Bukkit.shutdown();
            cancel();
        }
     timer--;
    }
}
