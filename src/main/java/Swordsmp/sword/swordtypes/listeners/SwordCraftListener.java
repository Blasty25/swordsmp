package Swordsmp.sword.swordtypes.listeners;


import Swordsmp.sword.CustomCrafting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SwordCraftListener implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack craftedItem = event.getCurrentItem();

        if (craftedItem != null && craftedItem.hasItemMeta()) {
            ItemMeta meta = craftedItem.getItemMeta();

            // Check for Water Sword
            if (meta.hasCustomModelData() && meta.getCustomModelData() == 1) { // Assuming Water Sword has CustomModelData = 1
                CustomCrafting.isWaterSwordCrafted = true;
                event.getWhoClicked().sendMessage("You have crafted the Water Sword!");
                return;
            }

            // Check for Fire Sword
            if (meta.hasCustomModelData() && meta.getCustomModelData() == 2) { // Assuming Fire Sword has CustomModelData = 2
                CustomCrafting.isFireSwordCrafted = true;
                event.getWhoClicked().sendMessage("You have crafted the Fire Sword!");
                return;
            }

            // Check for Earth Sword
            if (meta.hasCustomModelData() && meta.getCustomModelData() == 3) { // Assuming Earth Sword has CustomModelData = 3
                CustomCrafting.isEarthSwordCrafted = true;
                event.getWhoClicked().sendMessage("You have crafted the Earth Sword!");
                return;
            }
        }
    }
}
