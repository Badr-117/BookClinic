package com.example.segproject2019.Services;

public class ClinicService {

    String id, service, role;

    public ClinicService() {}

    public ClinicService(String id, String service, String role) {
        this.id = id;
        this.service = service;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
