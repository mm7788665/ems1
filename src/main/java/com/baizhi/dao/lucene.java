package com.baizhi.dao;

import com.baizhi.entity.Artical;
import com.baizhi.util.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class lucene {
    //添加
    public void add(Artical artical){
        IndexWriter indexWriter = LuceneUtil.add();
        Document docFromArt = getDocFromArt(artical);
        try {
            indexWriter.addDocument(docFromArt);
            LuceneUtil.commit(indexWriter);
        } catch (IOException e) {
            LuceneUtil.rollback(indexWriter);
            e.printStackTrace();
        }
    }
    //搜索    查询
    public List<Artical> query(String param){
        ArrayList<Artical> list = new ArrayList<>();
        IndexSearcher indexSearcher = LuceneUtil.queryAll();
        Query query = new TermQuery(new Term("content", param));
        try {
            TopDocs topDocs = indexSearcher.search(query, 100);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i=0;i<scoreDocs.length;i++){
                int doc = scoreDocs[i].doc;
                Document document = indexSearcher.doc(doc);
                Artical artical = getArtFromDoc(document);
                list.add(artical);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    //修改
    public void  update(Artical artical){
        Document docFromArt = getDocFromArt(artical);
        IndexWriter indexWriter = LuceneUtil.add();
        try {
            indexWriter.updateDocument(new Term("id",artical.getId()),docFromArt);
            LuceneUtil.commit(indexWriter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //删除
    public void delete(String id){
        IndexWriter add = LuceneUtil.add();
        try {
            add.deleteAll();
            LuceneUtil.commit(add);
        } catch (IOException e) {
            LuceneUtil.rollback(add);
            e.printStackTrace();
        }
    }
    //转换成对象
    public Document getDocFromArt(Artical artical){
        Document document = new Document();
        document.add(new StringField("id",artical.getId(),Field.Store.YES));
        document.add(new StringField("title",artical.getTitle(),Field.Store.YES));
        document.add(new StringField("author",artical.getAuthor(),Field.Store.YES));
        document.add(new TextField("content",artical.getContent(),Field.Store.YES));
        return document;
    }
    //
    public Artical getArtFromDoc(Document document){
        Artical artical = new Artical(document.get("id"), document.get("title"), document.get("author"), document.get("content"));
        return artical;
    }
}
