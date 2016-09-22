package com.demo.blog;

import com.demo.common.model.Blog;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;


/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	public void index() {
		getResponse().addHeader("Access-Control-Allow-Origin", "*");
		setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 15));
		render("blog.html");
//		renderJson(Blog.me.paginate(getParaToInt(0, 1), 10));
//		String want = getPara("to");
//		if (want.equals("json")){
//			renderJson(Blog.me.paginate(getParaToInt(0, 1), 10));
//		}
	}
	public void json(){
		getResponse().addHeader("Access-Control-Allow-Origin", "*");
		renderJson(Blog.me.paginate(getParaToInt(0, 1), 15));
		
	}
	
	public void add() {
	}
	
	@Before(BlogValidator.class)
	public void save() {
		getModel(Blog.class).save();
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", Blog.me.findById(getParaToInt()));
	}
	
	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		Blog.me.deleteById(getParaToInt());
		redirect("/blog/");
	}
}


