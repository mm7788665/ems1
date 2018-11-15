package com.baizhi.controller;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class lucene {
    public static void main(String[] args)throws IOException {
        //索引库位置
        Directory dir = FSDirectory.open(new File("d:/index"));
        Analyzer analyzer =new StandardAnalyzer(Version.LUCENE_44);
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_44,analyzer);
        //构建索引写入对象   参数1 索引库路径 参数2 索引写入对象相关配置 分配器
        IndexWriter indexWriter = new IndexWriter(dir,conf);
        //构建文档对象模型
        Document document = new Document();
        document.add(new StringField("title","女人",Field.Store.YES));
        document.add(new StringField("author","龚浩颖",Field.Store.YES));
        document.add(new StringField("content","是个臭狗逼",Field.Store.YES));
        indexWriter.addDocument(document);
        indexWriter.commit();
    }
}
