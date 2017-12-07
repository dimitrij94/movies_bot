package com.bots.crew.pp.webhook.enteties.db;

public enum MovieTechnology {
    T2D("2D"), T3D("3D"), T4D("4D"), ANY("Any");

    String name;

    MovieTechnology(String name) {
        this.name = name;
    }
}
