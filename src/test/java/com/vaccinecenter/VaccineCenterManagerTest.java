package com.vaccinecenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.vaccinecenter.enums.CostType;
import com.vaccinecenter.enums.DoseType;
import com.vaccinecenter.enums.VaccineType;
import com.vaccinecenter.models.Location;
import com.vaccinecenter.models.SearchRequest;
import com.vaccinecenter.models.SearchResponse;
import com.vaccinecenter.models.VaccineAvailability;
import com.vaccinecenter.models.VaccineCenter;
import com.vaccinecenter.service.VaccineCenterManagerInMemImpl;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
public class VaccineCenterManagerTest {
    private VaccineCenterManagerInMemImpl vaccineCenterManager;

    @BeforeEach
    void setup() throws IOException {
        vaccineCenterManager = new VaccineCenterManagerInMemImpl();
    }

    @Test
    void testAddVaccineCenterSuccess() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115), Collections.singleton(VaccineType.covaxin),
                Collections.singleton(DoseType.dose1), Collections.singleton(CostType.free), Collections.emptyList());
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        final VaccineCenter fetchedVaccineCenter = vaccineCenterManager.get(vaccineCenterId);
        assertEquals(fetchedVaccineCenter.getId(), vaccineCenterId);
    }

    @Test
    void testAddVaccineCenterFailure() {
        assertFalse(vaccineCenterManager.add(new VaccineCenter()));
        final String vaccineCenterId = UUID.randomUUID().toString();
        final VaccineCenter vaccineCenter1 = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115), Collections.singleton(VaccineType.covaxin),
                Collections.singleton(DoseType.dose1), Collections.singleton(CostType.free), Collections.emptyList());
        assertTrue(vaccineCenterManager.add(vaccineCenter1));
        final VaccineCenter vaccineCenter2 = new VaccineCenter(vaccineCenterId, "Pargaon RH 1",
                new Location("Maharashtra", "Kolhapur", "Shopping Center, Opp. SBI Bank", 416115), Collections.singleton(VaccineType.covaxin),
                Collections.singleton(DoseType.dose1), Collections.singleton(CostType.free), Collections.emptyList());
        assertFalse(vaccineCenterManager.add(vaccineCenter2));
    }

    @Test
    void testAddAvailabilitySuccess() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        final VaccineAvailability vaccineAvailability2 = new VaccineAvailability("2", VaccineType.covishield, DoseType.dose1, 10, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability2));
        final VaccineCenter fetchedVaccineCenter = vaccineCenterManager.get(vaccineCenterId);
        assertEquals(fetchedVaccineCenter.getId(), vaccineCenterId);
        assertEquals(fetchedVaccineCenter.getVaccineAvailabilities().size(), vaccineCenter.getVaccineAvailabilities().size());
    }

    @Test
    void testAddAvailabilityFailure() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        assertFalse(vaccineCenterManager.addAvailability(vaccineCenterId, null));
        assertFalse(vaccineCenterManager.addAvailability(vaccineCenterId, new VaccineAvailability()));
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertFalse(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
    }

    @Test
    void testUpdateAvailabilityNonExistent() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        assertFalse(vaccineCenterManager.updateAvailability(vaccineCenterId, null));
        assertFalse(vaccineCenterManager.updateAvailability(vaccineCenterId, new VaccineAvailability()));
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        final VaccineAvailability vaccineAvailability2 = new VaccineAvailability("2", VaccineType.covaxin, DoseType.dose1, 10, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertFalse(vaccineCenterManager.updateAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        final VaccineCenter fetchedVaccineCenter = vaccineCenterManager.get(vaccineCenterId);
        assertEquals(fetchedVaccineCenter.getId(), vaccineCenterId);
        assertEquals(fetchedVaccineCenter.getVaccineAvailabilities().size(), vaccineCenter.getVaccineAvailabilities().size());
        assertFalse(vaccineCenterManager.updateAvailability(vaccineCenter.getId(), vaccineAvailability2));
    }

    @Test
    void testUpdateAvailabilitySuccess() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        final VaccineAvailability vaccineAvailability2 = new VaccineAvailability("2", VaccineType.covaxin, DoseType.dose1, 10, 0);
        final VaccineAvailability updatedVaccineAvailability2 = new VaccineAvailability("2", VaccineType.covaxin, DoseType.dose1, 9, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability2));
        assertTrue(vaccineCenterManager.updateAvailability(vaccineCenter.getId(), updatedVaccineAvailability2));
        final VaccineCenter fetchedVaccineCenter = vaccineCenterManager.get(vaccineCenterId);
        assertEquals(fetchedVaccineCenter.getId(), vaccineCenterId);
        assertTrue(fetchedVaccineCenter.getVaccineAvailabilities().contains(updatedVaccineAvailability2));
    }

    @Test
    void testRemoveAvailabilityNonExistent() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        assertFalse(vaccineCenterManager.removeAvailability(vaccineCenterId, null));
        assertFalse(vaccineCenterManager.removeAvailability(vaccineCenterId, new VaccineAvailability()));
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        final VaccineAvailability vaccineAvailability2 = new VaccineAvailability("2", VaccineType.covaxin, DoseType.dose1, 10, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertFalse(vaccineCenterManager.removeAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertFalse(vaccineCenterManager.removeAvailability(vaccineCenter.getId(), vaccineAvailability2));
    }

    @Test
    void testRemoveAvailabilitySuccess() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        final VaccineAvailability vaccineAvailability2 = new VaccineAvailability("2", VaccineType.covaxin, DoseType.dose1, 10, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability2));
        assertTrue(vaccineCenterManager.removeAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertTrue(vaccineCenterManager.removeAvailability(vaccineCenter.getId(), vaccineAvailability2));
        final VaccineCenter fetchedVaccineCenter = vaccineCenterManager.get(vaccineCenterId);
        assertEquals(fetchedVaccineCenter.getVaccineAvailabilities().size(), 0);
        assertFalse(vaccineCenterManager.removeAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertFalse(vaccineCenterManager.removeAvailability(vaccineCenter.getId(), vaccineAvailability2));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability2));

    }

    @Test
    void testBookVaccineSlotFailure() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenterId, VaccineType.covishield, DoseType.dose1));
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covishield, DoseType.dose2));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covaxin, DoseType.dose2));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covishield, DoseType.dose2));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covishield, DoseType.dose1));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covaxin, DoseType.dose2));
    }

    @Test
    void testBookVaccineSlotSuccess() {
        final String vaccineCenterId = UUID.randomUUID().toString();
        final VaccineCenter vaccineCenter = new VaccineCenter(vaccineCenterId, "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineAvailability vaccineAvailability1 = new VaccineAvailability("1", VaccineType.covaxin, DoseType.dose1, 10, 0);
        final VaccineAvailability vaccineAvailability2 = new VaccineAvailability("2", VaccineType.covishield, DoseType.dose1, 1, 0);
        assertTrue(vaccineCenterManager.add(vaccineCenter));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability1));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covishield, DoseType.dose1));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covishield, DoseType.dose2));
        assertTrue(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covaxin, DoseType.dose1));
        assertTrue(vaccineCenterManager.addAvailability(vaccineCenter.getId(), vaccineAvailability2));
        assertTrue(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covishield, DoseType.dose1));
        assertFalse(vaccineCenterManager.bookVaccineSlot(vaccineCenter.getId(), VaccineType.covishield, DoseType.dose1));
        final VaccineCenter fetchedVaccineCenter = vaccineCenterManager.get(vaccineCenterId);
        final VaccineAvailability fetchedVaccineAvailability1 = fetchedVaccineCenter.getVaccineAvailabilities().stream()
                                                                                    .filter(i -> i.getId().equalsIgnoreCase("1")).findFirst()
                                                                                    .orElse(new VaccineAvailability());
        final VaccineAvailability fetchedVaccineAvailability2 = fetchedVaccineCenter.getVaccineAvailabilities().stream()
                                                                                    .filter(i -> i.getId().equalsIgnoreCase("2")).findFirst()
                                                                                    .orElse(new VaccineAvailability());
        assertEquals(fetchedVaccineAvailability1.getAvailableQuantityCount(), 9);
        assertEquals(fetchedVaccineAvailability2.getAvailableQuantityCount(), 0);
    }

    @Test
    void testSearchEmptyVaccineTypeEmptyDoseType() {
        final SearchResponse expectedResponse = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse = vaccineCenterManager.search(null, null);
        assertEquals(expectedResponse.getTotalCount(), actualResponse.getTotalCount());
        assertEquals(expectedResponse.getResults(), actualResponse.getResults());
    }

    @Test
    void testSearchEmptyVaccineTypeAndNonEmptyDoseType() {
        final VaccineCenter vaccineCenter1 = new VaccineCenter("1", "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineCenter vaccineCenter2 = new VaccineCenter("2", "Palghar", new Location("Maharashtra", "Kolhapur", "Near SBI Bank", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final SearchResponse expectedResponse1 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse1 = vaccineCenterManager.search(null, DoseType.dose1);
        assertEquals(expectedResponse1.getTotalCount(), actualResponse1.getTotalCount());
        assertEquals(expectedResponse1.getResults(), actualResponse1.getResults());
        vaccineCenterManager.add(vaccineCenter1);
        final SearchResponse expectedResponse2 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse2 = vaccineCenterManager.search(null, DoseType.dose1);
        assertEquals(expectedResponse2.getTotalCount(), actualResponse2.getTotalCount());
        assertEquals(expectedResponse2.getResults(), actualResponse2.getResults());
        vaccineCenterManager.add(vaccineCenter2);
        final SearchResponse expectedResponse3 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse3 = vaccineCenterManager.search(null, DoseType.dose1);
        assertEquals(expectedResponse3.getTotalCount(), actualResponse3.getTotalCount());
        assertEquals(expectedResponse3.getResults(), actualResponse3.getResults());
    }

    @Test
    void testSearchNonEmptyVaccineTypeAndEmptyDoseType() {
        final VaccineCenter vaccineCenter1 = new VaccineCenter("1", "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineCenter vaccineCenter2 = new VaccineCenter("2", "Palghar", new Location("Maharashtra", "Kolhapur", "Near SBI Bank", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final SearchResponse expectedResponse1 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse1 = vaccineCenterManager.search(VaccineType.covaxin, null);
        assertEquals(expectedResponse1.getTotalCount(), actualResponse1.getTotalCount());
        assertEquals(expectedResponse1.getResults(), actualResponse1.getResults());
        vaccineCenterManager.add(vaccineCenter1);
        final SearchResponse expectedResponse2 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse2 = vaccineCenterManager.search(VaccineType.covishield, null);
        assertEquals(expectedResponse2.getTotalCount(), actualResponse2.getTotalCount());
        assertEquals(expectedResponse2.getResults(), actualResponse2.getResults());
        vaccineCenterManager.add(vaccineCenter2);
        final SearchResponse expectedResponse3 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse3 = vaccineCenterManager.search(VaccineType.covaxin, null);
        assertEquals(expectedResponse3.getTotalCount(), actualResponse3.getTotalCount());
        assertEquals(expectedResponse3.getResults(), actualResponse3.getResults());
    }

    @Test
    void testSearchVaccineTypeAndDoseType() {
        final VaccineCenter vaccineCenter1 = new VaccineCenter("1", "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineCenter vaccineCenter2 = new VaccineCenter("2", "Palghar", new Location("Maharashtra", "Kolhapur", "Near SBI Bank", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineCenter vaccineCenter3 = new VaccineCenter("3", "Sangamner UHC", new Location("Maharashtra", "Kolhapur", "MG Road", 416115),
                new HashSet<>(Collections.singletonList(VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        vaccineCenterManager.add(vaccineCenter1);
        vaccineCenterManager.add(vaccineCenter2);
        vaccineCenterManager.add(vaccineCenter3);
        final SearchResponse expectedResponse1 = new SearchResponse(2, Arrays.asList(vaccineCenter1, vaccineCenter2));
        final SearchResponse actualResponse1 = vaccineCenterManager.search(VaccineType.covaxin, DoseType.dose1);
        assertEquals(expectedResponse1.getTotalCount(), actualResponse1.getTotalCount());
        assertEquals(expectedResponse1.getResults(), actualResponse1.getResults());

        final SearchResponse expectedResponse2 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse2 = vaccineCenterManager.search(VaccineType.covaxin, DoseType.dose2);
        assertEquals(expectedResponse2.getTotalCount(), actualResponse2.getTotalCount());
        assertEquals(expectedResponse2.getResults(), actualResponse2.getResults());

        final SearchResponse expectedResponse3 = new SearchResponse(3, Arrays.asList(vaccineCenter1, vaccineCenter2, vaccineCenter3));
        final SearchResponse actualResponse3 = vaccineCenterManager.search(VaccineType.covishield, DoseType.dose1);
        assertEquals(expectedResponse3.getTotalCount(), actualResponse3.getTotalCount());
        assertEquals(expectedResponse3.getResults(), actualResponse3.getResults());

        final SearchResponse expectedResponse4 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse4 = vaccineCenterManager.search(VaccineType.covishield, DoseType.dose2);
        assertEquals(expectedResponse4.getTotalCount(), actualResponse4.getTotalCount());
        assertEquals(expectedResponse4.getResults(), actualResponse4.getResults());
    }

    @Test
    void testMultiSearch() {
        final VaccineCenter vaccineCenter1 = new VaccineCenter("1", "Shahapur UHC",
                new Location("Maharashtra", "Kolhapur", "Vikramnagar, Opp. Kapad Market", 416115),
                new HashSet<>(Arrays.asList(VaccineType.covaxin, VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineCenter vaccineCenter2 = new VaccineCenter("2", "Palghar", new Location("Maharashtra", "Kolhapur", "Near SBI Bank", 416115),
                new HashSet<>(Collections.singletonList(VaccineType.covishield)), Collections.singleton(DoseType.dose1),
                Collections.singleton(CostType.free), new ArrayList<>());
        final VaccineCenter vaccineCenter3 = new VaccineCenter("3", "Sangamner UHC", new Location("Maharashtra", "Kolhapur", "MG Road", 416115),
                new HashSet<>(Collections.singletonList(VaccineType.covishield)), Collections.singleton(DoseType.dose2),
                Collections.singleton(CostType.free), new ArrayList<>());

        final SearchRequest searchRequest1 = new SearchRequest(VaccineType.covaxin, DoseType.dose2);
        final SearchResponse expectedResponse1 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse1 = vaccineCenterManager.search(Collections.singletonList(searchRequest1));
        assertEquals(expectedResponse1.getTotalCount(), actualResponse1.getTotalCount());
        assertEquals(expectedResponse1.getResults(), actualResponse1.getResults());

        vaccineCenterManager.add(vaccineCenter1);
        vaccineCenterManager.add(vaccineCenter2);
        vaccineCenterManager.add(vaccineCenter3);

        final SearchRequest searchRequest2 = new SearchRequest(VaccineType.covaxin, DoseType.dose2);
        final SearchResponse expectedResponse2 = new SearchResponse(0, Collections.emptyList());
        final SearchResponse actualResponse2 = vaccineCenterManager.search(Collections.singletonList(searchRequest2));
        assertEquals(expectedResponse2.getTotalCount(), actualResponse2.getTotalCount());
        assertEquals(expectedResponse2.getResults(), actualResponse2.getResults());

        final SearchRequest searchRequest31 = new SearchRequest(VaccineType.covaxin, DoseType.dose2);
        final SearchRequest searchRequest32 = new SearchRequest(VaccineType.covaxin, DoseType.dose1);
        final SearchResponse expectedResponse3 = new SearchResponse(1, Collections.singletonList(vaccineCenter1));
        final SearchResponse actualResponse3 = vaccineCenterManager.search(Arrays.asList(searchRequest31, searchRequest32));
        assertEquals(expectedResponse3.getTotalCount(), actualResponse3.getTotalCount());
        assertTrue(actualResponse3.getResults().contains(vaccineCenter1));
        assertFalse(actualResponse3.getResults().contains(vaccineCenter2));

        final SearchRequest searchRequest4 = new SearchRequest(VaccineType.covaxin, DoseType.dose1);
        final SearchResponse expectedResponse4 = new SearchResponse(1, Collections.singletonList(vaccineCenter1));
        final SearchResponse actualResponse4 = vaccineCenterManager.search(Collections.singletonList(searchRequest4));
        assertEquals(expectedResponse4.getTotalCount(), actualResponse4.getTotalCount());
        assertTrue(actualResponse4.getResults().contains(vaccineCenter1));
        assertFalse(actualResponse4.getResults().contains(vaccineCenter2));

        final SearchRequest searchRequest51 = new SearchRequest(VaccineType.covaxin, DoseType.dose1);
        final SearchRequest searchRequest52 = new SearchRequest(VaccineType.covishield, DoseType.dose2);
        final SearchResponse expectedResponse5 = new SearchResponse(2, Arrays.asList(vaccineCenter1, vaccineCenter3));
        final SearchResponse actualResponse5 = vaccineCenterManager.search(Arrays.asList(searchRequest51, searchRequest52));
        assertEquals(expectedResponse5.getTotalCount(), actualResponse5.getTotalCount());
        assertTrue(actualResponse5.getResults().contains(vaccineCenter1));
        assertTrue(actualResponse5.getResults().contains(vaccineCenter3));

        final SearchRequest searchRequest61 = new SearchRequest(VaccineType.covaxin, DoseType.dose1);
        final SearchRequest searchRequest62 = new SearchRequest(VaccineType.covaxin, DoseType.dose2);
        final SearchRequest searchRequest63 = new SearchRequest(VaccineType.covishield, DoseType.dose1);
        final SearchRequest searchRequest64 = new SearchRequest(VaccineType.covishield, DoseType.dose2);
        final SearchResponse expectedResponse6 = new SearchResponse(3, Arrays.asList(vaccineCenter1, vaccineCenter2, vaccineCenter3));
        final SearchResponse actualResponse6 = vaccineCenterManager
                .search(Arrays.asList(searchRequest61, searchRequest62, searchRequest63, searchRequest64));
        assertEquals(expectedResponse6.getTotalCount(), actualResponse6.getTotalCount());
        assertTrue(actualResponse6.getResults().contains(vaccineCenter1));
        assertTrue(actualResponse6.getResults().contains(vaccineCenter2));
        assertTrue(actualResponse6.getResults().contains(vaccineCenter3));
    }
}
