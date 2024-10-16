package Swordsmp.sword;

import Swordsmp.sword.swordtypes.EarthSword;
import Swordsmp.sword.swordtypes.FireSword;
import Swordsmp.sword.swordtypes.WaterSword;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Sword extends JavaPlugin {
    private CooldownManager cooldownManager;
    private WaterSword waterSword;
    private FireSword fireSword;
    private EarthSword earthSword;

    @Override
    public void onEnable() {
        cooldownManager = new CooldownManager();
        waterSword = new WaterSword(cooldownManager);
        fireSword = new FireSword(cooldownManager);
        earthSword = new EarthSword(cooldownManager);

        while (isEnabled()) {
            getServer().getPluginManager().registerEvents(waterSword, this);
            getServer().getPluginManager().registerEvents(fireSword, this);
            getServer().getPluginManager().registerEvents(earthSword,this);
            CustomCrafting.register();

            // Register command executor
            getCommand("give").setExecutor(new GiveCommandExecutor(waterSword, fireSword, earthSword));
        }
    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Sword plugin has stopped!");
    }
}