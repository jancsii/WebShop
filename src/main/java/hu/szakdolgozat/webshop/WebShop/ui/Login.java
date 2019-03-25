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
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@Route(Login.LOGIN)
public class Login extends VerticalLayout{

    @Autowired
    UserService userService;

    public final static String LOGIN = "login";

    private TextField userName = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private User user = new User();
    NativeButton productsButton = new NativeButton("Products =>");
    NativeButton loginButton = new NativeButton("Login");
    private boolean isAdmin = false;
    Notification notification = new Notification(
            "Successful login!", 5000, Notification.Position.TOP_CENTER);
    Notification notification2 = new Notification(
            "Login failed!", 5000, Notification.Position.TOP_CENTER);

    //HttpServlet request = new HttpServlet() {};
    //HttpServlet response = new HttpServlet() {};
    //HttpSession session = request.getSession();
      VaadinSession session;
//    VaadinRequest request;
//    VaadinResponse response;

    public Login() {

    }



    @PostConstruct
    public void init()
    {

        List<User> users = new ArrayList<>();
        users=(List)userService.getAllUsers();

        System.out.println(users);

        loginButton.addClickListener(e -> isRegistered());

        productsButton.setVisible(false);

        add(userName, password, loginButton, productsButton);
    }

    private void isRegistered() {
        System.out.println("Here");

        System.out.println("Session: " + UI.getCurrent().getSession());

        user = userService.findByUserName(userName.getValue());

        if ("admin".equals(userName.getValue()) && "admin".equals(password.getValue())) {
            isAdmin = true;
            productsButton.setVisible(true);

            productsButton.addClickListener(event -> {
                productsButton.getUI().ifPresent(ui -> ui.navigate("admin"));
            });
        } else if (user != null) {
            if (user.getUserName().equals(userName.getValue()) && user.getPassword().equals(password.getValue())) {
                System.out.println("Nice");
                System.out.println(user.getFirstName() + user.getLastName() + user.getUserName() + user.getPassword());
                System.out.println(userName.getValue() + password.getValue());
                UI.getCurrent().getSession().setAttribute("username", userName.getValue());
                //session.setAttribute("password", password.getValue());
                System.out.println("Session attribute: " + UI.getCurrent().getSession().getAttribute("username"));
                productsButton.setVisible(true);

                notification.open();

                productsButton.addClickListener(event -> {
                    productsButton.getUI().ifPresent(ui -> ui.navigate("products"));
                });
                //String sessionID = ((VaadinServletRequest) VaadinService.getCurrentRequest())
                //.getHttpServletRequest().getSession().getId();
            }
        } else {
            System.out.println("Nope");

            notification2.open();
        }
    }
}
