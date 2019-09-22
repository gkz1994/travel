package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    UserService userService=new UserServiceImpl();;

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check=request.getParameter("check");

        HttpSession session=request.getSession();
        String checkcode_server=(String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info=new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writerValueAsString(info,response);
            return;
        }

        Map<String,String[]> parameterMap=request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Boolean flag=userService.regist(user);
        ResultInfo info=new ResultInfo();
        if(flag){
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        String json=objectMapper.writeValueAsString(info);
        response.setContentType("application/json;chacter=utf-8");
        response.getWriter().write(json);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();

        User user=new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User u=userService.login(user);

        ResultInfo info=new ResultInfo();

        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("账号或密码错误");
        }

        if(u!=null && !u.getStatus().equals("Y")){
            info.setFlag(false);
            info.setErrorMsg("账号未激活,请激活后再登录");
        }

        if(u!=null && u.getStatus().equals("Y")){
            info.setFlag(true);
            request.getSession().setAttribute("user",u);
        }

        writerValue(info,response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code=request.getParameter("code");
        if(code!=null){
            UserService userService=new UserServiceImpl();
            Boolean flag=userService.active(code);
            String msg=null;
            if(flag){
                String url=request.getContextPath().replace("user","login.html");
                msg="激活成功,请<a href='"+url+"'>登录</a>";
            }else{
                msg="注册失败,请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user=request.getSession().getAttribute("user");
        writerValue(user,response);
    }
}
