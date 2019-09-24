package cn.itcast.travel.service.serviceImpl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.daoImpl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @ClassName FavoriteServiceImpl
 * @Description TODO
 * @Author gkz
 * @Date 2019/9/25 01:16
 * @Version 1.0
 **/
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao dao=new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = dao.findByUid(Integer.parseInt(rid), uid);
        return favorite!=null;
    }

    @Override
    public void add(String rid, int uid) {
        dao.add(Integer.parseInt(rid),uid);
    }
}
