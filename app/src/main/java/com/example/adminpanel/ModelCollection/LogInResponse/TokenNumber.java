
package com.example.adminpanel.ModelCollection.LogInResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenNumber {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("AdminId")
    @Expose
    private Integer adminId;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("DOB")
    @Expose
    private String dOB;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

}
