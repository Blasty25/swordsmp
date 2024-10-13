package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sword extends JavaPlugin {

    private static Sword instance;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        instance = this; // Set the instance to the current plugin instance
        cooldownManager = new CooldownManager();

        Bukkit.getLogger().info("Sword Plugin Enabled!");

        // Register sword event handlers
        getServer().getPluginManager().registerEvents(new FireSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new WaterSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new EarthSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new AirSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new SpaceSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new DragonSword(cooldownManager), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Sword Plugin Disabled!");
    }

    public static Sword getInstance() {
        return instance;
    }
}
