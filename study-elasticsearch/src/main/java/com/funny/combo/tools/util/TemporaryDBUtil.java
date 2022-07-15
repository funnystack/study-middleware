package com.funny.combo.tools.util;

import java.sql.*;

public class TemporaryDBUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String url,String name,String pwd){
        try {
            return DriverManager.getConnection(url, name, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询的通用方法
     * @return
     */
    public static ResultSet executeQuery(String url,String name,String pwd,String applicationName){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        try {
            conn = getConnection(url,name,pwd);
            String sql = "select key_name,value,des from configuration_key_m6 where application_id =(SELECT id FROM configuration_application where application_name='"+applicationName+"')";
            Statement statement = conn.createStatement();
            set = statement.executeQuery(sql);
//            for (int i=0;i<args.length;i++){
//                ps.setObject(i+1,args[i]);
//            }
//            set = ps.executeQuery();
//            List<Map<String,Object>> list = new ArrayList<>();
//            int count = set.getMetaData().getColumnCount();
//
//            while(set.next()){
//                Map<String, Object> map = new HashMap<>();
//                for(int i=0;i<count;i++){
//                    String name = set.getMetaData().getColumnLabel(i+1);
//                    map.put(name,set.getObject(name));
//                }
//                list.add(map);
//            }
//            return list;
            return set;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn,ps,set);
        }
        return null;
    }

    /** 关闭的通用方法 先进后出的原则
     * */
    private static void close(Connection conn,PreparedStatement st,ResultSet set){
        try {
            if(set!=null){
                set.close();
            }
            if(st!=null){
                st.close();
            }
            if(conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
