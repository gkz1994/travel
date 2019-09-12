package cn.itcast.travel.dao.daoImpl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName UserDaoImpl
 * @Description TODO
 * @Author gkz
 * @Date 2019/9/13 01:58
 * @Version 1.0
 **/
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUserName(String username) {
        String sql="select * from tab_user where username=?";
        User user=jdbcTemplate.queryForObject(sql,new org.springframework.jdbc.core.BeanPropertyRowMapper<User>(User.class),username);
        return user;
    }

    @Override
    public void save(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail());
    }
}
