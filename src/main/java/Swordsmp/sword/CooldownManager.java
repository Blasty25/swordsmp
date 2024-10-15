package Swordsmp.sword;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
    private final Map<Player, Map<String, Long>> cooldowns = new HashMap<>();

    public boolean isOnCooldown(Player player, String ability) {
        return cooldowns.containsKey(player) && cooldowns.get(player).containsKey(ability) &&
                cooldowns.get(player).get(ability) > System.currentTimeMillis();
    }

    public long getCooldownTime(Player player, String ability) {
        if (isOnCooldown(player, ability)) {
            return (cooldowns.get(player).get(ability) - System.currentTimeMillis()) / 1000;
        }
        return 0;
    }

    public void setCooldown(Player player, String ability, int seconds) {
        cooldowns.putIfAbsent(player, new HashMap<>());
        cooldowns.get(player).put(ability, System.currentTimeMillis() + (seconds * 1000));
    }
}
