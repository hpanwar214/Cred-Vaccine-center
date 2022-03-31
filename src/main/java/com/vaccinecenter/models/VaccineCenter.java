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

    @Override
    public boolean equals(Object object) {
        if (object instanceof VaccineCenter) {
            return this.id.equals(((VaccineCenter) object).id);
        }
        return false;
    }
}
