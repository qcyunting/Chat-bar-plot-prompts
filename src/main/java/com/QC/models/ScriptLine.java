package com.QC.models;

import net.md_5.bungee.api.ChatColor;
import java.util.HashMap;
import java.util.Map;

public class ScriptLine {
    private String speaker;
    private String content;
    private int duration;
    private String color;

    private static final Map<String, ChatColor> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("BLACK", ChatColor.BLACK);
        COLOR_MAP.put("DARK_BLUE", ChatColor.DARK_BLUE);
        COLOR_MAP.put("DARK_GREEN", ChatColor.DARK_GREEN);
        COLOR_MAP.put("DARK_AQUA", ChatColor.DARK_AQUA);
        COLOR_MAP.put("DARK_RED", ChatColor.DARK_RED);
        COLOR_MAP.put("DARK_PURPLE", ChatColor.DARK_PURPLE);
        COLOR_MAP.put("GOLD", ChatColor.GOLD);
        COLOR_MAP.put("GRAY", ChatColor.GRAY);
        COLOR_MAP.put("DARK_GRAY", ChatColor.DARK_GRAY);
        COLOR_MAP.put("BLUE", ChatColor.BLUE);
        COLOR_MAP.put("GREEN", ChatColor.GREEN);
        COLOR_MAP.put("AQUA", ChatColor.AQUA);
        COLOR_MAP.put("RED", ChatColor.RED);
        COLOR_MAP.put("LIGHT_PURPLE", ChatColor.LIGHT_PURPLE);
        COLOR_MAP.put("YELLOW", ChatColor.YELLOW);
        COLOR_MAP.put("WHITE", ChatColor.WHITE);
    }

    public ScriptLine() {}

    public ScriptLine(String speaker, String content, int duration, String color) {
        this.speaker = speaker;
        this.content = content;
        this.duration = duration;
        this.color = color;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ChatColor getChatColor() {
        if (color == null || color.isEmpty()) {
            return ChatColor.WHITE;
        }

        ChatColor chatColor = COLOR_MAP.get(color.toUpperCase());
        if (chatColor != null) {
            return chatColor;
        }

        if (color.length() == 2 && color.charAt(0) == '&') {
            char code = color.charAt(1);
            chatColor = ChatColor.getByChar(code);
            if (chatColor != null) {
                return chatColor;
            }
        }

        return ChatColor.WHITE;
    }

    public String getFormattedMessage() {
        ChatColor colorCode = getChatColor();
        return colorCode + "[" + speaker + "] " + content;
    }
}