package com.yy.lucene.dao;

import com.yy.lucene.entity.Book;

import java.beans.beancontext.BeanContextServiceRevokedEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YY
 * @Date 2019/11/13 19:46
 * @Version 1.0
 * 图书dao实现类
 */
public class BookImpl {

    /**
     * 查询全部图书列表
     */
    public List<Book> findAllBooks() {
        //创建结果集集合
        List<Book> bookList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //创建数据库连接对象
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "123456");
            //定义sql语句
            String sql = "select * from book";
            //创建statement语句对象
            psmt = connection.prepareStatement(sql);
            //设置参数
            //执行
            rs = psmt.executeQuery();
            //处理结果集
            while (rs.next()) {
                //创建图书对象
                Book book = new Book();
                //图书id
                book.setId(rs.getInt("id"));
                //图书名称
                book.setBookname(rs.getString("bookname"));
                //图书价格
                book.setPrice(rs.getLong("price"));
                //图书图片
                book.setPic(rs.getString("pic"));
                //图书描述
                book.setBookdesc(rs.getString("bookdesc"));
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psmt != null) {
                    psmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookList;
    }

}
