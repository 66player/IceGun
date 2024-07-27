package zero.nyc.mit;

import org.bukkit.plugin.java.JavaPlugin;

public final class IceGun extends JavaPlugin {


    private ConfigClass configClass;

    @Override
    public void onEnable() {
        this.configClass = new ConfigClass(this.getConfig());
        this.initCf();
        this.getCommand("icegun").setExecutor(new MainCommand(this));
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    public void initCf() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
    }
}
