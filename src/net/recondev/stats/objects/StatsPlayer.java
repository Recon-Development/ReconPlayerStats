package net.recondev.stats.objects;

import lombok.Data;

import java.util.UUID;

@Data
public class StatsPlayer {

    private final UUID uuid;
    private int kills;
    private int deaths;
    private int blocksBroken;
    private int blocksPlaced;
    private int timesConnected;
    private int expCollected;
    private int cactusPlaced;
    private int canePlaced;
    private int caneBroken;
    private int mobsKilled;
    private int netherwartBroken;
    private int fishCaught;
    private double damageDealt;
    private double damageReceived;

    public StatsPlayer(final UUID uuid) {
        this.uuid = uuid;
        this.kills=0;
        this.deaths=0;
        this.blocksBroken=0;
        this.blocksPlaced=0;
        this.timesConnected=0;
        this.expCollected=0;
        this.cactusPlaced=0;
        this.canePlaced=0;
        this.caneBroken=0;
        this.damageDealt=0;
        this.mobsKilled=0;
        this.damageReceived=0;
        this.netherwartBroken = 0;
        this.fishCaught = 0;
    }



}
