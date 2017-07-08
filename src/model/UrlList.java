package model;

/**
 * Created by cristianturetta on 05/07/2017.
 */
public enum UrlList {
    /*
    * List of all possibles methods in our Flask server
    * */
    getAllProducts ("getAllProducts"),
    buyProductById ("buyProductById"),
    getProductById ("getProductById"),
    register ("register"),
    searchProduct("searchProduct"),
    searchProductByPrice("searchProductByPrice"),
    getPurchasedProducts("purchaseHistory"),
    getTrackByProductId("getTrackByProductId"),
    checkoutProducts("buyProductById"),
    getProductByGenre("getProductByGenre"),
    getProductBySoloist("getProductBySoloist"),
    getProductByBand("getProductByBand"),
    getAvailability("getAvailability"),

    login("login");



    private String domain = "http://0.0.0.0:5000/";

    private UrlList(String str){
        domain += str;
    }
    
    public String toString(){
        return domain;
    }
}