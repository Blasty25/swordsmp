package Swordsmp.sword.swordtypes;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class waterSword implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasWaterSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Check cooldown
                if (!isOnCooldown(player, "water_sword")) {
                    // Launch opponent with water
                    launchOpponentInWater(player);
                    // Set cooldown
                    setCooldown(player, "water_sword", 150); // 2 min 30 sec cooldown
                    //Make sure to implement the cooldown manager after finishing other swords
                } else {
                    player.sendMessage("Water Sword is on cooldown!");
                }
            }
        }
    }
    public void launchNearbyPlayers(Player player) {
        // Get a list of nearby entities within a certain radius
        double radius = 10.0; // You can adjust this radius as needed
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                // Launch the nearby player 40 blocks into the air
                nearbyPlayer.setVelocity(new Vector(0, 25, 0)); // You can adjust the Y value for more height
                nearbyPlayer.sendMessage("You were launched by the Water Sword!");
            }
        }
    }



}
