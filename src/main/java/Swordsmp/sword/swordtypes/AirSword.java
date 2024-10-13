package Swordsmp.sword.swordtypes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AirSword {

    private static final int COOLDOWN_TIME = 300; // 5 minutes cooldown in seconds
    private static final int MAX_HEALTH = 30; // 15 hearts

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasAirSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Check cooldown
                if (!isOnCooldown(player, "air_sword")) {
                    // Launch player in the air
                    launchPlayerInAir(player);
                    // Set cooldown
                    setCooldown(player, "air_sword", COOLDOWN_TIME);
                } else {
                    player.sendMessage("Air Sword is on cooldown!");
                }
            }
        }
    }

    private void launchPlayerInAir(Player player) {
        // Set the player's velocity to launch them into the air
        player.setVelocity(new Vector(0, 80, 0)); // Adjust the Y value for more height
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1, false, false));
        player.sendMessage("You have been launched into the air!");
    }

    public void applyAirSwordPassives(Player player) {
        // Set player's max health to 15 hearts (30 health points)
        player.setMaxHealth(MAX_HEALTH);
        player.setHealth(MAX_HEALTH); // Restore health to max

        // Grant Strength V during thunderstorms
        if (player.getWorld().hasStorm()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 4, false, false));
            player.sendMessage("You feel a surge of strength!");
        }
    }

    private boolean hasAirSwordTag(ItemStack item) {
        // Implement your tag checking logic
        return true; // Replace with actual tag check
    }

    private boolean isOnCooldown(Player player, String ability) {
        // Implement your cooldown checking logic
        return false; // Replace with actual cooldown check
    }

    private void setCooldown(Player player, String ability, int seconds) {
        // Implement your cooldown setting logic
    }
}
