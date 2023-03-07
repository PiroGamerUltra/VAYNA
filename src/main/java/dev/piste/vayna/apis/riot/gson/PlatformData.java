package dev.piste.vayna.apis.riot.gson;

import dev.piste.vayna.apis.riot.gson.platformdata.Status;

import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class PlatformData {

    private String id;
    private String name;
    private List<String> locales;
    private List<Status> maintenances;
    private List<Status> incidents;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getLocales() {
        return locales;
    }

    public List<Status> getMaintenances() {
        return maintenances;
    }

    public List<Status> getIncidents() {
        return incidents;
    }

}