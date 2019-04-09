package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hu.szakdolgozat.webshop.WebShop.ApplicationContextHolder;
import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

@Route("purchase")
public class Purchase extends VerticalLayout{


    CartService cartService = ApplicationContextHolder.getContext().getBean(CartService.class);

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    private Label label = new Label("Heyyy");
    private ArrayList<String> container = new ArrayList<>();
    private Board containerBoard = new Board();
    private VerticalLayout verticalLayout = new VerticalLayout();
    private ArrayList<Cart> cartContent = new ArrayList<>();
    private Label counter = new Label("0");
    int count = 0;
    VaadinSession session = UI.getCurrent().getSession();
    WrappedSession wrappedSession;

    public Purchase() {
    }

    @PostConstruct
    public void init()
    {
        wrappedSession = session.getSession();

        if(wrappedSession.getAttribute("username") != null) {
            menu();
            listPurchasedElements();
        } else {
            UI.getCurrent().navigate("");
            UI.getCurrent().getPage().executeJavaScript("location.reload();");
        }

        counterInc();
    }

    public void counterInc()
    {
        counter.setText(String.valueOf(count));
    }

    public void listPurchasedElements() {

        User user = userService.findByUserName(wrappedSession.getAttribute("username").toString());

        try {
            if(cartService.getAllCart() != null) {
                cartContent = (ArrayList<Cart>) cartService.getAllCart();
                for(int i=0; i<cartContent.size(); i++) {
                    if(cartContent.get(i).getUserId() == user.getId()) {
                        ColumnLay columnLay = new ColumnLay(productService.getProduct(cartContent.get(i).getProductId()).getName(),
                                productService.getProduct(cartContent.get(i).getProductId()).getCategory(),
                                productService.getProduct(cartContent.get(i).getProductId()).getPrice(),
                                productService.getProduct(cartContent.get(i).getProductId()).getImage(),
                                productService.getProduct(cartContent.get(i).getProductId()).getQuantity(),
                                productService.getProduct(cartContent.get(i).getProductId()).getId(),
                                user.getId(), cartContent.get(i).getId());
                        containerBoard.addRow(columnLay);
                        count++;
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Exception: " + e);
        }

        add(containerBoard);
    }

    public void menu()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("96%");
        //horizontalLayout.getStyle().set("margin", "auto");
        Button login = new Button("Go to Login");
        horizontalLayout.add(login);
        NativeButton registration = new NativeButton("Go to Registration");
        horizontalLayout.add(registration);

        Button cartLogo = new Button();
        Image img = new Image("frontend/images/cart.png", "Cant display the image!");
        img.setWidth("55px");
        img.setHeight("45px");
        cartLogo.setIcon(img);
        horizontalLayout.add(cartLogo);
        horizontalLayout.add(counter);

        horizontalLayout.getStyle().set("background-color", "grey");

        cartLogo.addClickListener( event-> cartLogo.getUI().ifPresent(ui -> ui.navigate("purchase")));

        login.addClickListener( event-> login.getUI().ifPresent(ui -> ui.navigate("login")));

        registration.addClickListener( event-> registration.getUI().ifPresent(ui -> ui.navigate("")));

        login.getStyle().set("margin-left", "30px"); //"auto");
        login.getStyle().set("theme", "primary");
        login.getStyle().set("margin-top", "17.5px");
        login.getStyle().set("margin-bottom", "17.5px");
        registration.getStyle().set("theme", "primary");
        registration.getStyle().set("margin-top", "10px");
        registration.getStyle().set("margin-bottom", "10px");
        registration.getStyle().set("margin-right", "20px");
        cartLogo.getStyle().set("margin", "19px 10px 16px 0px");
        cartLogo.getStyle().set("padding", "0px 0px 0px 0px");
        counter.getStyle().set("margin-left", "10px");
        counter.getStyle().set("margin-right", "20px");
        counter.getStyle().set("text-align", "center");
        counter.getStyle().set("font-weight", "bold");
        counter.getStyle().set("font-size", "40px");
        counter.getStyle().set("color", "black");

        add(horizontalLayout);
    }

}
