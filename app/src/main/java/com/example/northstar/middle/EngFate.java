package com.example.northstar.middle;

public class EngFate {
    String field = null;
    String id = null;

    public EngFate(String field, String id) {
        this.field = field;
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return field + " " + id;
    }
}
