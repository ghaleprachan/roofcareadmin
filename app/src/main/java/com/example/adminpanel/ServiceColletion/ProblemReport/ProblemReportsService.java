package com.example.adminpanel.ServiceColletion.ProblemReport;


import com.example.adminpanel.ModelCollection.ReportedProblems.Result;

import java.util.ArrayList;
import java.util.List;

public class ProblemReportsService {
    public static ArrayList<Result> problemList = new ArrayList<>();

    public static boolean addAll(List<Result> results) {
        problemList.clear();
        problemList.addAll(results);
        return true;
    }

    public static Result getSelectedReport(Integer position) {
        return problemList.get(position);
    }
}
