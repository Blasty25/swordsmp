package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        if (args.length != 2) {
            sender.sendMessage("Usage: /give <player> <sword_name>");
            return false;
        }

        String playerName = args[0];
        String itemName = args[1].toLowerCase(); // Normalize to lowercase for easier comparison
        Player target = Bukkit.getPlayer(playerName);

        if (target == null || !target.isOnline()) {
            sender.sendMessage("Player not found or offline.");
            return false;
        }

        ItemStack sword = null;

        // Switch to handle which sword to give based on the itemName
        switch (itemName) {
            case "dragon_sword":
                sword = dragonSword.createDragonSword();
                break;
            case "fire_sword":
                sword = fireSword.createFireSword();
                break;
            case "water_sword":
                sword = waterSword.createWaterSword();
                break;
            case "earth_sword":
                sword = earthSword.createEarthSword();
                break;
            case "space_sword":
                sword = spaceSword.createSpaceSword();
                break;
            case "air_sword":
                sword = airSword.createAirSword();
                break;
            default:
                sender.sendMessage("Invalid sword name. Available swords: dragon_sword, fire_sword, water_sword, earth_sword, space_sword, air_sword.");
                return false;
        }

        if (sword != null) {
            target.getInventory().addItem(sword);  // Add the sword to player's inventory
            target.sendMessage("You have been given a " + itemName.replace("_", " ") + "!");
            sender.sendMessage("Successfully gave " + itemName.replace("_", " ") + " to " + playerName);
            return true;
        }

        return false;
    }
}
