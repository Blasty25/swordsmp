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

public class FireSword implements Listener {
    private final CooldownManager cooldownManager;

    public FireSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createFireSword() {
        ItemStack fireSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = fireSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cFire Sword");
            meta.setCustomModelData(2);
            fireSword.setItemMeta(meta);
        }
        return fireSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null && item.hasItemMeta()) { // Check if item and item meta are not null
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasCustomModelData()) { // Check if meta and custom model data exist
                int modelData = meta.getCustomModelData();
                if (modelData == 1) {
                    // This is the Water Sword (customModelData == 1)
                    player.sendMessage("You have the Water Sword!");
                } else if (modelData == 2) {
                    // This is the Fire Sword (customModelData == 2)
                    player.sendMessage("You have the Fire Sword!");
                }
                // Continue with other customModelData checks as needed
            }
        }

        if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 2) {
            player.sendMessage("You have the Fire Sword!");
                if (!cooldownManager.isOnCooldown(player, "fire_sword")) {
                    // Implement Fire Sword ability
                    if (isPlayerTouchingLava(player)) {
                        player.addPotionEffect((new PotionEffect(PotionEffectType.REGENERATION, 15 * 20, 14)));
                        player.sendMessage("You are touching Lava! You now have Regeneration for 15 seconds!");
                    } else {
                        player.sendMessage("You are not touching lava.");
                    }
                    cooldownManager.setCooldown(player, "fire_sword", 150);
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "fire_sword");
                    player.sendMessage("Fire Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));

        }
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


        private boolean isPlayerTouchingLava (Player player){
            // Get the player's location
            Location playerLocation = player.getLocation();

            // Get the player's bounding box to check if they are in contact with lava
            for (double x = -0.5; x <= 0.5; x += 0.5) {
                for (double z = -0.5; z <= 0.5; z += 0.5) {
                    // Check the block at the player's feet and the surrounding blocks
                    Block block = playerLocation.clone().add(x, -1, z).getBlock();
                    if (block.getType() == Material.LAVA) {
                        return true; // The player is touching lava
                    }
                }
            }
            return false; // The player is not touching lava
        }

}