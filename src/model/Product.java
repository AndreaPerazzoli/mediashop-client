package model;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by andreaperazzoli on 27/05/17.
 */
public class Product {
    private Integer id;
    private String title;
    private BigDecimal price;
    private Date storedDate;
    private String main_genre;
    private Integer quantity;

    private String description;
    private String type;
    private Soloist soloist;
    private String bandName;
    private String url_cover;
    private ArrayList<Track> tracks;

    public Product(){
        title = "";
    }

    public Product(Map<String,Object> productInfo) throws Exception{
        this.id = ((Double)productInfo.get("id")).intValue();
        this.title = (String) productInfo.get("title");
        this.price = new BigDecimal(productInfo.get("price").toString());
        this.storedDate = (Date) productInfo.get("storedDate");
        this.main_genre = (String) productInfo.get("main_genre");
        this.quantity = ((Double)productInfo.get("quantity")).intValue();
        this.description = (String) productInfo.get("description");
        this.type = (String) productInfo.get("type");
        this.soloist = new Soloist(productInfo);
        this.url_cover = (String) productInfo.get("url_cover");
        this.bandName = (String) productInfo.get("bandname");
        this.tracks = Track.getTracksBy(id);
    }



    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getStoreddDate() {
        return storedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getType_product() {
        return type;
    }

    public Soloist getSoloist() {
        return soloist;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getBandName() {
        return bandName;
    }


    public String getType() {
        return type;
    }

    public String getUrl_cover() {
        return url_cover;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public static ArrayList<Product> searchProductBy(Float minPrice, Float maxPrice) throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();

        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("minPrice", minPrice.toString()));
        queryParams.add(new BasicNameValuePair("maxPrice", maxPrice.toString()));

        for(Map<String, Object> productMap : handler.postRequest(UrlList.searchProduct.toString(), queryParams)){
            productsList.add(new Product(productMap));
        }

        return productsList;

    }

    public static ArrayList<Product> getSuggestedProducts(String username) throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();

        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("username", username));

        ArrayList<Map<String,Object>> queryResult = handler.postRequest(UrlList.getAllProductsPreferredByUsername.toString(), queryParams);
        if (queryResult.get(0).containsKey("preferred")){
            return new ArrayList<Product>();
        }

        for(Map<String, Object> productMap : queryResult){
            productsList.add(new Product(productMap));
        }

        return productsList;

    }

    public static ArrayList<Product> searchProductBy(String subject) throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();

        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("subject", subject));


        for(Map<String, Object> productMap : handler.postRequest(UrlList.searchProduct.toString(), queryParams)){
            productsList.add(new Product(productMap));
        }

        return productsList;

    }

    public static Product searchProductBy(int id) throws Exception{
        RestHandler handler = new RestHandler();
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("id", String.valueOf(id) ));
        return new Product(handler.postRequest(UrlList.getProductById.toString(), queryParams).get(0));
    }

    public static ArrayList<Product> getAllProducts() throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();


        for(Map<String, Object> productMap : handler.getRequest(UrlList.getAllProducts.toString())){
                productsList.add(new Product(productMap));
        }

        return productsList;
    }

    public static ArrayList<Product> getProductsByGenre(String genre) throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("genre", genre));


        for(Map<String, Object> productMap : handler.postRequest(UrlList.getProductByGenre.toString(),params)){
            productsList.add(new Product(productMap));
        }

        return productsList;
    }

    public static ArrayList<Product> getProductsBySoloist(String soloist) throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("soloist", soloist));


        for(Map<String, Object> productMap : handler.postRequest(UrlList.getProductBySoloist.toString(),params)){
            productsList.add(new Product(productMap));
        }

        return productsList;
    }

    public static ArrayList<Product> getProductsByBand(String bandName) throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("bandName", bandName));


        for(Map<String, Object> productMap : handler.postRequest(UrlList.getProductByBand.toString(),params)){
            productsList.add(new Product(productMap));
        }

        return productsList;
    }

    public static ArrayList<Product> getProductsBoughtBy(String username) throws Exception{
        RestHandler handler = new RestHandler();
        ArrayList<Product> productsList = new ArrayList<>();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));


        for(Map<String, Object> productMap : handler.postRequest(UrlList.getPurchasedProducts.toString(),params)){
            productsList.add(new Product(productMap));
        }

        return productsList;
    }


    @Override
    public String toString() {
        return "{" + id + ", " + title + ", " + price + ", " + storedDate + ", " + description + ", " + type + ", " + soloist + ", " + bandName + "}";
    }



    @Override
    public int hashCode() {
        return id.hashCode();
    }
}