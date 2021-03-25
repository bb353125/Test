package com.keeko.master.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.keeko.common.ResultResponse;
import com.keeko.common.annotation.BussinessLog;
import com.keeko.common.annotation.TokenAnnotation;
import com.keeko.common.dictMap.DictMap;
import com.keeko.master.entity.User;
import com.keeko.second.entity.SUser;
import com.keeko.second.service.ITestService;
import com.keeko.second.service.ITestService1;
import com.keeko.utils.AliyunOSSUtil;
import com.keeko.utils.EhcacheUtil;
import com.keeko.utils.HttpUtil;
import com.keeko.utils.JwtUtils;
import com.keeko.utils.RsaUtil;
import com.keeko.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 用户相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/rsa")
public class RsaController {

    private final ITestService testService;
    @Autowired
    private ITestService1 testService1;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public RsaController(ITestService testService) {
        this.testService = testService;
    }

    /**
     * 测试 rsa不对称加密
     */
    @GetMapping("/testRsa")
    @ResponseBody
    public ResultResponse testRsa(HttpServletRequest request) throws UnsupportedEncodingException {

        String s = HttpUtil.get("http://127.0.0.1:8080/test/test1");
        System.out.println(s);

        String privatestr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANMsKZUL8yPHBHJvg0H13/zx8BlGQIzD/Ik+Ih+imCPYzoyXY2TDpDuM+mJnxWzdYrpmi2JhgSY3Vv68AXxIYe4+K3YG0h+/59+HCjQhjnx/Wd2Ptz6+AacNoAdPqK0+5dQpS4MACI0fvjxjujgYOTe3rt9bCIprLXD02FcKSMbXAgMBAAECgYAhckEzxizAYekmZtsXO2Y6nec1WZcntHLvGdQ1xn7qTpCIXAH2F7aPaAjQRTdTwsy8UTmKX38hGmrA9e4WTTCVTXC6hLt1SdLNNDWSSYEj4hygxG4sZSqdnVsilJUfde0x90B0Ikbnu+DhZSpH7Xri+ZIHgc49dSR9H7SWE4BS4QJBAP97/EtA/GMZT6VLRQQgVleMVRsnayBsyPow/ernvFs8g/4+amo+fg0TGVi+LCu8tAlkK7jCuxDGOrbIBTr8V2UCQQDTmUeuM0VdmAgpftkKNRgqWu2L9+IiMXUDVciWL23nZNoSVCPc14ZGDPevPzN9I2vLw4XdH9jwAlWb1sMPKleLAkBTwGVYlhkms2l0mlQpoiAr2wkbObTYkFDiE6VpmEYa8p+ifPuNkKbpylUIzm7Ud6vdWEUs8Ek6moEs4i00xPwxAkBbwMGGVhEkDoKri6eNd4xtAVS0Nt7FuPBma9I43YQorGCrRfTvFD3TLlhQk3U+NBfQGY54XhDxsfFxS+T1NIUVAkEAlPw21p2cIFcj2ibHxkgMul5zfux1DVjdyks5rjdPzeU79H50vMNJQXcUUq16OjtIP9mHMG7lJ2bG/MD8nbHyzw==";
        String publicstr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8yohRQxj7z+9uvsNLqQRf7Vs2umRf44IrvOzn+YB1NwYjqFy5mvyMr4o+2sNN3D/DRQ50osA/OZ0NreJpF90WJsMJCrk2LUkbR07FOrS46BFJpfaTjVFoktxOkdcQPaYFRGFFcp+sIv4hYEpETG4Z/JCmkf1fjtvhzsf0+GpvHQIDAQAB";
        System.out.println(privatestr.length());
        System.out.println(publicstr.length());


        Map<String, Object> map = RsaUtil.generateKey();
        String publicKey = RsaUtil.getPublicKey(map);
        String privateKey = RsaUtil.getPrivateKey(map);

        System.out.println(publicKey);
        System.out.println(privateKey);

        String  str = "罗总123";


        //公钥加密:
        byte[] bytes = RsaUtil.encryptByPublicKey(publicKey, str);
        //私钥解密
        byte[] bytes1 = RsaUtil.decryptByPrivateKey(privateKey, bytes);
        String strbytes1 = new String (bytes1);
        System.out.println(strbytes1);


        String ciphertext = "package com.zhuyin.appApi.service.authorize";

        JSONObject json = new JSONObject();
        json.put("ciphertext", ciphertext);
        String token = jwtUtils.createJWT(json.toJSONString());

        String loginName = jwtUtils.getSubjectFromJwt(token, "ciphertext");
        System.out.println(loginName);

        //私钥加密
        byte[] sb = str.getBytes();
        byte[] bytes2 = RsaUtil.encryptByPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ1MgsXILApfLgT795lJfQs59H//AR8B//gyZsedZ54wfea58eighTub1EauPOPHFaxYEiHRsqt6N4Jnxigf2MYFCuUDsEY4uexazDBRGbz0ZZE3afRp1zLmC5fyDNuFY9jGS0GZmpzGk2VW4crh/aXmhUCZ+5SO2ST6J4fzFh8NAgMBAAECgYBL+XZ4XPSYldw+YAz5JruUvotPfzVlaqvdj0qBQ4+WoklNNaaX+2AeqGdhNCsIwVHAilFHsGch18gT3lehbbBfQpH6oDBLxylyuK5z0mk35U31Df5s/36RqLATimSVgcs8mcFoGleXVq5mKBE76Ycb5Gxl9TsbLxgXA7VtQBdqwQJBAMyQTVJvJo3+l5kcoy9hlA/n1M84wON2JdFZDgXhLFOzjIvj9JBZNfIY0kA7QfPC/FAIu3VHkVMfgZiprR7bAYkCQQDE2cnNBOvIQdeor6DLHE/rF3mzv9LMuipY5JT/oMZzVibjcn6+XkKg3en56aUJUFdsVRECujAmPA4zkUoNLWRlAkBLzhW5PJNO/09yX9hhEInrsv97ow5nvDqbb6LwrtbcVab5mAnjDAwfoO8Y+lygVltl1TaSaWkDIqPFzOroikPxAkBBaFw1Uz1E/Ru7YR/8XRvw8Yvuvf49KF6Jyun3RmSqTiX9zT1v48If0Zy8/XVo8tkZlephDZySF8JgQBZq6npBAkEAyYpy+gsDAGEYob2jtdrajnIkfVT6AI94ScLLkUKsnWXkcGrqwHEFX/NqW+vWzaRVGSKlj7sThPSTHYereWlxOg==", sb);
        String string = new String(bytes2, "ISO-8859-1");



        //公钥解密
        byte[] bytes4 = string.getBytes("ISO-8859-1");
        byte[] bytes3 = RsaUtil.decryptByPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdTILFyCwKXy4E+/eZSX0LOfR//wEfAf/4MmbHnWeeMH3mufHooIU7m9RGrjzjxxWsWBIh0bKrejeCZ8YoH9jGBQrlA7BGOLnsWswwURm89GWRN2n0adcy5guX8gzbhWPYxktBmZqcxpNlVuHK4f2l5oVAmfuUjtkk+ieH8xYfDQIDAQAB", bytes4);
        String strbytes3 = new String (bytes3);
        System.out.println(strbytes3);




        System.out.println("请求成功!");
        return new ResultResponse(true, "");
    }

}
