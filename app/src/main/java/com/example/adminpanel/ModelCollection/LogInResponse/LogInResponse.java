
package com.example.adminpanel.ModelCollection.LogInResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInResponse {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tokenNumber")
    @Expose
    private TokenNumber tokenNumber;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenNumber getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(TokenNumber tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

}
