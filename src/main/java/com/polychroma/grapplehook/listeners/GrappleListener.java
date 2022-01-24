package com.polychroma.grapplehook.listeners;

import com.polychroma.grapplehook.Grapplehook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GrappleListener implements Listener {

    private final ItemStack grappleHook;
    private final Grapplehook plugin;

    HashMap<String, Long> grappleCooldown = new HashMap<>();

    public GrappleListener(ItemStack grappleHook, Grapplehook plugin) {
        this.grappleHook = grappleHook;
        this.plugin = plugin;
    }

    @EventHandler
    public void onFishingRodUse(PlayerFishEvent event){
        if(event.getState() != PlayerFishEvent.State.FISHING){
            return;
        }

        Player player = event.getPlayer();

        //If not grappleHook
        if(!player.getInventory().getItemInMainHand().equals(grappleHook)){
            return;
        }
        event.setCancelled(true);

        if(grappleCooldown.containsKey(player.getName())){
            //Player is on cooldown
            long timeDiff = System.currentTimeMillis() - grappleCooldown.get(player.getName());
            long secondsLeft = 6 - TimeUnit.MILLISECONDS.toSeconds(timeDiff);
            player.sendMessage(ChatColor.CYAN + "Du kan ikke bruge grappling hook før " + (int)secondsLeft + " sekundter");
            return;
        }

        Block targetBlock = player.getTargetBlockExact(12);
        //Make sure target block exists
        if(targetBlock == null){
            return;
        }

        grappleCooldown.put(player.getName(), System.currentTimeMillis());
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                plugin,
                () -> grappleCooldown.remove(player.getName()),
                6*20L
        );

        Vector diff = targetBlock.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
        player.setVelocity(diff);
    }

}
