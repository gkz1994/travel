package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    Favorite findByUid(int rid, int uid);


    int findCountByRid(int rid);

    void add(int parseInt, int uid);
}
