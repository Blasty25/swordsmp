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

public class AirSword implements Listener {
    private final CooldownManager cooldownManager;

    public AirSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createAirSword() {
        ItemStack airSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = airSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§fAir Sword");
            meta.setCustomModelData(12);
            airSword.setItemMeta(meta);
        }
        return airSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasAirSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "air_sword")) {
                    increasePlayerHearts(player); // Implement Air Sword ability
                    cooldownManager.setCooldown(player, "air_sword", 240); // 240-second cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "air_sword");
                    player.sendMessage("Air Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void increasePlayerHearts(Player player) {
        // Air Sword ability: increase player's max health
        player.setMaxHealth(30); // Increase to 15 hearts
        player.sendMessage("Your health increased with the Air Sword!");
    }

    private boolean hasAirSwordTag(ItemStack item) {
        return true; // Implement actual logic to check for Air Sword tag
    }
}
