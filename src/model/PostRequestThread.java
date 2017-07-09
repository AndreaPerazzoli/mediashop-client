package model;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by andreaperazzoli on 08/07/17.
 */
public class PostRequestThread extends Thread {
    private ArrayList<Map<String, Object>> result;
    private String url;
    private List<NameValuePair> params;
    private Gson handler;




    public PostRequestThread(String url, List<NameValuePair> params){
        this.url = url;
        this.params=params;
        handler = new Gson();

    }

    public ArrayList<Map<String,Object>> getResult(){
        return result;
    }

    @Override
    public void run() {
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        DefaultHttpClient client = new DefaultHttpClient(httpParams);
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Execute and get response
        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            result = getRequest(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getJsonfromResponse(HttpResponse response) throws Exception{
        StringBuffer buffer = new StringBuffer();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))){
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        }

        return buffer.toString();

    }

    private ArrayList<Map<String, Object>> getRequest(HttpResponse response) throws Exception{
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        result = (ArrayList<Map<String, Object>>) handler.fromJson(getJsonfromResponse(response), result.getClass());

        return result;
    }

}
