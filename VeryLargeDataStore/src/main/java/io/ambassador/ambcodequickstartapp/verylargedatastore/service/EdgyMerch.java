package io.ambassador.ambcodequickstartapp.verylargedatastore.service;

public class EdgyMerch {
    private String sku;
    private String descript;
    private String country;
    private String season;

    public EdgyMerch(String sku, String descript, String country, String season) {
        this.sku = sku;
        this.descript = descript;
        this.country = country;
        this.season = season;
    }

    public String getSku() {
        return sku;
    }

    public String getDescript() {
        return descript;
    }

    public String getCountry() {
        return country;
    }

    public String getSeason() {
        return season;
    }
}
