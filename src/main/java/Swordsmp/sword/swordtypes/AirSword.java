package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;   //Having issues about resetting players health back to 20 points after death
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class AirSword implements Listener {
    private final CooldownManager cooldownManager;


    public AirSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createAirSword() {
        ItemStack airSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = airSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§eAir Sword");
            meta.setCustomModelData(5);
            airSword.setItemMeta(meta);
        }
        return airSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand(); // Ensure current health is not above max

        if (item != null && item.getType() == Material.NETHERITE_SWORD && hasSpecificSword(player, 5)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                handleAbility(player);
            }
        }
    }

    public void handleAbility(Player player) {
        player.setMaxHealth(20); // Increase to 20 hearts
        player.setHealth(Math.min(player.getHealth(), 20));
        if (!cooldownManager.isOnCooldown(player, "air_sword")) {
            launchNearbyPlayers(player);
            increasePlayerHearts(player);
            cooldownManager.setCooldown(player, "Air_sword", 150);
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "air_sword");
            player.sendMessage("Air Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));// Increase to 20 hearts
    }

    private void increasePlayerHearts(Player player) {
        // Air Sword ability: increase player's max health
        player.setMaxHealth(30); // Increase to 20 hearts
        player.setHealth(Math.min(player.getHealth(), 30)); // Ensure current health is not above max
        player.sendMessage("Your health increased with the Air Sword!");
    }

    private void launchNearbyPlayers(Player player) {
        double radius = 5.0;
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.setVelocity(new Vector(0, 1, 0).multiply(25));
                nearbyPlayer.sendMessage("You were launched by the Air Sword!");
            }
        }
    }

    private Boolean hasSpecificSword(Player player, int modelData){
        ItemStack item = player.getInventory().getItemInMainHand();
        return item != null  && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }

}