package br.com.essentialrx.hooks;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultHook {

    private final JavaPlugin plugin;
    private Economy economy;
    private Permission permissions;

    public VaultHook(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().info("Vault not found. Economy features will be disabled.");
            return false;
        }

        RegisteredServiceProvider<Permission> permProvider = Bukkit.getServicesManager()
                .getRegistration(Permission.class);
        if (permProvider != null) {
            permissions = permProvider.getProvider();
        }

        RegisteredServiceProvider<Economy> econProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (econProvider != null) {
            economy = econProvider.getProvider();
        }

        // Economy optional
        return permissions != null || economy != null;
    }

    public boolean hasEconomy() {
        return economy != null;
    }

    public boolean hasPermissions() {
        return permissions != null;
    }

    public Economy economy() {
        return economy;
    }

    public double getBalance(org.bukkit.entity.Player p) {
        if (hasEconomy())
            return economy.getBalance(p);
        return br.com.essentialrx.EssentialRXPlugin.get().playerData().getBalance(p.getUniqueId());
    }

    public void deposit(org.bukkit.entity.Player p, double amount) {
        if (hasEconomy()) {
            economy.depositPlayer(p, amount);
        } else {
            double current = getBalance(p);
            br.com.essentialrx.EssentialRXPlugin.get().playerData().setBalance(p.getUniqueId(), current + amount);
        }
    }

    public boolean withdraw(org.bukkit.entity.Player p, double amount) {
        if (hasEconomy()) {
            return economy.withdrawPlayer(p, amount).transactionSuccess();
        } else {
            double current = getBalance(p);
            if (current < amount)
                return false;
            br.com.essentialrx.EssentialRXPlugin.get().playerData().setBalance(p.getUniqueId(), current - amount);
            return true;
        }
    }

    public void setBalance(org.bukkit.entity.Player p, double amount) {
        if (hasEconomy()) {
            double current = economy.getBalance(p);
            if (current > amount) {
                economy.withdrawPlayer(p, current - amount);
            } else if (current < amount) {
                economy.depositPlayer(p, amount - current);
            }
        } else {
            br.com.essentialrx.EssentialRXPlugin.get().playerData().setBalance(p.getUniqueId(), amount);
        }
    }

    // TOP 10 Logic
    private final java.util.List<java.util.Map.Entry<String, Double>> topBalances = new java.util.ArrayList<>();
    private long lastTopUpdate = 0;

    public void updateTopBalances() {
        if (org.bukkit.Bukkit.isPrimaryThread()) {
            if (System.currentTimeMillis() - lastTopUpdate < 60000 && !topBalances.isEmpty())
                return;
            lastTopUpdate = System.currentTimeMillis();

            org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(br.com.essentialrx.EssentialRXPlugin.get(), () -> {
                java.util.Map<java.util.UUID, Double> all = br.com.essentialrx.EssentialRXPlugin.get().playerData()
                        .getAllBalances();
                java.util.List<java.util.Map.Entry<String, Double>> list = new java.util.ArrayList<>();

                for (java.util.Map.Entry<java.util.UUID, Double> entry : all.entrySet()) {
                    String name = org.bukkit.Bukkit.getOfflinePlayer(entry.getKey()).getName();
                    if (name == null)
                        name = "Unknown";
                    list.add(new java.util.AbstractMap.SimpleEntry<>(name, entry.getValue()));
                }

                list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

                org.bukkit.Bukkit.getScheduler().runTask(br.com.essentialrx.EssentialRXPlugin.get(), () -> {
                    topBalances.clear();
                    topBalances.addAll(list.subList(0, Math.min(list.size(), 10)));
                });
            });
        }
    }

    public java.util.List<java.util.Map.Entry<String, Double>> getTopBalances() {
        updateTopBalances();
        return topBalances;
    }

    // Migration Logic
    public int migrateToVault() {
        if (!hasEconomy())
            return -1;
        java.util.Map<java.util.UUID, Double> all = br.com.essentialrx.EssentialRXPlugin.get().playerData()
                .getAllBalances();
        int count = 0;
        for (java.util.Map.Entry<java.util.UUID, Double> entry : all.entrySet()) {
            if (entry.getValue() <= 0)
                continue;
            org.bukkit.OfflinePlayer op = org.bukkit.Bukkit.getOfflinePlayer(entry.getKey());
            economy.depositPlayer(op, entry.getValue());
            br.com.essentialrx.EssentialRXPlugin.get().playerData().setBalance(entry.getKey(), 0.0);
            count++;
        }
        return count;
    }

    public Permission perms() {
        return permissions;
    }
}
