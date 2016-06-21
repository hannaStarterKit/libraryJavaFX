package com.starterkit.library.mapper;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.starterkit.library.booksProvider.data.BookTo;
import com.starterkit.library.booksProvider.data.BookStatus;

import org.json.simple.parser.JSONParser;

public class BookMapper {

	final static JSONParser parser = new JSONParser();
	
	public static List<BookTo> jsonToBookTo(String text){
		Object object;
		List<BookTo> booksVO = new ArrayList<>();
		try {
			object = parser.parse(text);
			JSONArray array = (JSONArray)object;
			for (int i = 0; i < array.size(); i++) {
				JSONObject bookJSON = (JSONObject)array.get(i);
				BookTo book = new BookTo((Long) bookJSON.get("id"), (String) bookJSON.get("title"),(String) bookJSON.get("authors"), BookStatus.valueOf((String) bookJSON.get("status")));
				booksVO.add(book);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		return booksVO;
	}
}
