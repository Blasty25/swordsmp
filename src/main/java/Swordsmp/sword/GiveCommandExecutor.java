package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommandExecutor implements CommandExecutor {
    private final WaterSword waterSword;

    public GiveCommandExecutor(WaterSword waterSword) {
        this.waterSword = waterSword;
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
