package pl.extollite.DeathBan;

import cn.nukkit.level.GameRule;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.List;

public class DeathBan extends PluginBase {

    String banReason;
    int unBanAfter;
    boolean showUnBanDate;
    boolean lightningOnDeath;

    @Override
    public void onEnable(){
        List<String> authors = this.getDescription().getAuthors();
        this.getLogger().info(TextFormat.DARK_GREEN + "Plugin by "+authors.get(0));
        this.saveDefaultConfig();
        banReason = this.getConfig().getString("banReason", "banned until ");
        unBanAfter = this.getConfig().getInt("unbanAfter", 3600) * 1000;
        showUnBanDate = this.getConfig().getBoolean("showUnBanDate", true);
        lightningOnDeath = this.getConfig().getBoolean("lightningOnDeath", false);
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        if(!this.getServer().getDefaultLevel().getGameRules().getBoolean(GameRule.DO_IMMEDIATE_RESPAWN))
            this.getServer().getDefaultLevel().getGameRules().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
    }
}
