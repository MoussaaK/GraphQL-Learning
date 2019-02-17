package org.konate.telecom3.tutographql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {

		CloseableHttpClient client= null;
		CloseableHttpResponse response= null;

		client= HttpClients.createDefault();
		HttpPost httpPost= new HttpPost("https://api.github.com/graphql");
		
		String token = Utility.getToken("token.txt");
		System.out.println("token : " + token.toString());
		httpPost.addHeader("Authorization", token);
		httpPost.addHeader("Accept","application/json");

		JSONObject GraphQLQuery = new JSONObject(); 
		
		GraphQLQuery.put("query", "query {user(login: \"JosePaumard\") { name repositories(last: 5) { nodes { name description}}}}");
		
		StringEntity entity = null;
		try {
			entity = new StringEntity(GraphQLQuery.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(entity);
		try {
			response = client.execute(httpPost);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			BufferedReader reader= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line= null;
			StringBuilder builder= new StringBuilder();
			while((line=reader.readLine())!= null){

				builder.append(line);
			}
			
			JSONObject responseFromGithub = new JSONObject(builder.toString());
			JSONArray jsonArray = responseFromGithub.getJSONObject("data")
											  .getJSONObject("user")
											  .getJSONObject("repositories")
											  .getJSONArray("nodes");
			
			System.out.println("o " + jsonArray);
			List<Link> links = new ArrayList<Link>();
			/*for (int i = 0; i < jsonArray.length(); i++) {
				links.add(new Link(jsonArray.getJSONObject(i).getString("name"),
								   jsonArray.getJSONObject(i).getString("description")));
			}*/
			System.out.println(links);
			
			String queryString = "{ search(query: \"type:user\", first: 100, type: USER) { userCount pageInfo { endCursor hasNextPage } edges { node { ... on User { login name } } } } }";
			responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());
			
			jsonArray = responseFromGithub.getJSONObject("data")
					.getJSONObject("search")
					.getJSONArray("edges");
			System.out.println("rg" + jsonArray);
			
			/*REQUETEE POUR UPDATE LE CURSOR*/
			List<User> moreUsers = Utility.getMoreUsers(responseFromGithub);
		
			System.out.println("rgn" + moreUsers.size() + "  " + moreUsers);


		} catch (Exception e) {
			
		}
	}
}
