package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommandExecutor implements CommandExecutor {
    private final DragonSword dragonSword;
    private final FireSword fireSword;
    private final WaterSword waterSword;
    private final EarthSword earthSword;
    private final SpaceSword spaceSword;
    private final AirSword airSword;

    public GiveCommandExecutor(DragonSword dragonSword, FireSword fireSword, WaterSword waterSword,
                               EarthSword earthSword, SpaceSword spaceSword, AirSword airSword) {
        this.dragonSword = dragonSword;
        this.fireSword = fireSword;
        this.waterSword = waterSword;
        this.earthSword = earthSword;
        this.spaceSword = spaceSword;
        this.airSword = airSword;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            String playerName = args[0];
            String itemName = args[1];

            Player target = Bukkit.getPlayer(playerName);
            if (target != null && target.isOnline()) {
                switch (itemName.toLowerCase()) {
                    case "dragon_sword":
                        target.getInventory().addItem(dragonSword.createDragonSword());
                        target.sendMessage("You have been given a Dragon Sword!");
                        break;
                    case "fire_sword":
                        target.getInventory().addItem(fireSword.createFireSword());
                        target.sendMessage("You have been given a Fire Sword!");
                        break;
                    case "water_sword":
                        target.getInventory().addItem(waterSword.createWaterSword());
                        target.sendMessage("You have been given a Water Sword!");
                        break;
                    case "earth_sword":
                        target.getInventory().addItem(earthSword.createEarthSword());
                        target.sendMessage("You have been given an Earth Sword!");
                        break;
                    case "space_sword":
                        target.getInventory().addItem(spaceSword.createSpaceSword());
                        target.sendMessage("You have been given a Space Sword!");
                        break;
                    case "air_sword":
                        target.getInventory().addItem(airSword.createAirSword());
                        target.sendMessage("You have been given an Air Sword!");
                        break;
                    default:
                        sender.sendMessage("Invalid item name. Available items: Dragon Sword, Fire Sword, Water Sword, Earth Sword, Space Sword, Air Sword");
                }
                return true;
            } else {
                sender.sendMessage("Player not found or offline.");
            }
        }
        return false;
    }
}
