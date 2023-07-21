package net.recondev.stats.menus;


import net.recondev.commons.builders.PlaceholderReplacer;
import net.recondev.commons.menus.MenuBuilder;
import net.recondev.commons.menus.MenuSimple;
import net.recondev.stats.ReconStats;
import net.recondev.commons.builders.improved.ItemUtils;
import net.recondev.stats.objects.StatsPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


public class PlayerStatsMenu {
    private final ReconStats plugin;
    public PlayerStatsMenu(final ReconStats plugin) {
        this.plugin = plugin;
    }

    public StatsPlayer getStatsPlayer(final UUID uuid) {
        return plugin.getPlayerManager().getPlayer(uuid);
    }

    public void openMenu(final Player player, final Player holder) {
        final PlaceholderReplacer loreReplacer = new PlaceholderReplacer();
        final UUID uuid = player.getUniqueId();

        loreReplacer.addPlaceholder("%player_kills%", Integer.toString(getStatsPlayer(uuid).getKills()));
        loreReplacer.addPlaceholder("%deaths%", Integer.toString(getStatsPlayer(uuid).getDeaths()));
        loreReplacer.addPlaceholder("%mob_kills%", Integer.toString(getStatsPlayer(uuid).getMobsKilled()));
        loreReplacer.addPlaceholder("%blocks_broken%", Integer.toString(getStatsPlayer(uuid).getBlocksBroken()));
        loreReplacer.addPlaceholder("%blocks_placed%", Integer.toString(getStatsPlayer(uuid).getBlocksPlaced()));
        loreReplacer.addPlaceholder("%times_connected%", Integer.toString(getStatsPlayer(uuid).getTimesConnected()));
        loreReplacer.addPlaceholder("%exp_gained%", Integer.toString(getStatsPlayer(uuid).getExpCollected()));
        loreReplacer.addPlaceholder("%cactus_placed%", Integer.toString(getStatsPlayer(uuid).getCactusPlaced()));
        loreReplacer.addPlaceholder("%cane_placed%", Integer.toString(getStatsPlayer(uuid).getCanePlaced()));
        loreReplacer.addPlaceholder("%cane_broken%", Integer.toString(getStatsPlayer(uuid).getCaneBroken()));
        loreReplacer.addPlaceholder("%damage_dealt%", Double.toString(Math.round(getStatsPlayer(uuid).getDamageDealt())));
        loreReplacer.addPlaceholder("%damage_received%", Double.toString(Math.round(getStatsPlayer(uuid).getDamageReceived())));

        final FileConfiguration settingsConfig = plugin.getMenuConfig();

        final MenuSimple menu = MenuBuilder.builder().buildSimple(settingsConfig.getString("Menus.STATS-MENU.Title"), settingsConfig.getInt("Menus.STATS-MENU.Size"));

        for (final String key : settingsConfig.getConfigurationSection("Menus.STATS-MENU.Items").getKeys(false)) {

            final int slot = settingsConfig.getInt("Menus.STATS-MENU.Items." + key + ".Slot");

            menu.setItemAt(slot, ItemUtils.getItem(settingsConfig, "Menus.STATS-MENU.Items." + key).parse(loreReplacer));
        }

        for (final String key : settingsConfig.getConfigurationSection("Menus.STATS-MENU.Borders").getKeys(false)) {
            final ItemStack border = ItemUtils.getItem(settingsConfig, "Menus.STATS-MENU.Borders." + key).parse();
            for (final int i : settingsConfig.getIntegerList("Menus.STATS-MENU.Borders." + key + ".Slots")) menu.setItemAt(i, border);
        }

        menu.setClickable(false);
        holder.openInventory(menu.getGUI());
    }
}

