package com.example.hr.client.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.IntStream;

public class HrClientApp {
	private static final String URL = 
	     "http://localhost:5100/hr/api/v1"
		+ "/employees?page=0&size=10";
	public static void main(String[] args) 
			                       throws Exception{
		var client = 
				HttpClient.newHttpClient();
		var request =
    		HttpRequest.newBuilder()
	            .uri(URI.create(URL))
			    .header("Accept", "application/json")
			    .build();
		var start = System.currentTimeMillis();
		for( var  i = 0 ; i< 1_000 ; ++i) {
			var employees = client.send(request, 
					  HttpResponse.BodyHandlers
					                    .ofString()
				    ).body();
			//System.out.println(employees);
		}
		var stop = System.currentTimeMillis();
		System.out.println("Duration : " 
		        + (stop-start)
				+" ms.");
	}
}
