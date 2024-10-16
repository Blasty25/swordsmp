package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Fire;
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

public class FireSword implements Listener {
    private final CooldownManager cooldownManager;
    private static FireSword instance;

    public FireSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }
    public static FireSword getInstance(CooldownManager cooldownManager) {
        if (instance == null) {
            instance = new FireSword(cooldownManager);
        }
        return instance;
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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.getType() == Material.NETHERITE_SWORD && hasSpecificSword(player, 2)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                handleAbility(player);
            }
        }
    }

    public void handleAbility(Player player) {
        if (!cooldownManager.isOnCooldown(player, "fire_sword")) {
            if (isPlayerTouchingLava(player)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 15 * 20, 1));
                player.sendMessage("You are touching Lava! You now have Regeneration for 15 seconds!");
            } else {
                player.sendMessage("You are not touching lava.");
            }
            shootPlayerForward(player);

            cooldownManager.setCooldown(player, "fire_sword", 150);
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
        double distance = 30.0; // Distance to shoot forward
        Vector velocity = direction.multiply(distance); // Scale direction vector by distance
        player.setVelocity(velocity); // Set the player's velocity

        player.sendMessage("You have been shot forward with the Fire Sword!");
    }


    private boolean isPlayerTouchingLava(Player player) {
        Block block = player.getLocation().subtract(0, 1, 0).getBlock();
        return block.getType() == Material.LAVA;
    }

    private boolean hasSpecificSword(Player player, int modelData) {
        ItemStack item = player.getInventory().getItemInMainHand();
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }
}
