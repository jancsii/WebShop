package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import hu.szakdolgozat.webshop.WebShop.ApplicationContextHolder;
import hu.szakdolgozat.webshop.WebShop.Names;
import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Route(Names.PURCHASE)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@UIScope
public class Purchase extends VerticalLayout{


    CartService cartService = ApplicationContextHolder.getContext().getBean(CartService.class);

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    private Label empty = new Label("The cart is empty!");
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

        if(wrappedSession.getAttribute(Names.USERNAME) != null) {
            menu();
            listPurchasedElements();
            footer();
        } else {
            UI.getCurrent().navigate(Names.REGISTRATION);
            UI.getCurrent().getPage().executeJavaScript("location.reload();");
        }

        counterInc();
    }

    public void counterInc()
    {
        counter.setText(String.valueOf(count));
    }

    public void listPurchasedElements() {

        User user = userService.findByUserName(wrappedSession.getAttribute(Names.USERNAME).toString());

        try {
            if(cartService.getAllCart() != null) {
                remove(empty);
                remove(containerBoard);
                cartContent = (ArrayList<Cart>) cartService.getAllCart();
                for(int i=0; i<cartContent.size(); i++) {
                    if(cartContent.get(i).getUserId() == user.getId()) {
                        ColumnLay columnLay = new ColumnLay(productService.getProduct(cartContent.get(i).getProductId()).getName(),
                                productService.getProduct(cartContent.get(i).getProductId()).getCategory(),
                                productService.getProduct(cartContent.get(i).getProductId()).getPrice(),
                                productService.getProduct(cartContent.get(i).getProductId()).getImage(),
                                productService.getProduct(cartContent.get(i).getProductId()).getQuantity(),
                                productService.getProduct(cartContent.get(i).getProductId()).getDescription(),
                                productService.getProduct(cartContent.get(i).getProductId()).getId(),
                                user.getId(), cartContent.get(i).getId());
                        containerBoard.addRow(columnLay);
                        count++;
                    }
                }
            }
            if(count == 0) {
                add(empty);
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
        horizontalLayout.getStyle().set("margin", "0 auto");
        horizontalLayout.getStyle().set("border-bottom-left-radius", "10px");
        horizontalLayout.getStyle().set("border-bottom-right-radius", "10px");

        Button prod = new Button("Products");
        horizontalLayout.add(prod);
        Button logout = new Button("Logout");
        horizontalLayout.add(logout);

        Button cartLogo = new Button();
        Image img = new Image("frontend/images/cart.png", "Cant display the image!");
        img.setWidth("55px");
        img.setHeight("45px");
        cartLogo.setIcon(img);
        horizontalLayout.add(cartLogo);
        horizontalLayout.add(counter);

        horizontalLayout.getStyle().set("background-color", "#696969");

        prod.addClickListener(event -> prod.getUI().ifPresent(ui -> ui.navigate(Names.PRODUCTS)));

        logout.addClickListener( event-> {
            logout.getUI().ifPresent(ui -> ui.navigate(Names.LOGIN));
            wrappedSession.invalidate();
            session.close();
        });

        logout.getStyle().set("theme", "primary");
        logout.getStyle().set("margin", "auto 10px auto 10px");
        logout.getStyle().set("color", "#233348");
        prod.getStyle().set("theme", "primary");
        prod.getStyle().set("margin", "auto 10px auto 35px");
        prod.getStyle().set("color", "#233348");
        cartLogo.getStyle().set("margin", "19px 0px 16px 0px");
        cartLogo.getStyle().set("padding", "0px 0px 0px 0px");
        counter.getStyle().set("margin", "0px 0px 0px 0px");
        counter.getStyle().set("text-align", "left");
        counter.getStyle().set("vertical-align", "top");
        counter.getStyle().set("font-weight", "bold");
        counter.getStyle().set("font-size", "20px");
        counter.getStyle().set("color", "#233348");
        empty.getStyle().set("font-weight", "bold");
        empty.getStyle().set("font-size", "23px");
        empty.getStyle().set("margin", "25px 0px 0px 50px");
        empty.getStyle().set("color", "#696969");

        add(horizontalLayout);
    }

    public void footer()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.setWidth("96%");

        horizontalLayout.getStyle().set("margin", "0 auto 0 18px");
        horizontalLayout.getStyle().set("background-color", "#696969");
        horizontalLayout.getStyle().set("position", "absolute");
        horizontalLayout.getStyle().set("bottom", "0");
        System.out.println();
        horizontalLayout.getStyle().set("border-top-right-radius", "10px");
        horizontalLayout.getStyle().set("border-top-left-radius", "10px");

        Label label = new Label("Footer");
        label.getStyle().set("margin", "10px 10px 10px 50px");

        horizontalLayout.add(label);

        add(horizontalLayout);
    }
}
