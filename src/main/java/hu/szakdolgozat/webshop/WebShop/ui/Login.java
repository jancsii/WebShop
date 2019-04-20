package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import hu.szakdolgozat.webshop.WebShop.Names;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@Route(Names.LOGIN)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@UIScope
public class Login extends VerticalLayout{

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    private TextField userName = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private User user = new User();
    Button loginButton = new Button("Login");
    private boolean isAdmin = false;
    private Label noAccountLabel = new Label("Not registered?");
    Button registerButton = new Button("Create account");
    Button productsButton = new Button("Go to products");
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    Notification notification = new Notification(
            "Successful login!", 2000, Notification.Position.TOP_CENTER);
    Notification notification2 = new Notification(
            "Login failed!", 3000, Notification.Position.TOP_CENTER);

    VaadinSession vaadinSession = UI.getCurrent().getSession();
    WrappedSession wrappedSession;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Login() {}

    @PostConstruct
    public void init()
    {
        userName.focus();
        wrappedSession = vaadinSession.getSession();

        loginButton.addClickListener(e -> isRegistered());

        registerButton.addClickListener(e->
            registerButton.getUI().ifPresent(ui -> ui.navigate(Names.REGISTRATION)));

        productsButton.addClickListener(e -> productsButton.getUI().ifPresent(ui -> ui.navigate(Names.PRODUCTS)));

        style();

        horizontalLayout.add(noAccountLabel, registerButton);
        add(userName, password, loginButton, horizontalLayout, productsButton);
    }

    private void isRegistered() {
        user = userService.findByUserName(userName.getValue());

        if (Names.ADMIN.equals(userName.getValue()) && Names.ADMIN.equals(password.getValue())) {
            isAdmin = true;

            wrappedSession.setAttribute(Names.USERNAME, userName.getValue());

            loginButton.getUI().ifPresent(ui -> ui.navigate(Names.ADMIN));
//            productsButton.addClickListener(event -> {
//                productsButton.getUI().ifPresent(ui -> ui.navigate("admin"));
//            });
        } else if (user != null) {
            if (user.getUserName().equals(userName.getValue()) && passwordEncoder.matches(password.getValue(), user.getPassword())) {
                wrappedSession.setAttribute(Names.USERNAME, userName.getValue());

                notification.open();

                loginButton.getUI().ifPresent(ui -> ui.navigate(Names.PRODUCTS));
            } else {
                notification2.open();
            }
        } else {
            notification2.open();
        }
    }

    public void style() {
        loginButton.getElement().setAttribute("theme", "primary");
        loginButton.getStyle().set("margin", "40px 0px 0px 143px");
        registerButton.getElement().setAttribute("theme", "secondary");
        userName.getStyle().set("margin-top", "60px");
        horizontalLayout.getStyle().set("margin-top", "50px");
        noAccountLabel.getStyle().set("margin-top", "8px");
        productsButton.getStyle().set("margin", "10px 0px 0px 125px");
        productsButton.getStyle().set("theme", "primary");
        this.getStyle().set("width", "300px");
        this.getStyle().set("margin", "0 auto");
    }
}
