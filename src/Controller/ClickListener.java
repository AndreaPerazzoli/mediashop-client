package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andreaperazzoli on 10/07/17.
 */

public class ClickListener implements EventHandler<ActionEvent>{
    private View view;
    private Stage primaryStage;

    public ClickListener(View view, Stage primaryStage){
        this.view = view;
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent event) {
        Object eventSourceObject = event.getSource();

        // gestione tasto ricerca prodotti
        if(view.searchButton.equals(eventSourceObject)){
            try {
                view.searchIt(view.scrollContainer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // gestione tasto carrello
        else if(view.cartButton.equals(eventSourceObject)){
            view.showCart(primaryStage);
        }

        //gestione hyperlink login
        else if(view.loginLink.equals(eventSourceObject)){
            view.loging(primaryStage);
        }

        // gestione hyperlink sign up
        else if(view.signupLink.equals(eventSourceObject)){
            view.signingUp(primaryStage);
        }

        // gestione tasto pop-up login
        else if(view.loginButton.equals(eventSourceObject)){
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", view.loginPopUpUsernameTextField.getText()));
            params.add(new BasicNameValuePair("password", view.loginPopUpPswField.getText()));
            view.loginRequest(params);
        }

        // Gestione submit button in sign up pop-up
        else if (view.submitRegistationButton.equals(eventSourceObject)){
            System.out.println("fired");
            List<NameValuePair> params = new ArrayList<>();
            if (view.passwordField.getText().equals(view.checkPasswordField.getText())){
                view.passwordCheckResultLabel.setText("Correct password");
                view.passwordCheckResultLabel.setTextFill(Color.GREEN);
                view.passwordCheckResultLabel.setVisible(true);
                params.add(new BasicNameValuePair("username", view.usernameTextField.getText()));
                params.add(new BasicNameValuePair("password", view.passwordField.getText()));
                params.add(new BasicNameValuePair("fiscalCode", view.fiscalCodeTextField.getText()));
                params.add(new BasicNameValuePair("name", view.nameTextField.getText()));
                params.add(new BasicNameValuePair("surname", view.surnameTexField.getText()));
                params.add(new BasicNameValuePair("city", view.cityTextField.getText()));

                if (view.telephoneNumberTextField.getText().equals("")){
                    params.add(new BasicNameValuePair("phone", null));
                }else{
                    params.add(new BasicNameValuePair("phone", view.telephoneNumberTextField.getText()));
                }

                if (view.mobilePhoneNumberTextField.getText().equals("")){
                    params.add(new BasicNameValuePair("mobilePhone", null));
                }else{
                    params.add(new BasicNameValuePair("mobilePhone", view.mobilePhoneNumberTextField.getText()));
                }

                view.sigupRequest(params);
            }else{
                System.out.println("psw errate");
                view.passwordCheckResultLabel.setText("Passwords are differents");
                view.passwordCheckResultLabel.setTextFill(Color.RED);
                view.passwordCheckResultLabel.setVisible(true);
            }
        }

        // gestione hyperlink logout
        else if(view.logoutLink.equals(eventSourceObject)){
            view.loggingOut();
        }
        else {
            System.out.println("Error");
        }




        //TODO: suggested handler


    }


}
