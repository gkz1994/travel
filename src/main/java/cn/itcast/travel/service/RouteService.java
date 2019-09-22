package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {

    PageBean<Route>pageQuery(int currentPage, int pageSize,int cid,String rname);


}
