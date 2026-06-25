package com.davud.cleardrops;

import com.davud.cleardrops.commands.ClearDropsCommand;
import com.davud.cleardrops.commands.RejectClearCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClearDrops extends JavaPlugin {

    private static ClearDrops instance;
    private int pendingTaskId = -1;
    private boolean isClearPending = false;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("cleardrops").setExecutor(new ClearDropsCommand());
        getCommand("rejectclear").setExecutor(new RejectClearCommand());
        getLogger().info("ClearDrops eklentisi aktif edildi!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ClearDrops eklentisi devre disi birakildi!");
    }

    public static ClearDrops getInstance() {
        return instance;
    }

    public boolean isClearPending() {
        return isClearPending;
    }

    public void setClearPending(boolean pending) {
        this.isClearPending = pending;
    }

    public int getPendingTaskId() {
        return pendingTaskId;
    }

    public void setPendingTaskId(int taskId) {
        this.pendingTaskId = taskId;
    }
}