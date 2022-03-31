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
        int len = vaccineCenterMap.size();
        vaccineCenterMap.put(vaccineCenter.getId(),vaccineCenter);
        if(vaccineCenter.getId() == null)
            return false;


        if(len+1==vaccineCenterMap.size())
            return true;
        else
            return false;

    }

    @Override
    public boolean addAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = vaccineCenterMap.containsKey(vaccineCenterId)?
                vaccineCenterMap.get(vaccineCenterId):null;
        if(Objects.isNull(vaccineCenter)) return false;

        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();
        if(vaccineAvailabilities.isEmpty()){
            List<VaccineAvailability> vaccineAvailabilities1 = new ArrayList<>();
            vaccineAvailabilities1.add(vaccineAvailability);
            vaccineCenter.setVaccineAvailabilities(vaccineAvailabilities1);
        }else{
            for(VaccineAvailability vaccineAvailability1 : vaccineAvailabilities){
                if(vaccineAvailability1.getDoseType().equals(vaccineAvailability.getDoseType())&&
                        vaccineAvailability1.getVaccineType().equals(vaccineAvailability.getVaccineType())){
                    updateAvailability(vaccineCenterId,vaccineAvailability);
                }
            }
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
            return false;
        }else{
            for(VaccineAvailability vaccineAvailability1 : vaccineAvailabilities){
                if(vaccineAvailability1.equals(vaccineAvailability)){
                    vaccineAvailabilities.set(index,vaccineAvailability);
                    return true;
                }
                index++;
            }
        }
        return false;
    }

    @Override
    public boolean removeAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = vaccineCenterMap.containsKey(vaccineCenterId)?
                vaccineCenterMap.get(vaccineCenterId):null;
        if(Objects.isNull(vaccineCenter)) return false;
        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();
        int index = 0;
        if(vaccineAvailabilities.isEmpty()){
            return false;
        }
        for(VaccineAvailability vaccineAvailability1 : vaccineAvailabilities){
            if(vaccineAvailability1.equals(vaccineAvailability)){
                vaccineAvailabilities.remove(index);
                return true;
            }
            index++;
        }

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
