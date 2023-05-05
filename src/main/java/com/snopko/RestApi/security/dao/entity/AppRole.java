package com.snopko.RestApi.security.dao.entity;

public enum AppRole {
    ADMIN("ADMIN"), USER("USER");
    private String name;

    AppRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
