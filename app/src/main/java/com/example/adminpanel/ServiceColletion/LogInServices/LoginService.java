package com.example.adminpanel.ServiceColletion.LogInServices;

import com.example.adminpanel.ModelCollection.LogInResponse.TokenNumber;

import java.util.ArrayList;

public class LoginService {
    public static ArrayList<TokenNumber> userDetails = new ArrayList<>(0);

    /*public static String username;

    public static void setUsername(String username) {
        LoginService.username = username;
    }*/

    public static boolean addDetails(TokenNumber number) {
        userDetails.add(0, number);
        return true;
    }

    public static int getAdminId() {
        return userDetails.get(0).getAdminId();
    }
}
