
package com.example.adminpanel.ModelCollection.SpReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ReportedTo")
    @Expose
    private Integer reportedTo;
    @SerializedName("ReportId")
    @Expose
    private Integer reportId;
    @SerializedName("ReportDescription")
    @Expose
    private String reportDescription;
    @SerializedName("ReportedById")
    @Expose
    private Integer reportedById;
    @SerializedName("ReportedByName")
    @Expose
    private String reportedByName;
    @SerializedName("ReportedByImage")
    @Expose
    private String reportedByImage;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getReportedTo() {
        return reportedTo;
    }

    public void setReportedTo(Integer reportedTo) {
        this.reportedTo = reportedTo;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Integer getReportedById() {
        return reportedById;
    }

    public void setReportedById(Integer reportedById) {
        this.reportedById = reportedById;
    }

    public String getReportedByName() {
        return reportedByName;
    }

    public void setReportedByName(String reportedByName) {
        this.reportedByName = reportedByName;
    }

    public String getReportedByImage() {
        return reportedByImage;
    }

    public void setReportedByImage(String reportedByImage) {
        this.reportedByImage = reportedByImage;
    }

}
