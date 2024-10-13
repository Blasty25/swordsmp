package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AirSword implements Listener {
    private final CooldownManager cooldownManager;

    public AirSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasAirSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "air_sword")) {
                    launchPlayerInAir(player);
                    cooldownManager.setCooldown(player, "air_sword", 150); // Set cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "air_sword");
                    player.sendMessage("Air Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void launchPlayerInAir(Player player) {
        player.setVelocity(player.getLocation().getDirection().multiply(0).setY(2)); // Adjust the Y component to launch the player up
        player.sendMessage("You have been launched into the air!");
    }

    private boolean hasAirSwordTag(ItemStack item) {
        // Logic to check for Air Sword tag
        return true; // Replace with actual tag checking logic
    }
}
