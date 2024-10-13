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
    private CooldownManager cooldownManager;
    private FileConfiguration config;
    private DragonSword dragonSword;
    private FireSword fireSword;
    private WaterSword waterSword;
    private EarthSword earthSword;
    private SpaceSword spaceSword;
    private AirSword airSword;

    @Override
    public void onEnable() {
        instance = this;
        config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        cooldownManager = new CooldownManager(); // Initialize cooldownManager
        dragonSword = new DragonSword(new CooldownManager());
        fireSword = new FireSword(new CooldownManager()); // Initialize other swords accordingly
        waterSword = new WaterSword(new CooldownManager());
        earthSword = new EarthSword(new CooldownManager());
        spaceSword = new SpaceSword(new CooldownManager());
        airSword = new AirSword(new CooldownManager());

        // Register commands
        getCommand("give").setExecutor(new GiveCommandExecutor(dragonSword, fireSword, waterSword, earthSword, spaceSword, airSword));

        // Register all sword event listeners
        getServer().getPluginManager().registerEvents(new FireSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new WaterSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new EarthSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new AirSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new SpaceSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(new DragonSword(cooldownManager), this);
        getServer().getPluginManager().registerEvents(this, this);

        registerRecipes(); // Register crafting recipes

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

    private void registerFireSwordRecipe() {
        ItemStack fireSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = fireSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Fire Sword");
            fireSword.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(this, "fire_sword");
        ShapedRecipe fireSwordRecipe = new ShapedRecipe(key, fireSword);
        fireSwordRecipe.shape(
                " FAF ",
                "CNC",
                " FAF "
        );
        fireSwordRecipe.setIngredient('F', Material.NETHERITE_INGOT);
        fireSwordRecipe.setIngredient('N', Material.NETHERITE_SWORD);
        fireSwordRecipe.setIngredient('A', Material.WITHER_ROSE);
        fireSwordRecipe.setIngredient('C', Material.WITHER_SKELETON_SKULL);

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
        waterSwordRecipe.shape(
                " WAW ",
                "NCN",
                " WAW "
        );
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
                " SVS ",
                "VNV",
                " SVS "
        );
        recipe.setIngredient('S', Material.DIAMOND_BLOCK);
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
                " CEC ",
                "NIN",
                " CEC "
        );
        recipe.setIngredient('C', Material.ENDER_EYE);
        recipe.setIngredient('N', Material.ENDER_CHEST);
        recipe.setIngredient('I', Material.NETHERITE_SWORD);
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
                " FOF ",
                "NPN",
                " FOF "
        );
        recipe.setIngredient('F', Material.ENCHANTED_GOLDEN_APPLE);
        recipe.setIngredient('N', Material.HEAVY_CORE);
        recipe.setIngredient('O', Material.BREEZE_ROD);
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
                " D ",
                " N ",
                " KIK "
        );
        recipe.setIngredient('D', Material.DRAGON_HEAD);
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
        if (event.getRecipe() instanceof ShapedRecipe recipe) {
            NamespacedKey key = recipe.getKey();
            String swordKey = key.getKey(); // Gets the string key like "fire_sword"
            markSwordCrafted(swordKey);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Sword plugin has stopped!");
    }
}
