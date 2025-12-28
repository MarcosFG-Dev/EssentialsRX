package br.com.essentialrx.commands;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.commands.chat.*;
import br.com.essentialrx.commands.economy.*;
import br.com.essentialrx.commands.homes.*;
import br.com.essentialrx.commands.moderation.*;
import br.com.essentialrx.commands.player.*;
import br.com.essentialrx.commands.spawn.*;
import br.com.essentialrx.commands.teleport.*;
import br.com.essentialrx.commands.warps.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

public class CommandManager {

    private final EssentialRXPlugin plugin;
    private final Map<String, BaseCommand> commands = new HashMap<>();

    public CommandManager(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerAll() {
        register(new CmdEssentialRX(plugin));

        register(new CmdSpawn(plugin));
        register(new CmdSetspawn(plugin));

        register(new CmdTp(plugin));
        register(new CmdTphere(plugin));
        register(new CmdTpall(plugin));
        register(new CmdTpa(plugin));
        register(new CmdTpaccept(plugin));
        register(new CmdTpdeny(plugin));
        register(new CmdBack(plugin));

        register(new CmdHome(plugin));
        register(new CmdSethome(plugin));
        register(new CmdDelhome(plugin));

        register(new CmdWarp(plugin));
        register(new CmdSetwarp(plugin));
        register(new CmdDelwarp(plugin));

        register(new CmdHeal(plugin));
        register(new CmdFeed(plugin));
        register(new CmdFly(plugin));
        register(new CmdGamemode(plugin));
        register(new CmdGms(plugin));
        register(new CmdGmc(plugin));
        register(new CmdGma(plugin));
        register(new CmdClearInventory(plugin));
        register(new CmdInvsee(plugin));
        register(new CmdEnderchest(plugin));
        register(new CmdWorkBench(plugin));
        register(new CmdAnvil(plugin));
        register(new CmdTrash(plugin));
        register(new CmdHat(plugin));
        register(new CmdRepair(plugin));
        register(new CmdKill(plugin));
        register(new CmdVanish(plugin));

        register(new CmdMsg(plugin));
        register(new CmdReply(plugin));
        register(new CmdBroadcast(plugin));
        register(new CmdSeen(plugin));

        register(new CmdBal(plugin));
        register(new CmdPay(plugin));
        register(new CmdBaltop(plugin));
        register(new CmdEco(plugin));

        register(new CmdKick(plugin));
        register(new CmdBan(plugin));
        register(new CmdTempban(plugin));
        register(new CmdUnban(plugin));
        register(new CmdMute(plugin));
        register(new CmdUnmute(plugin));
    }

    private CommandMap getCommandMap() {
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            return (CommandMap) f.get(Bukkit.getServer());
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to get CommandMap: " + e.getMessage());
            return null;
        }
    }

    public void register(BaseCommand cmd) {
        commands.put(cmd.name().toLowerCase(), cmd);

        // plugin.yml already declares commands; still support fallback registration
        PluginCommand pc = plugin.getCommand(cmd.name());
        if (pc != null) {
            pc.setExecutor((sender, command, label, args) -> {
                dispatch(cmd, sender, args);
                return true;
            });
            return;
        }

        CommandMap map = getCommandMap();
        if (map == null)
            return;

        Command dynamic = new Command(cmd.name()) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                dispatch(cmd, sender, args);
                return true;
            }
        };
        map.register(plugin.getDescription().getName(), dynamic);
    }

    private void dispatch(BaseCommand cmd, CommandSender sender, String[] args) {
        if (cmd.playerOnly() && !(sender instanceof Player)) {
            plugin.msg().send(sender, "player_only");
            return;
        }
        if (!cmd.permission().isEmpty() && !sender.hasPermission(cmd.permission())) {
            plugin.msg().send(sender, "no_permission");
            return;
        }
        try {
            cmd.execute(sender, args);
        } catch (Exception e) {
            plugin.getLogger().severe("Command error: " + cmd.name() + " -> " + e.getMessage());
            e.printStackTrace();
            plugin.msg().send(sender, "error");
        }
    }

    public BaseCommand get(String name) {
        return commands.get(name.toLowerCase());
    }
}
