package model;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreaperazzoli on 08/07/17.
 */
public class GetRequestThread extends Thread {
    private String from;
    private Gson handler;
    private ArrayList<Map<String, Object>> result;

    public GetRequestThread(String from){
        this.from = from;
        handler = new Gson();

    }

    public ArrayList<Map<String,Object>> getResult(){
        return result;
    }

    @Override
    public void run() {
        try {
            result = getRequest(from);
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

    private String getJson(String url) throws Exception{
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        DefaultHttpClient client = new DefaultHttpClient(httpParams);
        HttpGet get = new HttpGet(url);



        HttpResponse response = client.execute(get);

        return getJsonfromResponse(response);
    }

    public ArrayList<Map<String, Object>> getRequest(String url) throws Exception{
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        result = (ArrayList<Map<String, Object>>) handler.fromJson(getJson(url), result.getClass());

        return result;
    }


}
