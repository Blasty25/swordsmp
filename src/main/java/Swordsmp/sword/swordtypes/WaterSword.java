package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class WaterSword implements Listener {
    private static WaterSword instance;
    private final CooldownManager cooldownManager;

    public WaterSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }


    public ItemStack createWaterSword() {
        ItemStack waterSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = waterSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bWater Sword");
            meta.setCustomModelData(1); // Custom Model Data for Water Sword
            waterSword.setItemMeta(meta);
        }
        return waterSword;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.getType() == Material.NETHERITE_SWORD && hasSpecificSword(player, 1)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                handleAbility(player);
            }
        }
    }

    public void handleAbility(Player player) {
        if (!cooldownManager.isOnCooldown(player, "water_sword")) {
            if (isPlayerInWater(player)) {
                player.addPotionEffect((new PotionEffect(PotionEffectType.RESISTANCE,30*20, 1,true,false)));
                player.sendMessage("You are touching water! You now have Resistance 2 for 30 seconds!");
            }
            cooldownManager.setCooldown(player, "water_sword", 150);
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "water_sword");
            player.sendMessage("Water Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE,Integer.MAX_VALUE,0,true,false));
    }

    private boolean isPlayerInWater(Player player) {
        Block block = player.getLocation().subtract(0, 1, 0).getBlock();
        return block.getType() == Material.WATER;
    }



    private boolean hasSpecificSword(Player player, int modelData) {
        ItemStack item = player.getInventory().getItemInMainHand();
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modelData;
    }
}
