package model;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by andreaperazzoli on 27/05/17.
 */
public class User {
    private String username;
    private String fiscalCode;
    private String name;
    private String surname;
    private String city;
    private String telephoneNumber;
    private String mobileTelephoneNumber;

    public User(){ username = "";}

    /**
     * User constructor
     * @param userInfo
     * */
    public User(Map<String, Object> userInfo){
        username        = (String) userInfo.get("username");
        fiscalCode      = (String) userInfo.get("fiscalCode");
        name            = (String) userInfo.get("name");
        surname         = (String) userInfo.get("surname");
        city            = (String) userInfo.get("city");
        telephoneNumber = (String) userInfo.get("phone");
        mobileTelephoneNumber = (String) userInfo.get("mobilePhone");
    }

    /**
    * Registers a new User
    * @param userInfo a List which represents the user to register
    * @return an User object
    * */
    public static User registerNewUser(List<NameValuePair> userInfo){
        try {
            RestHandler handler = new RestHandler();
            ArrayList<Map<String, Object>> userRequest = handler.postRequest(UrlList.register.toString(), userInfo);

            if (userRequest.get(0).containsKey("username")) {
                // it means that this user is already registered
                System.out.println("Created a new user");
                return new User(userRequest.get(0));
            } else if(userRequest.get(0).containsKey("registered")){
                // create an object User for the new user registered
                System.out.println("User already registered!");
            }else{
                System.out.println("Error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }

    /**
     * Returns the logged user
     * @param  userInfo
     * */
    public static User loginWithUser(List<NameValuePair> userInfo){
        try{
            RestHandler handler = new RestHandler();
            ArrayList<Map<String, Object>> userRequest = handler.postRequest(UrlList.login.toString(), userInfo);

            if(userRequest.isEmpty()){
                // User doesn't exist!
                return new User();
            }else if(userRequest.get(0).containsKey("error")){
                // error in database query
                return null;
            }else{
                // User logged1
                return new User(userRequest.get(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Getter
     * */
    public String getUsername() {
        return username;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCity() {
        return city;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getMobileTelephoneNumber() {
        return mobileTelephoneNumber;
    }

    /*
     * Setter
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setMobileTelephoneNumber(String mobileTelephoneNumber) {
        this.mobileTelephoneNumber = mobileTelephoneNumber;
    }

    /**
     * Returns true if this and obj had the same username
     * @param obj
     * */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return this.username.equals(((User) obj).username);
        }
        return false;
    }
}
