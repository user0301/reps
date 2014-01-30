package com.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class NewsController  {

	
	public static final String resourceFileName="news";
	@RequestMapping("/*/news")
	public void getNews(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String s=null;
		try{
		String url=Utils.getValue(resourceFileName, "newsfeedurl");
		//String count = request.getParameter("count");
		int itemsCount = (request.getParameter("count")==null)?10:Integer.parseInt(request.getParameter("count"));
		int offset = (request.getParameter("offset")==null)?0:Integer.parseInt(request.getParameter("offset"));
		String cat = request.getParameter("cat");
		System.out.println("itemsCount:"+itemsCount+" offset:"+offset +" cat:"+cat);
		s=Utils.getResponseFromURL(url, null, 0, 6, 6);
		Feed f=Utils.readObject(Feed.class, new ByteArrayInputStream(s.getBytes()));
		s=Utils.objectToJSON(f);
		Collections.sort(f.getN().getListOfItems(), new DateComparator());
		//s=Utils.objectToJSON(f);
		List newsItemsList = f.getN().getListOfItems();
		Feed result=null;
		ArrayList<Item> iList=new ArrayList<Item>();
		if(cat==null){
			System.out.println("no category");
			while(offset<newsItemsList.size()&& itemsCount>0){
				Item i = (Item) newsItemsList.get(offset);
				iList.add(i);
				itemsCount--;
				offset++;
				System.out.println("itemsCount:"+ itemsCount +" offset:"+offset);
			}
		}else{
			
		}
		News news=new News(iList);
		result=new Feed(news);
		s=Utils.objectToJSON(result);
		}catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
			s="Error";
		}
		response.setContentType("text/json");
		response.getOutputStream().write(s.getBytes());
	}
	
	@RequestMapping("/*/news/{id}")
	public void getNewsById(HttpServletRequest request, HttpServletResponse response,  @PathVariable String id) throws IOException{
		
		String s=null;
		try{
		String url=Utils.getValue(resourceFileName, "newsfeedurl");
		s=Utils.getResponseFromURL(url, null, 0, 6, 6);
		Feed f=Utils.readObject(Feed.class, new ByteArrayInputStream(s.getBytes()));
		Feed result=null;
		for(Item i:f.getN().getListOfItems()){
			if(i.getId().equals(id)){
				ArrayList<Item> iList=new ArrayList<Item>();
				iList.add(i);
				News news=new News(iList);
				result=new Feed(news);
			}
		}
		s=Utils.objectToJSON(result);
		//System.out.println(n.toString());
		
		}catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
			s="Error";
		}
		response.setContentType("text/json");
		response.getOutputStream().write(s.getBytes());
	}
	
}
