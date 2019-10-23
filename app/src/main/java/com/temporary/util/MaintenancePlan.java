package com.temporary.util;

/**
 * Created by Dee on 2018/6/22.
 */

public class MaintenancePlan {
    private String fCity;
    private String fCityId;
    private String fDevicename;
    private String fDevicetypecode;
    private String fSimplename;
    private String fSubstaionId;
    private PlanVO planVO;

    public String getfCity() {
        return fCity;
    }

    public String getfCityId() {
        return fCityId;
    }

    public String getfDevicename() {
        return fDevicename;
    }

    public String getfDevicetypecode() {
        return fDevicetypecode;
    }

    public String getfSimplename() {
        return fSimplename;
    }

    public String getfSubstaionId() {
        return fSubstaionId;
    }

    public PlanVO getPlanVO() {
        return planVO;
    }

    public void setfCity(String fCity) {
        this.fCity = fCity;
    }

    public void setfCityId(String fCityId) {
        this.fCityId = fCityId;
    }

    public void setfDevicename(String fDevicename) {
        this.fDevicename = fDevicename;
    }

    public void setfDevicetypecode(String fDevicetypecode) {
        this.fDevicetypecode = fDevicetypecode;
    }

    public void setfSimplename(String fSimplename) {
        this.fSimplename = fSimplename;
    }

    public void setfSubstaionId(String fSubstaionId) {
        this.fSubstaionId = fSubstaionId;
    }

    public void setPlanVO(PlanVO planVO) {
        this.planVO = planVO;
    }

}
