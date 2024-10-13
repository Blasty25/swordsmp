package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sword extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Sword plugin has been enabled!");

        // Initialize the CooldownManager
        CooldownManager cooldownManager = new CooldownManager();

        // Create SwordListener with the cooldown manager
        SwordListener swordListener = new SwordListener(this, cooldownManager);

        // Register the SwordListener
        getServer().getPluginManager().registerEvents(swordListener, this);

        // Register individual sword classes
        new FireSword(swordListener);
        new waterSword(swordListener);
        new earthSword(swordListener);
        new SpaceSword(swordListener);
        new AirSword(swordListener);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Sword plugin is shutting down.");
    }
}
