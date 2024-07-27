package zero.nyc.mit;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigClass {

    private FileConfiguration cf;

    public ConfigClass(FileConfiguration cf) {
        this.cf = cf;
        cf.options().copyDefaults(true);
    }

    public FileConfiguration getConfig() {
        return this.cf;
    }

    public void reloadConfig() {
        ((IceGun)IceGun.getPlugin(IceGun.class)).reloadConfig();
        this.cf = ((IceGun)IceGun.getPlugin(IceGun.class)).getConfig();
    }
}
