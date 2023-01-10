package com.github.kumo0621.cowcount;

import org.bukkit.Location;
import org.bukkit.block.data.type.TNT;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
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
                for (Map.Entry<Entity, Integer> entry : mapList.entrySet()) {
                    int set = 1;
                    int value = entry.getValue();
                    value -= set;
                    entry.setValue(value);
                    System.out.println(entry);
                    Entity entity = entry.getKey();
                    entity.setCustomNameVisible(true);
                    entity.setCustomName(String.valueOf(value));
                    if(value==0) {
                        Location location = entity.getLocation();
                        location.getWorld().createExplosion(location,4,false,false);
                        mapList.remove(entity);
                    }
                }
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
        }
    }
}
