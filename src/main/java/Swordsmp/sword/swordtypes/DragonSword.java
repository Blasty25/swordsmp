package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import Swordsmp.sword.Sword;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DragonSword implements Listener {
    private final CooldownManager cooldownManager;
    private final PlayerRadiusChecker checker;

    public DragonSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
        this.checker = new PlayerRadiusChecker();
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
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getMaxHealth() == 60.0) player.resetMaxHealth();
    }

    public void handleAbility(Player player) {
        if (!cooldownManager.isOnCooldown(player, "dragon_sword")) {
            player.setMaxHealth(60);
            player.setHealth(Math.min(player.getHealth(), 60));
            checker.checkNearbyPlayers(player, 10); // Check for nearby players in range
            cooldownManager.setCooldown(player, "dragon_sword", 100);
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "dragon_sword");
            player.sendMessage("Dragon Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false));
    }

    public Boolean hasSpecificSword(ItemStack item, int modelData) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }
    // Checker class for nearby players
    public static class PlayerRadiusChecker {

        public void checkNearbyPlayers(Player currentPlayer, double radius) {
            Location currentPlayerLocation = currentPlayer.getLocation();

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.equals(currentPlayer) && onlinePlayer.getLocation().distance(currentPlayerLocation) <= radius) {
                    currentPlayer.sendMessage(onlinePlayer.getName() + " is within " + radius + " blocks!");
                    onlinePlayer.sendMessage("You are close to " + currentPlayer.getName());

                    // Set walk and fly speed to 0
                    setPlayerSpeed(onlinePlayer, 0);

                    // Reset the speed after 10 seconds
                    Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Sword.class), () -> resetPlayerSpeed(onlinePlayer), 15 * 20L); // 10 seconds in ticks
                }
            }
        }

        private void setPlayerSpeed(Player player, float speed) {
            player.setWalkSpeed(speed);
            player.setFlySpeed(speed);
        }

        private void resetPlayerSpeed(Player player) {
            player.setWalkSpeed(0.2f); // Default walk speed
            player.setFlySpeed(0.1f);  // Default fly speed
        }
    }
}
