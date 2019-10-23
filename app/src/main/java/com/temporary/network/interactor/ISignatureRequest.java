package com.temporary.network.interactor;

import android.os.Handler;

/**
 * Created by wyy on 2019/2/21 0021.
 */

public interface ISignatureRequest {
    void requestSignatureToken(String grant_type, String client_id, String client_secret, Handler
            handler, int flag);

    void requestSignatureString(String access_token, String image, Handler
            handler, int flag);
}
