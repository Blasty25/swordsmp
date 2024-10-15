package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            meta.setCustomModelData(1);
            waterSword.setItemMeta(meta);
        }
        return waterSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (hasSpecificSword(player, 1)) {
            player.sendMessage("You have the Water Sword!");
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "water_sword")) {
                    if (isPlayerTouchingWater(player)) {
                        player.addPotionEffect((new PotionEffect(PotionEffectType.RESISTANCE, 30 * 20, 1, true, false)));
                        player.sendMessage("You are touching water! You now have Resistance 2 for 30 seconds!");
                    } else {
                        player.sendMessage("You are not touching water.");
                    }
                    cooldownManager.setCooldown(player, "water_sword", 150);
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "water_sword");
                    player.sendMessage("Water Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }

            // Check if the player is in water


            // Apply Water Breathing effect
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false));

    }

    public boolean hasSpecificSword(Player player, int swordNumber) {
        // Get the player's inventory
        ItemStack[] inventory = player.getInventory().getContents();

        // Loop through the inventory
        for (ItemStack item : inventory) {
            if (item != null && item.getType() == Material.NETHERITE_SWORD) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasCustomModelData()) {
                    // Check if the CustomModelData matches the specified sword number
                    if (meta.getCustomModelData() == swordNumber) {
                        return true; // Player has the sword
                    }
                }
            }
        }
        return false; // Player doesn't have the sword
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

    private boolean hasWaterSwordTag(ItemStack item) {
        // Logic to check for Water Sword tag
        return true; // Replace with actual tag checking logic
    }
}