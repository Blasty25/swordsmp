package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SwordManager implements Listener {
    private final CooldownManager cooldownManager;
    private final WaterSword waterSword;
    private final FireSword fireSword;
    private final EarthSword earthSword;
    private final SpaceSword spaceSword;
    private final AirSword airSword;
    private final DragonSword dragonSword;
    private final WardenSword wardenSword;

    public SwordManager(JavaPlugin plugin, CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
        this.waterSword = new WaterSword(cooldownManager);
        this.fireSword = new FireSword(cooldownManager);
        this.earthSword = new EarthSword(cooldownManager);
        this.spaceSword = new SpaceSword(cooldownManager);
        this.airSword = new AirSword(cooldownManager);
        this.dragonSword = new DragonSword(cooldownManager);
        this.wardenSword = new WardenSword(cooldownManager);

        // Register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // Check which sword is being held and call the corresponding ability
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (   item != null && item.hasItemMeta()) {
            int modelData = item.getItemMeta().getCustomModelData();
            switch (modelData) {
                case 1: // Water Sword
                    waterSword.handleAbility(player);
                    break;
                case 2: // Fire Sword
                    fireSword.handleAbility(player);
                    break;
                case 3:
                    earthSword.handleAbility(player);
                    break;
                case 4:
                    spaceSword.handleAbility(player);
                    break;
                case 5:
                    airSword.handleAbility(player);
                    break;
                case 6:
                    dragonSword.handleAbility(player);
                    break;
                case 7:
                    wardenSword.handleAbility(player);
                default:
                    break;
            }
        }
    }
}