package com.davud.cleardrops.commands;

import com.davud.cleardrops.ClearDrops;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClearDropsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ClearDrops plugin = ClearDrops.getInstance();

        if (sender.isOp()) {
            executeClear(sender);
            return true;
        }

        if (plugin.isClearPending()) {
            sender.sendMessage(ChatColor.RED + "Su anda zaten bekleyen bir temizlik islemi var!");
            return true;
        }

        plugin.setClearPending(true);

        TextComponent message1 = new TextComponent(ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + " yerdeki esyalari silmek istiyor. ");
        
        TextComponent rejectButton = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[REDDET]");
        rejectButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rejectclear"));
        rejectButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Tiklayarak islemi iptal et.")));
        
        TextComponent finalMessage = new TextComponent("");
        finalMessage.addExtra(message1);
        finalMessage.addExtra(rejectButton);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(sender)) {
                p.spigot().sendMessage(finalMessage);
            }
        }

        sender.sendMessage(ChatColor.YELLOW + "Temizlik istegi gonderildi. Birisi reddetmezse 5 saniye sonra esyalar silinecek.");

        int taskId = new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.isClearPending()) {
                    executeClear(sender);
                    plugin.setClearPending(false);
                }
            }
        }.runTaskLater(plugin, 100L).getTaskId();

        plugin.setPendingTaskId(taskId);
        return true;
    }

    private void executeClear(CommandSender sender) {
        int count = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                    count++;
                }
            }
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + "Yerdeki " + ChatColor.YELLOW + count + ChatColor.GREEN + " esya temizlendi!");
    }
}