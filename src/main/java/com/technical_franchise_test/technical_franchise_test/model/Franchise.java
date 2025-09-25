package com.technical_franchise_test.technical_franchise_test.model;

import java.util.List;
import java.util.UUID;

public class Franchise {
    private String id;
    private String name;
    private List<String> branches;

    public Franchise() {
    }

    public Franchise(String id, String name, List<String> branches) {
        this.id = id;
        this.name = name;
        this.branches = branches;
    }

    public Franchise(String name, List<String> branches) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.branches = branches;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<String> getBranches() {
        return branches;
    }
}
