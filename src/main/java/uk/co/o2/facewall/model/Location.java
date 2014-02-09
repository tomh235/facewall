package uk.co.o2.facewall.model;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Location {
    PUNE ("Pune, India"),
    BATH_ROAD ("Bath Road, Slough, UK"),
    BUCKINGHAM_AVENUE ("Buckingham Avenue, Slough, UK"),
    LEEDS ("Leeds, UK");

    private  String locationName;

    Location(String locationName) {
        this.locationName = locationName;
    }

    private String getLocationName() {
        return locationName;
    }

    public Map<String, String> options() {
        LinkedHashMap<String, String> vals = new LinkedHashMap<>();
        for(Location location : Location.values()) {
            vals.put(location.name(), location.locationName);
        }
        return vals;
    }
}
