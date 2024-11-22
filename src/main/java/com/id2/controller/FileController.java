package com.id2.controller;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.id2.model.file;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.id2.service.FileService;

import static com.id2.util.FileTypeUtil.getType;
import static java.lang.Math.pow;
import static org.apache.taglibs.standard.functions.Functions.length;

@Controller
//上传文件
public class FileController {
	@Autowired
	private FileService fileService;
	@RequestMapping(value="/uploadMultiple",method=RequestMethod.POST)
	public String uploadMultiple(@RequestParam("attachment") MultipartFile attachment,HttpServletRequest request,Map<String,Object> map) throws Exception {
		/*上传附件*/
		String user=request.getParameter("userfile");//当前文件所属的用户
		String fileName = attachment.getOriginalFilename();//文件的名字
		/*获取文件大小*/
		long filesize = attachment.getSize();
		String[] filesize_2={"b","kb","mb","gb"};
		int fw= (int) (filesize/1024);
		int fc=0;
		while (fw!=0){
			fc++;
			fw=fw/1024;
		}
		DecimalFormat df = new DecimalFormat("0.000");
		String filesize_1 = df.format((double) (filesize/pow(1024,fc)))+filesize_2[fc];

		/*获取不带后缀的文件名*/
		String fileName_1 = fileName.substring(0,fileName.lastIndexOf("."));
		String fileName_3 = fileName_1;//存入数据库的名称
		//获取文件的后缀格式
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		/*检验相同的文件数，并且进行相应的改名*/
		Integer num = fileService.checknum(fileName_1,fileType);
		if (num>0){
			String s = Integer.toString(num);
			fileName_3 = fileName_1+s+fileName.substring(fileName.lastIndexOf("."));
		}
		else {
			fileName_3 = fileName_1+fileName.substring(fileName.lastIndexOf("."));
		}
		/*上传到osb*/
		String endPoint = "obs.cn-north-4.myhuaweicloud.com"+'/'+user;
		String ak = "EF7XRHA5VMMKBVEGFWLP";
		String sk = "5EthO5x7xZ3bfdQp1eKH43YjWSZJCT3HPEFP00Ke";
		String buckername = "icloud-disk2";
		ObsClient obsClient = new ObsClient(ak,sk,endPoint);
		String url = "https://"+buckername+"."+endPoint+'/'+fileName_3;

		File file = File.createTempFile(fileName,fileName.substring(fileName.lastIndexOf(".")));
		attachment.transferTo(file);
		FileInputStream inputStream = new FileInputStream(file);
		obsClient.putObject(buckername,fileName_3,inputStream);
		/*把上传的文件信息存在数据库的file表中*/
		fileService.uploadfile(fileName_3,fileType,filesize_1,url,fileName_1,user);
		return "redirect:icloud_d2.jsp?ress=yes";
	}

	//下载
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public String download(HttpServletRequest request, HttpServletResponse response, String id) throws IOException {
		file file1=new file();
		id= id.replace(",","");
		file f=fileService.selectFileById(Integer.parseInt(id));
		String fileName=f.getFileName();
		String fileUser=f.getFileUser();
		String endPoint = "obs.cn-north-4.myhuaweicloud.com" + '/' + fileUser;
		String ak = "EF7XRHA5VMMKBVEGFWLP";
		String sk = "5EthO5x7xZ3bfdQp1eKH43YjWSZJCT3HPEFP00Ke";
		ObsClient obsClient = new ObsClient(ak, sk, endPoint);
		String bucketname = "icloud-disk2";
		//获取下载对象
		ObsObject obsObject = obsClient.getObject(bucketname, fileName);
		InputStream inputStream = obsObject.getObjectContent();
		if (inputStream != null) {
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
			response.setContentType("multipart/form-data;charset=UTF-8");//也可以明确的设置一下UTF-8，
			response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			byte[] items = new byte[1024 * 10];
			int i = 0;
			try {
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				OutputStream outputStream = response.getOutputStream();
				BufferedOutputStream outputStream1 = new BufferedOutputStream(outputStream);
				while ((i = bufferedInputStream.read(items)) != -1) {
					outputStream1.write(items, 0, i);
					outputStream1.flush();
				}
				outputStream1.close();
				outputStream.close();
				bufferedInputStream.close();
				inputStream.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "icloud_d2";
	}


	/*排序*/
	@RequestMapping("/SortFiles")
	public String SortFiles(Model model,@RequestParam(defaultValue = "1",value="pageNum") String pageNum,String type){
		int t=Integer.parseInt(type.replace(",",""));
		int pageNumber=Integer.parseInt(pageNum);
		int pageSize=8;
		List<file> fileInfo=null;
		if(t==1)
			fileInfo = fileService.SortByName();
		else if(t==2)
			fileInfo = fileService.SortBySize();
		else if(t==3)
			fileInfo = fileService.SortByTime();
		//创建Page类
		Page page = new Page(pageNumber, pageSize);
		//为Page类中的total属性赋值
		int total = fileInfo.size();
		page.setTotal(total);
		//计算当前需要显示的数据下标起始值
		int startIndex = (pageNumber - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize,total);
		//从链表中截取需要显示的子链表，并加入到Page
		page.addAll(fileInfo.subList(startIndex,endIndex));
		//以Page创建PageInfo
		PageInfo pageInfo = new PageInfo<>(page);
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("pageInfo", pageInfo);
		return "icloud_d2";
	}
	//分类
	@RequestMapping(value = "/typeClassify", method=RequestMethod.GET)
	public String typeClassify(Model model,@RequestParam(defaultValue = "1",value="pageNum") String pageNum,String type) throws Exception {
		int t=Integer.parseInt(type.replace(",",""));
		System.out.println(type);
		int p=Integer.parseInt(pageNum);
		List<file> fileInfo = fileService.selectFiles();
		if(t!=0) {
			for (int i = 0; i < fileInfo.size(); i++) {
				if (getType(fileInfo.get(i).getFilePath()) != t) {
					fileInfo.remove(i);
					i--;
				}
			}
		}
		int pageSize=8;
		//创建Page类
		Page page = new Page(p, pageSize);
		//为Page类中的total属性赋值
		int total = fileInfo.size();
		page.setTotal(total);
		//计算当前需要显示的数据下标起始值
		int startIndex = (p - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize,total);
		//从链表中截取需要显示的子链表，并加入到Page
		page.addAll(fileInfo.subList(startIndex,endIndex));
		//以Page创建PageInfo
		PageInfo pageInfo = new PageInfo<>(page);
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("pageInfo", pageInfo);
		return "icloud_d2";
	}


	//重命名
	@RequestMapping(value="/renameFile",method=RequestMethod.POST)
	public String renameFile(HttpServletRequest request) throws Exception {
		/*获取传值*/
		int id = Integer.parseInt(request.getParameter("fileId"));
		String oldName = fileService.selectFileById(id).getFileName();
		String oldPath = fileService.selectFileById(id).getFilePath();
		String newName = request.getParameter("newFileName");
		Date date = new Date();
        File oldFile=new File(oldPath);
        String newPath=oldFile.getParent() + "\\" + newName;
        if(newName == null || newName.equals("")) {              //传回的值为null值
            newName = oldName;
            return "icloud_d2";
        }
        else if(fileService.fileNameDuplicate(newName) > 0)      //输入名已存在
            return "redirect:icloud_d2.jsp?errorDuplicate=yes";
		else if(length(newName) > 50)                            //名称超过限定长度
		    return "redirect:icloud_d2.jsp?errorExcess=yes";
        else{
			fileService.renameFile(id,newName,newPath,date);
			file fileInfo = fileService.selectFileById(id);      //查询对应id文件行信息
			/*HttpSession session = request.getSession();
			session.setAttribute("fileName",fileInfo.getFileName());
			session.setAttribute("updateTime",fileInfo.getUpdateTime());
			session.setAttribute("filePath",fileInfo.getFilePath());*/
			return "redirect:icloud_d2.jsp?update=yes";
        }
	}

	//搜索
	@RequestMapping(value = "/searchFiles", method = RequestMethod.GET)
	public String searchFiles(Model model,@RequestParam(defaultValue = "1",value="pageNum") String pageNum, String searchType,String fileName1, String fileUser1, String fileTime1) throws Exception {
		//从path路径下取出所有文件，存放到数组files
		Date createTime = null;
		String fileTime=fileTime1;
		String fileName=fileName1;
		String fileUser=fileUser1;
		if (searchType.equals("3")) {
			if (fileTime!="") {
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
				createTime = ft.parse(fileTime);
			}
		}
		List<file> fileInfo = fileService.searchfile(fileName, fileUser, createTime);
		int pageNumber=Integer.parseInt(pageNum);
		int pageSize=8;
		//创建Page类
		Page page = new Page(pageNumber, pageSize);
		//为Page类中的total属性赋值
		int total = fileInfo.size();
		page.setTotal(total);
		//计算当前需要显示的数据下标起始值
		int startIndex = (pageNumber - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize,total);
		//从链表中截取需要显示的子链表，并加入到Page
		page.addAll(fileInfo.subList(startIndex,endIndex));
		//以Page创建PageInfo
		PageInfo pageInfo = new PageInfo<>(page);
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("pageInfo",pageInfo);
		return "icloud_d2";
	}

	//展示
	@RequestMapping(value = "/selectFiles")
	public String selectFiles(Model model) throws Exception {
		//从path路径下取出所有文件，存放到数组files
		List<file> fileInfo = fileService.selectFiles();
		model.addAttribute("fileInfo", fileInfo);
		return "icloud_d2";
	}

	//删除文件
	@RequestMapping(value="/deleteFiles",method=RequestMethod.POST)
	public String Deletefiles(HttpServletRequest request) throws Exception {
		String user=request.getParameter("fileUser");
		String fileName=request.getParameter("fileName");
		int fileId = Integer.parseInt(request.getParameter("fileId"));
		String endPoint = "obs.cn-north-4.myhuaweicloud.com"+'/'+user;
		String ak = "EF7XRHA5VMMKBVEGFWLP";
		String sk = "5EthO5x7xZ3bfdQp1eKH43YjWSZJCT3HPEFP00Ke";
		String buckername = "icloud-disk2";
		ObsClient obsClient = new ObsClient(ak,sk,endPoint);
		obsClient.deleteObject(buckername,fileName);

		fileService.deletefile(fileId);
		return "redirect:icloud_d2.jsp?error=yes";
	}
}

