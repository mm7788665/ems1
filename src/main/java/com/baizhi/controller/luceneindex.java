package com.baizhi.controller;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
public class luceneindex {
    public static void main(String[] args) throws IOException {
        Directory dir = FSDirectory.open(new File("d:/index"));
        IndexReader reader=DirectoryReader.open(dir);
        IndexSearcher indexSearcher=new IndexSearcher(reader);
        //参数1   索引库检查的条件 关键字查询
        Query query=new TermQuery(new Term("author","龚浩颖"));
       //参数2 查询条数
        TopDocs topDocs = indexSearcher.search(query, 100);
        //相关度排序后的结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(int i=0;i<scoreDocs.length;i++){
            ScoreDoc scoreDoc=scoreDocs[i];
            int doc =scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            String title = document.get("title");
            String author = document.get("author");
            String content = document.get("content");
            System.out.println("this is title"+title);
            System.out.println("this is author"+author);
            System.out.println("this is content"+content);

        }
    }
}
