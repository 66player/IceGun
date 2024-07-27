package zero.nyc.mit;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;


public class MainListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack icegun = new IceGunItemClass().CreateIceGun().getKey();
        for (ItemStack item : e.getInventory().getMatrix()) {
            if (item != null && item.isSimilar(icegun) && item.getItemMeta().equals(icegun.getItemMeta())) {
                e.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Plugin plugin = IceGun.getPlugin(IceGun.class);

        int cooldownTimeInSeconds = plugin.getConfig().getInt("cooldown-time") * 20;
        String message;

        Action action = e.getAction();
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        ItemStack icegun = new IceGunItemClass().CreateIceGun().getKey();
        if (!p.hasCooldown(item.getType())) {
            if (action == Action.RIGHT_CLICK_AIR && item != null && item.isSimilar(icegun) && item.getItemMeta().equals(icegun.getItemMeta())) {
                iceShot(p);
                p.setCooldown(item.getType(), cooldownTimeInSeconds);
            }
        } else {
            e.setCancelled(true);
            cooldownTimeInSeconds = plugin.getConfig().getInt("cooldown-time");
            message = plugin.getConfig().getString("messages.cooldown");
            message = message.replace("{cooldown-time}", String.valueOf(cooldownTimeInSeconds));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }


    public static void iceShot(Player player) {
        double damage = 5;
        double distance = 12;
        Vector direction = player.getLocation().getDirection().normalize();
        World world = player.getWorld();
        Location loc = player.getEyeLocation().clone().add(direction);

        for (int i = 0; i < distance * 2; ++i) {
            loc.add(direction);

            if (!isPassable(loc.getBlock().getType())) {
                break;
            }

            for (int j = 0; j < 3; ++j) {
                world.spawnParticle(Particle.BLOCK_CRACK, loc, 1, Material.ICE.createBlockData());
            }

            for (Entity entity : world.getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                if (entity instanceof Player || entity instanceof LivingEntity) {
                    if (entity.equals(player)) {
                        continue;
                    }

                    if (entity instanceof Player) {
                        Player target = (Player) entity;
                        target.damage(damage);
                        fullbox(target);
                        target.setKiller(player);
                    } else if (entity instanceof LivingEntity) {
                        LivingEntity target = (LivingEntity) entity;
                        target.damage(damage);
                        fullbox(target);
                        target.setKiller(player);
                    }
                    return;
                }
            }
        }
    }

    public static void fullbox(Entity target) {
        Location center = target.getLocation().add(0, 1, 0);
        Material material = Material.PACKED_ICE;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (!(x == 0 && y == 0 && z == 0)) {
                        Location blockLocation = center.clone().add(x, y, z);
                        if (blockLocation.getBlock().getType() == Material.AIR) {
                            blockLocation.getBlock().setType(material);
                        }
                    }
                }
            }
        }

    }

    private static boolean isPassable(Material material) {
        switch (material) {
            case AIR:
            case WATER:
            case LAVA:
            case SNOW:
            case DEAD_BUSH:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case TORCH:
            case FIRE:
            case LADDER:
            case RAIL:
            case ACTIVATOR_RAIL:
            case DETECTOR_RAIL:
            case POWERED_RAIL:
            case REDSTONE_WIRE:
                return true;
            default:
                return false;
        }
    }
}
