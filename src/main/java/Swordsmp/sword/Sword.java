package Swordsmp.sword;

import Swordsmp.sword.swordtypes.WaterSword;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Sword extends JavaPlugin {
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        cooldownManager = new CooldownManager();

        // Register the swords and their events
        WaterSword waterSword = new WaterSword(cooldownManager);

        getServer().getPluginManager().registerEvents(new WaterSword(cooldownManager), this);


        // Register command executor with numbers for each sword
        getCommand("give").setExecutor(new GiveCommandExecutor(waterSword));
    }

@Override
    public void onDisable() {
        Bukkit.getLogger().info("Sword plugin has stopped!");
    }

}
