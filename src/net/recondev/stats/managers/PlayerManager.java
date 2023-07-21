package net.recondev.stats.managers;

import net.recondev.commons.patterns.Registry;
import net.recondev.stats.objects.StatsPlayer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class PlayerManager implements Registry<UUID, StatsPlayer> {

    @Getter
    private final Map<UUID, StatsPlayer> registry;

    public PlayerManager() {
        this.registry = new HashMap<>();
    }

    public StatsPlayer getPlayer(final UUID uuid) {
        if (!this.contains(uuid)) {
            final StatsPlayer player = new StatsPlayer(uuid);
            this.registry.put(uuid, player);
            return player;
        }

        return this.registry.get(uuid);
    }

    private boolean contains(final UUID uuid) {
        return this.registry.containsKey(uuid);
    }
}
