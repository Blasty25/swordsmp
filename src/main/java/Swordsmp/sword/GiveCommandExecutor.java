package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommandExecutor implements CommandExecutor {
    private final WaterSword waterSword;
    private final FireSword fireSword; // Add more sword types as needed

    public GiveCommandExecutor(WaterSword waterSword, FireSword fireSword) {
        this.waterSword = waterSword;
        this.fireSword = fireSword; // Initialize other swords as needed
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
                    default:
                        sender.sendMessage("Invalid sword number. Available: 1 (Water), 2 (Fire), ...");
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
