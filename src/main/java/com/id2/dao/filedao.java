package com.id2.dao;
import com.id2.model.User;
import com.id2.model.file;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface filedao {
    /*查询有多少文件和类型都和上传的文件一致的*/
    public Integer checknum(@Param("fileOriginalName") String fileOriginalName, @Param("fileType") String fileType);

    /*上传文件写入数据库*/
    public void uploadfile(@Param("fileName") String fileName,
                           @Param("fileType") String fileType,
                           @Param("fileSize") String fileSize,
                           @Param("filePath") String filePath,
                           @Param("fileOriginalName") String fileOriginalName,
                           @Param("fileUser") String fileUser);

    /*查询文件信息*/
    public List<file> selectFiles();

    /*根据id查询文件信息*/
    public file selectFileById(@Param("id") int id);

    /*获取所有文件数*/
    public int selectFilesCount();


    /*按文件名分页排序*/
    public List<file> SortByName();
    /*按文件大小分页排序*/
    public List<file> SortBySize();
    /*按文件创建时间分页排序*/
    public List<file> SortByTime();


    /*文件重命名*/
    public void renameFile(@Param("fileId")int fileId,@Param("fileName")String fileName,
                           @Param("filePath")String filePath,@Param("updateTime") Date updateTime);

    /*文件名重复的文件数*/
    public int fileNameDuplicate(@Param("fileName")String fileName);

    /*文件搜索*/
    public List<file> searchfile(@Param("fileName") String fileName,
                                 @Param("fileUser") String fileUser,
                                 @Param("createTime") Date createTime);

    /*文件删除*/
    public void deletefile(@Param("fileId")int fileId);
}
