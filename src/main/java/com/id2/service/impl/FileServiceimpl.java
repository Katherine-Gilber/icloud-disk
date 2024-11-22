package com.id2.service.impl;

import com.id2.dao.filedao;
import com.id2.model.Page;
import com.id2.model.file;
import com.id2.service.FileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class FileServiceimpl implements FileService {
   @Autowired
   private filedao Filedao;
   /*查询有多少同名同类型的文件*/
   public Integer checknum(String fileName, String fileType){
      return Filedao.checknum(fileName,fileType);
   }
   /*上传文件的时候把文件的信息存入数据库*/
   /*public void uploadfile(String fileName, String fileType, Integer id,
						  String fileSize, Date createTime,Date updateTime){
   		filedao.uploadfile(fileName,fileType,id,fileSize,createTime,updateTime);

   }*/
   public void uploadfile(String fileName, String fileUser,String fileType,String fileSize,String filePath,String fileOriginalName) {
      Filedao.uploadfile(fileName,fileUser, fileType,fileSize,filePath,fileOriginalName);
   }

   /*查询文件信息*/
   public List<file> selectFiles() {
      return Filedao.selectFiles();
   }


   /*根据id查询文件信息*/
   public file selectFileById( int id){ return Filedao.selectFileById(id);}

   /*返回所有包含文件数*/
   public int selectFilesCount() {
      return Filedao.selectFilesCount();
   }

   /*按文件名分页排序*/
   public List<file> SortByName(){return Filedao.SortByName();}
   /*按文件大小分页排序*/
   public List<file> SortBySize(){return Filedao.SortBySize();}
   /*按文件名创建时间页排序*/
   public List<file> SortByTime(){return Filedao.SortByTime();}


   /*文件重命名*/
   public void renameFile(int fileId, String fileName, String filePath, Date updateTime) {
      Filedao.renameFile(fileId, fileName, filePath,updateTime);
   }

   /*文件名重复的文件数*/
   public int fileNameDuplicate(String fileName){
      return Filedao.fileNameDuplicate(fileName);
   }

   /*文件搜索*/
   public List<file> searchfile(String fileName, String fileUser, Date createTime){
      return Filedao.searchfile(fileName,fileUser,createTime);
   }

   /*文件删除*/
   public void deletefile(int fileId) {
      Filedao.deletefile(fileId);
   }

}
