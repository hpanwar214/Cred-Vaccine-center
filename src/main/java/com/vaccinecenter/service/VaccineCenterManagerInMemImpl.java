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
        for(Map.Entry<String,VaccineCenter> vaccineCenterEntry : vaccineCenterMap.entrySet() ){
            if(vaccineCenterEntry.getKey().equals(vaccineCenterId)){
                List<VaccineAvailability> vaccineAvailabilityList = vaccineCenterEntry.getValue().getVaccineAvailabilities();
                for(VaccineAvailability vaccineAvailability:vaccineAvailabilityList){
                    if(vaccineAvailability.getVaccineType().equals(vaccineType)&&
                    vaccineAvailability.getDoseType().equals(doseType)&&
                    vaccineAvailability.getAvailableQuantityCount()>0){
                        vaccineAvailability.setAvailableQuantityCount(
                                vaccineAvailability.getAvailableQuantityCount()-1
                        );;
                        updateAvailability(vaccineCenterId,vaccineAvailability);
                        return true;
                    }

                }
            }
        }
        return false;
    }

    @Override
    public SearchResponse search(VaccineType vaccineType, DoseType doseType) {
        int totalCount = 0;
        SearchResponse searchResponse = new SearchResponse(0, Collections.emptyList());
        for(Map.Entry<String,VaccineCenter> vaccineCenterEntry : vaccineCenterMap.entrySet() ){
            List<VaccineAvailability> vaccineAvailabilityList = vaccineCenterEntry.getValue().getVaccineAvailabilities();
            for(VaccineAvailability vaccineAvailability:vaccineAvailabilityList){
                if(vaccineAvailability.getVaccineType().equals(vaccineType)&&
                        vaccineAvailability.getDoseType().equals(doseType)&&
                        vaccineAvailability.getAvailableQuantityCount()>0){
                    totalCount++;
                    searchResponse.getResults().add(vaccineCenterEntry.getValue());
            }
            }
        }
        searchResponse.setTotalCount(totalCount);
        return searchResponse;
    }

    @Override
    public SearchResponse search(List<SearchRequest> searchRequestList) {
        int totalCount = 0;
        SearchResponse searchResponse = new SearchResponse(0, Collections.emptyList());
        SearchResponse tempSearchResponse = new SearchResponse(0, Collections.emptyList());

        for(SearchRequest searchRequest:searchRequestList){
             tempSearchResponse = search(searchRequest.getVaccineType(),searchRequest.getDoseType());
        }
        totalCount = searchResponse.getTotalCount() + tempSearchResponse.getTotalCount();
        List<VaccineCenter> vaccineCenters = new ArrayList<>();
        vaccineCenters.addAll(searchResponse.getResults());
        vaccineCenters.addAll(tempSearchResponse.getResults());
        searchResponse.setTotalCount(totalCount);
        searchResponse.setResults(vaccineCenters);
        return searchResponse;
    }

    public VaccineCenter get(final String vaccineCenterId) {
        return vaccineCenterMap.get(vaccineCenterId);
    }

    public HashSet availableVaccineTypeAndDoseTypeAtCentre(List<VaccineAvailability> vaccineAvailabilityList){
        HashSet<String> availableVaccineTypeAndDoseType = new HashSet<>();
        for(VaccineAvailability vaccineAvailability:vaccineAvailabilityList){
            availableVaccineTypeAndDoseType.add(vaccineAvailability.getVaccineType()+"-"+vaccineAvailability.getDoseType());
        }
        return availableVaccineTypeAndDoseType;

    }
}
