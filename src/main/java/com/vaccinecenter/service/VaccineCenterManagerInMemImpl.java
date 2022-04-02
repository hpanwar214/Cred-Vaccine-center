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
        if(vaccineCenter.getId() == null)
            return false;
        else if(get(vaccineCenter.getId()) != null)
            return false;//if vaccine center already exist

        vaccineCenterMap.put(vaccineCenter.getId(),vaccineCenter);
        return true;
    }

    @Override
    public boolean addAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = get(vaccineCenterId);
        if(Objects.isNull(vaccineCenter)) return false;

        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();

        if(vaccineAvailabilities.isEmpty()){
            List<VaccineAvailability> vaccineAvailabilities1 = new ArrayList<>();
            vaccineAvailabilities1.add(vaccineAvailability);
            vaccineCenter.setVaccineAvailabilities(vaccineAvailabilities1);
        }else{
            for(VaccineAvailability vaccineAvailability1 : vaccineAvailabilities){
                if(vaccineAvailability1.equals(vaccineAvailability)){
                    return false;
                }
            }
            vaccineAvailabilities.add(vaccineAvailability);
            vaccineCenter.setVaccineAvailabilities(vaccineAvailabilities);
        }
        return true;
    }

    @Override
    public boolean updateAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = get(vaccineCenterId);
        if(Objects.isNull(vaccineCenter)) return false;

        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();
        if(vaccineAvailabilities.isEmpty()) return false;

        int index = 0;
        for(VaccineAvailability vaccineAvailability1 : vaccineAvailabilities){
            if(vaccineAvailability1.equals(vaccineAvailability)){
                vaccineAvailabilities.set(index,vaccineAvailability);
                return true;
            }
            index++;
        }
        return false;
    }

    @Override
    public boolean removeAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability) {
        VaccineCenter vaccineCenter = get(vaccineCenterId);
        if(Objects.isNull(vaccineCenter)) return false;
        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();

        if(vaccineAvailabilities.isEmpty()) return false;

        int index = 0;
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
        VaccineCenter vaccineCenter = get(vaccineCenterId);
        if(Objects.isNull(vaccineCenter)) return false;

        List<VaccineAvailability> vaccineAvailabilities = vaccineCenter.getVaccineAvailabilities();

        if(vaccineAvailabilities.isEmpty()) return false;

        for(VaccineAvailability vaccineAvailability:vaccineAvailabilities){
            if(vaccineAvailability.getVaccineType().equals(vaccineType)&&
                    vaccineAvailability.getDoseType().equals(doseType)&&
                    vaccineAvailability.getAvailableQuantityCount()>0){
                vaccineAvailability.setAvailableQuantityCount(
                        vaccineAvailability.getAvailableQuantityCount()-1);
                updateAvailability(vaccineCenterId,vaccineAvailability);
                return true;
            }
        }
        return false;
    }

    @Override
    public SearchResponse search(VaccineType vaccineType, DoseType doseType) {
        int totalCount = 0;
        SearchResponse searchResponse = new SearchResponse(0, new ArrayList<VaccineCenter>());
        for(Map.Entry<String,VaccineCenter> vaccineCenterEntry : vaccineCenterMap.entrySet() ){
            VaccineCenter vaccineCenter = vaccineCenterEntry.getValue();
            if(vaccineCenter.getVaccineTypes().contains(vaccineType) &&
            vaccineCenter.getDoseTypes().contains(doseType)){
                totalCount++;
                searchResponse.getResults().add(vaccineCenter);
            }
        }
        searchResponse.setTotalCount(totalCount);
        return searchResponse;
    }

    @Override
    public SearchResponse search(List<SearchRequest> searchRequestList) {
        SearchResponse searchResponse = new SearchResponse(0, new ArrayList<VaccineCenter>());
        Set<VaccineCenter> vaccineCenterSet = new HashSet<>();

        for(SearchRequest searchRequest:searchRequestList){
            SearchResponse tempSearchResponse = search(searchRequest.getVaccineType(),searchRequest.getDoseType());
            vaccineCenterSet.addAll(tempSearchResponse.getResults());
        }
        searchResponse.setResults(new ArrayList<>(vaccineCenterSet));
        searchResponse.setTotalCount(vaccineCenterSet.size());
        return searchResponse;
    }

    public VaccineCenter get(final String vaccineCenterId) {
        return vaccineCenterMap.get(vaccineCenterId);
    }
}
