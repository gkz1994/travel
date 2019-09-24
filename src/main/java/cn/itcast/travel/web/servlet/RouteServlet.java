package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.serviceImpl.FavoriteServiceImpl;
import cn.itcast.travel.service.serviceImpl.RouteServiceImpl;
import org.omg.IOP.Encoding;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Version: V1.0
 * @Date: 2019/3/5 20:49
 * @Description: 分页查询
 **/
@WebServlet(name = "RouteServlet", urlPatterns = "/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    private FavoriteService favoriteService=new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        //接收rname线路名称
        String rname = request.getParameter("rname");
        if(rname!=null){
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }else{

            int cid = 0;
            if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
                cid = Integer.parseInt(cidStr);
            }

            int currentPage = 0;
            if (currentPageStr != null && currentPageStr.length() > 0) {
                currentPage = Integer.parseInt(currentPageStr);
            } else {
                currentPage = 1;
            }

            int pageSize = 0;
            if (pageSizeStr != null && pageSizeStr.length() > 0) {
                pageSize = Integer.parseInt(pageSizeStr);
            } else {
                pageSize = 5;
            }
            PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
            writerValue(routePageBean, response);
        }

    }


    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        Route route=routeService.findOne(rid);
        writerValue(route,response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            uid=0;
        }else{
            uid=user.getUid();
        }

        boolean flag = favoriteService.isFavorite(rid, uid);

        writerValue(flag,response);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            return ;
        }else{
            uid=user.getUid();
        }

        favoriteService.add(rid, uid);

    }
}