package net.recondev.stats.commands.subcommands;

import net.recondev.stats.ReconStats;
import net.recondev.stats.commands.objects.StatsSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OtherCommand extends StatsSubCommand {

    private final ReconStats plugin;

    public OtherCommand(final ReconStats plugin) {
        super(plugin, "view", "playerstats.command", true);
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if(args.length<2) {

            plugin.getMessageCache().sendMessage((Player) sender, "Messages.OPENING-STATS-MENU");
            plugin.getPlayerStatsMenu().openMenu((Player) sender, (Player) sender);

            return;
        }

        if(!plugin.getPlayerManager().containsKey(Bukkit.getPlayerExact(args[1]).getUniqueId())) {
            this.plugin.getMessageCache().sendMessage((Player) sender, "Messages.NULL-PLAYER");
            return;
        }

        this.plugin.getMessageCache().sendMessage((Player) sender, "Messages.OPENING-STATS-MENU");
        plugin.getPlayerStatsMenu().openMenu(Bukkit.getPlayerExact(args[1]), (Player) sender);
    }
}
