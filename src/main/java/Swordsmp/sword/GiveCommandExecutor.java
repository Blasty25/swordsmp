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
            String swordNumber = args[1];

            Player target = Bukkit.getPlayer(playerName);
            if (target != null && target.isOnline()) {
                switch (swordNumber) {
                    case "1":
                        target.getInventory().addItem(waterSword.createWaterSword());
                        target.sendMessage("You have been given a Water Sword!");
                        break;
                    case "2":
                        target.getInventory().addItem(fireSword.createFireSword());
                        target.sendMessage("You have been given a Fire Sword!");
                        break;
                    case "3":
                        target.getInventory().addItem(earthSword.createEarthSword());
                        target.sendMessage("You have been given an Earth Sword!");
                        break;
                    case "4":
                        target.getInventory().addItem(dragonSword.createDragonSword());
                        target.sendMessage("You have been given a Dragon Sword!");
                        break;
                    case "5":
                        target.getInventory().addItem(spaceSword.createSpaceSword());
                        target.sendMessage("You have been given a Space Sword!");
                        break;
                    case "6":
                        target.getInventory().addItem(airSword.createAirSword());
                        target.sendMessage("You have been given an Air Sword!");
                        break;
                    default:
                        sender.sendMessage("Invalid sword number. Available: 1 (Water), 2 (Fire), 3 (Earth), 4 (Dragon), 5 (Space), 6 (Air)");
                }
                return true;
            } else {
                sender.sendMessage("Player not found or offline.");
            }
        }
        return false;
    }
}
