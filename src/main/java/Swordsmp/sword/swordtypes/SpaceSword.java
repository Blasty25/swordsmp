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

public class SpaceSword implements Listener {
    private final CooldownManager cooldownManager;

    public SpaceSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createSpaceSword() {
        ItemStack spaceSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = spaceSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§9Space Sword");
            meta.setCustomModelData(11);
            spaceSword.setItemMeta(meta);
        }
        return spaceSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasSpaceSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "space_sword")) {
                    teleportPlayer(player); // Implement teleport ability
                    cooldownManager.setCooldown(player, "space_sword", 300); // 300-second cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "space_sword");
                    player.sendMessage("Space Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void teleportPlayer(Player player) {
        // Space Sword ability: teleport player to a random location
        player.teleport(player.getLocation().add(0, 5, 0)); // Example teleport
        player.sendMessage("You teleported using the Space Sword!");
    }

    private boolean hasSpaceSwordTag(ItemStack item) {
        return true; // Implement actual logic to check for Space Sword tag
    }
}
