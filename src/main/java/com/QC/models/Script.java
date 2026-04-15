package com.QC.models;

import java.util.List;

public class Script {
    private String name;
    private String description;
    private List<ScriptLine> lines;

    public Script() {}

    public Script(String name, String description, List<ScriptLine> lines) {
        this.name = name;
        this.description = description;
        this.lines = lines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ScriptLine> getLines() {
        return lines;
    }

    public void setLines(List<ScriptLine> lines) {
        this.lines = lines;
    }
}