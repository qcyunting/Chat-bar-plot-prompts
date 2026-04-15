package com.QC.managers;

import com.QC.ChatBarPlotPrompts;
import com.QC.models.Script;
import com.QC.models.ScriptLine;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ScriptManager {

    private final ChatBarPlotPrompts plugin;
    private final Map<String, Script> scripts;
    private final Map<UUID, ScriptTask> activeScripts;

    public ScriptManager(ChatBarPlotPrompts plugin) {
        this.plugin = plugin;
        this.scripts = new HashMap<>();
        this.activeScripts = new ConcurrentHashMap<>();
        loadAllScripts();
    }

    public void loadAllScripts() {
        scripts.clear();
        File scriptsDir = new File(plugin.getDataFolder(), "scripts");

        if (!scriptsDir.exists()) {
            scriptsDir.mkdirs();
            return;
        }

        File[] yamlFiles = scriptsDir.listFiles((dir, name) -> name.endsWith(".yml"));
        if (yamlFiles == null) return;

        for (File file : yamlFiles) {
            try {
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                Script script = loadScriptFromYaml(yaml);
                String name = file.getName().replace(".yml", "");
                script.setName(name);
                scripts.put(name, script);
                plugin.getLogger().info("已加载剧本: " + name);
            } catch (Exception e) {
                plugin.getLogger().warning("无法加载剧本文件: " + file.getName() + " - " + e.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Script loadScriptFromYaml(YamlConfiguration yaml) {
        String description = yaml.getString("description", "无描述");
        List<ScriptLine> lines = new ArrayList<>();

        List<Map<?, ?>> linesList = yaml.getMapList("lines");
        for (Map<?, ?> lineMap : linesList) {
            String speaker = (String) lineMap.get("speaker");
            String content = (String) lineMap.get("content");

            // 修复类型转换问题
            Object durationObj = lineMap.get("duration");
            int duration = 2000; // 默认值
            if (durationObj instanceof Number) {
                duration = ((Number) durationObj).intValue();
            } else if (durationObj instanceof String) {
                try {
                    duration = Integer.parseInt((String) durationObj);
                } catch (NumberFormatException e) {
                    duration = 2000;
                }
            }

            Object colorObj = lineMap.get("color");
            String color = "WHITE"; // 默认值
            if (colorObj != null) {
                color = colorObj.toString();
            }

            lines.add(new ScriptLine(speaker, content, duration, color));
        }

        return new Script(null, description, lines);
    }

    public void reloadScripts() {
        loadAllScripts();
    }

    public boolean playScript(Player player, String scriptName) {
        // 如果玩家正在播放剧本，先停止
        if (activeScripts.containsKey(player.getUniqueId())) {
            stopScript(player);
        }

        Script script = scripts.get(scriptName);
        if (script == null) {
            player.sendMessage("§c未找到剧本: " + scriptName);
            return false;
        }

        ScriptTask task = new ScriptTask(player, script);
        task.start();
        activeScripts.put(player.getUniqueId(), task);
        player.sendMessage("§a开始播放剧本: " + script.getDescription());
        return true;
    }

    public void stopScript(Player player) {
        ScriptTask task = activeScripts.remove(player.getUniqueId());
        if (task != null) {
            task.stop();
            player.sendMessage("§e已停止播放剧本");
        }
    }

    public void stopAllScripts() {
        for (ScriptTask task : activeScripts.values()) {
            task.stop();
        }
        activeScripts.clear();
    }

    public boolean isPlaying(Player player) {
        return activeScripts.containsKey(player.getUniqueId());
    }

    public Map<String, Script> getScripts() {
        return scripts;
    }

    private class ScriptTask {
        private final Player player;
        private final Script script;
        private int currentLine;
        private BukkitTask task;
        private boolean isRunning;

        public ScriptTask(Player player, Script script) {
            this.player = player;
            this.script = script;
            this.currentLine = 0;
            this.isRunning = true;
        }

        public void start() {
            playNextLine();
        }

        private void playNextLine() {
            if (!isRunning) return;

            if (currentLine >= script.getLines().size()) {
                // 剧本播放完毕
                activeScripts.remove(player.getUniqueId());
                player.sendMessage("§a剧本播放完毕！");
                return;
            }

            ScriptLine line = script.getLines().get(currentLine);
            player.sendMessage(line.getFormattedMessage());
            currentLine++;

            if (currentLine < script.getLines().size()) {
                // 获取当前行的持续时间，用于延迟下一行
                int duration = line.getDuration();
                if (duration > 0) {
                    task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (isRunning) {
                                playNextLine();
                            }
                        }
                    }.runTaskLater(plugin, duration / 50); // 转换为 ticks (20 ticks = 1秒)
                } else {
                    playNextLine();
                }
            } else {
                // 剧本结束
                activeScripts.remove(player.getUniqueId());
                player.sendMessage("§a剧本播放完毕！");
            }
        }

        public void stop() {
            isRunning = false;
            if (task != null && !task.isCancelled()) {
                task.cancel();
            }
        }
    }
}