package br.com.essentialrx.commands.base;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.messages.Messages;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand {

    protected final EssentialRXPlugin plugin;

    public BaseCommand(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract String name();
    public abstract String permission();
    public abstract boolean playerOnly();
    public abstract String usage();

    public abstract void execute(CommandSender sender, String[] args);

    protected Messages msg() {
        return plugin.msg();
    }

    protected boolean has(CommandSender sender) {
        String perm = permission();
        return perm == null || perm.isEmpty() || sender.hasPermission(perm);
    }
}
