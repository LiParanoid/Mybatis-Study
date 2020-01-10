package com.paranoid.dao;

import com.paranoid.pojo.User;
import com.paranoid.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {

    @Test
    public void getUserList() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtil.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();

            for (User user : userList) {
                System.out.println(user.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void insertUser() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtil.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int i = mapper.insertUser(new User("小美", 24));
            if(i > 0){
                System.out.println("插入数据成功");
            }
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void updateUser() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtil.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int i = mapper.updateUser(new User(8,"大美", 16));
            if(i > 0){
                System.out.println("更新数据成功");
            }
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void deleteUser() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtil.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int i = mapper.deleteUser(8);
            if(i > 0){
                System.out.println("删除数据成功");
            }
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
