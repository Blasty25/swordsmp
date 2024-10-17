package Swordsmp.sword;

import Swordsmp.sword.swordtypes.EarthSword;
import Swordsmp.sword.swordtypes.FireSword;
import Swordsmp.sword.swordtypes.WaterSword;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public final class CustomCrafting {

    public static Boolean isWaterSwordCrafted = false;
    public static Boolean isFireSwordCrafted = false;
    public static Boolean isEarthSwordCrafted = false;



    public static void register() {
        System.out.println("Find me");
        if (isWaterSwordCrafted) {
            System.out.println("starting if statment for sword 1");
            ShapedRecipe recipe1 = new ShapedRecipe(new NamespacedKey("swordsmp", "water_sword"), WaterSword.getInstance(null).createWaterSword());
            recipe1.shape(
                    "DOD",
                    "BPB",
                    "DOD");
            recipe1.setIngredient('D', Material.CONDUIT);
            recipe1.setIngredient('O', Material.TURTLE_EGG);
            recipe1.setIngredient('B', Material.SPONGE);
            recipe1.setIngredient('P', Material.DIAMOND_SWORD);

            // Register the recipe
            Bukkit.addRecipe(recipe1);
            System.out.println("Sword 1 crafted");
        }

        // Check if Fire Sword is crafted
        if (isFireSwordCrafted) {
            ShapedRecipe recipe2 = new ShapedRecipe(new NamespacedKey("swordsmp", "fire_sword"), FireSword.getInstance(null).createFireSword());
            recipe2.shape(
                    "TYT",
                    "RVR",
                    "TYT");
            recipe2.setIngredient('T', Material.NETHERITE_INGOT);
            recipe2.setIngredient('Y', Material.WITHER_ROSE);
            recipe2.setIngredient('V', Material.NETHERITE_SWORD);
            recipe2.setIngredient('R', Material.WITHER_SKELETON_SKULL);

            // Register the recipe
            Bukkit.addRecipe(recipe2);
            isFireSwordCrafted = true;
        }

        // Check if Earth Sword is crafted
        if (isEarthSwordCrafted) {
            ShapedRecipe recipe3 = new ShapedRecipe(new NamespacedKey("swordsmp", "earth_sword"), EarthSword.getInstance(null).createEarthSword());
            recipe3.shape(
                    "HJH",
                    "JLJ",
                    "HJH");
            recipe3.setIngredient('H', Material.DIAMOND_BLOCK);
            recipe3.setIngredient('J', Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
            recipe3.setIngredient('L', Material.NETHERITE_SWORD);

            // Register the recipe
            Bukkit.addRecipe(recipe3);
            isEarthSwordCrafted = true;
        }
    }
}

