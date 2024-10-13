package Swordsmp.sword;

import Swordsmp.sword.swordtypes.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public final class Sword extends JavaPlugin implements Listener {
    private static Sword instance;
    private CooldownManager cooldownManager; // Declare cooldownManager here
    private FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        registerRecipes();
        // Initialize CooldownManager
        cooldownManager = new CooldownManager();

        // Register sword event handlers
        getServer().getPluginManager().registerEvents(new FireSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new WaterSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new EarthSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new AirSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new SpaceSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new DragonSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(this, this);


        // Additional plugin startup logic
        Bukkit.getLogger().info("Sword plugin has started!");
    }

    public static Sword getInstance() {
        return instance;
    }

    private void registerRecipes() {
        if (!config.getBoolean("fire_sword_crafted")) {
            registerFireSwordRecipe();
        }
        if (!config.getBoolean("water_sword_crafted")) {
            registerWaterSwordRecipe();
        }
        if (!config.getBoolean("earth_sword_crafted")) {
            registerEarthSwordRecipe();
        }
        if (!config.getBoolean("space_sword_crafted")) {
            registerSpaceSwordRecipe();
        }
        if (!config.getBoolean("air_sword_crafted")) {
            registerAirSwordRecipe();
        }
        if (!config.getBoolean("dragon_sword_crafted")) {
            registerDragonSwordRecipe();
        }
    }

    // Add similar methods for other swords...

    private void registerFireSwordRecipe() {
        ItemStack fireSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = fireSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Fire Sword");
            fireSword.setItemMeta(meta);
        }

        // Create a new NamespacedKey for the recipe
        NamespacedKey key = new NamespacedKey(this, "fire_sword");

        // Define the recipe shape (3x3 grid)
        ShapedRecipe fireSwordRecipe = new ShapedRecipe(key, fireSword);
        fireSwordRecipe.shape(
                " FAF ",  // Top row
                "CNC",  // Middle row
                " FAF "   // Bottom row
        );

        // Set the ingredients for each letter in the shape
        fireSwordRecipe.setIngredient('F', Material.NETHERITE_INGOT);  // Blaze powder in the top center
        fireSwordRecipe.setIngredient('N', Material.NETHERITE_SWORD);  // Netherite in the middle
        fireSwordRecipe.setIngredient('A', Material.WITHER_ROSE);  // Diamonds in bottom corners
        fireSwordRecipe.setIngredient('C', Material.WITHER_SKELETON_SKULL);


        // Register the recipe with Bukkit
        Bukkit.addRecipe(fireSwordRecipe);
    }

    private void registerWaterSwordRecipe() {
        ItemStack waterSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = waterSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Water Sword");
            waterSword.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(this, "water_sword");
        ShapedRecipe waterSwordRecipe = new ShapedRecipe(key, waterSword);

        // Change shape and materials as needed
        waterSwordRecipe.shape(
                " WAW ",  // Top row
                "NCN",  // Middle row
                " WAW "   // Bottom row
        );

        // Set the materials used for crafting
        waterSwordRecipe.setIngredient('W', Material.CONDUIT);
        waterSwordRecipe.setIngredient('N', Material.SPONGE);
        waterSwordRecipe.setIngredient('A', Material.TURTLE_EGG);
        waterSwordRecipe.setIngredient('C', Material.DIAMOND_SWORD);

        Bukkit.addRecipe(waterSwordRecipe);
    }

    private void registerEarthSwordRecipe() {
        ItemStack earthSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = earthSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Earth Sword");
            earthSword.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(this, "earth_sword");
        ShapedRecipe recipe = new ShapedRecipe(key, earthSword);
        recipe.shape(
                " SVS ", // Top row
                "VNV", // Middle row
                " SVS "  // Bottom row
        );
        recipe.setIngredient('S', Material.DIAMOND_BLOCK); // Replace 'D' with an appropriate material
        recipe.setIngredient('N', Material.NETHERITE_SWORD);
        recipe.setIngredient('V', Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);

        Bukkit.addRecipe(recipe);
        markSwordCrafted("earth_sword");
    }


    private void registerSpaceSwordRecipe() {
        ItemStack spaceSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = spaceSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Space Sword");
            spaceSword.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(this, "space_sword");
        ShapedRecipe recipe = new ShapedRecipe(key, spaceSword);
        recipe.shape(
                " CEC ", // Top row
                "NIN", // Middle row
                " CEC "  // Bottom row
        );
        recipe.setIngredient('C', Material.ENDER_EYE); // Replace with appropriate material for space
        recipe.setIngredient('N', Material.ENDER_CHEST);
        recipe.setIngredient('I', Material.NETHERITE_SWORD); // Cosmic ingredient
        recipe.setIngredient('E', Material.ELYTRA);

        Bukkit.addRecipe(recipe);
        markSwordCrafted("space_sword");
    }

    private void registerAirSwordRecipe() {
        ItemStack airSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = airSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Air Sword");
            airSword.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(this, "air_sword");
        ShapedRecipe recipe = new ShapedRecipe(key, airSword);
        recipe.shape(
                " FOF ", // Top row
                "NPN", // Middle row
                " FOF "  // Bottom row
        );
        recipe.setIngredient('F', Material.ENCHANTED_GOLDEN_APPLE); // Feather for air
        recipe.setIngredient('N', Material.HEAVY_CORE);
        recipe.setIngredient('O', Material.BREEZE_ROD); // Air ingredient
        recipe.setIngredient('P', Material.DIAMOND_SWORD);

        Bukkit.addRecipe(recipe);
        markSwordCrafted("air_sword");
    }

    private void registerDragonSwordRecipe() {
        ItemStack dragonSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = dragonSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Dragon Sword");
            dragonSword.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(this, "dragon_sword");
        ShapedRecipe recipe = new ShapedRecipe(key, dragonSword);
        recipe.shape(
                " D ", // Top row
                " N ", // Middle row
                " KIK "  // Bottom row
        );
        recipe.setIngredient('D', Material.DRAGON_HEAD); // Dragon Breath
        recipe.setIngredient('N', Material.DRAGON_EGG);
        recipe.setIngredient('K', Material.END_ROD);
        recipe.setIngredient('I', Material.NETHERITE_SWORD);

        Bukkit.addRecipe(recipe);
        markSwordCrafted("dragon_sword");
    }

    public void markSwordCrafted(String swordKey) {
        config.set(swordKey + "_crafted", true);
        saveConfig();
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        // Check if the crafted item is one of the swords
        if (event.getRecipe() instanceof ShapedRecipe recipe) {
            NamespacedKey key = recipe.getKey();

            // Get the string representation of the key
            String swordKey = key.getKey(); // Gets the string key like "fire_sword"

            // Mark the sword as crafted based on its key
            markSwordCrafted(swordKey);
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Sword plugin has stopped!");
    }
}
