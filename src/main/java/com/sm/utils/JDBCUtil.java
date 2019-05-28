package com.sm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static String url = "jdbc:mysql://127.0.0.1:3306/db_student?useUnicode=true&characterEncoding=utf8";
    private static String name = "root";
    private static String password = "root";
    private static Connection connnection = null;
    private static JDBCUtil jdbcUtil = null;

    public static JDBCUtil getInitJDBCUtil() {
        if (jdbcUtil == null) {
            synchronized (JDBCUtil.class) {   //绾跨▼鍔犻攣
                if (jdbcUtil == null) {
                    jdbcUtil = new JDBCUtil();   //鎳掓眽寮忓姞杞�
                }
            }
        }
        return jdbcUtil;
    }

    private JDBCUtil() {
    }

    // 閫氳繃闈欐€佷唬鐮佸潡娉ㄥ唽鏁版嵁搴撻┍鍔紝淇濊瘉娉ㄥ唽鍙墽琛屼竴娆�
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 鑾峰緱杩炴帴
    public Connection getConnection() {
        try {
            connnection = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connnection;

    }

    //鍏抽棴杩炴帴
    public void closeConnection() {
        if (connnection != null) {
            try {
                connnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

//    public static void main(String[] args) {
//        Connection connection = JDBCUtil.getInitJDBCUtil().getConnection();
//        if (connection != null) {
//            System.out.println("杩炴帴鎴愬姛");
//        }
//    }
}
