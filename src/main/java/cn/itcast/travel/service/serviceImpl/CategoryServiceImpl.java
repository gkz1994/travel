package cn.itcast.travel.service.serviceImpl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.daoImpl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author gkz
 * @Date 2019/9/20 21:34
 * @Version 1.0
 **/
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao=new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        //1.1获取redis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可使用sortedset排序查询
        Set<String> set = jedis.zrange("category", 0, -1);
        List<Category>cs=null;
        if(set==null||set.size()==0){
            //3.如果为空,需要从数据库查询，在将数据存入redis
            //3.1从数据库查询
            cs=categoryDao.findAll();
            //3.2将集合数据存储到redis中的category的key
            for (int i=0;i<cs.size();i++){
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{

        }
        return cs;
    }
}
