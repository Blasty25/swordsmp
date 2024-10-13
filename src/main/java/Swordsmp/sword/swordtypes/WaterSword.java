package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;

public class WaterSword implements Listener {
    private final CooldownManager cooldownManager;
    public ItemStack createWaterSword() {
        ItemStack waterSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = waterSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bWater Sword");
            meta.setCustomModelData(7);
            waterSword.setItemMeta(meta);
        }
        return waterSword;
    }
    public WaterSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasWaterSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "water_sword")) {
                    launchNearbyPlayers(player); // Implement your ability
                    cooldownManager.setCooldown(player, "water_sword", 150); // Set cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "water_sword");
                    player.sendMessage("Water Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void launchNearbyPlayers(Player player) {
        double radius = 5.0; // Adjust as needed
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.setVelocity(new Vector(0, 1, 0).multiply(25)); // Launch upwards
                nearbyPlayer.sendMessage("You were launched by the Water Sword!");
            }
        }
    }

    private boolean hasWaterSwordTag(ItemStack item) {
        // Logic to check for Water Sword tag
        return true; // Replace with actual tag checking logic
    }
}
