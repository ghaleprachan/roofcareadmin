
package com.example.adminpanel.ModelCollection.OfferReports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferReport {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ReportText")
    @Expose
    private String reportText;
    @SerializedName("ReportedById")
    @Expose
    private Integer reportedById;
    @SerializedName("ReportedByName")
    @Expose
    private String reportedByName;
    @SerializedName("ReportedByImage")
    @Expose
    private Object reportedByImage;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
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

    public Object getReportedByImage() {
        return reportedByImage;
    }

    public void setReportedByImage(Object reportedByImage) {
        this.reportedByImage = reportedByImage;
    }

}
