package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import Swordsmp.sword.Sword;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


public class WardenSword implements Listener {
    private final CooldownManager cooldownManager;
    private final Sword sword = new Sword();

    public WardenSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }


    public ItemStack createWardenSword() {
        ItemStack wardenSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = wardenSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§tWarden Sword");
            meta.setCustomModelData(7); // Custom Model Data for Water Sword
            wardenSword.setItemMeta(meta);
        }
        return wardenSword;
    }


    public void handleAbility(Player player) {
        if (!cooldownManager.isOnCooldown(player, "warden_sword")) {
            applyBlindness();
            cooldownManager.setCooldown(player, "warden_sword", 70);
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "water_sword");
            player.sendMessage("Water Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        disableWarden();
        player.removePotionEffect(PotionEffectType.DARKNESS);
    }

    private void applyBlindness(){
        new BukkitRunnable() {
            @Override
            public void run(){
                int radius = 15;
                int blindnessDuration = 15 * 20; //15 Seconds per tick

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location playerLocation = player.getLocation();
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        if (target != player && target.getLocation().distance(playerLocation) <= radius){
                            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, blindnessDuration, 1));
                        }
                    }
                }
            }
        }.runTaskTimer(sword, 0L, 20L);
    }

    private void disableWarden()
    {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                    if (entity instanceof Warden) {
                        Warden warden = (Warden) entity;
                        makeWardenPassive(warden);
                    }
                }
            }
        }.runTaskTimer(sword, 0L, 100L); // 100L = 5 seconds interval
    }

    private void makeWardenPassive(Warden warden) {
        // Set the Warden's AI to prevent it from attacking players
        warden.setAware(false); // Stops targeting players
        warden.setPersistent(false); // Optionally, if you don’t want it to persist
    }



    public boolean hasSpecificSword(ItemStack item, int modelData) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }
}
