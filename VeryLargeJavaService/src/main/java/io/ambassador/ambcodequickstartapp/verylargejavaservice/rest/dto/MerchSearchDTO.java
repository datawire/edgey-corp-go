package io.ambassador.ambcodequickstartapp.verylargejavaservice.rest.dto;

public class MerchSearchDTO {
    private String country;
    private String season;

    public MerchSearchDTO() {
    }

    public MerchSearchDTO(String country, String season) {
        this.country = country;
        this.season = season;
    }

    public String getCountry() {
        return country;
    }

    public String getSeason() {
        return season;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
