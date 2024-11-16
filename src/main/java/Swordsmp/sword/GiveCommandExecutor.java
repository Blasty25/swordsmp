package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommandExecutor implements CommandExecutor {
    private final WaterSword waterSword;
    private final FireSword fireSword; // Add more sword types as
    private  final  EarthSword earthSword;
    private final SpaceSword spaceSword;
    private final AirSword airSword;
    private final DragonSword dragonSword;
    private final WardenSword wardenSword;

    public GiveCommandExecutor(WaterSword waterSword, FireSword fireSword, EarthSword earthSword, SpaceSword spaceSword, AirSword airSword, DragonSword dragonSword, WardenSword wardenSword) {
        this.waterSword = waterSword;
        this.fireSword = fireSword; // Initialize other swords as needed
        this.earthSword = earthSword;
        this.spaceSword = spaceSword;
        this.airSword = airSword;
        this.dragonSword = dragonSword;
        this.wardenSword = wardenSword;
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
                    // Add more cases for additional swords here
                    case "3":
                        target.getInventory().addItem(earthSword.createEarthSword());
                        target.sendMessage("You have been given a Earth Sword");
                        break;

                    case "4":
                        target.getInventory().addItem(spaceSword.createSpaceSword());
                        target.sendMessage("You have been given a Space Sword");
                        break;
                    case "5":
                        target.getInventory().addItem((airSword.createAirSword()));
                        target.sendMessage("You have been given a Air Sword");
                        break;
                    case "6":
                        target.getInventory().addItem((dragonSword.createDragonSword()));
                        target.sendMessage("You have been given a Dragon Sword");
                        break;
                    case "7":
                        target.getInventory().addItem((wardenSword.createWardenSword()));
                        target.sendMessage("You have been given a Warden Sword");
                        break;
                    default:
                        sender.sendMessage("Invalid sword number. Available: 1 (Water), 2 (Fire), 3 (Earth), 4 (Space)");
                }
                return true;
            } else {
                sender.sendMessage("Player not found or offline.");
                return false;
            }
        } else {
            sender.sendMessage("Usage: /give <player> <sword_number>");
            return false;
        }
    }
}
