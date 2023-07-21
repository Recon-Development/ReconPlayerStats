package net.recondev.stats.listeners;


import net.recondev.stats.ReconStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final ReconStats plugin;
    public PlayerListener(final ReconStats plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClick(final PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            if (!event.getPlayer().isSneaking()) return;
            if (!plugin.getConfig().getBoolean("Settings.ShiftClickable")) return;

            if (event.getRightClicked() instanceof Player) {
                final Player rightClicked = (Player) event.getRightClicked();
                final Player player = event.getPlayer();
                plugin.getMessageCache().sendMessage(player, "Messages.OPENING-STATS-MENU");
                plugin.playerStatsMenu.openMenu(rightClicked, event.getPlayer());
            }
        }
    }
   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent event) {
         plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setTimesConnected(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getTimesConnected()+1);
   }

   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlace(final BlockPlaceEvent event) {
       plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setBlocksPlaced(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getBlocksPlaced()+1);
        if(event.getBlock().getType()== Material.CACTUS) {
            plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setCactusPlaced(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getCactusPlaced()+1);
        }

        if(event.getBlock().getType()== Material.SUGAR_CANE_BLOCK) {
            plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setCanePlaced(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getCanePlaced()+1);
        }

        if(event.getBlock().getType()==Material.AIR) {

        }
   }
   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(final BlockBreakEvent event) {
       plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setBlocksBroken(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getBlocksBroken()+1);
       if(event.getBlock().getType()== Material.SUGAR_CANE_BLOCK) {
           plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setCaneBroken(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getCaneBroken()+1);
       }

       if(event.getBlock().getType()==Material.NETHER_WARTS) {
           plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setNetherwartBroken(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getNetherwartBroken()+1);
       }
   }
   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
   public void onKill(final PlayerDeathEvent event) {
       plugin.getPlayerManager().getPlayer(event.getEntity().getUniqueId()).setDeaths(plugin.getPlayerManager().getPlayer(event.getEntity().getUniqueId()).getDeaths()+1);
       if(event.getEntity().getLastDamageCause().getEntity() instanceof Player) {
           plugin.getPlayerManager().getPlayer(event.getEntity().getLastDamageCause().getEntity().getUniqueId()).setKills(plugin.getPlayerManager().getPlayer(event.getEntity().getLastDamageCause().getEntity().getUniqueId()).getKills()+1);
       }
   }

   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
   public void onExpCollect(final PlayerExpChangeEvent event) {
        if(event.getAmount()>0) {
            plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setExpCollected(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getExpCollected()+event.getAmount());
        }
   }

   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
   public void on(final EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            plugin.getPlayerManager().getPlayer(event.getDamager().getUniqueId()).setDamageDealt(plugin.getPlayerManager().getPlayer(event.getEntity().getUniqueId()).getDamageDealt()+event.getDamage());
        }
   }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobKill(final EntityDeathEvent event) {
        if(event.getEntity() instanceof Player) return;
        if(event.getEntity().getKiller() instanceof Player) {
            plugin.getPlayerManager().getPlayer(event.getEntity().getKiller().getUniqueId()).setMobsKilled(plugin.getPlayerManager().getPlayer(event.getEntity().getUniqueId()).getMobsKilled()+1);
        }
    }
   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
   public void onDamage(final EntityDamageByEntityEvent event) {
       if(event.getEntity() instanceof Player) {
           plugin.getPlayerManager().getPlayer(event.getEntity().getUniqueId()).setDamageReceived(plugin.getPlayerManager().getPlayer(event.getEntity().getUniqueId()).getDamageReceived()+event.getDamage());
       }
   }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFish(final PlayerFishEvent event) {
        plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).setFishCaught(plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getFishCaught()+1);
    }


}
