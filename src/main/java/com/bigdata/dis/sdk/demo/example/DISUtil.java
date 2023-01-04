package com.bigdata.dis.sdk.demo.example;

import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISClientBuilder;

public class DISUtil
{
    private static DIS dic;
    
    private static String STREAM_NAME = "dis-stream-example";
    
    private static String APP_NAME = "dis-consumer-example";
    
    private DISUtil()
    {
        
    }
    
    public static DIS getInstance()
    {
        if (dic == null)
        {
            synchronized (DISUtil.class)
            {
                if (dic == null)
                {
                    dic = createDISClient();
                }
            }
        }
        return dic;
    }
    
    public static String getStreamName()
    {
        return STREAM_NAME;
    }
    
    public static String getAppName()
    {
        return APP_NAME;
    }
    
    private static DIS createDISClient()
    {
        return DISClientBuilder.standard()
            .withRegion("cn-north-1")
            .withEndpoint("https://dis.cn-north-1.myhuaweicloud.com")
            .withAk("YOUR_AK")
            .withSk("YOUR_SK")
            .withProjectId("YOUR_PROJECT_ID")
            .build();
    }
}
