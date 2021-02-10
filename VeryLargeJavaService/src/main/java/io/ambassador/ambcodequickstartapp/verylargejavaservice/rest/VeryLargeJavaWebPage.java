package io.ambassador.ambcodequickstartapp.verylargejavaservice.rest;


import io.ambassador.ambcodequickstartapp.verylargejavaservice.rest.dto.EdgyMerchDTO;
import io.ambassador.ambcodequickstartapp.verylargejavaservice.rest.dto.MerchSearchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Controller
public class VeryLargeJavaWebPage {

    private static Logger LOGGER = LoggerFactory.getLogger(VeryLargeJavaWebPage.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${nodeservice.host}")
    String nodeServiceHost;

    @Value("${nodeservice.port}")
    String nodeServicePort;

    @GetMapping("/")
    public String welcome(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model,
                           @RequestHeader Map<String, String> headers) {
        LOGGER.info("greeting entry");

        String nodeServiceURL = "http://" + nodeServiceHost + ":" + nodeServicePort;

        try {
            String color = restTemplate.getForObject(nodeServiceURL + "/color", String.class);
            color = color.replace("\"", "");
            model.addAttribute("color", color);

            String environment = restTemplate.getForObject(nodeServiceURL + "/environment", String.class);
            model.addAttribute("environment", environment);

            String recordCount = restTemplate.getForObject(nodeServiceURL + "/recordCount", String.class);
            model.addAttribute("recordCount", recordCount);
        } catch (RestClientException ex) {
            LOGGER.error(ex.toString());
        }

        return "welcome";
    }

    @GetMapping("/findMerch")
    public String findMerchForm(Model model) {
        LOGGER.info("findMerch GET entry");

        model.addAttribute("merchSearch", new MerchSearchDTO());
        model.addAttribute("edgyMerchs", new LinkedList<EdgyMerchDTO>());
        return "merchSearchForm";
    }

    @PostMapping("/findMerch")
    public String searchMerch(MerchSearchDTO merchSearchDTO, Model model) {
        LOGGER.info("search POST entry");

        model.addAttribute("merchSearch", merchSearchDTO);
        try {
            String nodeServiceURL = "http://" + nodeServiceHost + ":" + nodeServicePort;
            List<EdgyMerchDTO> edgyMerch = restTemplate.getForObject(nodeServiceURL + "/findMerch?country=" +
                    merchSearchDTO.getCountry() + "&season=" + merchSearchDTO.getSeason(), List.class);
            model.addAttribute("edgyMerchs", edgyMerch);
        } catch (RestClientException ex) {
            LOGGER.error(ex.toString());
        }
        return "merchSearchForm";
    }
}