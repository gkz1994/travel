package cn.itcast.travel.service.serviceImpl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.daoImpl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @ClassName RouteServiceImpl
 * @Description TODO
 * @Author gkz
 * @Date 2019/9/22 01:56
 * @Version 1.0
 **/
public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao=new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int currentPage, int pageSize, int cid,String rname) {
        PageBean<Route>pageBean=new PageBean<>();
        //获取总条数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //设置每页显示条数
        pageBean.setPageSize(pageSize);
        //设置当前页码
        pageBean.setCurrentPage(currentPage);
        //开始的记录条数
        int start=(currentPage-1)*pageSize;
        pageBean.setList(routeDao.findByPage(cid,start,pageSize,rname));
        //设置总页数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }
}
