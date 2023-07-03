package com.rhy.bdmp.system.modules.sys.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bcp.common.util.ZipUtils;
import com.rhy.bcp.common.util.plupload.Plupload;
import com.rhy.bcp.common.util.plupload.PluploadUtil;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.base.modules.sys.service.IBaseFileService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

@Api(tags = {"文件上传及下载"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/plupload")
public class PluploadContoller {

    @Autowired
    private IBaseFileService baseFileService;


    @Value("${sys.plupload.dir}")
    private String rootPath;

    /**
     * Plupload文件上传处理方法
     */
    @Log("文件：上传")
    @PostMapping("/upload")
    public RespResult upload(Plupload plupload, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                plupload.setRequest(request);// 手动传入Plupload对象HttpServletRequest属性
                // 文件存储绝对路径,会是一个文件夹，项目相应Servlet容器下的"pluploadDir"文件夹，还会以用户唯一id作划分
                String filePath = (null == request.getParameter("filePath")) ? "" : request.getParameter("filePath");
                String datePath = DateUtil.format(new Date(), "yyyy-MM").replace("-",File.separator);
                String rootFilePath = rootPath + File.separator + filePath + File.separator + datePath;
                File dir = new File(rootFilePath);
                if (!dir.exists()) {
                    dir.mkdirs();// 可创建多级目录，而mkdir()只能创建一级目录
                }
                // 开始上传文件
                File targetFile = PluploadUtil.upload(plupload, dir);
                if (null != targetFile) { //上传成功
                    com.rhy.bdmp.base.modules.sys.domain.po.File file = new com.rhy.bdmp.base.modules.sys.domain.po.File();
                    String realFileName = request.getParameter("fileName");
                    String md5 = request.getParameter("md5");
                    file.setFileId(targetFile.getName());
                    file.setFileName(realFileName);
                    file.setContentType(plupload.getMultipartFile().getContentType());
                    file.setFileSize(plupload.getMultipartFile().getSize());
                    file.setCreateTime(DateUtil.date());
                    file.setCreateBy(WebUtils.getUserId());
                    file.setPath(targetFile.getParent());
                    file.setRealFileName(realFileName);
                    file.setFileType(realFileName.substring(realFileName.lastIndexOf(".")));
                    file.setFileMd5(md5);
                    baseFileService.save(file);
                    result.put("fileId", targetFile.getName());
                    log.info(LogUtil.buildUpParams("文件上传成功", LogTypeEnum.OPERATE.getCode(),null));
                    return RespResult.ok(result);
                } else {
                    log.info(LogUtil.buildUpParams("文件上传失败", LogTypeEnum.OPERATE.getCode(),null));
                   return RespResult.error("上传失败");
                }
            }
            return RespResult.error("未找到多媒体文件");
        } catch (Exception e) {
            log.error("文件上传出错 {}",LogUtil.buildUpParams("", LogTypeEnum.OPERATE.getCode(),null),e);
            return RespResult.error(e.getMessage());
        }
    }

    /**
     * 文件删除
     */
    @Log("文件：删除")
    @DeleteMapping(value = "/removeAll")
    public RespResult removeAll(HttpServletRequest request, HttpServletResponse response){
        try {
            String fileIds = request.getParameter("fileIds");
            if (StrUtil.isNotEmpty(fileIds)){
                String[] fileIdArray = fileIds.split(";sep;");
                for (String fileId : fileIdArray){
                    com.rhy.bdmp.base.modules.sys.domain.po.File file = baseFileService.getById(fileId);
                    if (null != file){
                        File delFile = new File(file.getPath() + File.separator + file.getFileId());
                        if (delFile.exists()){
                            delFile.delete();
                        }
                        baseFileService.removeById(fileId);
                        log.info(LogUtil.buildUpParams("文件删除成功",LogTypeEnum.OPERATE.getCode(),fileIds));
                    }
                }
            }else {
                return RespResult.error("参数[fileIds]文件ID为空");
            }
            return RespResult.ok();
        } catch (Exception e) {
            log.info("文件删除出错 {}",LogUtil.buildUpParams("",LogTypeEnum.OPERATE.getCode(),null),e);
            return RespResult.error(e.getMessage());
        }
    }

    /**
     * Plupload文件下载（单文件）
     */
    @Log("文件：下载")
    @GetMapping(value = "/downFile")
    public void downFile(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        BufferedInputStream br = null;
        OutputStream out = null;
        try {
            String fileId = request.getParameter("fileId");
            String vFileName = request.getParameter("fileName");
            String isOnLine = request.getParameter("isOnLine");
            if (StrUtil.isNotEmpty(fileId)){
                com.rhy.bdmp.base.modules.sys.domain.po.File file = baseFileService.getById(fileId);
                if (null != file){
                    String filePath = file.getPath() + File.separator + file.getFileId();
                    String fileName = new String(file.getFileName().getBytes(),"ISO8859-1");
                    File f = new File(filePath);
                    if (!f.exists()) {
                        response.getWriter().write("<script>alert(\"" + vFileName + "，文件不存在或已删除！\");window.close();</script>");
                        return;
                    }
                    br = new BufferedInputStream(new FileInputStream(f));
                    byte[] buf = new byte[1024];
                    int len = 0;
                    response.reset(); // 非常重要
                    if (StrUtil.isNotEmpty(isOnLine) && "1".equals(isOnLine)) { // 在线打开方式
                        URL u = new URL("file:///" + filePath);
                        response.setContentType(u.openConnection().getContentType());
                        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName  + "\"");
                    } else { // 纯下载方式
                        response.setContentType("application/x-msdownload");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                    }
                    out = response.getOutputStream();
                    log.info(LogUtil.buildUpParams("文件下载成功",LogTypeEnum.GET_RESOURCE.getCode(),null));
                    while ((len = br.read(buf)) > 0){
                        out.write(buf, 0, len);
                    }
                }else{
                    response.getWriter().write("<script>alert(\"" + vFileName + "，文件不存在或已删除！\");window.close();</script>");
                    return;
                }
            } else{
                response.getWriter().write("<script>alert(\"" + fileId + "，文件实例不存在！\");window.close();</script>");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try{
                response.getWriter().write("<script>alert(\"服务器异常！\");window.close();</script>");
            }catch (Exception e1){
                log.error("文件下载出错 {}",LogUtil.buildUpParams("",LogTypeEnum.GET_RESOURCE.getCode(),null),e1);
                e1.printStackTrace();
            }
        } finally {
            try{
                if (null != br){
                    br.close();
                }
                if (null != out){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Plupload文件下载（多文件）
     */
    @Log("文件：多文件下载")
    @GetMapping(value = "/downFiles")
    public void downFiles(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        BufferedInputStream br = null;
        OutputStream out = null;
        String zipFilePath = "";
        try {
            String fileId = request.getParameter("fileId");
            String zipName = request.getParameter("fileName");
            String isOnLine = request.getParameter("isOnLine");
            if (StrUtil.isNotEmpty(fileId)){
                String[] fileIdArray = fileId.split(";sep;");
                List<String> fileIdList = Arrays.stream(fileIdArray).filter(Objects::nonNull).collect(Collectors.toList());
                List<com.rhy.bdmp.base.modules.sys.domain.po.File> fileList = baseFileService.list(new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.File>().in("file_id", fileIdList));
                //创建需要下载的文件路径的集合
                List<Map<String, String>> fileMapList = new ArrayList<Map<String, String>>();
                for(com.rhy.bdmp.base.modules.sys.domain.po.File file : fileList){
                    Map<String, String> fileMap = new HashMap<String, String>();
                    String filePath = file.getPath() + File.separator + file.getFileId();
                    String fileName = file.getFileName();
                    fileMap.put("filePath", filePath);
                    fileMap.put("fileName", fileName);
                    fileMapList.add(fileMap);
                }
                if (null != fileMapList && 0 < fileMapList.size()){
                    //1、压缩成zip文件
                    if (StrUtil.isNotEmpty(zipName)){
                        if (-1 != zipName.lastIndexOf(".")){
                            zipName = zipName.substring(0, zipName.lastIndexOf(".")) + ".zip";
                        }else{
                            zipName = zipName + ".zip";
                        }
                    }else{
                        zipName = "temp.zip";
                    }
                    zipFilePath = rootPath + File.separator + zipName;
                    File zip = new File(zipFilePath);
                    if (!zip.exists()){
                        zip.createNewFile();
                    }
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
                    try{
                        ZipUtils.zipFile(fileMapList, zos);
                        zos.close();
                    }catch (Exception e){
                        e.printStackTrace();
                        if (null != zos){
                            zos.close();
                        }
                    }
                    //2、下载zip文件
                    String fileName = new String(zipName.getBytes(),"ISO8859-1");
                    File zipFile = new File(zipFilePath);
                    if (!zipFile.exists()) {
                        response.getWriter().write("<script>alert(\"文件压缩失败！\");window.close();</script>");
                        return;
                    }
                    br = new BufferedInputStream(new FileInputStream(zipFile));
                    byte[] buf = new byte[1024];
                    int len = 0;
                    response.reset(); // 非常重要
                    if (StrUtil.isNotEmpty(isOnLine) && "1".equals(isOnLine)) { // 在线打开方式
                        URL u = new URL("file:///" + zipFilePath);
                        response.setContentType(u.openConnection().getContentType());
                        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
                    } else { // 纯下载方式
                        response.setContentType("application/x-msdownload");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName  + "\"");
                    }
                    out = response.getOutputStream();
                    log.info(LogUtil.buildUpParams("文件批量下载成功",LogTypeEnum.GET_RESOURCE.getCode(),null));
                    while ((len = br.read(buf)) > 0){
                        out.write(buf, 0, len);
                    }
                }else{
                    response.getWriter().write("<script>alert(\"文件不存在或已删除！\");window.close();</script>");
                    return;
                }
            } else{
                response.getWriter().write("<script>alert(\"" + fileId + "，文件实例不存在！\");window.close();</script>");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try{
                response.getWriter().write("<script>alert(\"服务器异常！\");window.close();</script>");
            }catch (Exception e1){
                log.error("文件批量下载出错 {}",LogUtil.buildUpParams("",LogTypeEnum.GET_RESOURCE.getCode(),null),e);
                e1.printStackTrace();
            }
        } finally {
            try{
                if (null != br){
                    br.close();
                }
                if (null != out){
                    out.close();
                    if (StrUtil.isNotEmpty(zipFilePath)){
                        File delFile = new File(zipFilePath);
                        if (delFile.exists()){
//                            delFile.deleteOnExit();
                            delFile.delete();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Log("文件：图片显示")
    @GetMapping(value = "/showImg")
    public void showImg(HttpServletResponse response, String fileId) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/*");
        //获取图片
        BufferedInputStream br = null;
        OutputStream out = null;
        if (StrUtil.isNotEmpty(fileId)){
            com.rhy.bdmp.base.modules.sys.domain.po.File file = baseFileService.getById(fileId);
            if (null != file){
                String filePath = file.getPath() + File.separator + file.getFileId();
//                String fileName = new String(file.getFileName().getBytes(),"ISO8859-1");
                File f = new File(filePath);
                if (!f.exists()) {
                    response.getWriter().write("<script>alert(\"" + fileId + "，文件不存在或已删除！\");window.close();</script>");
                    return;
                }
                br = new BufferedInputStream(new FileInputStream(f));
                byte[] buf = new byte[1024];
                int len = 0;
                response.reset(); // 非常重要
                out = response.getOutputStream();
                while ((len = br.read(buf)) > 0){
                    out.write(buf, 0, len);
                }
                log.info(LogUtil.buildUpParams("获取图片成功",LogTypeEnum.GET_RESOURCE.getCode(),null));
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(br);
            }else{
                response.getWriter().write("<script>alert(\"" + fileId + "，文件不存在或已删除！\");window.close();</script>");
                return;
            }
        } else{
            response.getWriter().write("<script>alert(\"" + fileId + "，文件实例不存在！\");window.close();</script>");
            return;
        }
    }
}
