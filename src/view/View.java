package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;
import model.User;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by tglfba on 06/07/17.
 */
public class View extends Application{

    public void start(Stage primaryStage) throws URISyntaxException {

        //HOMEPAGE
        VBox vBox = new VBox(5.0);
        Scene scene;

        HBox topHBox = new HBox(50.0);
        topHBox.setAlignment(Pos.BASELINE_CENTER);

        HBox.setHgrow(topHBox, Priority.ALWAYS);

        Image i_mediashopLogo = null;
        try {   i_mediashopLogo = new Image(getClass().getResource("assets/mediashop-logo.png").toURI().toString());   }
        catch(URISyntaxException e){ System.out.println(e);}
        ImageView iv_mediashopLogo = new ImageView();
        iv_mediashopLogo.setPreserveRatio(true);
        iv_mediashopLogo.setImage(i_mediashopLogo);
        iv_mediashopLogo.setPreserveRatio(true);
        iv_mediashopLogo.setSmooth(true);
        iv_mediashopLogo.setFitHeight(50.0);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search...");

        Hyperlink l_signup = new Hyperlink("SIGN UP");
        l_signup.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage signupStage = new Stage();
                        signupStage.setTitle("SIGN UP");
                        signupStage.initModality(Modality.APPLICATION_MODAL);
                        signupStage.initOwner(primaryStage);

                        // Grid settings
                        VBox v_signup = new VBox(8);
                        v_signup.setPadding(new Insets(8,8,8,8));

                        Label usernameLabel = new Label("Username");
                        TextField usernameTextField = new TextField();
                        usernameTextField.setPromptText("Username");

                        Label passwordLabel = new Label("Password");
                        PasswordField passwordField = new PasswordField();
                        passwordField.setPromptText("Type a password");

                        Label checkPasswordLabel = new Label("Rewrite Password");
                        PasswordField checkPasswordField = new PasswordField();
                        checkPasswordField.setPromptText("Retype the password");

                        Label passwordCheckResultLabel = new Label();
                        passwordCheckResultLabel.setVisible(false);

                        Label fiscalCodeLabel = new Label("Fiscal Code");
                        TextField fiscalCodeTextField = new TextField();

                        fiscalCodeTextField.setPromptText("Type your fiscal code");

                        Label nameLabel = new Label("Name");
                        TextField nameTextField = new TextField();
                        nameTextField.setPromptText("Name");

                        Label surnameLabel = new Label("Surname");
                        TextField surnameTexField = new TextField();
                        surnameTexField.setPromptText("Surname");

                        Label cityLabel = new Label("City");
                        TextField cityTextField = new TextField();
                        cityTextField.setPromptText("Your City");

                        Label telephoneLabel = new Label("Telephone");
                        TextField telephoneNumberTextField = new TextField();
                        telephoneNumberTextField.setPromptText("Your telephone number");

                        Label optionalDescriptionLabel = new Label("Optional mobile phone");

                        TextField mobilePhoneNumberTextField = new TextField();
                        mobilePhoneNumberTextField.setPromptText("Mobile phone number");

                        Button btn_submit = new Button("Submit");
                        HBox wrapper = new HBox();
                        wrapper.getChildren().add(btn_submit);
                        wrapper.setAlignment(Pos.BASELINE_RIGHT);
                        wrapper.setPadding(new Insets(8,0,0,0));

                        Node[] fields = {usernameLabel, usernameTextField, passwordLabel,
                                passwordField, checkPasswordLabel, checkPasswordField,
                                passwordCheckResultLabel, fiscalCodeLabel, fiscalCodeTextField, nameLabel,
                                nameTextField, surnameLabel, surnameTexField,
                                cityLabel, cityTextField, telephoneLabel,
                                telephoneNumberTextField, optionalDescriptionLabel, mobilePhoneNumberTextField, wrapper};

                        for (Node node: fields) {
                            v_signup.getChildren().add(node);
                        }

                        btn_submit.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("Fired");
                                List<NameValuePair> params = new ArrayList<>();

                                if (passwordField.getText().equals(checkPasswordField.getText())){
                                    passwordCheckResultLabel.setText("Correct password");
                                    passwordCheckResultLabel.setTextFill(Color.GREEN);
                                    passwordCheckResultLabel.setVisible(true);
                                    params.add(new BasicNameValuePair("username", usernameTextField.getText()));
                                    params.add(new BasicNameValuePair("password", passwordField.getText()));
                                    params.add(new BasicNameValuePair("fiscalCode", fiscalCodeTextField.getText()));
                                    params.add(new BasicNameValuePair("name", nameTextField.getText()));
                                    params.add(new BasicNameValuePair("surname", surnameTexField.getText()));
                                    params.add(new BasicNameValuePair("city", cityTextField.getText()));

                                    if (telephoneNumberTextField.getText().equals("")){
                                        params.add(new BasicNameValuePair("phone", null));
                                    }else{
                                        params.add(new BasicNameValuePair("phone", telephoneNumberTextField.getText()));
                                    }

                                    if (mobilePhoneNumberTextField.getText().equals("")){
                                        params.add(new BasicNameValuePair("mobilePhone", null));
                                    }else{
                                        params.add(new BasicNameValuePair("mobilePhone", mobilePhoneNumberTextField.getText()));
                                    }

                                    params.add(new BasicNameValuePair("favouriteGenre", null));
                                    User newUser = User.registerNewUser(params);
                                    if (newUser.getUsername() != null){
                                        signupStage.close();
                                    }
                                }else{
                                    System.out.println("psw errate");
                                    passwordCheckResultLabel.setText("Passwords are differents");
                                    passwordCheckResultLabel.setTextFill(Color.RED);
                                    passwordCheckResultLabel.setVisible(true);
                                }
                            }
                        });

                        v_signup.isFillWidth();
                        Scene dialogScene = new Scene(v_signup);

                        signupStage.setScene(dialogScene);
                        signupStage.setOpacity(0.95);
                        signupStage.setMinWidth(500);
                        signupStage.show();

                    }
                }
        );

        Hyperlink l_login = new Hyperlink("LOG IN");

        l_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage logInStage = new Stage();
                logInStage.setTitle("LOG IN");
                logInStage.initModality(Modality.APPLICATION_MODAL);
                logInStage.initOwner(primaryStage);

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));

                TextField userTextField = new TextField();
                userTextField.setPromptText("username");
                grid.add(userTextField, 1, 1);

                PasswordField pwField = new PasswordField();
                pwField.setPromptText("password");
                grid.add(pwField, 1, 2);

                Image image = null;
                try {   image = new Image(getClass().getResource("assets/icon_key_black.png").toURI().toString()); }
                catch(URISyntaxException e1){ System.out.println(e1);}

                ImageView iview = new ImageView();
                iview.setImage(image);
                iview.setPreserveRatio(true);
                iview.setSmooth(true);
                iview.setCache(true);

                Button btn_login = new Button("LOGIN",iview);
                HBox hbBtn = new HBox(10);
                hbBtn.setAlignment(Pos.BASELINE_RIGHT);
                hbBtn.getChildren().add(btn_login);
                grid.add(hbBtn, 1, 3);

                final Text actiontarget = new Text();
                grid.add(actiontarget, 1, 5);

                Scene dialogScene = new Scene(grid, 300, 200);

                btn_login.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        String username = userTextField.getText();
                        String password = pwField.getText();
                        List<NameValuePair> userInfo = new ArrayList<>();
                        userInfo.add(new BasicNameValuePair("username", username));
                        userInfo.add(new BasicNameValuePair("password", password));
                        User u = User.loginWithUser(userInfo);
                        if (u == null) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("Error connecting to db");
                        } else if (u.equals(new User())) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("wrong username/password");
                        } else {
                            actiontarget.setFill(Color.GREEN);
                            actiontarget.setText("Logged in!");
                            l_login.setVisible(false);
                            logInStage.close();
                            primaryStage.show();
                            //TODO: RENDI VISIBILI BOTTONI REGISTRAZIONE
                        }
                    }
                });
                logInStage.setScene(dialogScene);
                logInStage.show();
            }
        });

        topHBox.getChildren().addAll(iv_mediashopLogo,l_signup,l_login);

        HBox centerHBox = new HBox(10.0);
        centerHBox.setAlignment(Pos.BOTTOM_RIGHT);

        HBox.setHgrow(centerHBox, Priority.ALWAYS);

        centerHBox.getChildren().addAll(searchTextField);

        //CART
        Image i_emptyCart = null;
        try {   i_emptyCart = new Image(getClass().getResource("assets/cart.png").toURI().toString()); }
        catch(URISyntaxException e){ System.out.println(e);}
        ImageView iv_cart = new ImageView();
        iv_cart.setImage(i_emptyCart);
        Button b_cart = new Button(new Integer(5).toString(5),iv_cart);
        centerHBox.getChildren().addAll(b_cart);

        HBox bottomHBox = new HBox(10.0);

        VBox leftVBox = new VBox(5.0);
        leftVBox.setPadding(new Insets(8,8,8,8));
        leftVBox.setMinWidth(200);

        CheckBox cb_CD = new CheckBox("CD");
        cb_CD.setIndeterminate(false);
        leftVBox.getChildren().addAll(cb_CD);
        CheckBox cb_DVD = new CheckBox("DVD");
        cb_DVD.setIndeterminate(false);
        leftVBox.getChildren().addAll(cb_DVD);

        ChoiceBox cb_genres = new ChoiceBox();
        cb_genres.setItems(FXCollections.observableArrayList(
                "Punk Rock", "Classical",
                new Separator(), "Ambient", "Jazz")
        );
        //http://docs.oracle.com/javafx/2/ui_controls/choice-box.htm
        leftVBox.getChildren().addAll(cb_genres);

        Slider s_min = new Slider(0, 40, 0.5);
        s_min.setShowTickMarks(true);
        s_min.setShowTickLabels(true);
        s_min.setMajorTickUnit(10.0f);
        s_min.setBlockIncrement(5.0f);
        leftVBox.getChildren().addAll(s_min);

        Slider s_max = new Slider(0, 40, 0.5);
        s_max.setShowTickMarks(true);
        s_max.setShowTickLabels(true);
        s_max.setMajorTickUnit(10.0f);
        s_max.setBlockIncrement(5.0f);
        leftVBox.getChildren().addAll(s_max);

        bottomHBox.getChildren().addAll(leftVBox);

        Image i_default = null;
        try {   i_default = new Image(getClass().getResource("assets/cdDefaultCoverImg.png").toURI().toString());  }
        catch(URISyntaxException e){ System.out.println(e);}

        TilePane tile = new TilePane();
        tile.setPrefColumns(3);

        ArrayList<Product> allProducts = null;
        try {
            allProducts = Product.getAllProducts();
        } catch (Exception e) {
            allProducts = new ArrayList<>();
        }

        for(int i=0; i< allProducts.size(); i++){

            VBox container = new VBox(8);
            container.setMaxWidth(320);
            container.setMaxHeight(320);
            container.setPadding(new Insets(8,8,8,8));

            ImageView coverImageView = new ImageView(i_default);
            coverImageView.setFitWidth(310);
            coverImageView.setFitHeight(310);

            container.getChildren().add(coverImageView);
            tile.getChildren().add(container);

            VBox infoContainer = new VBox(8);
            infoContainer.setAlignment(Pos.CENTER);
            Label titleLabel = new Label(allProducts.get(i).getTitle());
            Label artistLabel = new Label();

            if (allProducts.get(i).getSoloist().getStageName() == null){
                artistLabel.setText(allProducts.get(i).getBandName());
            }else{
                artistLabel.setText(allProducts.get(i).getSoloist().getStageName());
            }

            Label genreLabel = new Label(allProducts.get(i).getMain_genre());

            Image plus = null;
            try {
                plus  = new Image(getClass().getResource("assets/plus.png").toURI().toString());
            }
            catch(Exception e){
                System.out.println(e);
            }

            ImageView plusImageView = new ImageView();
            plusImageView.setImage(plus);
            plusImageView.setPreserveRatio(true);
            plusImageView.setFitHeight(16);
            plusImageView.setFitWidth(16);
            plusImageView.setSmooth(true);
            plusImageView.setCache(true);

            Button addToCartButton = new Button(allProducts.get(i).getPrice().toString() + "€",
                    plusImageView);

            addToCartButton.setTextAlignment(TextAlignment.LEFT);

            Node[] items = {titleLabel, artistLabel, genreLabel, addToCartButton};

            for (Node item: items) {
                infoContainer.getChildren().add(item);
            }

            container.getChildren().add(infoContainer);
        }

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setMinWidth(1000);
        scroll.setContent(tile);

        bottomHBox.getChildren().addAll(scroll);

        vBox.getChildren().addAll(topHBox, centerHBox, bottomHBox);
        scene = new Scene(vBox);
        primaryStage.setTitle("");
        primaryStage.setMinWidth(1200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}