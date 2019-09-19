package cn.itcast.travel.service.serviceImpl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.daoImpl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author gkz
 * @Date 2019/9/13 01:59
 * @Version 1.0
 **/
public class UserServiceImpl implements UserService {

    private UserDao userdao=new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        User u=userdao.findByUserName(user.getUsername());
        if(u!=null){
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userdao.save(user);

        //发送邮件
        String context="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(),context,"一封激活邮件");
        return true;
    }

    @Override
    public Boolean active(String code) {

        User user=userdao.findByCode(code);
        if(user!=null){
            userdao.updateStatus(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User login(User user) {
        return userdao.findByUserNameAndPassword(user.getUsername(),user.getPassword());
    }
}
