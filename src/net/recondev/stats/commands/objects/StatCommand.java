package net.recondev.stats.commands.objects;

import net.recondev.commons.command.CommandBuilder;
import net.recondev.stats.ReconStats;
import net.recondev.stats.commands.subcommands.ResetCommand;
import net.recondev.stats.commands.subcommands.OtherCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StatCommand extends CommandBuilder {

    private final ReconStats plugin;
    private final Set<StatsSubCommand> subCommands;

    public StatCommand(final ReconStats plugin) {
        super("playerstats");
        this.setAliases(Arrays.asList(
                "reconstats"));

        this.plugin = plugin;
        this.subCommands = new HashSet<>(Arrays.asList(
                new OtherCommand(plugin),
                new ResetCommand(plugin)));
    }


    @Override
    public boolean onCommand(final CommandSender sender, final String[] args) {

        if (args.length == 0) {
                this.plugin.getMessageCache().sendMessage((Player) sender, "Messages.HELP");
                return false;
        }


        for (final StatsSubCommand subCommand : this.subCommands) {
            if (!subCommand.getName().equalsIgnoreCase(args[0])) {
                continue;
            }

            subCommand.onCommand(sender, args);
            return true;
        }

            this.plugin.getMessageCache().sendMessage((Player) sender, "Messages.HELP");
            return false;

    }
}
