package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.spring.annotation.UIScope;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@Route(Login.LOGIN)
public class Login extends VerticalLayout{

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    public final static String LOGIN = "login";

    private TextField userName = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private User user = new User();
    NativeButton loginButton = new NativeButton("Login");
    private boolean isAdmin = false;
    Notification notification = new Notification(
            "Successful login!", 2000, Notification.Position.TOP_CENTER);
    Notification notification2 = new Notification(
            "Login failed!", 3000, Notification.Position.TOP_CENTER);

    VaadinSession vaadinSession = UI.getCurrent().getSession();
    WrappedSession wrappedSession;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Login() {

    }

    @PostConstruct
    public void init()
    {
        wrappedSession = vaadinSession.getSession();

        loginButton.addClickListener(e -> isRegistered());

        //productsButton.setVisible(false);

        add(userName, password, loginButton);
    }

    private void isRegistered() {
        user = userService.findByUserName(userName.getValue());

        if ("admin".equals(userName.getValue()) && "admin".equals(password.getValue())) {
            isAdmin = true;

            wrappedSession.setAttribute("username", userName.getValue());

            loginButton.getUI().ifPresent(ui -> ui.navigate("admin"));
//            productsButton.addClickListener(event -> {
//                productsButton.getUI().ifPresent(ui -> ui.navigate("admin"));
//            });
        } else if (user != null) {
            if (user.getUserName().equals(userName.getValue()) && passwordEncoder.matches(password.getValue(), user.getPassword())) {
                wrappedSession.setAttribute("username", userName.getValue());

                notification.open();

                loginButton.getUI().ifPresent(ui -> ui.navigate("products"));
            } else {
                notification2.open();
            }
        } else {
            notification2.open();
        }
    }
}
