package com.palvair.tuto.orm.constants;

/**
 * Created by rpalvair on 09/10/2014.
 */
public enum PropertiesConstants {

    DB_USERNAME("db.username"),
    DB_PASSWORD("db.password");

    private String value;

    private PropertiesConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
