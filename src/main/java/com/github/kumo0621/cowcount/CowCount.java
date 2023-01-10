package com.github.kumo0621.cowcount;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public final class CowCount extends JavaPlugin implements org.bukkit.event.Listener {
    int count = 30;
    Map<Entity, Integer> mapList = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {

            @Override

            public void run() {
                //何かやりたいときはここに書き込む
                mapList.entrySet().removeIf((entry) -> {
                    int set = 1;
                    int value = entry.getValue();
                    value -= set;
                    entry.setValue(value);
                    Entity entity = entry.getKey();
                    entity.setCustomNameVisible(true);
                    entity.setCustomName(String.valueOf(value));
                    Location location = entity.getLocation();
                    if (value == 0) {
                        if (!entity.getScoreboardTags().contains("cow") && !entity.getScoreboardTags().contains("mooshroom")) {
                            location.getWorld().createExplosion(location, 10, false, false);
                        }
                        return true;
                    }
                    return false;
                });
            }
        }.runTaskTimer(this, 0L, 10L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void entitySpawnEvent(EntitySpawnEvent e) {
        if (e.getEntityType() == EntityType.COW) {
            Entity entity = e.getEntity();
            mapList.put(entity, count);
        } else if (e.getEntityType() == EntityType.MUSHROOM_COW) {
            Entity entity = e.getEntity();
            mapList.put(entity, count);
        }
    }
}
