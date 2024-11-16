package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class EarthSword implements Listener {
    private final CooldownManager cooldownManager;


    public EarthSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public ItemStack createEarthSword() {
        ItemStack earthSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = earthSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§aEarth Sword");
            meta.setCustomModelData(3);
            earthSword.setItemMeta(meta);
        }
        return earthSword;
    }



    public void handleAbility(Player player) {
        if (!cooldownManager.isOnCooldown(player, "earth_sword")) {
            encircleWithObsidian(player);
            cooldownManager.setCooldown(player, "earth_sword", 300); // Set cooldown
            player.sendMessage("You summoned a protective Earth Wall!");
            cooldownManager.setCooldown(player, "earth_sword", 50); // 180-second cooldown
        } else {
            long remainingTime = cooldownManager.getCooldownTime(player, "earth_sword");
            player.sendMessage("Earth Sword is on cooldown for " + remainingTime + " more seconds!");
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, Integer.MAX_VALUE,6,true,false));
    }

    private void encircleWithObsidian(Player player){
        Block block = player.getLocation().getBlock();
        int[][] offsets = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {-1, 1}, {1, -1}, {-1, -1}
        };
        for (int[] offset : offsets) {
            Block relative = block.getRelative(offset[0], 0, offset[1]);
            relative.setType(Material.OBSIDIAN);
        }
        block.getRelative(0, 2, 0).setType(Material.OBSIDIAN);
        block.getRelative(0, -1, 0).setType(Material.OBSIDIAN);
        block.getRelative(1,1,0).setType(Material.OBSIDIAN);
        block.getRelative(-1,1,0).setType(Material.OBSIDIAN);
        block.getRelative(0,1,1).setType(Material.OBSIDIAN);
        block.getRelative(0,1,-1).setType(Material.OBSIDIAN);
        player.sendMessage("You encircled yourself with obsidian!");
    }

    public boolean hasSpecificSword(ItemStack item, int modeldata){
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == modeldata;
    }

}
