package Swordsmp.sword.swordtypes;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
    private final Map<Player, Map<String, Long>> cooldowns = new HashMap<>();

    public boolean isOnCooldown(Player player, String swordName) {
        Map<String, Long> playerCooldowns = cooldowns.get(player);
        if (playerCooldowns != null && playerCooldowns.containsKey(swordName)) {
            long remaining = playerCooldowns.get(swordName) - System.currentTimeMillis();
            return remaining > 0;
        }
        return false;
    }

    public long getCooldownTime(Player player, String swordName) {
        Map<String, Long> playerCooldowns = cooldowns.get(player);
        if (playerCooldowns != null && playerCooldowns.containsKey(swordName)) {
            return (playerCooldowns.get(swordName) - System.currentTimeMillis()) / 1000;
        }
        return 0;
    }

    public void setCooldown(Player player, String swordName, int durationInSeconds) {
        cooldowns.putIfAbsent(player, new HashMap<>());
        long cooldownEndTime = System.currentTimeMillis() + (durationInSeconds * 1000);
        cooldowns.get(player).put(swordName, cooldownEndTime);
    }
}
