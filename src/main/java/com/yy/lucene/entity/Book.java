package com.yy.lucene.entity;

/**
 * @Author YY
 * @Date 2019/11/13 19:45
 * @Version 1.0
 */
public class Book {
    private Integer id;      //ID
    private String bookname; //书名
    private Long price;     //价格
    private String pic;      //图片
    private String bookdesc; //描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBookdesc() {
        return bookdesc;
    }

    public void setBookdesc(String bookdesc) {
        this.bookdesc = bookdesc;
    }

    public Book(Integer id, String bookname, Long price, String pic, String bookdesc) {
        this.id = id;
        this.bookname = bookname;
        this.price = price;
        this.pic = pic;
        this.bookdesc = bookdesc;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", price=" + price +
                ", pic='" + pic + '\'' +
                ", bookdesc='" + bookdesc + '\'' +
                '}';
    }
}
