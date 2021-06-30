package com.alaan.outn.model;

import com.alaan.outn.interfac.ListItem;
import java.util.List;

public class Param implements ListItem {
    private String name;
    private String id;
    private List state;

    public Param(String name, String id) {
        this.name = name;
        this.id = id;
        this.state = state;
    }

    public Param() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }


    public List getStates() {
        return state;
    }


    public void setId(String id) {
        this.id = id;
    }
}