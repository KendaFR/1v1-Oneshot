package fr.kenda.oneshot.GameEvents;

import fr.kenda.oneshot.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory {

    Player player;

    public void Inventory(Player player){
        this.player = player;
    }

    public void GiveInventoryPlay(Player player) {
        ItemStack sword = new ItemBuilder(Material.IRON_SWORD).setName("§6Épée du OneShot").toItemStack();
        ItemStack bow = new ItemBuilder(Material.BOW).setName("§6Arc du OneShot").toItemStack();
        ItemStack arrow = new ItemBuilder(Material.ARROW).setName("§6Flèche du OneShot").toItemStack();

        player.getInventory().clear();
        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, bow);
        player.getInventory().setItem(8, arrow);
        player.updateInventory();
    }

    public void GiveInventorySpectator(Player player){
        ItemStack TeleportPlayer = new ItemBuilder(Material.NETHER_STAR).setName("§cTéléportation à un joueur").toItemStack();

        player.getInventory().clear();
        player.getInventory().setItem(4, TeleportPlayer);
        player.updateInventory();
    }


}
