package io.ambassador.ambcodequickstartapp.verylargedatastore.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataStore {

    private static final String GB = "GB";
    private static final String USA = "USA";

    private static final String DASH = "-";

    private static final String AUTUMN = "Autumn";
    private static final String FALL = "Fall";
    private static final String SUMMER = "Summer";
    private static final String WINTER = "Winter";

    private static final List<String> seasons = Arrays.asList(AUTUMN, FALL, SUMMER, WINTER);


    Map<String, List<EdgyMerch>> datastore = new HashMap<>();

    public DataStore() {
        List<EdgyMerch> gbAutumnMerch = new LinkedList<>();
        gbAutumnMerch.add(new EdgyMerch("123", "Edgy coat", GB, AUTUMN));
        gbAutumnMerch.add(new EdgyMerch("124", "Edgy coat (vintage)", GB, AUTUMN));
        gbAutumnMerch.add(new EdgyMerch("125", "Edgy coat", GB, AUTUMN));
        gbAutumnMerch.add(new EdgyMerch("126", "Edgy t-shirt", GB, AUTUMN));
        gbAutumnMerch.add(new EdgyMerch("127", "Edgy hat", GB, AUTUMN));
        gbAutumnMerch.add(new EdgyMerch("128", "Edgy slippers", GB, AUTUMN));
        datastore.put(GB + DASH + AUTUMN, gbAutumnMerch);

        List<EdgyMerch> usaFallMerch = new LinkedList<>();
        usaFallMerch.add(new EdgyMerch("134", "Edgy hoodie 1", USA, FALL));
        usaFallMerch.add(new EdgyMerch("135", "Edgy hoodie 2", USA, FALL));
        usaFallMerch.add(new EdgyMerch("136", "Edgy beanie", USA, FALL));
        datastore.put(USA + DASH + FALL, usaFallMerch);

        List<EdgyMerch> usaSummerMerch = new LinkedList<>();
        usaSummerMerch.add(new EdgyMerch("144", "Edgy t-shirt", USA, SUMMER));
        usaSummerMerch.add(new EdgyMerch("145", "Edgy cap", USA, SUMMER));
        usaSummerMerch.add(new EdgyMerch("146", "Edgy cooler", USA, SUMMER));
        usaSummerMerch.add(new EdgyMerch("147", "Edgy cap (vintage)", USA, SUMMER));
        usaSummerMerch.add(new EdgyMerch("148", "Edgy cap 2", USA, SUMMER));
        datastore.put(USA + DASH + SUMMER, usaSummerMerch);

        List<EdgyMerch> gbSummerMerch = new LinkedList<>();
        gbSummerMerch.add(new EdgyMerch("154", "Edgy t-shirt", GB, SUMMER));
        gbSummerMerch.add(new EdgyMerch("155", "Edgy cap", GB, SUMMER));
        gbSummerMerch.add(new EdgyMerch("156", "Edgy cooler", GB, SUMMER));
        datastore.put(GB + DASH + SUMMER, gbSummerMerch);
    }

    public Long getTotalRecordCount() {
        return 99999999999999l;
    }

    public List<EdgyMerch> findEdgyMerch(String country, String season) {
        return datastore.get(country + DASH + season);
    }

    public List<String> getSeasons() {
        return seasons;
    }
}
