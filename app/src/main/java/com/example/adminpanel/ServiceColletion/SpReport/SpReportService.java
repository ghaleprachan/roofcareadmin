package com.example.adminpanel.ServiceColletion.SpReport;

import com.example.adminpanel.ModelCollection.SpReport.Result;

import java.util.ArrayList;
import java.util.List;

public class SpReportService {
    public static ArrayList<Result> spList = new ArrayList<>();

    public static boolean addAll(List<Result> results) {
        spList.clear();
        spList.addAll(results);
        return true;
    }

    public static Result getSelectedReports(Integer position) {
        return spList.get(position);
    }
}
