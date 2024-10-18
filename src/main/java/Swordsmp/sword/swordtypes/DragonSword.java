package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import Swordsmp.sword.Sword;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.ArrayList;
import java.util.List;

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
            meta.setCustomModelData(6);
            dragonSword.setItemMeta(meta);
        }
        return dragonSword;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null && item.getType() == Material.NETHERITE_SWORD && hasSpecificSword(player, 6)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                handleAbility(player);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(player.getMaxHealth() == 60.0) player.resetMaxHealth();
    }

    public void handleAbility(Player player) {
        if (!cooldownManager.isOnCooldown(player, "dragon_sword")) {
           //Main ability
            player.setMaxHealth(60);
            player.setHealth(Math.min(player.getHealth(),60));
            Location playerLocation = player.getLocation();
            double radius = 10.0; // Adjust radius as needed
            EntityManipulator entityManipulator = new EntityManipulator();
            entityManipulator.manipulateNearbyEntities(playerLocation, radius);
            cooldownManager.setCooldown(player, "dragon_sword", 150);
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "dragon_sword");
            player.sendMessage("Dragon Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 2, true, false));

    }
    private void increasePlayerHearts(Player player) {
        // Air Sword ability: increase player's max health
        player.setMaxHealth(60); // Increase to 20 hearts
        player.setHealth(Math.min(player.getHealth(), 60)); // Ensure current health is not above max
        player.sendMessage("Your health increased with the Dragon Sword!");
    }

    public class EntityManipulator {

        // Method to manipulate nearby entities
        public void manipulateNearbyEntities(Location location, double radius) {
            // Get nearby entities
            List<Entity> nearbyEntities = new ArrayList<>(location.getWorld().getNearbyEntities(location, radius, radius, radius));

            for (Entity entity : nearbyEntities) {
                // Check if the entity is a LivingEntity
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    // Check if the entity is a Player
                    if (livingEntity instanceof Player) {
                        Player player = (Player) livingEntity;
                        // Stop player movement
                        player.setWalkSpeed(0);
                        player.setFlySpeed(0);
                    } else {
                        // Disable AI for non-player living entities
                        livingEntity.setAI(false);
                    }
                }
            }
        }
    }
    

    private Boolean hasSpecificSword(Player player, int modelData){
        ItemStack item = player.getInventory().getItemInMainHand();
        return item != null  && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }
}