package Swordsmp.sword.swordtypes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class FireSword implements Listener {

    private final CooldownManager cooldownManager;

    public FireSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasFireSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Check cooldown
                if (!cooldownManager.isOnCooldown(player, "fire_sword")) {
                    // Activate Fire Sword ability: Shoot player forward
                    shootPlayerForward(player);
                    // Set cooldown
                    cooldownManager.setCooldown(player, "fire_sword", 150); // 2 min 30 sec cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "fire_sword");
                    player.sendMessage("Fire Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }

        // Apply passive abilities
        applyFireSwordPassives(player);
    }

    private void shootPlayerForward(Player player) {
        // Shoot the player forward by applying velocity
        Vector forward = player.getLocation().getDirection().multiply(2); // Adjust the multiplier to change the distance
        player.setVelocity(forward);
        player.sendMessage("You used the Fire Sword's ability!");
    }

    private void applyFireSwordPassives(Player player) {
        // Infinite Fire Resistance
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));

        // If the player is in lava, give them Regeneration 15
        if (player.getLocation().getBlock().getType() == Material.LAVA) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 14, false, false)); // 5-second regeneration
            player.sendMessage("You are in lava and regenerating health!");
        }
    }

    private boolean hasFireSwordTag(ItemStack item) {
        // Implement your logic to check if the item has the Fire Sword tag
        return true; // Placeholder
    }
}

