package model;
/**
 * Created by cristianturetta on 04/07/2017.
 */
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class RestHandler {
//    private Gson handler;
//
//    public RestHandler(){
//        handler = new Gson();
//    }
//
//    private String getJsonfromResponse(HttpResponse response) throws Exception{
//        StringBuffer buffer = new StringBuffer();
//        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))){
//            String line = "";
//            while ((line = bufferedReader.readLine()) != null) {
//                buffer.append(line + "\n");
//            }
//        }
//
//        return buffer.toString();
//
//    }
//
//    private String getJson(String url) throws Exception{
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpGet get = new HttpGet(url);
//
//        HttpResponse response = client.execute(get);
//
//        return getJsonfromResponse(response);
//    }
//
//
//
//    public ArrayList<Map<String, Object>> getRequest(String url) throws Exception{
//        ArrayList<Map<String, Object>> result = new ArrayList<>();
//        result = (ArrayList<Map<String, Object>>) handler.fromJson(getJson(url), result.getClass());
//
//        return result;
//    }
//
//    private ArrayList<Map<String, Object>> getRequest(HttpResponse response) throws Exception{
//        ArrayList<Map<String, Object>> result = new ArrayList<>();
//        result = (ArrayList<Map<String, Object>>) handler.fromJson(getJsonfromResponse(response), result.getClass());
//
//        return result;
//    }
//
//    public ArrayList<Map<String, Object>> postRequest(String url, List<NameValuePair> params) throws Exception {
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost(url);
//        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//        //Execute and get response
//        HttpResponse response = client.execute(post);
//
//        return getRequest(response);
//    }

    public ArrayList<Map<String, Object>> getRequest(String url){
        GetRequestThread get = new GetRequestThread(url);
        get.start();
        try {
            get.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return get.getResult();

    }


    public ArrayList<Map<String, Object>> postRequest(String url, List<NameValuePair> params)  {
        PostRequestThread post = new PostRequestThread(url,params);
        post.start();
        try {
            post.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return post.getResult();


    }

    public static void main(String[] args) throws Exception {
        RestHandler handler = new RestHandler();

        List<NameValuePair> l = new ArrayList<>();

        //l.add(new BasicNameValuePair("idProduct", "1"));

        l.add(new BasicNameValuePair("username", "PsychoAndreaFroci"));
        l.add(new BasicNameValuePair("password", "ebreobruciacchiato"));
        l.add(new BasicNameValuePair("city", "Lamecca"));
        l.add(new BasicNameValuePair("fiscalCode", "vbqertyuiopasdfg"));
        l.add(new BasicNameValuePair("name", "Andrea"));
        l.add(new BasicNameValuePair("surname", "Perazzoli"));
        l.add(new BasicNameValuePair("phone", "+38423762347823"));
        l.add(new BasicNameValuePair("mobilePhone", "+434273462784"));
        l.add(new BasicNameValuePair("favouriteGenre", "Rap"));


        /*
        Scanner scnanner = new Scanner(System.in);
        System.out.println("search: ");
        String find = scnanner.nextLine();
        l.add(new BasicNameValuePair("title", find));

        System.out.println(handler.postRequest(UrlList.searchProduct.toString(), l));
    */

    }
}
