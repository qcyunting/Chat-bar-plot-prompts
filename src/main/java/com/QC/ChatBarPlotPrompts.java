package com.QC;

import com.QC.commands.ScriptCommand;
import com.QC.managers.ScriptManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class ChatBarPlotPrompts extends JavaPlugin {

    private static ChatBarPlotPrompts instance;
    private ScriptManager scriptManager;

    @Override
    public void onEnable() {
        instance = this;

        // 初始化剧本管理器
        scriptManager = new ScriptManager(this);

        // 注册命令
        if (getCommand("script") != null) {
            getCommand("script").setExecutor(new ScriptCommand(this));
        }
        if (getCommand("scriptadmin") != null) {
            getCommand("scriptadmin").setExecutor(new ScriptCommand(this));
        }

        // 保存默认剧本配置
        saveDefaultScripts();

        getLogger().info("§aChatBarPlotPrompts 插件已启用!");
    }

    @Override
    public void onDisable() {
        // 停止所有正在播放的剧本
        if (scriptManager != null) {
            scriptManager.stopAllScripts();
        }
        getLogger().info("§cChatBarPlotPrompts 插件已禁用!");
    }

    private void saveDefaultScripts() {
        // 创建scripts文件夹
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File scriptsDir = new File(getDataFolder(), "scripts");
        if (!scriptsDir.exists()) {
            scriptsDir.mkdirs();
        }

        // 保存示例剧本
        File exampleScript = new File(scriptsDir, "example_script.yml");
        if (!exampleScript.exists()) {
            saveResource("scripts/example_script.yml", false);
        }
    }

    public static ChatBarPlotPrompts getInstance() {
        return instance;
    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }
}