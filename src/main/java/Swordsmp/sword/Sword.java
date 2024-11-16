package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Sword extends JavaPlugin implements Listener {

    private WaterSword waterSword;
    private FireSword fireSword;
    private EarthSword earthSword;
    private SpaceSword spaceSword;
    private AirSword airSword;
    private DragonSword dragonSword;
    private WardenSword wardenSword;

    @Override
    public void onEnable() {
        CooldownManager cooldownManager = new CooldownManager();
        waterSword = new WaterSword(cooldownManager);
        fireSword = new FireSword(cooldownManager);
        earthSword = new EarthSword(cooldownManager);
        spaceSword  = new SpaceSword(cooldownManager);
        airSword = new AirSword(cooldownManager);
        dragonSword = new DragonSword(cooldownManager);
        wardenSword = new WardenSword(cooldownManager);

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(waterSword, this);
        getServer().getPluginManager().registerEvents(fireSword, this);
        getServer().getPluginManager().registerEvents(earthSword, this);
        getServer().getPluginManager().registerEvents(spaceSword, this);
        getServer().getPluginManager().registerEvents(airSword, this);
        getServer().getPluginManager().registerEvents(dragonSword, this);
        getServer().getPluginManager().registerEvents(wardenSword, this);
        // Your other initialization code
        // Register command executor
         getCommand("givesword").setExecutor(new GiveCommandExecutor(waterSword, fireSword, earthSword, spaceSword, airSword, dragonSword, wardenSword));
         startSwordEffectCheck();

    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Check if the item is a sword and if it's being placed in the offhand
        if (clickedItem != null && (
                dragonSword.hasSpecificSword(clickedItem, 6) ||
                        spaceSword.hasSpecificSword(clickedItem, 4) ||
                        waterSword.hasSpecificSword(clickedItem, 1) ||
                        fireSword.hasSpecificSword(clickedItem, 2) ||
                        earthSword.hasSpecificSword(clickedItem, 3) ||
                        airSword.hasSpecificSword(clickedItem, 5) ||
                        wardenSword.hasSpecificSword(clickedItem, 7)
        )) {
            // If the clicked item is being moved to the offhand, cancel the event
            if (event.getSlot() == 40) {
                event.setCancelled(false);  // Prevent the sword from moving into the offhand
                player.sendMessage(ChatColor.RED + "You cannot place this sword in your offhand!");
            }
        }
    }
    private void startSwordEffectCheck() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack mainHandItem = player.getInventory().getItemInMainHand();
                ItemStack offhandItem = player.getInventory().getItemInOffHand();

                // If the player is not holding a sword in either hand, cancel their abilities
                if (!hasAnySword(player)) {
                    // Cancel abilities (clear potion effects or handle ability clearing)
                    cancelAbilities(player);
                }
            }
        }, 0L, 5L); // Check every 5 ticks
    }

    // Check if a player is holding any sword
    private boolean hasAnySword(Player player) {
        // Iterate over all the items in the player's inventory
        for (ItemStack item : player.getInventory().getContents()) {
            // Check if the item is a sword and matches any specific sword
            if (item != null && (
                    dragonSword.hasSpecificSword(item, 6) ||
                            spaceSword.hasSpecificSword(item, 4) ||
                            waterSword.hasSpecificSword(item, 1) ||
                            fireSword.hasSpecificSword(item, 2) ||
                            earthSword.hasSpecificSword(item, 3) ||
                            airSword.hasSpecificSword(item, 5) ||
                            wardenSword.hasSpecificSword(item, 7)
            )) {
                return true; // If a sword is found, return true
            }
        }
        return false; // If no sword is found, return false
    }


    private void cancelAbilities(Player player) {

        // Iterate over all the potion effects currently applied to the player
        for (PotionEffect effect : player.getActivePotionEffects()) {
            // Check if the effect has infinite duration (considered "permanent")
            if (effect.getDuration() > 12000) {
                // Remove permanent effects (infinite duration)
                player.removePotionEffect(effect.getType());
            }
        }


        // Example: Remove specific permanent potion effects

        // Reset health if it was modified by a permanent effect (e.g., Health Boost)
        player.setMaxHealth(20.0);  // Reset to the default Minecraft max health (20 hearts)
        player.setHealth(Math.min(player.getHealth(), 20.0)); // Ensure current health doesn't exceed 20

        // Reset other attributes if the sword granted permanent attributes like health
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0); // Reset max health to default

        // Handle removal of custom abilities, if necessary.
    }


    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = event.getOffHandItem();
        event.setCancelled(true);
        // If the sword is being swapped, we want to activate its ability but prevent the swap
        if (mainHandItem != null && (
                dragonSword.hasSpecificSword(mainHandItem, 6) ||
                        spaceSword.hasSpecificSword(mainHandItem, 4) ||
                        waterSword.hasSpecificSword(mainHandItem, 1) ||
                        fireSword.hasSpecificSword(mainHandItem, 2) ||
                        earthSword.hasSpecificSword(mainHandItem, 3) ||
                        airSword.hasSpecificSword(mainHandItem, 5)  ||
                        wardenSword.hasSpecificSword(mainHandItem, 7)
        )) {


            if(player.isSneaking()) {
                if (dragonSword.hasSpecificSword(mainHandItem, 6)) {
                    dragonSword.handleAbility(player);
                } else if (spaceSword.hasSpecificSword(mainHandItem, 4)) {
                    spaceSword.handleAbility(player);
                } else if (waterSword.hasSpecificSword(mainHandItem, 1)) {
                    waterSword.handleAbility(player);
                } else if (fireSword.hasSpecificSword(mainHandItem, 2)) {
                    fireSword.handleAbility(player);
                } else if (earthSword.hasSpecificSword(mainHandItem, 3)) {
                    earthSword.handleAbility(player);
                } else if (airSword.hasSpecificSword(mainHandItem, 5)) {
                    airSword.handleAbility(player);
                } else if (wardenSword.hasSpecificSword(mainHandItem, 7)) {
                    wardenSword.handleAbility(player);
                }
            }
        }
    }






    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Sword plugin has stopped!");
    }
}