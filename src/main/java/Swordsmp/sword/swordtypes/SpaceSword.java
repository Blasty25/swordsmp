package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpaceSword implements Listener {
    private final CooldownManager cooldownManager;

    public SpaceSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasSpaceSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "space_sword")) {
                    turnToSpectator(player);
                    cooldownManager.setCooldown(player, "space_sword", 300); // Set cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "space_sword");
                    player.sendMessage("Space Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void turnToSpectator(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage("You have been turned into a spectator!");
    }

    private boolean hasSpaceSwordTag(ItemStack item) {
        // Logic to check for Space Sword tag
        return true; // Replace with actual tag checking logic
    }
}
