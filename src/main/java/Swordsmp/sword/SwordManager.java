package Swordsmp.sword;

import Swordsmp.sword.swordtypes.EarthSword;
import Swordsmp.sword.swordtypes.FireSword;
import Swordsmp.sword.swordtypes.WaterSword;
import org.bukkit.entity.Player;
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

    public SwordManager(JavaPlugin plugin, CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
        this.waterSword = new WaterSword(cooldownManager);
        this.fireSword = new FireSword(cooldownManager);
        this.earthSword = new EarthSword(cooldownManager);

        // Register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // Check which sword is being held and call the corresponding ability
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.hasItemMeta()) {
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
                default:
                    break;
            }
        }
    }
}