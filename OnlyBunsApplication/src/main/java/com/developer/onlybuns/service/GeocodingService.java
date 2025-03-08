package com.developer.onlybuns.service;

import com.developer.onlybuns.dto.request.LokacijaDTO;

public interface GeocodingService {

    double[] getCoordinates(String address);

    LokacijaDTO getAddress(double latitude, double longitude) throws Exception;
}
