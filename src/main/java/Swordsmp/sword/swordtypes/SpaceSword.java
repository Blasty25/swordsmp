package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import Swordsmp.sword.Sword;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpaceSword implements Listener {
    private final CooldownManager cooldownManager;

    public SpaceSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createSpaceSword() {
        ItemStack spaceSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = spaceSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§dSpace Sword");
            meta.setCustomModelData(4); // Custom Model Data for Water Sword
            spaceSword.setItemMeta(meta);
        }
        return spaceSword;
    }


    public void handleAbility(Player player) {
        if (!cooldownManager.isOnCooldown(player, "space_sword")) {
            // Change player to Spectator mode
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("You are now in Spectator mode for 15 seconds!");

            // Schedule task to change the player's gamemode back to Survival after 15 seconds
            Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Sword.class), new Runnable() {
                @Override
                public void run() {
                    player.sendMessage("You will be in Survival by the time your read this");
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage("You are now back in Survival mode!");
                }
            }, 15 * 20L); // 15 seconds delay (20 ticks = 1 second)
            cooldownManager.setCooldown(player, "space_sword", 50);
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "space_sword");
            player.sendMessage("Space Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, true, false));
    }


    public Boolean hasSpecificSword(ItemStack item, int modelData){
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }
}
