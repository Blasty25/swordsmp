package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import Swordsmp.sword.Sword;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DragonSword implements Listener {

    private final CooldownManager cooldownManager;
    private int hitCount = 0;

    public DragonSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    // This method applies the passive abilities when a player equips the Dragon Sword
    public void applyDragonSwordPassives(Player player) {
        // Passive: Resistance 3 (infinite)
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 2, false, false));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasDragonSwordTag(item)) {
            applyDragonSwordPassives(player); // Apply passives when interacting
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item.getType() == Material.NETHERITE_SWORD && hasDragonSwordTag(item)) {
                hitCount++;

                // Check cooldown
                if (!cooldownManager.isOnCooldown(player, "dragon_sword")) {
                    LivingEntity target = (LivingEntity) event.getEntity();

                    // Launch every 5th hit
                    if (hitCount % 5 == 0) {
                        launchPlayer(target);
                        // Apply extra damage
                        event.setDamage(event.getDamage() * 1.2); // 20% more damage
                        lockTargetInPlace(target); // Lock the target in place
                    }

                    // Set cooldown
                    cooldownManager.setCooldown(player, "dragon_sword", 600); // 10-minute cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "dragon_sword");
                    player.sendMessage("Dragon Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    // This method locks the target in place and surrounds them with dragon breath
    public void lockTargetInPlace(LivingEntity target) {
        target.setVelocity(new Vector(0, 0, 0)); // Lock the target in place
        target.sendMessage("You are locked in place!");

        // Surround target with dragon breath
        new BukkitRunnable() {
            int duration = 10; // 10 seconds of effect
            @Override
            public void run() {
                if (duration <= 0) {
                    cancel(); // Stop after 10 seconds
                    target.sendMessage("You are free!");
                } else {
                    // Implement logic to apply dragon breath effect here
                    target.sendMessage("You are surrounded by dragon breath!");
                    duration--;
                }
            }
        }.runTaskTimer(Sword.getInstance(), 0, 20); // Run every second
    }

    private void launchPlayer(LivingEntity target) {
        // Launch the player 20 blocks up
        target.setVelocity(new Vector(0, 20, 0));
        target.sendMessage("You were launched by the Dragon Sword!");
    }

    private boolean hasDragonSwordTag(ItemStack item) {
        return item != null && item.getType() == Material.NETHERITE_SWORD && item.hasItemMeta()
                && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 6; // Change 6 to your Dragon Sword's custom model data
    }

    // Create the Dragon Sword ItemStack
    public ItemStack createDragonSword() {
        ItemStack dragonSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = dragonSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cDragon Sword"); // Set the display name
            meta.setCustomModelData(6); // Set custom model data (adjust as necessary)
            dragonSword.setItemMeta(meta);
        }
        return dragonSword;
    }
}
