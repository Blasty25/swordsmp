package Swordsmp.sword.swordtypes;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Creature;

import java.util.Collection;

public class earthSword implements Listener {

    private final CooldownManager cooldownManager;

    public earthSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasEarthSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Check cooldown
                if (!cooldownManager.isOnCooldown(player, "earth_sword")) {
                    // Encircle the player with obsidian
                    encircleWithObsidian(player);
                    // Set cooldown
                    cooldownManager.setCooldown(player, "earth_sword", 300); // 5-minute cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "earth_sword");
                    player.sendMessage("Earth Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }

        // Apply passive abilities
        applyEarthSwordPassives(player);
    }

    private void encircleWithObsidian(Player player) {
        // Get the player's current location and encircle them with obsidian
        Block block = player.getLocation().getBlock();
        int[][] offsets = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {-1, 1}, {1, -1}, {-1, -1}
        };

        for (int[] offset : offsets) {
            Block relative = block.getRelative(offset[0], 0, offset[1]);
            relative.setType(Material.OBSIDIAN);
        }

        // Place obsidian above the player for a full encirclement
        block.getRelative(0, 1, 0).setType(Material.OBSIDIAN);
        block.getRelative(0, -1, 0).setType(Material.OBSIDIAN);
        player.sendMessage("You encircled yourself with obsidian!");
    }



    private void applyEarthSwordPassives(Player player) {
        // Passive: Speed 1
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));

        // Passive: Mobs do not attack the player
        Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Creature) { // Only creatures can have a target
                Creature mob = (Creature) entity;
                if (mob.getType() != EntityType.PLAYER) { // Avoid players
                    mob.setTarget(null);  // Cancel mob targeting on the player
                }
            }
        }
    }




    private boolean hasEarthSwordTag(ItemStack item) {
        // Implement your logic to check if the item has the Earth Sword tag
        return true; // Placeholder
    }
}
