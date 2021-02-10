package io.ambassador.ambcodequickstartapp.verylargedatastore.rest;

import io.ambassador.ambcodequickstartapp.verylargedatastore.service.DataStore;
import io.ambassador.ambcodequickstartapp.verylargedatastore.service.EdgyMerch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class DataAccess {

    @Autowired
    private DataStore dataStore;

    @GetMapping("/recordCount")
    public String getRecordCount() {
        return dataStore.getTotalRecordCount().toString();
    }

    @GetMapping("/findMerch")
    public List<EdgyMerch> findEdgyMerch(@RequestParam String country,
                                         @RequestParam String season) {
        List<EdgyMerch> merch = dataStore.findEdgyMerch(country, season);
        if (merch == null) {
            merch = new LinkedList<>();
        }
        return merch;
    }

    @GetMapping("/seasons")
    public List<String> getSeasons() {
        return dataStore.getSeasons();
    }
}
