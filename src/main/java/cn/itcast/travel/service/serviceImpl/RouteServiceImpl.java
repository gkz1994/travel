package cn.itcast.travel.service.serviceImpl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.daoImpl.FavoriteDaoImpl;
import cn.itcast.travel.dao.daoImpl.RouteDaoImpl;
import cn.itcast.travel.dao.daoImpl.RouteImgDaoImpl;
import cn.itcast.travel.dao.daoImpl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
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

    private RouteImgDao routeImgDao=new RouteImgDaoImpl();

    private SellerDao sellerDao=new SellerDaoImpl();

    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pageBean = new PageBean<Route>();

        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = routeDao.findTotalCount(cid, rname);
        pageBean.setTotalCount(totalCount);

        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pageBean.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        Route route=routeDao.findOne(Integer.parseInt(rid));
        List<RouteImg>routeImgs=routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgs);
        Seller seller =sellerDao.findById(route.getSid());
        route.setSeller(seller);
        int count=favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
