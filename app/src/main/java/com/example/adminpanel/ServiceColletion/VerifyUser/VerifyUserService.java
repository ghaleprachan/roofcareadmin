package com.example.adminpanel.ServiceColletion.VerifyUser;

import com.example.adminpanel.ModelCollection.VerifyUser.Result;

import java.util.ArrayList;
import java.util.List;

public class VerifyUserService {
    public static ArrayList<Result> users = new ArrayList<>();

    public static boolean addAll(List<Result> results) {
        users.clear();
        users.addAll(results);
        return true;
    }
}
