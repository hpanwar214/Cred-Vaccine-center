package com.vaccinecenter.service;

import java.util.List;
import com.vaccinecenter.enums.DoseType;
import com.vaccinecenter.enums.VaccineType;
import com.vaccinecenter.models.SearchRequest;
import com.vaccinecenter.models.SearchResponse;
import com.vaccinecenter.models.VaccineAvailability;
import com.vaccinecenter.models.VaccineCenter;

public interface VaccineCenterManager {
    /**
     * Add vaccine center in the system,
     * return true if insertion is successful else false
     *
     * @return boolean
     */
    boolean add(VaccineCenter vaccineCenter);

    /**
     * Add availability of vaccines in the center based on type of vaccine and type of dose,
     * return true if insertion is successful else false
     *
     * @return boolean
     */
    boolean addAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability);

    /**
     * Update availability of vaccines in the center based on type of vaccine and type of dose,
     * return true if update is successful else false
     *
     * @return boolean
     */
    boolean updateAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability);

    /**
     * Remove availability of vaccines in the center based on type of vaccine and type of dose,
     * return true if remove is successful else false
     *
     * @return boolean
     */
    boolean removeAvailability(String vaccineCenterId, VaccineAvailability vaccineAvailability);

    /**
     * Book vaccine slot for given vaccineCenterId where vaccineType & doseType are available.
     * return true if slot is booked successfully else false.
     */
    boolean bookVaccineSlot(String vaccineCenterId, VaccineType vaccineType, DoseType doseType);

    /**
     * Search by vaccineType & doseType, returns all the vaccine centers which supports given vaccine type and dose type
     * return SearchResponse contains count & list of the Vaccine Center
     *
     * @return {@SearchResponse}
     */
    SearchResponse search(VaccineType vaccineType, DoseType doseType);

    /**
     * Search vaccine centers which supports any of the given vaccine type and dose type
     * return SearchResponse contains count & list of the Vaccine Center
     *
     * @return {@SearchResponse}
     */
    SearchResponse search(List<SearchRequest> searchRequestList);
}
