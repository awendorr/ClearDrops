package com.davud.cleardrops.commands;

import com.davud.cleardrops.ClearDrops;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RejectClearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ClearDrops plugin = ClearDrops.getInstance();

        if (!plugin.isClearPending()) {
            sender.sendMessage(ChatColor.RED + "Su anda iptal edilecek bir temizlik islemi yok.");
            return true;
        }

        Bukkit.getScheduler().cancelTask(plugin.getPendingTaskId());
        plugin.setClearPending(false);

        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " temizlik islemini reddetti! Yerdeki esyalar silinmeyecek.");
        return true;
    }
}