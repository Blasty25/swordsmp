package Swordsmp.sword;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SwordListener implements Listener {

    private final CooldownManager cooldownManager;

    public SwordListener(JavaPlugin plugin, CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
        // Register the listener here if needed
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Additional logic can be handled here if needed for all swords
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
