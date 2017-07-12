package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import view.HomeView;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by andreaperazzoli on 10/07/17.
 */

public class ClickListener implements EventHandler<ActionEvent>{
    private HomeView homeView;
    private Stage primaryStage;
    private Matcher matcher;
    private Pattern pattern;

    public ClickListener(HomeView homeView, Stage primaryStage){
        this.homeView = homeView;
        this.primaryStage = primaryStage;
    }

    private boolean checkFiscalCode(String cf){
        String fiscalCodeRegex = "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";
        pattern = Pattern.compile(fiscalCodeRegex);
        matcher = pattern.matcher(cf);
        if (matcher.find()){
            homeView.fiscalCodeTextField.setStyle(null);
            return true;
        }
        homeView.fiscalCodeTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        return false;
    }

    private  boolean checkTelephoneNumber(String number){
        String telephoneRegex = "^\\+([0-9]?){9,11}[0-9]$";
        pattern = Pattern.compile(telephoneRegex);
        if (homeView.mobilePhoneNumberTextField.getText().equals("")){
            matcher = pattern.matcher(number);
            if (matcher.find()){
                homeView.telephoneNumberTextField.setStyle(null);
                return true;
            }
            homeView.telephoneNumberTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return false;
        }
        else {
            matcher = pattern.matcher(number);
            if (matcher.find()){
                homeView.telephoneNumberTextField.setStyle(null);
            } else{
                homeView.telephoneNumberTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
            matcher = pattern.matcher(homeView.mobilePhoneNumberTextField.getText());
            if (matcher.find()){
                homeView.mobilePhoneNumberTextField.setStyle(null);
                return true;
            }else {
                homeView.mobilePhoneNumberTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
            return false;
        }
    }

    private  boolean checkPassword(String psw, String retypedPsw){
        if (psw.equals(retypedPsw) && psw.length() >= 8 && psw.length() <= 20){
            homeView.passwordCheckResultLabel.setVisible(false);
            return  true;
        }
        homeView.passwordCheckResultLabel.setText("Passwords are differents");
        homeView.passwordCheckResultLabel.setTextFill(Color.RED);
        homeView.passwordCheckResultLabel.setVisible(true);
        return false;
    }

    private boolean checkNeededParams(TextField[] params){
        int count = 0;
        for(TextField param : params){
            if(param.getText().equals("")){
                param.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                count++;
            }else{
                param.setStyle(null);
            }
        }
        if (count == 0){
            return true;
        }
        return false;
    }

    @Override
    public void handle(ActionEvent event) {
        Object eventSourceObject = event.getSource();

        // gestione tasto ricerca prodotti
        if(homeView.searchButton.equals(eventSourceObject)){
            try {
                homeView.searchIt(homeView.scrollContainer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // gestione tasto carrello
        else if(homeView.cartButton.equals(eventSourceObject)){
            homeView.showCart(primaryStage);
        }

        //gestione hyperlink login
        else if(homeView.loginLink.equals(eventSourceObject)){
            homeView.loging(primaryStage);
        }
        // gestione hyperlink sign up
        else if(homeView.signupLink.equals(eventSourceObject)){
            homeView.signingUp(primaryStage);
        }

        // Gestione submit button in sign up pop-up
        else if (homeView.submitRegistationButton.equals(eventSourceObject)){
            List<NameValuePair> params = new ArrayList<>();
            checkFiscalCode(homeView.fiscalCodeTextField.getText());
            checkTelephoneNumber(homeView.telephoneNumberTextField.getText());
            checkPassword(homeView.signUpPopUpPasswordField.getText(), homeView.signUpPopUpCheckPasswordField.getText());

            TextField[] allNeededFields = {homeView.usernameTextField, homeView.nameTextField, homeView.surnameTexField, homeView.cityTextField};

            if (checkNeededParams(allNeededFields) && checkFiscalCode(homeView.fiscalCodeTextField.getText()) &&
                    checkTelephoneNumber(homeView.telephoneNumberTextField.getText()) &&
                    checkPassword(homeView.signUpPopUpPasswordField.getText(), homeView.signUpPopUpCheckPasswordField.getText())){
                homeView.passwordCheckResultLabel.setText("Correct password");
                homeView.passwordCheckResultLabel.setTextFill(Color.GREEN);
                homeView.passwordCheckResultLabel.setVisible(true);
                params.add(new BasicNameValuePair("username", homeView.usernameTextField.getText()));
                params.add(new BasicNameValuePair("password", homeView.signUpPopUpPasswordField.getText()));
                params.add(new BasicNameValuePair("fiscalCode", homeView.fiscalCodeTextField.getText()));
                params.add(new BasicNameValuePair("name", homeView.nameTextField.getText()));
                params.add(new BasicNameValuePair("surname", homeView.surnameTexField.getText()));
                params.add(new BasicNameValuePair("city", homeView.cityTextField.getText()));

                if (homeView.telephoneNumberTextField.getText().equals("")){
                    params.add(new BasicNameValuePair("phone", null));
                }else{
                    params.add(new BasicNameValuePair("phone", homeView.telephoneNumberTextField.getText()));
                }

                if (homeView.mobilePhoneNumberTextField.getText().equals("")){
                    params.add(new BasicNameValuePair("mobilePhone", null));
                }else{
                    params.add(new BasicNameValuePair("mobilePhone", homeView.mobilePhoneNumberTextField.getText()));
                }

                homeView.sigupRequest(params);
            }
        }
        // gestione hyperlink logout
        else if(homeView.logoutLink.equals(eventSourceObject)){
            homeView.loggingOut();
        }
        else if(homeView.suggestedForMe.equals(eventSourceObject)){
            try {
                homeView.displaySuggestedProducts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // gestione tasto pop-up login
        else if(homeView.loginPopUpButton.equals(eventSourceObject)){
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", homeView.loginPopUpUsernameTextField.getText()));
            params.add(new BasicNameValuePair("password", homeView.loginPopUpPswField.getText()));
            homeView.loginRequest(params);
        }
        else {
            System.out.println("Error");
        }




        //TODO: suggested handler


    }


}
