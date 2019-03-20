package com.demo.controller;

import com.demo.service.FastDfsClientWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/1116:21
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FastDfsController {

    private final FastDfsClientWrapper fastDfsClientWrapper;


    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity upload(HttpServletRequest request) throws Exception {
        log.info("请求参数：{}", request.toString());
        return ResponseEntity.ok().build();
    }

    /**
     * 上传图片
     */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public ResponseEntity upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String imgUrl = fastDfsClientWrapper.uploadFile(file);
//        Map responseMap = Map.of(
//                "returnCode", "0000",
//                "message", "上传文件成功",
//                "filePath", imgUrl);
//        return ResponseEntity.ok(responseMap);
//    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/**")
    public ResponseEntity batchUpload(HttpServletRequest request) {
        String contextPath = request.getRequestURL().toString();
        log.info("请求地址：{}", contextPath);
        log.info("参数：{}", request.getAttribute("filePath"));
//        MultipartHttpServletRequest servletRequest = ;
//        List<MultipartFile> file = ((MultipartHttpServletRequest) request).getFiles("file");
//        List<String> filePaths = fastDfsClientWrapper.uploadFile(file);
        String filePaths = "/test/test/test/adasd";
//        request.setAttribute("filePath", filePaths);
//        return "forward:http://10.138.30.217:2000/app";
        Map responseMap = Map.of(
                "returnCode", "0000",
                "message", "上传文件成功",
                "filePaths", filePaths);
        return ResponseEntity.ok(responseMap);
    }


    /**
     * 上传图片
     */
    @RequestMapping(value = "/app", method = RequestMethod.POST)
    public ResponseEntity batchUpload2(HttpServletRequest request) {
        String contextPath = request.getRequestURL().toString();
        log.info("请求地址：{}", contextPath);
        List<MultipartFile> file = ((MultipartHttpServletRequest) request).getFiles("file");
        if (file.isEmpty()) {
            log.info("文件已被清除");
        } else {
            log.warn("文件任然存在");
        }
        Object filePath = request.getAttribute("filePath");
        Map responseMap = Map.of(
                "returnCode", "0000",
                "message", "上传文件成功",
                "filePaths", filePath);
        return ResponseEntity.ok(responseMap);
    }

//    public String deleteUser(@RequestParam("managerId")Integer managerId, @RequestParam("id")Integer id,
//                             RedirectAttributes redirectAttributes) {
//        redirectAttributes.addAttribute("adminId",managerId);
//        return "redirect:/user/userList";//相当于转发到user/userList?adminId=managerId
//    }


    /**
     * 上传图片
     */
//    @RequestMapping(value = "/bigContent", method = RequestMethod.POST)
//    public ResponseEntity content(@RequestBody Map<String,String> param) {
//        String filePaths = fastDfsClientWrapper.uploadFile(param.get("content"),param.get("extension"));
//        Map responseMap = Map.of(
//                "returnCode", "0000",
//                "message", "上传文件成功",
//                "filePaths", filePaths);
//        return ResponseEntity.ok(responseMap);
//    }


    /**
     * 上传图片
     */
    @RequestMapping(value = "/removeFile", method = RequestMethod.POST)
    public ResponseEntity removeFile(@Param("fileUrl") String fileUrl) {
        fastDfsClientWrapper.deleteFile(fileUrl);
        Map responseMap = Map.of(
                "returnCode", "0000",
                "message", "文件删除成功");
        return ResponseEntity.ok(responseMap);
    }

}
