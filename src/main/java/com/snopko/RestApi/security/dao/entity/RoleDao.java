package com.snopko.RestApi.security.dao.entity;

import jakarta.persistence.Entity;

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
