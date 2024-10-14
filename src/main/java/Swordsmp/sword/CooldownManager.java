package Swordsmp.sword;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
    // Map to store player's cooldowns per sword
    private final Map<Player, Map<String, Long>> cooldowns = new HashMap<>();

    // Check if the player is on cooldown for a specific sword
    public boolean isOnCooldown(Player player, String swordName) {
        Map<String, Long> playerCooldowns = cooldowns.get(player);
        if (playerCooldowns != null && playerCooldowns.containsKey(swordName)) {
            long remaining = playerCooldowns.get(swordName) - System.currentTimeMillis();
            return remaining > 0; // Return true if the cooldown is still active
        }
        return false; // No cooldown, so it's not on cooldown
    }



    // Get remaining cooldown time in seconds
    public long getCooldownTime(Player player, String swordName) {
        Map<String, Long> playerCooldowns = cooldowns.get(player);
        if (playerCooldowns != null && playerCooldowns.containsKey(swordName)) {
            return (playerCooldowns.get(swordName) - System.currentTimeMillis()) / 1000;
        }
        return 0; // No cooldown active
    }

    // Set cooldown for a specific sword for a player
    public void setCooldown(Player player, String swordName, int durationInSeconds) {
        cooldowns.putIfAbsent(player, new HashMap<>()); // Ensure player entry exists
        long cooldownEndTime = System.currentTimeMillis() + (durationInSeconds * 1000L);
        cooldowns.get(player).put(swordName, cooldownEndTime); // Set sword's cooldown
    }
}
