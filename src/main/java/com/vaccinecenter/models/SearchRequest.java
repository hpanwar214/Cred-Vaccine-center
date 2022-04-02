package com.vaccinecenter.models;

import com.vaccinecenter.enums.DoseType;
import com.vaccinecenter.enums.VaccineType;

public class SearchRequest {
    private VaccineType vaccineType;

    private DoseType doseType;

    public SearchRequest(VaccineType vaccineType, DoseType doseType) {
        this.vaccineType = vaccineType;
        this.doseType = doseType;
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
}
