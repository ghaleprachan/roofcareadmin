package com.example.adminpanel.ServiceColletion.OfferReports;

import com.example.adminpanel.ModelCollection.OfferReports.Result;

import java.util.ArrayList;
import java.util.List;

public class OfferReportsService {
    public static ArrayList<Result> offersList = new ArrayList<>();

    public static boolean addAll(List<Result> results) {
        offersList.clear();
        offersList.addAll(results);
        return true;
    }

    public static Result getSelectedReport(Integer position) {
        return offersList.get(position);
    }
}
