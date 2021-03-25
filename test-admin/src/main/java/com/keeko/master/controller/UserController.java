package com.keeko.master.controller;

import com.github.pagehelper.PageHelper;
import com.keeko.common.ResultResponse;
import com.keeko.master.service.ISysUserService;
import com.keeko.utils.JwtUtils;
import io.xjar.XConstants;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.key.XKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final ISysUserService userService;

    @Autowired
    public UserController(ISysUserService userService) {
        this.userService = userService;
    }
    @Autowired
    private JwtUtils jwtUtils;


    /**
     * 删除
     */
    //@PreAuthorize(hasPermi = "system:message:remove")
    @PostMapping("/remove")
    public ResultResponse remove(@RequestBody List<String> ids) {
        PageHelper.startPage(vo.getPageNum() == null ? 1 : vo.getPageNum(), vo.getPageSize() == null ? 20 : vo.getPageSize());
        for (String id : ids) {
            System.out.println(id);
        }
        return new ResultResponse(true, ids);
    }

    /**
     * 根据id查询用户   http://localhost:8080/user/test
     */
    @GetMapping("/test")
    @ResponseBody
    public ResultResponse test(HttpServletRequest request) {


        request.getSession().setAttribute("loginName", "18206079949");

        //获取 request ?号后面所有参数
        String requestURL = request.getQueryString();
        System.out.println(requestURL);
        String scheme = request.getScheme();//-->http
        System.out.println(scheme);
        String serverName = request.getServerName();//-->localhost
        System.out.println(serverName);
        int serverPort = request.getServerPort();//-->3000
        System.out.println(serverPort);
        String localAddr = request.getLocalAddr();//-->192.168.0.123
        System.out.println(localAddr);
        int localPort = request.getLocalPort();//-->8080
        System.out.println(localPort);

        //CharacterCondition characterCondition = userService.selectTestCharacterCondition();
        //User user = userService.selectTestVipMemberAccount();
        return new ResultResponse();
    }


    public static void main(String[] args) throws Exception {
        String password = "123456";
        XKey xKey = XKit.key(password);
        //XBoot.encrypt("D:\\java\\project\\zyjhAppApi\\target\\zhuyingAppApi.jar", "D:\\java\\tomcat\\apache-tomcat-9.0.33\\webapps\\encrypted.jar", xKey, XConstants.MODE_DANGER);
        XBoot.encrypt("C:\\Users\\admin\\Desktop\\jm\\zyAppVipApi\\1\\zhuyingAppVipApi.jar", "C:\\Users\\admin\\Desktop\\jm\\zyAppVipApi\\2\\zhuyingAppVipApi.jar", xKey, XConstants.MODE_DANGER);
        System.out.println("执行完成");
    }

}
