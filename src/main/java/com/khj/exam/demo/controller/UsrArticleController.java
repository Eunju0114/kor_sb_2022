package com.khj.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khj.exam.vo.Article;

@Controller
public class UsrArticleController {
	// 인스턴스 변수 시작
	private int articlesLastId;
	private List<Article> articles;
	// 인스턴스 변수 끝
	
	// 생성자
	public UsrArticleController() {
		articlesLastId = 0;
		articles = new ArrayList<>();
		
		makeTestData();
	}
	
	// 서비스 메서드 시작
	private void makeTestData() {
		for ( int i = 1; i <= 10; i++ ) {
			String title = "제목 " + i;
			String body = "내용 " + i;
			
			writeArticle(title, body);
		}
	}
	
	public Article writeArticle(String title, String body) {
		int id = articlesLastId + 1;
		Article article = new Article(id, title, body);
		
		articles.add(article);
		articlesLastId = id;
		
		return article;
	}
	
	private Article getArticle(int id) {
		for ( Article article : articles ) {
			if ( article.getId() == id ) {
				return article;
			}
		}
		
		return null;
	}
	
	private void deleteArticle(int id) {
		Article article = getArticle(id);
		
		articles.remove(article);
	}
	
	private void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);
		
		article.setTitle(title);
		article.setBody(body);
	}
	// 서비스 메서드 끝

	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticleAction(int id) {
		Article article = getArticle(id);
		
		if ( article == null) {
			return id + "번 게시물은 존재하지 않습니다.";
		}
		
		return article;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticle(id);
		
		if ( article == null ) {
			return id + "번 게시물이 존재하지 않습니다.";
		}
		
		deleteArticle(id);
		
		return id + "번 게시물을 삭제하였습니다.";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article article = getArticle(id);
		
		if ( article == null ) {
			return id + "번 게시물이 존재하지 않습니다.";
		}
		
		modifyArticle(id, title, body);
		
		return id + "번 게시물을 수정하였습니다.";
	}
	// 액션 메서드 끝
}
