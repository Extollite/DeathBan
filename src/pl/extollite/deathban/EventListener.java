package pl.extollite.deathban;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.network.protocol.AddEntityPacket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class EventListener implements Listener {

    private DeathBan plugin;

    EventListener(DeathBan plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent ev) {
        Player player = ev.getEntity();
        if (!player.hasPermission("deathban.noban")) {
            Date now = new Date(System.currentTimeMillis() + plugin.unBanAfter);
            if(plugin.nameBan)
                plugin.getServer().getNameBans().addBan(player.getName(),
                    plugin.banReason + (plugin.showUnBanDate ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(now) : "" ), now);
            if(plugin.ipBan)
                plugin.getServer().getIPBans().addBan(player.getAddress(),
                    plugin.banReason + (plugin.showUnBanDate ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(now) : "" ), now);
            if (plugin.lightningOnDeath) {
                AddEntityPacket light = new AddEntityPacket();
                light.type = 93;
                light.entityRuntimeId = Entity.entityCount++;
                light.metadata = new EntityMetadata();
                light.x = (float) player.getX();
                light.y = (float) player.getY();
                light.z = (float) player.getZ();
                light.speedX = 0;
                light.speedZ = 0;
                light.speedY = 0;
                light.yaw = (float) player.getYaw();
                light.pitch = (float) player.getPitch();
                for (Map.Entry<Long, Player> playerEntry : player.getLevel().getPlayers().entrySet()) {
                    playerEntry.getValue().dataPacket(light);
                }
            }
            plugin.getServer().getScheduler().scheduleDelayedTask(plugin, () -> player.kick(
                    plugin.banReason + (plugin.showUnBanDate ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(now) : "" )
            ), 30);
        }
    }
}


