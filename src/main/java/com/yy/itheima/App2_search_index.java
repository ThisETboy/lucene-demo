package com.yy.itheima;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author YY
 * @Date 2019/11/13 21:21
 * @Version 1.0
 * 数据检索
 */
public class App2_search_index {
    //索引的位置
    private static final String PATH = "D:\\code\\120day\\day02\\index";

    /**
     * 搜索
     */
    public static void main(String[] args) throws Exception {
        //建立分析器对象(Analyzer)，用于分词
        Analyzer analyzer = new IKAnalyzer();
        //建立查询对象(Query)
        QueryParser queryParser = new QueryParser("bookname", analyzer);
        Query query = queryParser.parse("java");
        //建立索引库目录对象(Dircetory),指定索引库的位置
        Directory directory = FSDirectory.open(new File(PATH));
        //4. 建立索引读取对象（IndexReader），把索引数据读取到内存中
        IndexReader indexReader = DirectoryReader.open(directory);
        //建立索引搜索对象(IndexSearcher),执行搜索，返回搜索的结果集(
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //处理结果集
        TopDocs topDocs = indexSearcher.search(query, 10);
        //总记录数
        System.out.println("总记录数:" + topDocs.totalHits);
        //查询到Document
        ScoreDoc[] scoreDoc = topDocs.scoreDocs;
        for (ScoreDoc doc : scoreDoc) {
            System.out.println("文档id:" + doc.doc);
            //通过倒排索引的id获取文档对象
            Document document = indexSearcher.doc(doc.doc);
            System.out.println("图书id:" + document.get("id"));
            System.out.println("书名:" + document.get("bookname"));
            System.out.println("图书详情:" + document.get("bookdesc"));
            System.out.println("价格:" + document.get("price"));
            System.out.println("图片:" + document.get("pic"));
            System.out.println("==========");
        }
        //释放资源
        indexReader.close();
    }
}
