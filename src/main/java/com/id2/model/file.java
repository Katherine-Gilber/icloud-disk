package com.id2.model;
import java.util.Date;

public class file {
    private int id;//id
    private String fileName;//文件名称
    private String filePath;//文件路径
    private String fileUser;//文件用户
    private String fileSize;//文件大小
    private String fileType;//文件类型
    private Date createTime;//上传时间
    private Date updateTime;//修改时间
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFileUser() {
        return fileUser;
    }
    public void setFileUser(String fileUser) {
        this.fileUser = fileUser;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public file() {
        super();
    }
    public file(int id, String fileName, String filePath, String fileUser,String fileSize,String fileType,Date createTime,Date updateTime){
        super();
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileUser = fileUser;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    @Override
    public String toString() {
        return "fileDao [id=" + id + ", fileName=" + fileName + ", filePath=" + filePath + ", fileUser=" + fileUser + ", fileSize=" + fileSize +", fileType=" + fileType +
                ", creatTime=" + createTime +", updateTime=" + updateTime +"]";
    }


}