package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import Swordsmp.sword.Sword;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
            meta.setCustomModelData(2); // Custom Model Data for Fire Sword
            fireSword.setItemMeta(meta);
        }
        return fireSword;
    }



    public void handleAbility(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Sword.class), new Runnable() {
            @Override
            public void run() {
                if (isPlayerTouchingLava(player)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 15 * 20, 14));
                    player.sendMessage("You are touching Lava! You now have Regeneration for 15 seconds!");
                }
            }
        });
        if (!cooldownManager.isOnCooldown(player, "fire_sword")) {

            shootPlayerForward(player);

            cooldownManager.setCooldown(player, "fire_sword", 50);
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "fire_sword");
            player.sendMessage("Fire Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
    }
    private void shootPlayerForward(Player player) {
        // Get the player's location and direction
        Location playerLocation = player.getLocation();
        Vector direction = playerLocation.getDirection().normalize(); // Normalize to unit vector

        // Set the player's velocity to shoot them forward 20 blocks
        double distance = 15.0; // Distance to shoot forward
        Vector velocity = direction.multiply(distance); // Scale direction vector by distance
        player.setVelocity(velocity); // Set the player's velocity

        player.sendMessage("You have been shot forward with the Fire Sword!");
    }


    private boolean isPlayerTouchingLava(Player player) {
        Block block = player.getLocation().subtract(0, 1, 0).getBlock();
        return block.getType() == Material.LAVA;
    }

    public boolean hasSpecificSword(ItemStack item, int modelData) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }
}
