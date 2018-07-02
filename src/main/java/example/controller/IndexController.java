package example.controller;

import example.util.RandomNumUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
 * @Author:ShaochaoZhao
 * @Description:
 * @Date:Create in 10:14 2018/7/2
 * @Modified By:
 **/
@Controller
@RequestMapping("/home")
public class IndexController {

    @RequestMapping("/index")
    public  String index() {
        return "index";
    }

    @RequestMapping("/getSysManageLoginCode")
    @ResponseBody
    public void getSysManageLoginCode(HttpServletResponse response,
                                        HttpServletRequest request) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);

        RandomNumUtil randomNumUtil = RandomNumUtil.Instance();

        try {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            tmp = randomNumUtil.getImage();
            Integer contentLength = tmp.size();
            response.setHeader("content-length", contentLength + "");
            response.getOutputStream().write(tmp.toByteArray());// 将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
