package pl.extollite.deathban;

import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.List;
import java.util.Map;

public class DeathBan extends PluginBase {

    String banReason;
    int unBanAfter;
    boolean showUnBanDate;
    boolean lightningOnDeath;
    boolean ipBan;
    boolean nameBan;

    @Override
    public void onEnable(){
        List<String> authors = this.getDescription().getAuthors();
        this.getLogger().info(TextFormat.DARK_GREEN + "Plugin by "+authors.get(0));
        this.saveDefaultConfig();
        banReason = this.getConfig().getString("banReason", "banned until ");
        unBanAfter = this.getConfig().getInt("unbanAfter", 3600) * 1000;
        showUnBanDate = this.getConfig().getBoolean("showUnBanDate", true);
        lightningOnDeath = this.getConfig().getBoolean("lightningOnDeath", false);
        ipBan = this.getConfig().getBoolean("ip-ban", true);
        nameBan = this.getConfig().getBoolean("name-ban", true);
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        for(Map.Entry<Integer, Level> entry : this.getServer().getLevels().entrySet()){
            if(!entry.getValue().getGameRules().getBoolean(GameRule.DO_IMMEDIATE_RESPAWN))
                entry.getValue().getGameRules().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        }
    }
}
