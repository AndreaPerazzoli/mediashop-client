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
    getAllProductsPreferredByUsername("getAllProductsPreferredByUsername"),

    login("login");



    private String domain = "http://172.20.10.2:5000/";

    private UrlList(String str){
        domain += str;
    }
    
    public String toString(){
        return domain;
    }
}