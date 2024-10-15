package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EarthSword implements Listener {
    private final CooldownManager cooldownManager;

    public EarthSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createEarthSword() {
        ItemStack earthSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = earthSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§aEarth Sword");
            meta.setCustomModelData(10);
            earthSword.setItemMeta(meta);
        }
        return earthSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasEarthSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "earth_sword")) {
                    summonEarthWall(player); // Implement Earth Sword ability
                    cooldownManager.setCooldown(player, "earth_sword", 180); // 180-second cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "earth_sword");
                    player.sendMessage("Earth Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void summonEarthWall(Player player) {
        // Earth Sword ability: summon a temporary wall of blocks
        player.sendMessage("You summoned a protective Earth Wall!");
        // Logic to create a wall or block formation
    }

    private boolean hasEarthSwordTag(ItemStack item) {
        return true; // Implement actual logic to check for Earth Sword tag
    }
}
