package com.bigdata.dis.sdk.demo;

import com.alibaba.fastjson2.JSON;
import com.bigdata.dis.sdk.demo.domain.msg.ImageMsg;
import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISClientBuilder;
import com.huaweicloud.dis.exception.DISClientException;
import com.huaweicloud.dis.iface.data.request.GetPartitionCursorRequest;
import com.huaweicloud.dis.iface.data.request.GetRecordsRequest;
import com.huaweicloud.dis.iface.data.response.GetPartitionCursorResult;
import com.huaweicloud.dis.iface.data.response.GetRecordsResult;
import com.huaweicloud.dis.iface.data.response.Record;
import com.huaweicloud.dis.util.PartitionCursorTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsumerDemo
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemo.class);
    
    public static void main(String args[])
    {
        runConsumerDemo();
    }
    
    private static void runConsumerDemo()
    {
        // 创建DIS客户端实例
        DIS dic = DISClientBuilder.standard()
            .withEndpoint("https://dis.cn-north-4.myhuaweicloud.com")
            .withAk("QBGCCVSYQGPPNSLNYAFJ")
            .withSk("aRoDjSPAwrXExghc3MUeHer3MeMmjF5iFzXy0j9f")
            .withProjectId("0a6fccc0d800f4632fefc00d3f4e4bfd")
            .withRegion("cn-north-4")
            .withDefaultClientCertAuthEnabled(true)
            .build();
        
        // 配置流名称
        String streamName = "dis-Huku";
        
        // 配置数据下载分区ID
        String partitionId = "shardId-0000000000";
        
        // 配置下载数据序列号
        String startingSequenceNumber = "3";
        
        // 配置下载数据方式
        // AT_SEQUENCE_NUMBER: 从指定的sequenceNumber开始获取，需要设置GetPartitionCursorRequest.setStartingSequenceNumber
        // AFTER_SEQUENCE_NUMBER: 从指定的sequenceNumber之后开始获取，需要设置GetPartitionCursorRequest.setStartingSequenceNumber
        // TRIM_HORIZON: 从最旧的记录开始获取
        // LATEST: 从最新的记录开始获取
        // AT_TIMESTAMP: 从指定的时间戳(13位)开始获取，需要设置GetPartitionCursorRequest.setTimestamp
        String cursorType = PartitionCursorTypeEnum.AT_SEQUENCE_NUMBER.name();
        
        try
        {
            // 获取数据游标
            GetPartitionCursorRequest request = new GetPartitionCursorRequest();
            request.setStreamName(streamName);
            request.setPartitionId(partitionId);
            request.setCursorType(cursorType);
            request.setStartingSequenceNumber(startingSequenceNumber);
            GetPartitionCursorResult response = dic.getPartitionCursor(request);
            String cursor = response.getPartitionCursor();
            
            LOGGER.info("Get stream {}[partitionId={}] cursor success : {}", streamName, partitionId, cursor);
            
            GetRecordsRequest recordsRequest = new GetRecordsRequest();
            GetRecordsResult recordResponse = null;
            while (true)
            {
                recordsRequest.setPartitionCursor(cursor);
                recordResponse = dic.getRecords(recordsRequest);
                // 下一批数据游标
                cursor = recordResponse.getNextPartitionCursor();
                
                for (Record record : recordResponse.getRecords())
                {
//                    LOGGER.info("Get Record [{}], partitionKey [{}], sequenceNumber [{}].",
//                        new String(record.getData().array()),
//                        record.getPartitionKey(),
//                        record.getSequenceNumber());
                    //转换为对象处理
                    if (JSON.isValid(new String(record.getData().array()))){
                        ImageMsg imageMsg = JSON.parseObject(new String(record.getData().array()), ImageMsg.class);
                        //byte[] encode = Base64.getDecoder().decode(imageMsg.getImageData().getBytes());
                        LocalDateTime now = LocalDateTime.now();
                        //根据时间对图片命名
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String format = now.format(dateTimeFormatter);
                        String imagePath="/Users/mac/appData/images/"+format+".jpg";
                        GenerateImage(imageMsg.getImageData(),imagePath);
                        LOGGER.info("save image :{} success!",imagePath);
                        //IOUtils.writeToFile(new String(encode),"/Users/mac/appData/images/image.jpg");
                    }
                }
            }
        }
        catch (DISClientException e)
        {
            LOGGER.error("Failed to get a normal response, please check params and retry. Error message [{}]",
                e.getMessage(),
                e);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }
    @SuppressWarnings("finally")
    public static boolean GenerateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = Files.newOutputStream(Paths.get(imgFilePath));
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return true;
        }
    }
}
