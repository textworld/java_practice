package cn.textworld.javapractice.jdkproxy.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class TestFastjson {
    private JSONObject getTestObject(){
        String emptyData = "{\n" +
                "    \"success\": true,\n" +
                "    \"errorCtx\": null,\n" +
                "    \"errorCode\": null,\n" +
                "    \"errorMessage\": null,\n" +
                "    \"traceId\": \"64584d25163636420483417494840\",\n" +
                "    \"hostName\": \"easycase-eu95-0.gz00b.stable.alipay.net\",\n" +
                "    \"data\": {}\n" +
                "}";
        return JSON.parseObject(emptyData);
    }

    @Test
    public void testEmptyData(){
        JSONObject resp = getTestObject();
        JSONObject dataObj = resp.getJSONObject("data");
        Assert.assertNotNull(dataObj);
    }

    @Test
    public void testEmptyDataToJavaObject(){
        JSONObject resp = getTestObject();
        TestBean testBean = resp.getJSONObject("data").toJavaObject(TestBean.class);
        Assert.assertNotNull(testBean);
    }
}

