package br.com.essentialrx.hooks;

import br.com.essentialrx.EssentialRXPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EssentialRXExpansion extends PlaceholderExpansion {

    private final EssentialRXPlugin plugin;

    public EssentialRXExpansion(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "essentialrx";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null)
            return "";

        if (params.equalsIgnoreCase("balance")) {
            return String.format("%.2f", plugin.getVaultHook().getBalance(player));
        }

        if (params.equalsIgnoreCase("balance_formatted")) {
            return String.format("§a$%.2f", plugin.getVaultHook().getBalance(player));
        }

        // Top Balances
        if (params.startsWith("top_name_")) {
            int pos = Integer.parseInt(params.split("_")[2]) - 1;
            java.util.List<java.util.Map.Entry<String, Double>> top = plugin.getVaultHook().getTopBalances();
            if (pos < top.size())
                return top.get(pos).getKey();
            return "---";
        }

        if (params.startsWith("top_balance_")) {
            int pos = Integer.parseInt(params.split("_")[2]) - 1;
            java.util.List<java.util.Map.Entry<String, Double>> top = plugin.getVaultHook().getTopBalances();
            if (pos < top.size())
                return String.format("%.2f", top.get(pos).getValue());
            return "0.00";
        }

        return null;
    }
}
