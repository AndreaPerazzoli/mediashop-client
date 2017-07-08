package model;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cristianturetta on 07/07/2017.
 */

public class Cart {
    private HashMap<Integer, Product> addedProduct;

    /**
     * Construct an empty Cart
     * */
    public Cart(){
        addedProduct = new HashMap<Integer, Product>();
    }

    /**
     * Construct a Cart with selected products
     * @param selectedProducts
     * */
    public Cart( HashMap<Integer, Product> selectedProducts){
        addedProduct = selectedProducts;
    }

    /**
     * Removes an item from the Cart
     * @param id
     * */
    public void removeItem(Integer id){
        this.addedProduct.remove(id);
    }

    /**
     * Add an item to the Cart
     * @param id
     * @param product
     * */
    public void addItem(Integer id, Product product){
        this.addedProduct.put(id, product);
    }

    /**
     * Erase the Cart
     * */
    public void emptyCart(){
        if (addedProduct.isEmpty())
            addedProduct = new  HashMap<Integer, Product>();
    }

    /**
     * Checkout products in the Cart
     * @param user
     * @param paymentType
     * */
    public void checkoutCart(User user, String paymentType) throws Exception {
       RestHandler handler = new RestHandler();
       for (Product product: this.getCartContent()){
           List<NameValuePair> outgoing = new ArrayList<>();
           outgoing.add(new BasicNameValuePair("id", product.getId().toString()));
           outgoing.add(new BasicNameValuePair("paymentType", paymentType));
           outgoing.add(new BasicNameValuePair("client", user.getUsername().toString()));
           handler.postRequest(UrlList.buyProductById.toString(), outgoing);
       }
    }

    /**
     * Returns the Cart filled with the product selected by the user
     * */
    public ArrayList<Product> getCartContent(){
        return (ArrayList<Product>)addedProduct.values();
    }

    /**
     * Returns the number of products inside the Cart
     * */
    public int size(){
        return this.addedProduct.size();
    }
}