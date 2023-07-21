package net.recondev.stats;

import net.recondev.commons.chat.MessageCache;
import net.recondev.commons.config.ConfigManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import net.recondev.stats.commands.objects.StatCommand;
import net.recondev.stats.listeners.PlayerListener;
import net.recondev.stats.managers.PlayerManager;
import net.recondev.stats.menus.PlayerStatsMenu;
import net.recondev.stats.objects.StatsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

@Getter
public class ReconStats extends JavaPlugin {

    public ReconStats instance;
    private FileConfiguration menuConfig;
    private FileConfiguration langConfig;
    private MessageCache messageCache;
    public PlayerStatsMenu playerStatsMenu;
    private PlayerManager playerManager;
    private ConfigManager<ReconStats> fileManager;

    public void onEnable() {
        init();
        registerCommands();
        registerListeners();
        loadData();
        loadMessages();
        saveDefaultConfig();
    }

    public void onDisable() {
        saveDefaultConfig();
        saveData();
    }
    private void loadMessages() {
        this.messageCache = new MessageCache(langConfig);

        for (final String key : this.langConfig.getConfigurationSection("Messages").getKeys(false)) {
            this.messageCache.loadMessage("Messages." + key);
        }

    }
    private void loadFiles() {
        this.fileManager.loadConfiguration("menus");
        this.fileManager.loadConfiguration("messages");

        this.menuConfig= this.fileManager.getConfig("menus");
        this.langConfig= this.fileManager.getConfig("messages");
    }
    public void init() {
        instance = this;
        this.fileManager = new ConfigManager<>(this);
        this.loadFiles();
        this.playerManager = new PlayerManager();
        this.playerStatsMenu = new PlayerStatsMenu(this);
    }

    public void registerCommands() {
        new StatCommand(this).register();
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }


    @SneakyThrows
    private void saveData() {
        final File file = new File(getDataFolder(), "data.json");

        if (!file.exists()) {
            file.getParentFile().mkdir();
            saveResource("data.json", false);
        }

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (final Writer writer = new FileWriter(file)) {
            gson.toJson(this.playerManager.getRegistry().values(), writer);
            writer.flush();
        }
    }

    @SneakyThrows
    private void loadData() {
        final File file = new File(getDataFolder(), "data.json");

        if (!file.exists()) {
            file.getParentFile().mkdir();
            saveResource("data.json", false);
            return;
        }

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final StatsPlayer[] profiles = gson.fromJson(new FileReader(file), StatsPlayer[].class);

        for (final StatsPlayer profile : profiles) {
            this.playerManager.getRegistry().put(profile.getUuid(), profile);
        }
    }
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }
}

