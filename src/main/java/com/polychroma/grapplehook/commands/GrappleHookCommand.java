package com.polychroma.grapplehook.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GrappleHookCommand implements CommandExecutor {

    private final ItemStack grappleHook;

    public GrappleHookCommand(ItemStack grappleHook){
        this.grappleHook = grappleHook;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You must be a player to use this command");
            return true;
        }
        ((Player)sender).getInventory().addItem(grappleHook);
        sender.sendMessage(ChatColor.DARK_GREEN + "You have received a grappling hook!");
        return true;
    }
}
