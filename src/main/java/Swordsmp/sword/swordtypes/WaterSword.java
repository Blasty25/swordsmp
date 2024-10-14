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

    public WaterSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    // Method to create the Water Sword item
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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the player is holding the Water Sword
        if (item.getType() == Material.NETHERITE_SWORD && hasWaterSwordTag(item)) {
            // Check if it's a right-click action
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Check if the Water Sword ability is on cooldown
                if (!cooldownManager.isOnCooldown(player, "water_sword")) {
                    // Launch nearby players with the Water Sword ability
                    launchNearbyPlayers(player);
                    // Set the cooldown for the Water Sword (150 seconds in this case)
                    cooldownManager.setCooldown(player, "water_sword", 150);
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "water_sword");
                    player.sendMessage("Water Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    // Method to launch nearby players upwards
    private void launchNearbyPlayers(Player player) {
        double radius = 5.0; // Adjust the radius as needed
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.setVelocity(new Vector(0, 1, 0).multiply(25)); // Launch the player upwards
                nearbyPlayer.sendMessage("You were launched by the Water Sword!");
            }
        }
    }

    // Method to check if the item is the Water Sword (can be modified to check tags or other identifiers)
    private boolean hasWaterSwordTag(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && "§bWater Sword".equals(meta.getDisplayName()); // Example check based on display name
    }
}
