package com.vaccinecenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.vaccinecenter.enums.DoseType;
import com.vaccinecenter.enums.VaccineType;
import com.vaccinecenter.models.SearchRequest;
import com.vaccinecenter.models.SearchResponse;
import com.vaccinecenter.models.VaccineAvailability;
import com.vaccinecenter.models.VaccineCenter;

public class VaccineCenterManagerInMemImpl implements VaccineCenterManager {
    final Map<String, VaccineCenter> vaccineCenterMap = new HashMap<>();

    @Override
    public boolean add(VaccineCenter vaccineCenter) {
        return false;
    }

    @Override
    public boolean addAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        return false;
    }

    @Override
    public boolean updateAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        return false;
    }

    @Override
    public boolean removeAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        return false;
    }

    @Override
    public boolean bookVaccineSlot(String vaccineCenterId, VaccineType vaccineType, DoseType doseType) {
        return false;
    }

    @Override
    public SearchResponse search(VaccineType vaccineType, DoseType doseType) {
        return null;
    }

    @Override
    public SearchResponse search(List<SearchRequest> searchRequestList) {
        return null;
    }

    public VaccineCenter get(final String vaccineCenterId) {
        return vaccineCenterMap.get(vaccineCenterId);
    }
}
