package com.baizhi.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class LuceneUtil {
    private static Directory directory;
    private static Version version;
    private static Analyzer analyzer;
    private static IndexWriterConfig writerConfig;

    static {
        try {
            directory=FSDirectory.open(new File("d:/index"));
            version=Version.LUCENE_44;
            analyzer=new IKAnalyzer();
            writerConfig =new IndexWriterConfig(version,analyzer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //添加
    public static IndexWriter add() {
        IndexWriter indexWriter = null;
        try {
            indexWriter =new IndexWriter(directory,writerConfig);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexWriter;
    }
    //查询
    public static IndexSearcher queryAll(){
        IndexReader reader=null;
        try {
            reader = DirectoryReader.open(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        return indexSearcher;
    }
    //提交
    public static void commit(IndexWriter indexWriter){
        try {
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void rollback(IndexWriter indexWriter){
        try {
            indexWriter.rollback();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
