package Swordsmp.sword.swordtypes;

import Swordsmp.sword.Sword;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Bukkit;

public class SpaceSword {

    private static final int SPECTATOR_DURATION = 15 * 20; // 15 seconds in ticks
    private static final int COOLDOWN_TIME = 300; // 5 minutes cooldown in seconds

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasSpaceSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Check cooldown
                if (!isOnCooldown(player, "space_sword")) {
                    // Activate spectator mode
                    activateSpectatorMode(player);
                    // Set cooldown
                    setCooldown(player, "space_sword", COOLDOWN_TIME);
                } else {
                    player.sendMessage("Space Sword is on cooldown!");
                }
            }
        }
    }

    private void activateSpectatorMode(Player player) {
        // Set the player to spectator mode
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage("You have entered spectator mode for 15 seconds!");

        // Use a delayed task to return the player to survival mode after 15 seconds
        Bukkit.getScheduler().runTaskLater(Sword.getInstance(), () -> {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage("You have returned to your original mode!");
        }, SPECTATOR_DURATION); // 15 seconds in ticks
    }

    public void applySpaceSwordPassives(Player player) {
        // Passive: Resistance I
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 0, false, false));

        // Passive: Strength I
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 0, false, false));
    }

    private boolean hasSpaceSwordTag(ItemStack item) {
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
