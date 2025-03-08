package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.dto.request.LokacijaDTO;
import com.developer.onlybuns.service.GeocodingService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class GeocodingServiceImpl implements GeocodingService {
    private static final String GEOCODING_RESOURCE = "https://geocode.search.hereapi.com/v1/geocode";
    private static final String REVERSE_GEOCODING_RESOURCE = "https://revgeocode.search.hereapi.com/v1/revgeocode";

    @Value("${here.api.key}")
    private String API_KEY;

    @Override
    public double[] getCoordinates(String address) {
        RestTemplate restTemplate = new RestTemplate();
        // Build the full URL for the API request
        String url = UriComponentsBuilder.fromHttpUrl(GEOCODING_RESOURCE)
                .queryParam("q", address)
                .queryParam("apiKey", API_KEY)
                .toUriString();

        // Send the GET request and capture the response
        String response = restTemplate.getForObject(url, String.class);

        // Parse the response to extract latitude and longitude
        JSONObject jsonResponse = new JSONObject(response);
        double latitude = jsonResponse
                .getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("position")
                .getDouble("lat");
        double longitude = jsonResponse
                .getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("position")
                .getDouble("lng");

        return new double[]{latitude, longitude};
    }

    public LokacijaDTO getAddressDetails(String address) {

        String[] addressParts = address.split(",");

        String ulica = addressParts[0].trim(); // Street address
        String grad = addressParts[2].trim();  // City
        String drzava = addressParts[3].trim(); // Country

        return new LokacijaDTO(ulica, grad, drzava);
    }


    @Override
    public LokacijaDTO getAddress(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();

        // Build the full URL for the API request
        String url = UriComponentsBuilder.fromHttpUrl(REVERSE_GEOCODING_RESOURCE)
                .queryParam("at", latitude + "," + longitude)
                .queryParam("lang", "en-US")
                .queryParam("apiKey", API_KEY)
                .toUriString();

        // Send the GET request and capture the response
        String response = restTemplate.getForObject(url, String.class);

        // Parse the response to extract address components
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray items = jsonResponse.optJSONArray("items");

        if (items != null && !items.isEmpty()) {
            JSONObject firstItem = items.getJSONObject(0);
            JSONObject address = firstItem.optJSONObject("address");

            if (address != null) {
                String ulica = address.optString("street", "") + " " + address.optString("houseNumber", "");
                String grad = address.optString("city", "");
                String drzava = address.optString("countryName", "");

                return new LokacijaDTO(ulica, grad, drzava);
            }
        }

        return null;
    }
}

