package zero.nyc.mit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MainCommand implements CommandExecutor {

    public MainCommand(IceGun iceGun) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Plugin plugin = IceGun.getPlugin(IceGun.class);
        if (sender instanceof Player && sender.hasPermission("icegun.command")) {
            Player p = ((Player) sender).getPlayer();
            p.getInventory().addItem(new IceGunItemClass().CreateIceGun().getKey());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "messages.icegun-received"));
        } else if (!sender.hasPermission("icegun.command")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.non-player")));
        }
        return true;
    }
}
