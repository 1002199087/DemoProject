package com.temporary.test;

import java.util.List;
import java.util.Map;

/**
 * Created by Dee on 2018/7/6.
 */

public class DetailResult {
    private Data data;
    private int pageNo;
    private int status;
    private String statusText;
    private int totalCount;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public class Data {
        private List<Map<String, String>> detailedResult;
        private String fDevicecode;
        private String fExecuteperson;
        private String fMaintainproject;
        private String fMaintainprojectid;
        private String fMaintaintype;
        private String fMaintaintypeid;
        private String fMaintenanceresult;
        private String fMaintenancetime;
        private String fOrderid;
        private String fStation;
        private String fStationId;

        public List<Map<String, String>> getDetailedResult() {
            return detailedResult;
        }

        public void setDetailedResult(List<Map<String, String>> detailedResult) {
            this.detailedResult = detailedResult;
        }

        public String getfDevicecode() {
            return fDevicecode;
        }

        public void setfDevicecode(String fDevicecode) {
            this.fDevicecode = fDevicecode;
        }

        public String getfExecuteperson() {
            return fExecuteperson;
        }

        public void setfExecuteperson(String fExecuteperson) {
            this.fExecuteperson = fExecuteperson;
        }

        public String getfMaintainproject() {
            return fMaintainproject;
        }

        public void setfMaintainproject(String fMaintainproject) {
            this.fMaintainproject = fMaintainproject;
        }

        public String getfMaintainprojectid() {
            return fMaintainprojectid;
        }

        public void setfMaintainprojectid(String fMaintainprojectid) {
            this.fMaintainprojectid = fMaintainprojectid;
        }

        public String getfMaintaintype() {
            return fMaintaintype;
        }

        public void setfMaintaintype(String fMaintaintype) {
            this.fMaintaintype = fMaintaintype;
        }

        public String getfMaintaintypeid() {
            return fMaintaintypeid;
        }

        public void setfMaintaintypeid(String fMaintaintypeid) {
            this.fMaintaintypeid = fMaintaintypeid;
        }

        public String getfMaintenanceresult() {
            return fMaintenanceresult;
        }

        public void setfMaintenanceresult(String fMaintenanceresult) {
            this.fMaintenanceresult = fMaintenanceresult;
        }

        public String getfMaintenancetime() {
            return fMaintenancetime;
        }

        public void setfMaintenancetime(String fMaintenancetime) {
            this.fMaintenancetime = fMaintenancetime;
        }

        public String getfOrderid() {
            return fOrderid;
        }

        public void setfOrderid(String fOrderid) {
            this.fOrderid = fOrderid;
        }

        public String getfStation() {
            return fStation;
        }

        public void setfStation(String fStation) {
            this.fStation = fStation;
        }

        public String getfStationId() {
            return fStationId;
        }

        public void setfStationId(String fStationId) {
            this.fStationId = fStationId;
        }
    }
}
