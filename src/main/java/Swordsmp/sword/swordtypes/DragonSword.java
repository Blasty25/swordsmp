package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DragonSword implements Listener {
    private final CooldownManager cooldownManager;

    public DragonSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createDragonSword() {
        ItemStack dragonSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = dragonSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§5Dragon Sword");
            meta.setCustomModelData(9);
            dragonSword.setItemMeta(meta);
        }
        return dragonSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasDragonSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "dragon_sword")) {
                    lockTarget(player); // Implement ability: lock a nearby player
                    cooldownManager.setCooldown(player, "dragon_sword", 600); // 10-minute cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "dragon_sword");
                    player.sendMessage("Dragon Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void lockTarget(Player player) {
        // Dragon Sword ability: lock target in place with dragon breath
        LivingEntity target = findNearbyEntity(player); // Implement method to find target
        if (target != null) {
            target.setAI(false); // Freeze target in place
            player.sendMessage("You locked " + target.getName() + " with the Dragon Sword!");
        }
    }

    private LivingEntity findNearbyEntity(Player player) {
        // Implement logic to find the nearest target player or mob
        return null;
    }

    private boolean hasDragonSwordTag(ItemStack item) {
        return true; // Implement actual logic to check for Dragon Sword tag
    }
}
