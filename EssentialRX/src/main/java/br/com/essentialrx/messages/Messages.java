package br.com.essentialrx.messages;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Messages {

    private final EssentialRXPlugin plugin;
    private final String fileName;
    private YamlConfiguration config;

    public Messages(EssentialRXPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
    }

    public void reload() {
        try {
            File file = new File(plugin.getDataFolder(), fileName);
            if (!file.exists()) {
                plugin.saveResource(fileName, false);
            }
            this.config = YamlConfiguration.loadConfiguration(file);

            // Load defaults from jar
            InputStreamReader def = new InputStreamReader(plugin.getResource(fileName), "UTF-8");
            YamlConfiguration defCfg = YamlConfiguration.loadConfiguration(def);
            this.config.setDefaults(defCfg);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load messages.yml: " + e.getMessage());
        }
    }

    public String raw(String key) {
        String prefix = plugin.getConfig().getString("prefix", "&bEssentialRX&7 » ");
        String v = config.getString(key, "{prefix}&cMensagem não encontrada: " + key);
        v = v.replace("{prefix}", prefix);
        return CC.translate(v);
    }

    public void send(CommandSender sender, String key) {
        sender.sendMessage(raw(key));
    }

    public void send(CommandSender sender, String key, Map<String, String> placeholders) {
        String msg = raw(key);
        for (Map.Entry<String, String> e : placeholders.entrySet()) {
            msg = msg.replace("{" + e.getKey() + "}", e.getValue());
        }
        sender.sendMessage(msg);
    }

    public Map<String, String> ph(String k, String v) {
        Map<String, String> map = new HashMap<>();
        map.put(k, v);
        return map;
    }

    public Map<String, String> ph2(String k1, String v1, String k2, String v2) {
        Map<String, String> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public void send(Player p, String key) {
        send((CommandSender)p, key);
    }

    public void send(Player p, String key, Map<String, String> placeholders) {
        send((CommandSender)p, key, placeholders);
    }
}
