package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class WaterSword implements Listener {
    private final CooldownManager cooldownManager;

    public WaterSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

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

        if (item.getType() == Material.NETHERITE_SWORD && hasWaterSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "water_sword")) {
                    launchNearbyPlayers(player);
                    cooldownManager.setCooldown(player, "water_sword", 150);
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "water_sword");
                    player.sendMessage("Water Sword is on cooldown for " + remainingTime + " more seconds!");
                }

                // Check if the player is in water
                if (isPlayerTouchingWater(player)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 0, true, false));
                    player.sendMessage("You are touching water! You now have Dolphins Grace!");
                } else {
                    player.sendMessage("You are not touching water.");
                }

                // Apply Water Breathing effect
                player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false));
            }
        }
    }

    private boolean isPlayerTouchingWater(Player player) {
        // Get the player's location
        Location playerLocation = player.getLocation();

        // Get the player's bounding box to check if they are in contact with water
        for (double x = -0.5; x <= 0.5; x += 0.5) {
            for (double z = -0.5; z <= 0.5; z += 0.5) {
                // Check the block at the player's feet and the surrounding blocks
                Block block = playerLocation.clone().add(x, -1, z).getBlock();
                if (block.getType() == Material.WATER) {
                    return true; // The player is touching water
                }
            }
        }
        return false; // The player is not touching water
    }

    private void launchNearbyPlayers(Player player) {
        double radius = 5.0;
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.setVelocity(new Vector(0, 1, 0).multiply(25));
                nearbyPlayer.sendMessage("You were launched by the Water Sword!");
            }
        }
    }

    private boolean hasWaterSwordTag(ItemStack item) {
        // Logic to check for Water Sword tag
        return true; // Replace with actual tag checking logic
    }
}
