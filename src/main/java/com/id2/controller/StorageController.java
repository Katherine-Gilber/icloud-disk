package com.id2.controller;

import java.io.IOException;
import com.obs.services.ObsClient;
import java.math.BigDecimal;
import com.obs.services.model.BucketStorageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StorageController {
    @RequestMapping(value="/userlist3",method = RequestMethod.GET)
    public String hello(Model model) throws IOException {
        String endPoint     = "obs.cn-north-4.myhuaweicloud.com";
        String ak           = "EF7XRHA5VMMKBVEGFWLP";
        String sk           = "5EthO5x7xZ3bfdQp1eKH43YjWSZJCT3HPEFP00Ke";
        String g_bucketLoc  = "cn-north-4";
        String bucketname = "icloud-disk2";
        ObsClient obsClient = null;
        long number1 =0;
        float size1 =0;
        obsClient = new ObsClient(ak, sk, endPoint);
        BucketStorageInfo storageInfo = obsClient.getBucketStorageInfo(bucketname);
        number1 =storageInfo.getObjectNumber();
        size1 =((float)storageInfo.getSize())/1024/1024;
        BigDecimal b = new BigDecimal(size1);
        size1= b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        model.addAttribute("number1",number1);
        model.addAttribute("size1",size1);
        obsClient.close();
        return "/icloud_d2";
    }
 /*    public static void main(String[] args) throws IOException {
            String endPoint     = "obs.cn-north-4.myhuaweicloud.com";
            String ak           = "EF7XRHA5VMMKBVEGFWLP";
            String sk           = "5EthO5x7xZ3bfdQp1eKH43YjWSZJCT3HPEFP00Ke";
            String g_bucketLoc  = "cn-north-4";
            String bucketname = "icloud-disk2";
            ObsClient obsClient = null;
            obsClient = new ObsClient(ak, sk, endPoint);
            String objectname ="yanyan/颜欢.xls";
            String url=null;

            BucketStorageInfo storageInfo = obsClient.getBucketStorageInfo(bucketname);
            System.out.println("bbbbbbbbbbbbbbb\t" + storageInfo.getObjectNumber());
            System.out.println("aaaaaaaaaaa\t" + storageInfo.getSize());

            obsClient.close();

    }*/
}

