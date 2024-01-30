package com.udd.lawsearch.shared;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeoLocationService {
    public static List<Double> getCoordinates(String address) {
        try {
            String baseUrl = "https://nominatim.openstreetmap.org/search";
            String finalUrl = baseUrl + "?q=" + address.replace(" ", "+") + "&format=json";

            RestTemplate restTemplate = new RestTemplate();
            JsonNode[] response = restTemplate.getForObject(finalUrl, JsonNode[].class);

            if (response != null && response.length > 0) {
                double latitude = response[0].get("lat").asDouble();
                double longitude = response[0].get("lon").asDouble();
                return ConvertToList(latitude, longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Double> ConvertToList(double lat, double lon)
    {
        List<Double> list = new ArrayList<>();
        list.add(lat);
        list.add(lon);
        return list;
    }
}
