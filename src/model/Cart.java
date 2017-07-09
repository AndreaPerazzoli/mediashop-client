package model;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cristianturetta on 07/07/2017.
 */

public class Cart {
    private HashMap<Integer, Product> addedProduct;
    private HashMap<Integer,Integer> quantityToBuy;

    private boolean checkIfAvailability(Product p, Integer quantity){
        RestHandler handler = new RestHandler();
        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("id",p.getId().toString()));

        ArrayList<Map<String,Object>> result = handler.postRequest(UrlList.getAvailability.toString(), params);

        if(((Double)result.get(0).get("quantity")).intValue() < quantity)
            return false;

        return true;

    }

    /**
     * Construct an empty Cart
     * */
    public Cart(){
        addedProduct = new HashMap<Integer, Product>();
        quantityToBuy = new HashMap<>();
    }



    public Cart(Product p, int quantity) throws Exception {
        addedProduct = new HashMap<Integer, Product>();
        quantityToBuy = new HashMap<>();

        addItem(p, quantity);
    }

    /**
     * Removes an item from the Cart
     * @param product
     * */
    public void removeItem(Product product){
        this.addedProduct.remove(product.getId());
        this.quantityToBuy.remove(product.getId());
    }

    /**
     * Add an item to the Cart
     *
     * @param product
     * */
    public void addItem(Product product, int quantity)  {
        if(quantity <= 0) return;
        if(quantityToBuy.containsKey(product.getId())){
            // element already exist in the cart
            quantityToBuy.put(product.getId(), quantityToBuy.get(product.getId()) + quantity);

        }else {
            addedProduct.put(product.getId(), product);
            quantityToBuy.put(product.getId(), quantity);

        }
    }

    /**
     * Erase the Cart
     * */
    public void emptyCart(){
        if (!addedProduct.isEmpty()) {
            addedProduct = new HashMap<>();
            quantityToBuy = new HashMap<>();
        }
    }

    public void setProductQuantity(Product p, Integer quantity){
        if(quantity>0) {
            addedProduct.put(p.getId(), p);
            quantityToBuy.put(p.getId(), quantity);
        }else{
            removeItem(p);
        }
    }

    /**
     * Checkout products in the Cart
     * @param user
     * @param paymentType
     * */
    public boolean checkoutCart(User user, String paymentType) {

        for (Product product: this.getCartContent()) {
            if(!checkIfAvailability(product,quantityToBuy.get(product.getId()))) {
                return false;
            }
        }

       RestHandler handler = new RestHandler();
       for (Product product: this.getCartContent()){
           List<NameValuePair> outgoing = new ArrayList<>();
           outgoing.add(new BasicNameValuePair("id", product.getId().toString()));
           outgoing.add(new BasicNameValuePair("paymentType", paymentType));
           outgoing.add(new BasicNameValuePair("client", user.getUsername()));
           outgoing.add(new BasicNameValuePair("quantity", quantityToBuy.get(product.getId()).toString()));

           handler.postRequest(UrlList.buyProductById.toString(), outgoing);
       }
       emptyCart();
       return true;
    }

    /**
     * Returns the Cart filled with the product selected by the user
     * */
    public ArrayList<Product> getCartContent(){
        ArrayList<Product> result = new ArrayList<>();
        result.addAll(addedProduct.values());
        return result;
    }

    /**
     * Returns the number of products inside the Cart
     * */
    public int getCartSize(){return addedProduct.size();}
<<<<<<< HEAD
=======

    public Integer getProductQuantity(Product p){
        return quantityToBuy.get(p.getId());
    }

>>>>>>> 257834a33089e158234158fcc4ea35f64c0a2a1d
}