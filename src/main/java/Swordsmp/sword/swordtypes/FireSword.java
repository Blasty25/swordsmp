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

public class FireSword implements Listener {
    private final CooldownManager cooldownManager;

    public FireSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createFireSword() {
        ItemStack fireSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = fireSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cFire Sword");
            meta.setCustomModelData(8);
            fireSword.setItemMeta(meta);
        }
        return fireSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasFireSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "fire_sword")) {
                    // Implement Fire Sword ability
                    cooldownManager.setCooldown(player, "fire_sword", 150);
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "fire_sword");
                    player.sendMessage("Fire Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private boolean hasFireSwordTag(ItemStack item) {
        // Logic to check for Fire Sword tag
        return true; // Replace with actual tag checking logic
    }
}
