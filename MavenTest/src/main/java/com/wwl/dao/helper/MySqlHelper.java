package com.wwl.dao.helper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author long
 * @date 2020年7月7日
 */
public class MySqlHelper {
    private static InputStream config = null;
    private static SqlSessionFactory ssf = null;

    static {
        try {
            // 读取配置文件 mybatis-config.xml
            config = Resources.getResourceAsStream("mybatis-config.xml");
            // 根据配置文件构建SqlSessionFactory
            ssf = new SqlSessionFactoryBuilder().build(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> SelectAllList(String sqlkey) {

        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            // 查询所有用户
            List<T> result = ss.selectList(sqlkey);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭 SqlSession
            ss.close();
        }
        return null;
    }

    public static <T> T SelectOneObject(int id,String sqlkey) {

        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            // 查询单个用户
            T result = ss
                    .selectOne(sqlkey,id);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭 SqlSession
            ss.close();
        }
        return null;
    }

    public static <T> int InsertEntity(T bean,String sqlkey){
        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            return ss.insert(sqlkey,bean);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
             ss.commit();
            // 关闭 SqlSession
            ss.close();
        }
    }

    public static <T> int UpdateEntity(T bean,String sqlkey){
        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            return ss.update(sqlkey,bean);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            ss.commit();
            // 关闭 SqlSession
            ss.close();
        }
    }

    public static int DeleteEntity(int id,String sqlkey){
        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            return ss.delete(sqlkey,id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            ss.commit();
            // 关闭 SqlSession
            ss.close();
        }
    }

}
