package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.serviceImpl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service=new RouteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname=request.getParameter("rname");
        rname=new String(rname.getBytes("iso-8859-1"),"utf-8");
        int cid=0;
        if(cidStr!=null &&cidStr.length()>0){
            cid=Integer.parseInt(cidStr);
        }

        int currentPage=0;//当前页码
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage=Integer.parseInt(currentPageStr);
        }else{
            currentPage=1;
        }

        int pageSize=0;//每页显示的条数
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize=Integer.parseInt(pageSizeStr);
        }else{
            pageSize=5;
        }

        PageBean<Route>routePageBean=service.pageQuery(currentPage,pageSize,cid,rname);
        writerValue(routePageBean,response);
    }



}
