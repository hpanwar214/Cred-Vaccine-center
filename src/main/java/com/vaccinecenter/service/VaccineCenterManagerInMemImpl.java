package com.vaccinecenter.service;

import java.util.*;

import com.vaccinecenter.enums.DoseType;
import com.vaccinecenter.enums.VaccineType;
import com.vaccinecenter.models.SearchRequest;
import com.vaccinecenter.models.SearchResponse;
import com.vaccinecenter.models.VaccineAvailability;
import com.vaccinecenter.models.VaccineCenter;

public class VaccineCenterManagerInMemImpl implements VaccineCenterManager {
    HashMap<String, VaccineCenter> vaccineCenterMap = new HashMap<>();

    @Override
    public boolean add(VaccineCenter vaccineCenter) {
        return Objects.nonNull(
                vaccineCenterMap.put(vaccineCenter.getId(),vaccineCenter))?
                true:false;
    }

    @Override
    public boolean addAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = vaccineCenterMap.containsKey(vaccineCenterId)?
                vaccineCenterMap.get(vaccineCenterId):null;
        if(Objects.isNull(vaccineCenter)) return false;

        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();
        if(vaccineAvailabilities.isEmpty()){
            vaccineCenter.setVaccineAvailabilities(Arrays.asList(vaccineAvailability));
        }else{
            vaccineAvailabilities.add(vaccineAvailability);
            vaccineCenter.setVaccineAvailabilities(vaccineAvailabilities);
        }
        return true;
    }

    @Override
    public boolean updateAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = vaccineCenterMap.containsKey(vaccineCenterId)?
                vaccineCenterMap.get(vaccineCenterId):null;
        if(Objects.isNull(vaccineCenter)) return false;

        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();
        int index = 0;
        if(vaccineAvailabilities.isEmpty()){
            vaccineCenter.setVaccineAvailabilities(Arrays.asList(vaccineAvailability));
        }else{
            for(VaccineAvailability vaccineAvailability1 : vaccineAvailabilities){
                if(vaccineAvailability1.getDoseType().equals(vaccineAvailability.getDoseType())&&
                        vaccineAvailability1.getVaccineType().equals(vaccineAvailability.getVaccineType())){
                    vaccineAvailabilities.set(index,vaccineAvailability);
                    index++;
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = vaccineCenterMap.containsKey(vaccineCenterId)?
                vaccineCenterMap.get(vaccineCenterId):null;
        if(Objects.isNull(vaccineCenter)) return false;
        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();
        int index = 0;
        if(vaccineAvailabilities.isEmpty()){
            return true;
        }
        for(VaccineAvailability vaccineAvailability1 : vaccineAvailabilities){
            if(vaccineAvailability1.getDoseType().equals(vaccineAvailability.getDoseType())&&
                    vaccineAvailability1.getVaccineType().equals(vaccineAvailability.getVaccineType())){
                vaccineAvailabilities.remove(index);
                index++;
            }
        }

        return true;

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
