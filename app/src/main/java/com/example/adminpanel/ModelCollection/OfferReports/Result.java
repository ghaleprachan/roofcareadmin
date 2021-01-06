
package com.example.adminpanel.ModelCollection.OfferReports;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("offerId")
    @Expose
    private Integer offerId;
    @SerializedName("OfferById")
    @Expose
    private Integer offerById;
    @SerializedName("OfferByName")
    @Expose
    private String offerByName;
    @SerializedName("OfferByImage")
    @Expose
    private Object offerByImage;
    @SerializedName("ValidDate")
    @Expose
    private String validDate;
    @SerializedName("OfferDescription")
    @Expose
    private String offerDescription;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("OfferImage")
    @Expose
    private String offerImage;
    @SerializedName("ReportCount")
    @Expose
    private Integer reportCount;
    @SerializedName("OfferReports")
    @Expose
    private List<OfferReport> offerReports = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getOfferById() {
        return offerById;
    }

    public void setOfferById(Integer offerById) {
        this.offerById = offerById;
    }

    public String getOfferByName() {
        return offerByName;
    }

    public void setOfferByName(String offerByName) {
        this.offerByName = offerByName;
    }

    public Object getOfferByImage() {
        return offerByImage;
    }

    public void setOfferByImage(Object offerByImage) {
        this.offerByImage = offerByImage;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public List<OfferReport> getOfferReports() {
        return offerReports;
    }

    public void setOfferReports(List<OfferReport> offerReports) {
        this.offerReports = offerReports;
    }

}
