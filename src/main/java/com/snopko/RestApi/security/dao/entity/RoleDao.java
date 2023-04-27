package com.snopko.RestApi.security.dao.entity;

public enum RoleDao {
    ADMIN("ADMIN"), USER("USER");
    private String name;

    RoleDao(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
