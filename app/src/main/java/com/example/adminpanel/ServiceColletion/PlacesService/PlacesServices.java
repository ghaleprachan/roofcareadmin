package com.example.adminpanel.ServiceColletion.PlacesService;

import com.example.adminpanel.ModelCollection.PlacesResponse.Result;

import java.util.ArrayList;
import java.util.List;

public class PlacesServices {
    public static ArrayList<Result> places = new ArrayList<>();

    public static boolean addAllPlaces(List<Result> results) {
        places.clear();
        places.addAll(results);
        return true;
    }

    public static ArrayList<Result> getMunicipalities(Integer districtId) {
        ArrayList<Result> municipalities = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getDistrictId().equals(districtId)) {
                municipalities.add(places.get(i));
            }
        }
        return municipalities;
    }

}
