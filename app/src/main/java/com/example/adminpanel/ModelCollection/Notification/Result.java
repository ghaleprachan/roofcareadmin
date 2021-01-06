
package com.example.adminpanel.ModelCollection.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("NotificationId")
    @Expose
    private Integer notificationId;
    @SerializedName("NotificationType")
    @Expose
    private String notificationType;
    @SerializedName("NotificationText")
    @Expose
    private String notificationText;
    @SerializedName("NotificationDate")
    @Expose
    private String notificationDate;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

}
