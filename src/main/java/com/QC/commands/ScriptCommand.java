package com.QC.commands;

import com.QC.ChatBarPlotPrompts;
import com.QC.models.Script;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScriptCommand implements CommandExecutor {

    private final ChatBarPlotPrompts plugin;

    public ScriptCommand(ChatBarPlotPrompts plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("scriptadmin")) {
            if (!sender.hasPermission("chatbarplotprompts.admin")) {
                sender.sendMessage("§c你没有权限使用此命令！");
                return true;
            }

            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                plugin.getScriptManager().reloadScripts();
                sender.sendMessage("§a已重新加载所有剧本！");
                return true;
            }

            sender.sendMessage("§c用法: /scriptadmin reload");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令只能由玩家执行！");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "play":
                if (args.length < 2) {
                    player.sendMessage("§c用法: /script play <剧本名称> [玩家名称]");
                    return true;
                }

                String scriptName = args[1];
                Player targetPlayer = player;

                if (args.length >= 3) {
                    if (!player.hasPermission("chatbarplotprompts.admin")) {
                        player.sendMessage("§c你没有权限给其他玩家播放剧本！");
                        return true;
                    }
                    targetPlayer = Bukkit.getPlayer(args[2]);
                    if (targetPlayer == null) {
                        player.sendMessage("§c未找到玩家: " + args[2]);
                        return true;
                    }
                }

                plugin.getScriptManager().playScript(targetPlayer, scriptName);
                if (targetPlayer != player) {
                    player.sendMessage("§a已向玩家 " + targetPlayer.getName() + " 播放剧本: " + scriptName);
                }
                break;

            case "stop":
                plugin.getScriptManager().stopScript(player);
                break;

            case "list":
                player.sendMessage("§6=== 可用剧本列表 ===");
                for (Script script : plugin.getScriptManager().getScripts().values()) {
                    player.sendMessage("§e- " + script.getName() + ": §7" + script.getDescription());
                }
                break;

            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§6=== 剧本插件帮助 ===");
        player.sendMessage("§e/script play <剧本名> [玩家名] §7- 播放剧本");
        player.sendMessage("§e/script stop §7- 停止当前剧本");
        player.sendMessage("§e/script list §7- 列出所有剧本");
        if (player.hasPermission("chatbarplotprompts.admin")) {
            player.sendMessage("§e/scriptadmin reload §7- 管理员重载");
        }
    }
}