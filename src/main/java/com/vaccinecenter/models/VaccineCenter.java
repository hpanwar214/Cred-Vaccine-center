package com.vaccinecenter.models;

import java.util.List;
import java.util.Set;
import com.vaccinecenter.enums.CostType;
import com.vaccinecenter.enums.DoseType;
import com.vaccinecenter.enums.VaccineType;

public class VaccineCenter {
    private String id;

    private String name;

    private Location location;

    //Supported set of vaccine types
    private Set<VaccineType> vaccineTypes;

    //Supported set of dose types
    private Set<DoseType> doseTypes;

    private Set<CostType> costTypes;

    // This data can be added/updated/removed separately.
    private List<VaccineAvailability> vaccineAvailabilities;

    public VaccineCenter() {
    }

    public VaccineCenter(String id, String name, Location location, Set<VaccineType> vaccineTypes, Set<DoseType> doseTypes, Set<CostType> costTypes,
            List<VaccineAvailability> vaccineAvailabilities) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.vaccineTypes = vaccineTypes;
        this.doseTypes = doseTypes;
        this.costTypes = costTypes;
        this.vaccineAvailabilities = vaccineAvailabilities;
    }

    public List<VaccineAvailability> getVaccineAvailabilities() {
        return vaccineAvailabilities;
    }

    public String getId() {
        return id;
    }

    //TODO: Add Getter and Setters

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<VaccineType> getVaccineTypes() {
        return vaccineTypes;
    }

    public void setVaccineTypes(Set<VaccineType> vaccineTypes) {
        this.vaccineTypes = vaccineTypes;
    }

    public Set<DoseType> getDoseTypes() {
        return doseTypes;
    }

    public void setDoseTypes(Set<DoseType> doseTypes) {
        this.doseTypes = doseTypes;
    }

    public Set<CostType> getCostTypes() {
        return costTypes;
    }

    public void setCostTypes(Set<CostType> costTypes) {
        this.costTypes = costTypes;
    }

    public void setVaccineAvailabilities(List<VaccineAvailability> vaccineAvailabilities) {
        this.vaccineAvailabilities = vaccineAvailabilities;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof VaccineCenter) {
            return this.id.equals(((VaccineCenter) object).id);
        }
        return false;
    }
}
