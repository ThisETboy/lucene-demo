package com.yy.itheima;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @Author YY
 * @Date 2019/11/15 18:51
 * @Version 1.0
 * 新增
 */
public class App1_index_insert {
    public static void main(String[] args) throws IOException {

    }

    @Test
    public void test() throws Exception {
        //1. 建立分析器（分词器）对象(Analyzer)，用于分词 // 数据分析
        Analyzer analyzer = new IKAnalyzer();

        //2. 建立索引库配置对象（IndexWriterConfig），配置索引库
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        // OpenMode.CREATE 指定索引的打开方式为新建；会新建或者覆盖原有索引文件的方式创建新的索引
        // OpenMode.APPEND 会在原有索引的基础上追加新创建的索引。
        //indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        //3. 建立索引库目录对象（Directory），指定索引库的位置
        Directory directory = FSDirectory.open(new File("D:\\118\\luceneindex"));

        //4. 建立索引库操作对象（IndexWriter），操作索引库
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);


        //=============================================================================
        //5. 使用IndexWriter，把文档对象写入索引库
        Document doc = new Document();
        //不分词不索引，但是存储
        doc.add(new StoredField("id", "11111"));
        //书名：分词，索引，存储
        doc.add(new TextField("bookname", "Mysql删库到跑路", Field.Store.YES));
        //价格：分词，索引，存储
        doc.add(new LongField("price", 2222222222L, Field.Store.YES));
        //图片：存储即可
        doc.add(new StoredField("pic", "https://img13.360buyimg.com/n1/s200x200_jfs/t1/70360/31/13694/359480/5db1608fE892bab40/85bfed77eec81962.jpg"));
        //详情：分词、索引、但是不存储
        doc.add(new TextField("bookdesc", "《高性能MySQL（第3版）》是MySQL 领域的经典之作，拥有广泛的影响力。第3 版更新了大量的内容，不但涵盖了MySQL5.5版本的新特性，也讲述了关于固态盘、高可扩展性设计和云计算环境下的数据库相关的新内容，原有的基准测试和性能优化部分也做了大量的扩展和补充。全书共分为16章和6 个附录，内容涵盖MySQL架构和历史，基准测试和性能剖析，数据库软硬件性能优化，复制、备份和恢复，高可用与高可扩展性，以及云端的MySQL和MySQL相关工具等方面的内容。每一章都是相对独立的主题，读者可以有选择性地单独阅读。", Field.Store.NO));
        indexWriter.addDocument(doc);
        //=============================================================================


        //6. commit/关闭释放资源
        indexWriter.close();
    }

    /**
     * 删除索引文档
     */
    @Test
    public void testDelete() throws Exception {
        //创建分词器analyzer
        Analyzer analyzer = new IKAnalyzer();
        //创建存放索引目录Directory，指定索引存放路径
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //创建存放索引目录Directory，指定索引存放路径
        File file = new File("D:\\118\\luceneindex");
        Directory directory = FSDirectory.open(file);
        //创建索引编写器IndexWriter
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //创建条件对象Term
        Term term = new Term("bookName", "lucene");
        //根据词条删除
//        indexWriter.deleteDocuments(term);
        indexWriter
                .deleteAll();

        indexWriter.close();
    }

    @Test
    public void update() throws IOException {
        //创建分词器
        Analyzer analyzer = new IKAnalyzer();
        //创建文档索引配置对象IndexWriterConfig
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //创建存放索引目录Directory，指定索引存放路径
        File file = new File("E:\\IDEAPROJECTYY\\lucene-demo\\src\\document");
        Directory directory = FSDirectory
                .open(file);
        //创建索引编辑器
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //创建文档Document
        Document document = new Document();
        document.add(new StringField("id", "123", Field.Store.YES));
        document.add(new TextField("name", "spring and struts and springmvc and mybatis", Field.Store.YES));
        //创建条件对象Trem
        Term term = new Term("name", "mybatis");
        //根据词条更新:如果存在则更新，不存在则更新
        indexWriter.updateDocument(term, document);
        indexWriter.close();
    }

    @Test
    public void termQuery() throws Exception {
        Term term = new Term("bookname", "java");
        TermQuery termQuery = new TermQuery(term);
        search(termQuery);
    }

    private void search(Query query) throws Exception {
        //打印查询语法
        System.out.println("查询的语法:" + query);
        //创建分词器Analyzer
        Analyzer analyzer = new IKAnalyzer();
        //创建存放索引目录Directory，指定索引存放路径
        Directory directory = FSDirectory.open(new File("D:\\library\\index"));
        //创建索引读取对象IndexReader
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索对象IndexSearcher，执行搜索，返回结果
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        /**
         * 参数1：查询对象
         * 参数2：查询前n个文档
         * 返回结果：得分文档(包含文档数组，总的命中数)
         */
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("符合本次查询的总命中文档数为:" + topDocs.totalHits);
        //处理搜索结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("文档在Lucene中id为：" + scoreDoc.doc + "文档分值为:" + scoreDoc.score);
            //根据
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println("文档id为：" + document.get("id"));
            System.out.println("名称为：" + document.get("bookname"));
            System.out.println("价格为：" + document.get("price"));
            System.out.println("图片为：" + document.get("pic"));
            System.out.println("描述为：" + document.get("bookdesc"));
            System.out.println("---------------------------------------");
        }
        indexReader.close();
    }

}
