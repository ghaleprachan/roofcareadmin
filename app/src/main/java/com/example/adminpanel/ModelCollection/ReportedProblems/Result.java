
package com.example.adminpanel.ModelCollection.ReportedProblems;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ProblemId")
    @Expose
    private Integer problemId;
    @SerializedName("ProblemById")
    @Expose
    private Integer problemById;
    @SerializedName("ProblemByName")
    @Expose
    private String problemByName;
    @SerializedName("ProblemByImage")
    @Expose
    private String problemByImage;
    @SerializedName("ProblemDescription")
    @Expose
    private String problemDescription;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("ProblemImage")
    @Expose
    private String problemImage;
    @SerializedName("ReportCount")
    @Expose
    private Integer reportCount;
    @SerializedName("ProblemReports")
    @Expose
    private List<ProblemReport> problemReports = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getProblemById() {
        return problemById;
    }

    public void setProblemById(Integer problemById) {
        this.problemById = problemById;
    }

    public String getProblemByName() {
        return problemByName;
    }

    public void setProblemByName(String problemByName) {
        this.problemByName = problemByName;
    }

    public String getProblemByImage() {
        return problemByImage;
    }

    public void setProblemByImage(String problemByImage) {
        this.problemByImage = problemByImage;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getProblemImage() {
        return problemImage;
    }

    public void setProblemImage(String problemImage) {
        this.problemImage = problemImage;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public List<ProblemReport> getProblemReports() {
        return problemReports;
    }

    public void setProblemReports(List<ProblemReport> problemReports) {
        this.problemReports = problemReports;
    }

}
