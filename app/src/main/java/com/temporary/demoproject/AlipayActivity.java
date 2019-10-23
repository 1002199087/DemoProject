package com.temporary.demoproject;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.sdk.app.PayTask;
import com.temporary.demoproject.databinding.ActivityAlipayBinding;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class AlipayActivity extends SimpleBaseActivity<ActivityAlipayBinding> {
    // 支付宝分配给开发者的应用ID
    private final String app_id = "2019040663780642";

    // 接口名称
    private final String method = "alipay.trade.app.pay";

    // 仅支持JSON
    private final String format = "JSON";

    // 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
    private final String sign_type = "RSA2";

    // 商户请求参数的签名串，详见签名
    private String sign = "";

    // 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
    private String timestamp = "";

    // 调用的接口版本，固定为：1.0
    private final String version = "1.0";

    // 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
    private final String notify_url = "";

    // 业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
    private String biz_content = "";

    // 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
    private String body = "测试 demo";

    // 商品的标题/交易标题/订单标题/订单关键字等。
    private String subject = "demo";

    private final String PRIVATE_KEY =
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDHkyUzgROMNDR8glrSbZxglGPIjVPubTc5jRMBFqmFgFUUhLTuJSmJMtuNvbTo0Qh9b6Fk77FkZg36dyU5OrC5nVlJCcXU8m3H4Ww7QDnihSy9QNPTS2ciVf+P6Ot20RKFM0sl+xTPHkw/9FBFhzdrzaBdTeXQv47BxqCWoUOCFIUVZhTxm8pi5FUeGLbgTtbmnJ9Nvuo4/691uJWFk0NgR3yzV6/QGbEz9wqW1gMmEBAnemVh9T6Uv7DYYLDQ64vn6Zt3sVN77/c1JhCB5F9Nw5EO7TWurijtn04YagqWwlXCZrL3lr3UMC7wVzagJnngBWDroSPhj0ub79kJgRG7AgMBAAECggEAWo5I3gLehMqXWjunzD2SLDyIvlJqWBBUc5ewnNjUmM/vSv0gP8K7AxcBimIe+VkH+iqx1+Pu4PhxtUlkqYAY+lAMWFjH3GUYLQ77FjOBUGhPMZx9wS1AodLVwE3WsbYSKkBpA9XWzlgYDxjjZ3kAS7kk39FdywdwVjqzWUXZJOcu2IWaAxCNM0O6meFSPqxTCaBCmakFQ/fcwSXPr8rkv+TxYrauvIdO+z5wjAAFGYq9I0F2A6Iz00djR1+Hc17QTQw3pc6uEGHrbEH5EMzjhB39Z2tTuuzOdU6ovsG7p9dxlUxbDmPmSCItvEb0i98RVruPVXz1Y4JLf6RuXatR6QKBgQD1tynnd2vLXrw1tOKfuyq3LBBq4bhm/lpn3oMTUOExZ1a6xOEvuK1FsYzc63NUEqGOl6/PE8B3VXD9g/F+uARiQRcBKdpqFbU+kIiNHQzJIsVvYF97P7sfYc1lmF0N7g5rbfxBOLUxAU8mY2Xd2Lpdvh/9j7HpvNNTSGKxv5jT/QKBgQDP7ZXDzp4m8gkqphoejHe5/YNTwAZZhP+u3tIfFDE+T0FF9YWCnuQp/d63swIMmNxkQeKYQkXCcH8NJcSHFZGwT88cNfBKpNL6bhM6LZC99TEiXfXXgwMJqmxZhYfJuW/8/LxUBYis9crZghTD0oW6Vo6VwTBPjLAzpD4HpoX+FwKBgH32yR+lI/ic90VdFrSmIc7HYx832e3KxRPsiSuU0e0eueKDXaG2PfW1gK4ccgIOjw3nizRQPuGuRn9gbaP1uCGalq19BEBQIpsRQz98Pfq6vZZMRIaNdGMxIzfK9SFYAcJnHuNcq96UejwGxskE+v1lo0a8rItTxs4yFI5k13cNAoGAJokPwpRedzVQQZk7KgI6hjvXxQPHqXuer1gYdS4W2kdx/3c7YTP1cVrdlgoVDSSfY+1aA4H0z85tnZAsViTHNXf1GHNer1bQyxzvb07Wjn4MtyPW1jQvsfDl18mJmvdFLAvjE2dbJ0L+b0VOd+RiaGB2WIbPX07hsRB1dpGEL80CgYBhl6lx0RF6YvRK87QZWaR1cNAJfvfJbiVUxwlzquDhMbntDFF+k74E5xkSU+72FtU2ggsxNBOSRBpAr6cSC7dVfcoSs+j8hupSXRtscwgSl/E4ueRPif+aDQvqw16HbBQhe+32+bJGXXetVvrr0fF+UPWsndkxgE2FhoNxMbLljg==";

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private String mOrderInfo;

    @Override
    protected int getContentView() {
        return R.layout.activity_alipay;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string.alipay_text));
        mDataBinding.mQmuiTopbar.addLeftBackImageButton().setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        mDataBinding.setActivity(this);
        mOrderInfo = "app_id=" + app_id + "&method=" + method + "&format=" + format +
                "&sign_type=" + sign_type + "&version=" + version;
        sign = getSignValue(mOrderInfo);
        Log.e("wyy", "AlipayActivity initView " + sign);
        mOrderInfo = mOrderInfo + "&sign=" + sign;

    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_button: {// 支付
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PayTask payTask = new PayTask(AlipayActivity.this);
                        String result = payTask.pay(mOrderInfo, true);
                        Log.e("wyy", "AlipayActivity run " + result);
                        Toast.makeText(AlipayActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }).start();
                break;
            }
        }
    }

    private String getSignValue(String orderInfo) {
        try {
            return AlipaySignature.rsaSign(orderInfo, PRIVATE_KEY, "utf-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

}
