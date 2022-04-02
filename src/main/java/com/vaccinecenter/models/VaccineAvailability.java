package com.vaccinecenter.models;

import com.vaccinecenter.enums.DoseType;
import com.vaccinecenter.enums.VaccineType;

public class VaccineAvailability {
    private String id;

    private VaccineType vaccineType;

    private DoseType doseType;

    private Integer availableQuantityCount;

    private Integer bookedQuantityCount;

    public VaccineAvailability() {
    }

    public VaccineAvailability(String id, VaccineType vaccineType, DoseType doseType, Integer availableQuantityCount, Integer bookedQuantityCount) {
        this.id = id;
        this.vaccineType = vaccineType;
        this.doseType = doseType;
        this.availableQuantityCount = availableQuantityCount;
        this.bookedQuantityCount = bookedQuantityCount;
    }

    public String getId() {
        return id;
    }

    public Integer getAvailableQuantityCount() {
        return availableQuantityCount;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof VaccineAvailability) {
            return this.id.equals(((VaccineAvailability) object).id);
        }
        return false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VaccineType getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(VaccineType vaccineType) {
        this.vaccineType = vaccineType;
    }

    public DoseType getDoseType() {
        return doseType;
    }

    public void setDoseType(DoseType doseType) {
        this.doseType = doseType;
    }

    public void setAvailableQuantityCount(Integer availableQuantityCount) {
        this.availableQuantityCount = availableQuantityCount;
    }

    public Integer getBookedQuantityCount() {
        return bookedQuantityCount;
    }

    public void setBookedQuantityCount(Integer bookedQuantityCount) {
        this.bookedQuantityCount = bookedQuantityCount;
    }
//TODO: Add Getter and Setters
}
