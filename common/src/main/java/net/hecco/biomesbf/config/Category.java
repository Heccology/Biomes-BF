package net.hecco.biomesbf.config;

import java.util.ArrayList;

public record Category(String title, boolean isChild, boolean isClient, ArrayList<String> values, ArrayList<String> categoryChildrenIds) {

    public void putValue(String id) {
        this.values.add(id);
    }
}
