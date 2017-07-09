package view;

import exceptions.BuyingException;
import exceptions.SqlConnectionException;
import exceptions.RegistrationException;
import exceptions.UserNotRegisteredException;
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
import model.Cart;
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
    private User u = null;
    ScrollPane scroll = new ScrollPane();
    Cart cart = new Cart();

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


        ChoiceBox cb_search = new ChoiceBox();
        cb_search.setItems(FXCollections.observableArrayList(
                "all", new Separator(),"by genre","by soloist","by bandname")
        );
        cb_search.getSelectionModel().selectFirst();

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search...");
        searchTextField.setMinWidth(400);

        Image i_search = null;
        try {   i_search = new Image(getClass().getResource("assets/search.png").toURI().toString());   }
        catch(URISyntaxException e){ System.out.println(e);}
        ImageView iv_search = new ImageView();
        iv_search.setPreserveRatio(true);
        iv_search.setImage(i_search);
        iv_search.setPreserveRatio(true);
        iv_search.setSmooth(true);
        iv_search.setFitHeight(20.0);

        Button btn_search = new Button("",iv_search);
        btn_search.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        ArrayList<Product> products = new ArrayList<>();
                        if(cb_search.getValue().equals("by bandname"))
                            try { products = Product.getProductsByBand(searchTextField.getText());
                            } catch (Exception e1) { e1.printStackTrace(); }
                        else if(cb_search.getValue().equals("by soloist"))
                            try { products = Product.getProductsBySoloist(searchTextField.getText());
                            } catch (Exception e1) { e1.printStackTrace(); }
                        else if(cb_search.getValue().equals("by genre"))
                            try { products = Product.getProductsByGenre(searchTextField.getText());
                            } catch (Exception e1) { e1.printStackTrace(); }
                        else if(cb_search.getValue().equals("all"))
                            try { products = Product.searchProductBy(searchTextField.getText());
                            } catch (Exception e1) { e1.printStackTrace(); }

                        displayProducts(products);
                    }
                }
        );




        Hyperlink l_orders = new Hyperlink("MY ORDERS");
        l_orders.setVisible(false);
        Hyperlink l_infos = new Hyperlink("MY INFOS");
        l_infos.setVisible(false);
        Hyperlink l_logout = new Hyperlink("LOG_OUT");
        l_logout.setVisible(false);
//TODO LOGOUT (set user null), MY ORDERS, MY INFOS


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

                                    try {
                                        User newUser = User.registerNewUser(params);
                                    }catch(SqlConnectionException | RegistrationException err){
                                        err.printStackTrace();
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
                        try {
                            u = User.loginWithUser(userInfo);
                            actiontarget.setFill(Color.GREEN);
                            actiontarget.setText("Logged in!");
                            System.out.println(u);
                            l_login.setVisible(false);
                            l_signup.setVisible(false);
                            l_logout.setVisible(true);
                            l_infos.setVisible(true);
                            l_orders.setVisible(true);
                            logInStage.close();
                            primaryStage.show();
                        }catch (SqlConnectionException sqlexc) {
                            sqlexc.printStackTrace();
                            // actiontarget.setFill(Color.FIREBRICK);
                            //actiontarget.setText("Error connecting to db");
                        } catch (UserNotRegisteredException unregistered) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("wrong username/password");
                        }
                    }
                });
                logInStage.setScene(dialogScene);
                logInStage.show();
            }
        });


        l_logout.setVisible(false);
        l_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                u = null;
                l_login.setVisible(true);
                l_signup.setVisible(true);
                l_logout.setVisible(false);
                l_infos.setVisible(false);
                l_orders.setVisible(false);
            }
        });

        topHBox.getChildren().addAll(iv_mediashopLogo,l_signup,l_login,l_orders,l_infos,l_logout);

        HBox centerHBox = new HBox(10.0);
        centerHBox.setAlignment(Pos.BOTTOM_RIGHT);

        HBox.setHgrow(centerHBox, Priority.ALWAYS);

        centerHBox.getChildren().addAll(cb_search,searchTextField,btn_search);

        //CART
        Image i_emptyCart = null;
        try {   i_emptyCart = new Image(getClass().getResource("assets/cart.png").toURI().toString()); }
        catch(URISyntaxException e){ System.out.println(e);}
        ImageView iv_cart = new ImageView();
        iv_cart.setImage(i_emptyCart);
        Button b_cart = new Button("",iv_cart);
        centerHBox.getChildren().addAll(b_cart);

        b_cart.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   Stage cartInStage = new Stage();
                                   cartInStage.setTitle("CART");
                                   cartInStage.initModality(Modality.APPLICATION_MODAL);
                                   cartInStage.initOwner(primaryStage);

                                   VBox v_cart = new VBox();


                                   Label l_payment = new Label("PAYMENT METHOD");
                                   ChoiceBox cb_payment = new ChoiceBox<String>();
                                   cb_payment.setItems(FXCollections.observableArrayList(
                                           "Bankwire","Credit Card","Paypal")
                                   );
                                   cb_payment.getSelectionModel().selectFirst();

                                   v_cart.getChildren().addAll(l_payment,cb_payment,new Separator());

//TODO: CHANGE PRODUCTION CODE
                                   ArrayList<Product> cartProducts = cart.getCartContent();
//                                   try { dummyProducts = Product.getProductsByGenre("Class");
//                                   } catch (Exception e1) { e1.printStackTrace(); }

                                   for(int i=0; i< cartProducts.size(); i++){
                                       HBox h_cart = new HBox(5);
                                       h_cart.setAlignment(Pos.BASELINE_RIGHT);

                                       Product p = cartProducts.get(i);
                                       Label l_name = new Label(p.getTitle());
                                       TextField f_quantity = new TextField(cart.getProductQuantity(p).toString());

                                       Button b_changeQuantity = new Button("Change");
                                       f_quantity.setMinWidth(25);

                                       b_changeQuantity.setOnAction(new EventHandler<ActionEvent>() {
                                                                        @Override
                                                                        public void handle(ActionEvent e) {
                                                                            cart.setProductQuantity(p,Integer.parseInt(f_quantity.getText()));

                                                                        }
                                                                    });


                                       h_cart.getChildren().addAll(l_name, f_quantity, b_changeQuantity);
                                       v_cart.getChildren().add(h_cart);
                                   }



                                   //TODO: rendi invisibile bottone ad utente non autenticato

                                   Button btn_checkout = new Button("CHECKOUT");

                                   if(u == null)
                                       btn_checkout.setVisible(false);
                                   else
                                       btn_checkout.setVisible(true);

                                   btn_checkout.setOnAction(new EventHandler<ActionEvent>() {
                                                                @Override
                                                                public void handle(ActionEvent e) {

                                                                    Stage cartInStage = new Stage();
                                                                    cartInStage.setTitle("CHECKOUT");
                                                                    cartInStage.initModality(Modality.APPLICATION_MODAL);
                                                                    cartInStage.initOwner(primaryStage);

                                                                    VBox v_checkout = new VBox();
                                                                    Label l_checkout = new Label("Almeno uno o più prodotti in quantità insufficiente");
                                                                    try {
                                                                        if (cart.checkoutCart(u, cb_payment.getValue().toString())) {
                                                                            l_checkout.setText("Grazie per l'acquisto!");
                                                                        }
                                                                    }catch (BuyingException bexc){
                                                                        l_checkout.setText("Errore nel db!");
                                                                        bexc.printStackTrace();
                                                                    }
                                                                    v_checkout.getChildren().add(l_checkout);






                                                                    Scene dialog = new Scene(v_checkout);
                                                                    cartInStage.setScene(dialog);
                                                                    cartInStage.setOpacity(0.95);
                                                                    cartInStage.setMinWidth(50);
                                                                    cartInStage.show();

                                                                }
                                                            }
                                   );



                                   v_cart.getChildren().addAll(new Separator(),btn_checkout);

                                   Scene dialog = new Scene(v_cart);
                                   cartInStage.setScene(dialog);
                                   cartInStage.setOpacity(0.95);
                                   cartInStage.setMinWidth(500);
                                   cartInStage.show();

                               }
                           }
        );


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


        ArrayList<Product> allProducts = null;
        try {
            allProducts = Product.getAllProducts();
        } catch (Exception e) {
            allProducts = new ArrayList<>();
        }
        displayProducts(allProducts);



        bottomHBox.getChildren().addAll(scroll);

        vBox.getChildren().addAll(topHBox, centerHBox, bottomHBox);
        scene = new Scene(vBox);
        primaryStage.setTitle("");
        primaryStage.setMinWidth(1200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void displayProducts(ArrayList<Product> allProducts){

        Image i_default = null;
        try {   i_default = new Image(getClass().getResource("assets/cdDefaultCoverImg.png").toURI().toString());  }
        catch(URISyntaxException e){ System.out.println(e);}

        ImageView coverImageView;

        TilePane tile;
        tile = new TilePane();
        tile.setPrefColumns(3);

        int i;
        for(i=0; i< allProducts.size(); i++){

            VBox container = new VBox(8);
            container.setMaxWidth(320);
            container.setMaxHeight(320);
            container.setPadding(new Insets(8,8,8,8));


            String url_cover = allProducts.get(i).getUrl_cover();
            Image i_cover;
            if(url_cover != null)
                i_cover = new Image(url_cover);
            else
                i_cover = i_default;

            coverImageView = new ImageView(i_cover);



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

            //Label genreLabel = new Label(allProducts.get(i).);

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
            Product p = allProducts.get(i);
            addToCartButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            try {
                                // TODO : add parametric textfield of quantity
                                cart.addItem(p,1);
                                
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
            );
            Node[] items = {titleLabel, artistLabel, /*genreLabel,*/ addToCartButton};

            for (Node item: items) {
                infoContainer.getChildren().add(item);
            }

            container.getChildren().add(infoContainer);
        }



        scroll.setFitToHeight(true);
        scroll.setMinWidth(1000);
        scroll.setContent(tile);

    }


    public static void main(String[] args) {
        launch();
    }
}