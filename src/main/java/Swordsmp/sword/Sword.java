package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Sword extends JavaPlugin {
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        cooldownManager = new CooldownManager();

        // Register the swords and their events
        WaterSword waterSword = new WaterSword(cooldownManager);
        FireSword fireSword = new FireSword(cooldownManager);
        DragonSword dragonSword = new DragonSword(cooldownManager);
        EarthSword earthSword = new EarthSword(cooldownManager);
        SpaceSword spaceSword = new SpaceSword(cooldownManager);
        AirSword airSword = new AirSword(cooldownManager);

        getServer().getPluginManager().registerEvents(new WaterSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new FireSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new EarthSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new DragonSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new SpaceSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new AirSword(cooldownManager), this);


        // Register command executor with numbers for each sword
        getCommand("give").setExecutor(new GiveCommandExecutor(dragonSword, fireSword, waterSword, earthSword, spaceSword, airSword));
    }

@Override
    public void onDisable() {
        Bukkit.getLogger().info("Sword plugin has stopped!");
    }

}
