package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Sword extends JavaPlugin {
    private CooldownManager cooldownManager;
    private WaterSword waterSword;
    private FireSword fireSword;
    private EarthSword earthSword;
    private SpaceSword spaceSword;
    private AirSword airSword;
    private DragonSword dragonSword;

    @Override
    public void onEnable() {
        cooldownManager = new CooldownManager();
        waterSword = new WaterSword(cooldownManager);
        fireSword = new FireSword(cooldownManager);
        earthSword = new EarthSword(cooldownManager);
        spaceSword = new SpaceSword(cooldownManager);
        airSword = new AirSword(cooldownManager);
        dragonSword = new DragonSword(cooldownManager);


        getServer().getPluginManager().registerEvents(waterSword, this);
        getServer().getPluginManager().registerEvents(fireSword, this);
        getServer().getPluginManager().registerEvents(earthSword, this);
        getServer().getPluginManager().registerEvents(spaceSword, this);
        getServer().getPluginManager().registerEvents(airSword, this);
        getServer().getPluginManager().registerEvents(dragonSword, this);


        // Register command executor
        //  getCommand("give").setExecutor(new GiveCommandExecutor(waterSword, fireSword, earthSword, spaceSword, airSword, dragonSword));
    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Sword plugin has stopped!");
    }
}