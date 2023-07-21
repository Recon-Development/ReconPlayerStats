package net.recondev.stats.commands.objects;


import net.recondev.commons.command.SubCommand;
import net.recondev.stats.ReconStats;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class StatsSubCommand extends SubCommand {

    private final ReconStats plugin;
    private final boolean playerOnly;

    public StatsSubCommand(final ReconStats plugin, final String name, final String permission, final boolean playerOnly) {
        super(name, permission);
        this.plugin = plugin;
        this.playerOnly = playerOnly;
    }

    @Override
    public void onCommand(final CommandSender sender, final String[] strings) {
        if (!(sender instanceof Player) && this.playerOnly) {
            System.out.println("[ExodusStats] This commands is player only.");
            return;
        }

        execute(sender, strings);
    }

    public abstract void execute(final CommandSender sender, final String[] args);

}
