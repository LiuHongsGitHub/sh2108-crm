package com.bjpowernode.crm.base.util;

import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public class FileUploadUtil {
    public static ResultVo fileUpLoad(MultipartFile photo, HttpServletRequest request){
        ResultVo resultVo = new ResultVo();
        //1、文件存放位置
        String realPath = request.getSession().getServletContext().getRealPath("/upPhoto");
        //用file类查找文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            //文件不存在，新建此文件夹
            file.mkdirs();
        }

        //文件命名
        String filename = photo.getOriginalFilename();
        filename = System.currentTimeMillis() + filename;


        //将文件放入相应位置的文件夹/upPhoto/filename
        try {
            //校验后缀名
            checkSubffix(filename);
            //校验文件大小
            checkFileSize(photo.getSize());

            photo.transferTo(new File(realPath + File.separator + filename) );

            //将头像存放路径返回前端页面
            //request.getContextPath():/crm + realPath:/upPhoto + File.separator:/ + filename:头像名
            String upFilePath = request.getContextPath() + File.separator + "upPhoto" + File.separator + filename;

            //放置成功，返回结果
            resultVo.setT(upFilePath);
            resultVo.setOk(true);
            resultVo.setMessage("头像上传成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CrmException c) {
            resultVo.setMessage(c.getMessage());
        }
        return resultVo;
    }

    private static void checkFileSize(long size ) {
        //定义文件最大大小
        long maxSize = 2*1024*1024;
        if(size > maxSize){
            //超出
            throw new CrmException(CrmEnum.USER_UPDATE_MAXSIZE);
        }
    }

    public static void checkSubffix(String filename){
        //校验文件扩展名是否合法，将文件名进行分割
        String subffix = filename.substring(filename.lastIndexOf(".") + 1);
        //正确的拓展名webp,png,jpg,gif
        String correctSubffix ="webp,png,jpg,gif";
        if(!correctSubffix.contains(subffix)){
            //不正确，通过异常来返回信息
            throw new CrmException(CrmEnum.USER_UPDATE_SUFFIX);
        }
    }
}
