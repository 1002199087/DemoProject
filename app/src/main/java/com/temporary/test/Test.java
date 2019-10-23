package com.temporary.test;

/**
 * Created by Dee on 2018/7/5.
 */

public class Test {
    private int pageNo;
    private int status;
    private String statusText;
    private int totalCount;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
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
        private String fAccount;
        private String fGmtcreate;
        private String fGmtmodify;
        private String fId;
        private String fName;
        private String fPassword;
        private String fSalt;
        private int fValid;
        private String token;

        public String getfAccount() {
            return fAccount;
        }

        public void setfAccount(String fAccount) {
            this.fAccount = fAccount;
        }

        public String getfGmtcreate() {
            return fGmtcreate;
        }

        public void setfGmtcreate(String fGmtcreate) {
            this.fGmtcreate = fGmtcreate;
        }

        public String getfGmtmodify() {
            return fGmtmodify;
        }

        public void setfGmtmodify(String fGmtmodify) {
            this.fGmtmodify = fGmtmodify;
        }

        public String getfId() {
            return fId;
        }

        public void setfId(String fId) {
            this.fId = fId;
        }

        public String getfName() {
            return fName;
        }

        public void setfName(String fName) {
            this.fName = fName;
        }

        public String getfPassword() {
            return fPassword;
        }

        public void setfPassword(String fPassword) {
            this.fPassword = fPassword;
        }

        public String getfSalt() {
            return fSalt;
        }

        public void setfSalt(String fSalt) {
            this.fSalt = fSalt;
        }

        public int getfValid() {
            return fValid;
        }

        public void setfValid(int fValid) {
            this.fValid = fValid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
