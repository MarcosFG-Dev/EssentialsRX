package br.com.essentialrx;

import br.com.essentialrx.cache.PlayerCache;
import br.com.essentialrx.cache.TpaRequestManager;
import br.com.essentialrx.commands.CommandManager;
import br.com.essentialrx.hooks.VaultHook;
import br.com.essentialrx.listeners.ChatListener;
import br.com.essentialrx.listeners.PlayerLoginListener;
import br.com.essentialrx.listeners.PlayerDeathListener;
import br.com.essentialrx.listeners.PlayerJoinQuitListener;
import br.com.essentialrx.messages.Messages;
import br.com.essentialrx.storage.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EssentialRXPlugin extends JavaPlugin {

    private static EssentialRXPlugin instance;

    private VaultHook vaultHook;
    private Messages messages;

    private PlayerCache playerCache;
    private TpaRequestManager tpaRequestManager;

    private PlayerDataStore playerDataStore;
    private HomeStore homeStore;
    private WarpStore warpStore;
    private PunishmentStore punishmentStore;

    private CommandManager commandManager;

    public static EssentialRXPlugin get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        reloadAll();

        setupVault();

        this.playerCache = new PlayerCache();
        this.tpaRequestManager = new TpaRequestManager(this);

        this.playerDataStore = new PlayerDataStore(this);
        this.homeStore = new HomeStore(this);
        this.warpStore = new WarpStore(this);
        this.punishmentStore = new PunishmentStore(this);

        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(this), this);

        this.commandManager = new CommandManager(this);
        this.commandManager.registerAll();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new br.com.essentialrx.hooks.EssentialRXExpansion(this).register();
            getLogger().info("PlaceholderAPI expansion registered.");
        }

        getLogger().info("EssentialRX enabled.");
    }

    @Override
    public void onDisable() {
        if (playerDataStore != null)
            playerDataStore.saveAll();
        getLogger().info("EssentialRX disabled.");
    }

    private boolean setupVault() {
        this.vaultHook = new VaultHook(this);
        return this.vaultHook.setup();
    }

    public void reloadAll() {
        reloadConfig();
        this.messages = new Messages(this, getConfig().getString("lang", "messages.yml"));
        this.messages.reload();
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }

    public Messages msg() {
        return messages;
    }

    public PlayerCache cache() {
        return playerCache;
    }

    public TpaRequestManager tpa() {
        return tpaRequestManager;
    }

    public PlayerDataStore playerData() {
        return playerDataStore;
    }

    public HomeStore homes() {
        return homeStore;
    }

    public WarpStore warps() {
        return warpStore;
    }

    public PunishmentStore punishments() {
        return punishmentStore;
    }
}
