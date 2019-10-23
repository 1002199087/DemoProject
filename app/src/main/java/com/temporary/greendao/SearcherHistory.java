package com.temporary.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Dee on 2018/8/9.
 */
@Entity
public class SearcherHistory {
    private Long mId;
    private String mHistory;
    @Generated(hash = 344106552)
    public SearcherHistory(Long mId, String mHistory) {
        this.mId = mId;
        this.mHistory = mHistory;
    }
    @Generated(hash = 1840213400)
    public SearcherHistory() {
    }
    public Long getMId() {
        return this.mId;
    }
    public void setMId(Long mId) {
        this.mId = mId;
    }
    public String getMHistory() {
        return this.mHistory;
    }
    public void setMHistory(String mHistory) {
        this.mHistory = mHistory;
    }

}
