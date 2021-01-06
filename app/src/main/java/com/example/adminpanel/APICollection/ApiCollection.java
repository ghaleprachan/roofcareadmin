package com.example.adminpanel.APICollection;

public class ApiCollection {
    public static String BaseUrl = "https://192.168.1.4:45455/";

    public static String logIn = BaseUrl + "api/AdminLogIn/LogIn";

    public static String getPlaces = BaseUrl + "api/AdminDistrictsMunicipality/GetPlaces?adminId=";

    public static String addDistrict = BaseUrl + "api/AdminDistrictsMunicipality/AddDistricts";

    public static String deleteDistrict = BaseUrl + "api/AdminDistrictsMunicipality/DeleteDistrict?districtId=";

    public static String deleteMunicipality = BaseUrl + "api/AdminDistrictsMunicipality/DeleteMunicipality?muniCipalityId=";

    public static String addMunicipality = BaseUrl + "api/AdminDistrictsMunicipality/AddMunicipality";

    public static String getUserDetails = BaseUrl + "api/AdminUserVerify/GetUserToVerify?adminId=";

    public static String verifyProfile = BaseUrl + "api/AdminUserVerify/VerifyProfile?userId=";

    public static String addNotification = BaseUrl + "api/AdminAddNotification/AddNotification";

    public static String getNotification = BaseUrl + "api/AdminAddNotification/GetNotification?notificationId=";

    public static String getReportedOffers = BaseUrl + "api/AdminReportPost/GetReportedOffers?adminId=";

    public static String deleteReportedOffer = BaseUrl + "api/AdminReportPost/DeleteReportedOffer?offerId=";

    public static String getReportedProblems = BaseUrl + "api/AdminReportPost/GetReportedProblems?adminId=";

    public static String deleteReportedProblem = BaseUrl + "api/AdminReportPost/DeleteReportedProblem?problemId=";

    public static String getReportedSp = BaseUrl + "api/AdminDeregisterUser/GetReportedUsers?adminId=";

    public static String deregisterUser = BaseUrl + "api/AdminDeregisterUser/DeregisterUser?userId=";
}
