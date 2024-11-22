package com.id2.controller;

import java.io.*;
import java.io.IOException;
import com.obs.services.ObsClient;
import java.util.*;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ShareController {
    public static String file22=null;

    @RequestMapping(value="/userlist",method = { RequestMethod.GET, RequestMethod.POST })
    public String userlist(HttpServletRequest req,Model model) throws FileNotFoundException {
        HttpSession session = req.getSession();
        String user = req.getParameter("fileName");
        String fileUser = req.getParameter("fileUser");
        file22=user;

        String endPoint     = "obs.cn-north-4.myhuaweicloud.com";
        String ak           = "EF7XRHA5VMMKBVEGFWLP";
        String sk           = "5EthO5x7xZ3bfdQp1eKH43YjWSZJCT3HPEFP00Ke";
        String bucketname = "icloud-disk2";
        ObsClient obsClient = null;
        obsClient = new ObsClient(ak, sk, endPoint);
        String objectname =fileUser+'/'+file22;

        String url=null;
        long expireSeconds = 300L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(bucketname);
        request.setObjectKey(objectname);

        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        url=response.getSignedUrl();
        List<String> userlist=new ArrayList<String>();
        userlist.add(url);
        model.addAttribute("userlist",userlist);
        return "Share";
    }

    @RequestMapping(value="/show",method=RequestMethod.POST)
    public String show(HttpServletRequest req,Model model) throws IOException {
        HttpSession session = req.getSession();
        String user = req.getParameter("urlTime");
        String fileUser = req.getParameter("fileUser");

        String endPoint     = "obs.cn-north-4.myhuaweicloud.com";
        String ak           = "EF7XRHA5VMMKBVEGFWLP";
        String sk           = "5EthO5x7xZ3bfdQp1eKH43YjWSZJCT3HPEFP00Ke";
        String g_bucketLoc  = "cn-north-4";
        String bucketname = "icloud-disk2";
        ObsClient obsClient = null;
        obsClient = new ObsClient(ak, sk, endPoint);

        String objectname =fileUser+'/'+file22;
        String url=null;

        long expireSeconds =Long.valueOf(user);
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(bucketname);
        request.setObjectKey(objectname);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        url=response.getSignedUrl();
        List<String> userlist2=new ArrayList<String>();
        userlist2.add(url);
        model.addAttribute("userlist",userlist2);
        obsClient.close();
        return "Share";
    }

}