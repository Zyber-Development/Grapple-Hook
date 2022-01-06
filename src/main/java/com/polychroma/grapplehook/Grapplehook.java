package com.polychroma.grapplehook;

import com.polychroma.grapplehook.commands.GrappleHookCommand;
import com.polychroma.grapplehook.listeners.GrappleListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Grapplehook extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemStack grappleHook = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = grappleHook.getItemMeta();

        meta.setDisplayName("Grappling Hook");
        meta.setLore(new ArrayList<>(){
            {
                add("To use the grappling hook right click");
            }
        });

        grappleHook.setItemMeta(meta);

        getCommand("grapplehook").setExecutor(new GrappleHookCommand(grappleHook));
        Bukkit.getPluginManager().registerEvents(new GrappleListener(grappleHook, this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
