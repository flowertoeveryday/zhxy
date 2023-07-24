package com.hahah.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hahah.myzhxy.pojo.Admin;
import com.hahah.myzhxy.pojo.LoginForm;
import com.hahah.myzhxy.pojo.Student;
import com.hahah.myzhxy.pojo.Teacher;
import com.hahah.myzhxy.service.AdminService;
import com.hahah.myzhxy.service.StudentService;
import com.hahah.myzhxy.service.TeacherService;
import com.hahah.myzhxy.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Api
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    /*
     *修改密码处理器
     * 请求参数  oldPwd newPwd token头
     * 响应的数据  Result.OK     date=null
     **/
    @ApiOperation("更新用户密码的处理器")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
           @ApiParam("获得token") @RequestHeader("token") String token,
          @ApiParam("原密码") @PathVariable String oldPwd,
          @ApiParam("新密码") @PathVariable String newPwd
    ){
      boolean e= JwtHelper.isExpiration(token);
      if(e){ //判断token是否过期
          return  Result.fail("token失效，请重新登录");
      }

      //获取id和用户类型
         Long userID=JwtHelper.getUserId(token);
        Integer userType =JwtHelper.getUserType(token);

        oldPwd= MD5.encrypt(oldPwd);
        newPwd=MD5.encrypt(newPwd);

        switch(userType){
            case 1:
                QueryWrapper<Admin> queryWrapper =new QueryWrapper<>();
                queryWrapper.eq("id",userID.intValue());
                queryWrapper.eq("",oldPwd);
                 Admin admin= adminService.getOne(queryWrapper);
                 if(admin==null){
                     admin.setPassword(newPwd);
                     adminService.saveOrUpdate(admin);
                 }else{
                     return Result.fail().message("原密码有误");
                 }
                break;
            case 2:
                QueryWrapper<Student> queryWrapper2 =new QueryWrapper<>();
                queryWrapper2.eq("id",userID.intValue());
                queryWrapper2.eq("",oldPwd);
                Student student= studentService.getOne(queryWrapper2);
                if(student==null){
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else{
                    return Result.fail().message("原密码有误");
                }

                break;
            case 3:
                QueryWrapper<Teacher> queryWrapper3 =new QueryWrapper<>();
                queryWrapper3.eq("id",userID.intValue());
                queryWrapper3.eq("",oldPwd);
                Teacher teacher= teacherService.getOne(queryWrapper3);
                if(teacher==null){
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok("修改完成");


    }

    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUploade")
    public Result headerImagUploade(
          @ApiParam("头像文件") @RequestPart("multpartFile") MultipartFile multipartFile,
          HttpServletRequest request
    ){
        //先修改图片名字避免冲突
        // UUID.randomUUID().toString() 生成一个UUDID的字符串
        // UUID.randomUUID().toString().replace("-","").toLowerCase()  并将里面的-替换为空这样就得到唯一的名字
        String uuid=UUID.randomUUID().toString().replace("-","").toLowerCase();
        String originalFilename=multipartFile.getOriginalFilename(); //获得上传的图片
        int i=originalFilename.lastIndexOf("."); //找到最后一个点出现的位置即 .jpg的点
        String newFileName=uuid+originalFilename.substring(i);

        //保存图片  先将文件发送到独立的图片服务器上，然后拿到独立服务器上图片的url存过去 但是目前没有独立的服务器
        //request.getServletContext().getRealPath("public/upload"); 因为用的是SpringBoot内置TomCat图片的存储位置对不准

        //先手动找到upload的真实目录所在的位置
        String portraitPath="C:/Users/86166/IdeaProjects/myzhxy/target/classes/public/upload/"+newFileName;
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //响应图片路径

        String path="upload/"+newFileName;
        return Result.ok(path);

    }

    @GetMapping("/getInfo")
    public  Result getInfoByToken(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null,ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出 用户id 和用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);


        Map<String,Object> map =new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin =adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student =studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher= teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    @ApiOperation("登录的方法")
    @PostMapping("/login")
    public Result login(
            @ApiParam("登录提交信息的form表单")@RequestBody LoginForm loginForm,
            HttpServletRequest request){
        // 验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String)session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if("".equals(sessionVerifiCode) || null == sessionVerifiCode){
            return Result.fail().message("验证码失效,请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)){
            return Result.fail().message("验证码错误,请重试");
        }
        // 从session域中移除现有验证码
        session.removeAttribute("verifiCode");
        // 分用户类型进行校验


        // 准备一个map用户存放响应的数据
        Map<String,Object> map=new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin=adminService.login(loginForm);
                    if (null != admin) {
                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
                        map.put("token",JwtHelper.createToken(admin.getId().longValue(), 1));
                    }else{
                        throw new RuntimeException("用户名或者密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student =studentService.login(loginForm);
                    if (null != student) {
                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
                        map.put("token",JwtHelper.createToken(student.getId().longValue(), 2));
                    }else{
                        throw new RuntimeException("用户名或者密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teahcer =teacherService.login(loginForm);
                    if (null != teahcer) {
                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
                        map.put("token",JwtHelper.createToken(teahcer.getId().longValue(), 3));
                    }else{
                        throw new RuntimeException("用户名或者密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("找不到该用户");

    }



    //GetMapping只接受get请求
    @GetMapping("/getVerifiCodeImage")   //和上面RequestMapping 连一起是一个完整请求路径
    public void getVerfiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage =CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码放入Session域，为下次验证做准备
        //先获取Session对象
        HttpSession session =request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将图片响应给浏览器
        // ServletOutputStream outputStream=response.getOutputStream();
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
