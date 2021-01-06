
package com.example.adminpanel.ModelCollection.VerifyUser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("UserProfileImage")
    @Expose
    private String userProfileImage;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("AboutUser")
    @Expose
    private String aboutUser;
    @SerializedName("Verified")
    @Expose
    private Object verified;
    @SerializedName("Contacts")
    @Expose
    private List<Contact> contacts = null;
    @SerializedName("Emails")
    @Expose
    private List<Email> emails = null;
    @SerializedName("Professions")
    @Expose
    private List<Profession> professions = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public Object getVerified() {
        return verified;
    }

    public void setVerified(Object verified) {
        this.verified = verified;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

}
