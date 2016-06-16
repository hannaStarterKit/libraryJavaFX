/**
 * 
 */
package com.starterkit.library.RESTServiceClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.starterkit.library.booksProvider.data.BookVO;
import com.starterkit.library.booksProvider.data.StatusVO;
import com.starterkit.library.mapper.BookMapper;

/**
 * @author HSIENKIE
 *
 */
public class RestServiceClient {

	public List<BookVO> searchBooks(String title, String authors, StatusVO status) {
		StringBuilder result = new StringBuilder();
		try {
			String urlBasis = "http://localhost:8080/webstore/findBooks?";
			URL url = new URL(urlBasis + "title=" + title + "&author=" + authors + "&status=" + status);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			System.out.println(conn.getURL());
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
		} catch (Exception e) {
			System.out.println("\nError while calling REST Service");
			System.out.println(e);
		}
		return BookMapper.jsonToBookVO(result.toString());
	}
}
