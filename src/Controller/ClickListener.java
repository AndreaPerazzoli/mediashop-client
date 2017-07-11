package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import view.HomeView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andreaperazzoli on 10/07/17.
 */

public class ClickListener implements EventHandler<ActionEvent>{
    private HomeView homeView;
    private Stage primaryStage;

    public ClickListener(HomeView homeView, Stage primaryStage){
        this.homeView = homeView;
        this.primaryStage = primaryStage;
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
            System.out.println("fired");
            List<NameValuePair> params = new ArrayList<>();
            if (homeView.signUpPopUpPasswordField.getText().equals(homeView.signUpPopUpCheckPasswordField.getText())){
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
            }else{
                System.out.println("psw errate");
                homeView.passwordCheckResultLabel.setText("Passwords are differents");
                homeView.passwordCheckResultLabel.setTextFill(Color.RED);
                homeView.passwordCheckResultLabel.setVisible(true);
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
