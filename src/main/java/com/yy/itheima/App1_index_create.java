package com.yy.itheima;

import com.yy.lucene.dao.BookImpl;
import com.yy.lucene.entity.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YY
 * @Date 2019/11/13 20:12
 * @Version 1.0
 */
public class App1_index_create {
    /**
     * 需求:创建索引库，生成索引文件。
     * 在哪里存储索引文件:D:\library\inedx
     */
    private static final String PATH = "D:\\library\\index";

    public static void main(String[] args) throws IOException {

        //采集数据
        List<Book> bookList = new BookImpl().findAllBooks();

        //把从数据库采集的数据，装换为Document文档对象
        List<Document> documentList = new ArrayList<>();
        for (Book book : bookList) {
            //创建Document文档对象
            Document doc = new Document();
            /**
             *给文档添加区域
             * TextFiled 文本域
             * 参数1:域的名称，可以随意定义，只要唯一，一般是和表的列一致
             * 参数2:域的值
             * 参数3:是否存储域的信息
             */
            doc.add(new TextField("id", book.getId() + "", Field.Store.YES));
            doc.add(new TextField("bookname", book.getBookname(), Field.Store.YES));
            doc.add(new TextField("price", book.getPrice() + "", Field.Store.YES));
            doc.add(new TextField("pic", book.getPic(), Field.Store.YES));
            doc.add(new TextField("bookdesc", book.getBookdesc(), Field.Store.YES));

            //把文档对象添加到集合
            documentList.add(doc);
        }
        //创建分析器对象，用于分词(切词)
        Analyzer analyzer = new StandardAnalyzer();
        //创建索引库配置对象，配置索引库
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        //创建索引目录对象，指定索引库目录地址
        Directory d = FSDirectory.open(new File(PATH));
        //创建索引库操作对象，用于把文档写入索引文件
        IndexWriter writer = new IndexWriter(d, config);
        for (Document document : documentList) {
            //把文档写入索引库
            writer.addDocument(document);
        }
        //关闭释放资源
        writer.commit();
        writer.close();
    }
}
