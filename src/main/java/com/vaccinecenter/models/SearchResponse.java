package com.vaccinecenter.models;

import java.util.List;

public class SearchResponse {
    private int totalCount;

    private List<VaccineCenter> results;

    public SearchResponse(int totalCount, List<VaccineCenter> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<VaccineCenter> getResults() {
        return results;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setResults(List<VaccineCenter> results) {
        this.results = results;
    }
}
