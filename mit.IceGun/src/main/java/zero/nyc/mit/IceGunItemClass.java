package zero.nyc.mit;

import javafx.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class IceGunItemClass {

    public Pair<ItemStack, ItemMeta> CreateIceGun() {
        Plugin plugin = IceGun.getPlugin(IceGun.class);

        ItemStack iceGunItem = new ItemStack(Material.STICK);
        iceGunItem.addUnsafeEnchantment(Enchantment.DURABILITY, 5);

        ItemMeta iceGunItemMeta = iceGunItem.getItemMeta();

        iceGunItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("icegun.name")));

        List<String> list = new ArrayList<>();
        for (int i = 0; i < plugin.getConfig().getList("icegun.lore").size(); i++) {
            list.add(ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().getList("icegun.lore").get(i)));
        }
        if (list.size() > 0) {
            iceGunItemMeta.setLore(list);
        }

        iceGunItem.setItemMeta(iceGunItemMeta);
        return new Pair<>(iceGunItem, iceGunItemMeta);
    }
}
