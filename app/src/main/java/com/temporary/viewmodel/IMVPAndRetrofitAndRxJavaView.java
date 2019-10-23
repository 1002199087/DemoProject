package com.temporary.viewmodel;

import com.temporary.bean.ExpressageData;
import com.temporary.bean.ExpressageNetDao;

/**
 * Created by Administrator on 2019/3/18 0018.
 */

public interface IMVPAndRetrofitAndRxJavaView {
    void updateUI(ExpressageNetDao expressageNetDao);

    void updateListSize(int count);

    void updateInfoInList(ExpressageData expressageData);
}
