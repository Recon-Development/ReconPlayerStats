package net.recondev.stats.commands.subcommands;

import net.recondev.commons.builders.PlaceholderReplacer;
import net.recondev.stats.ReconStats;
import net.recondev.stats.commands.objects.StatsSubCommand;
import net.recondev.stats.objects.StatsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResetCommand extends StatsSubCommand {

    private final ReconStats plugin;

    public ResetCommand(final ReconStats plugin) {
        super(plugin, "reset", "playerstats.reset", false);
        this.plugin = plugin;
    }

    public StatsPlayer getStatsPlayer(final UUID uuid) {
        return plugin.getPlayerManager().getPlayer(uuid);
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if(args.length<2) {
            this.plugin.getMessageCache().sendMessage((Player) sender, "Messages.HELP");
        }

        if(!plugin.getPlayerManager().containsKey(Bukkit.getPlayerExact(args[1]).getUniqueId())) {
            this.plugin.getMessageCache().sendMessage((Player) sender, "Messages.NULL-PLAYER");
            return;
        }

        final PlaceholderReplacer placeholderReplacer = new PlaceholderReplacer();
        placeholderReplacer.addPlaceholder("%player%", args[1]);
        this.plugin.getMessageCache().sendMessage((Player) sender, placeholderReplacer,"Messages.RESET-STATS");

        final Player targetPlayer = Bukkit.getPlayerExact(args[1]);
        final UUID uuid = targetPlayer.getUniqueId();
            getStatsPlayer(uuid).setDamageReceived(0.0);
            getStatsPlayer(uuid).setMobsKilled(0);
            getStatsPlayer(uuid).setBlocksPlaced(0);
            getStatsPlayer(uuid).setBlocksBroken(0);
            getStatsPlayer(uuid).setDeaths(0);
            getStatsPlayer(uuid).setCanePlaced(0);
            getStatsPlayer(uuid).setCactusPlaced(0);
            getStatsPlayer(uuid).setCaneBroken(0);
            getStatsPlayer(uuid).setTimesConnected(0);
            getStatsPlayer(uuid).setExpCollected(0);
            getStatsPlayer(uuid).setDamageDealt(0.0);
            getStatsPlayer(uuid).setKills(0);
            getStatsPlayer(uuid).setFishCaught(0);
            getStatsPlayer(uuid).setNetherwartBroken(0);
    }
}