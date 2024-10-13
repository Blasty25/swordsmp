package Swordsmp.sword.swordtypes;

import Swordsmp.sword.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.inventory.meta.ItemMeta;

public class EarthSword implements Listener {
    private final CooldownManager cooldownManager;
    public ItemStack createEarthSword() {
        ItemStack earthSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = earthSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§aEarth Sword");
            meta.setCustomModelData(9);
            earthSword.setItemMeta(meta);
        }
        return earthSword;
    }
    public EarthSword(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.NETHERITE_SWORD && hasEarthSwordTag(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldownManager.isOnCooldown(player, "earth_sword")) {
                    encircleWithObsidian(player);
                    cooldownManager.setCooldown(player, "earth_sword", 300); // Set cooldown
                } else {
                    long remainingTime = cooldownManager.getCooldownTime(player, "earth_sword");
                    player.sendMessage("Earth Sword is on cooldown for " + remainingTime + " more seconds!");
                }
            }
        }
    }

    private void encircleWithObsidian(Player player) {
        Block block = player.getLocation().getBlock();
        int[][] offsets = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {-1, 1}, {1, -1}, {-1, -1}
        };

        for (int[] offset : offsets) {
            Block relative = block.getRelative(offset[0], 0, offset[1]);
            relative.setType(Material.OBSIDIAN);
        }

        block.getRelative(0, 1, 0).setType(Material.OBSIDIAN);
        block.getRelative(0, -1, 0).setType(Material.OBSIDIAN);
        player.sendMessage("You encircled yourself with obsidian!");
    }

    private boolean hasEarthSwordTag(ItemStack item) {
        // Logic to check for Earth Sword tag
        return true; // Replace with actual tag checking logic
    }
}
