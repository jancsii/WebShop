package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import hu.szakdolgozat.webshop.WebShop.ApplicationContextHolder;
import hu.szakdolgozat.webshop.WebShop.Names;
import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Route(Names.PRODUCTS)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Push(PushMode.MANUAL)
@UIScope
public class Products extends VerticalLayout {

    @Autowired
    ProductService productService;

    UserService userService = ApplicationContextHolder.getContext().getBean(UserService.class);

    CartService cartService = ApplicationContextHolder.getContext().getBean(CartService.class);

    private Label counter = new Label("0");
    Board board = new Board();

    VaadinSession session = UI.getCurrent().getSession();
    WrappedSession wrappedSession = session.getSession();

    private Label filterLabel = new Label("Filter by category:");
    private TextField filter = new TextField();
    private HorizontalLayout hfilter = new HorizontalLayout();

    Icon filterLogo = new Icon(VaadinIcon.FILTER);
    Button filterButton =new Button("", filterLogo);
    private String isFilter = "";
    UI ui = UI.getCurrent();

    Notification notification = new Notification(
            "Login to purchase!", 2000, Notification.Position.TOP_CENTER);
    Notification logoutNotification = new Notification(
            "You have successfully logged out!", 2000, Notification.Position.TOP_CENTER);
    Notification notLoggedInNotification = new Notification(
            "You are not logged in!", 2000, Notification.Position.TOP_CENTER);

    public Products() {

    }

    public void counterInc()
    {
        if(wrappedSession.getAttribute(Names.USERNAME) != null) {
            String userName = (String) wrappedSession.getAttribute(Names.USERNAME);
            User user = userService.findByUserName(userName);
            int userId = user.getId();

            ArrayList<Cart> carts = (ArrayList<Cart>) cartService.getAllCart();

            int count = 0;
            for (int i = 0; i < carts.size(); i++) {
                if (userId == carts.get(i).getUserId()) {
                    count++;
                }
            }
            System.out.println("The count: " + count);
            counter.setText(String.valueOf(count));
        }
    }

    @PostConstruct
    public void init()
    {
//        UI.getCurrent().getPage().executeJavaScript(
//                "getElementById('vaadin-license-validation-notification-vaadin-board')." +
//                        "style['display'] = 'none');");
        menu();

        counterInc();

        filterLogo.getStyle().set("cursor", "pointer");
        filterLabel.getStyle().set("margin-top", "7px");
        hfilter.getStyle().set("margin", "20px 0px 0px 40px");

        hfilter.add(filterLabel, filter, filterButton);
        add(hfilter);

        ArrayList<Product> products = (ArrayList<Product>) productService.getAllProducts();

        listingProducts(products);

        filterButton.addClickListener(
                event -> {UI.getCurrent().access( () -> {
                    if(!filter.getValue().isEmpty()){
                        listingProductsFilter(products, filter.getValue());
                    }
                });
                });

        footer();
    }

    public void listingProducts(ArrayList<Product> products) {

        int productsNum = products.size();

        ArrayList<ColumnLay> storingTemp = new ArrayList<>();

        for(int i=0; i<productsNum; i++)
        {
             ColumnLay columnLay = new ColumnLay(products.get(i).getId(), products.get(i).getName(), "frontend/images/" +
                        products.get(i).getImage(), products.get(i).getPrice(), products.get(i).getQuantity(), products.get(i).getDescription());
                storingTemp.add(columnLay);

                if (storingTemp.size() == 4) {
                    board.addRow(storingTemp.get(0), storingTemp.get(1), storingTemp.get(2), storingTemp.get(3));
                    board.getStyle().set("margin-left", "200px");
                    board.setWidth("70%");
                    storingTemp.clear();
                }
        }

        if(storingTemp.size()==3) {
            Row row = board.addRow(storingTemp.get(0), storingTemp.get(1), storingTemp.get(2));
            row.setComponentSpan(storingTemp.get(0),1);
            row.setComponentSpan(storingTemp.get(1),1);
            row.setComponentSpan(storingTemp.get(2),2);
        }
        else if(storingTemp.size()==2){
            Row row = board.addRow(storingTemp.get(0), storingTemp.get(1));
            row.setComponentSpan(storingTemp.get(0),1);
            row.setComponentSpan(storingTemp.get(1),3);
        }
        else if(storingTemp.size()==1){
            Row row = board.addRow(storingTemp.get(0));
            row.setComponentSpan(storingTemp.get(0),1);
        }


        add(board);
    }

    public void listingProductsFilter(ArrayList<Product> products, String filter) {

        //Board board2 = new Board();

        board.removeAll();

        ArrayList<ColumnLay> storingTemp = new ArrayList<>();
        ArrayList<Product> productsCopy = new ArrayList<>();

        for(int i=0; i<products.size(); i++)
        {
            if(filter.equals(products.get(i).getCategory()))
            {
               productsCopy.add(products.get(i));
            }
        }
        if(productsCopy.size() == 0)
        {
            productsCopy = products;
        }

        for(int i=0; i<productsCopy.size(); i++)
        {
            ColumnLay columnLay = new ColumnLay(productsCopy.get(i).getId(), productsCopy.get(i).getName(),
                    "frontend/images/" + productsCopy.get(i).getImage(), productsCopy.get(i).getPrice(),
                    productsCopy.get(i).getQuantity(), productsCopy.get(i).getDescription());
            storingTemp.add(columnLay);

            if (storingTemp.size() == 4) {
                board.addRow(storingTemp.get(0), storingTemp.get(1), storingTemp.get(2), storingTemp.get(3));
                board.getStyle().set("margin-left", "200px");
                board.setWidth("70%");
                storingTemp.clear();
            }
        }

        if(storingTemp.size()==3) {
            Row row = board.addRow(storingTemp.get(0), storingTemp.get(1), storingTemp.get(2));
            row.setComponentSpan(storingTemp.get(0),1);
            row.setComponentSpan(storingTemp.get(1),1);
            row.setComponentSpan(storingTemp.get(2),2);
        }
        else if(storingTemp.size()==2){
            Row row = board.addRow(storingTemp.get(0), storingTemp.get(1));
            row.setComponentSpan(storingTemp.get(0),1);
            row.setComponentSpan(storingTemp.get(1),3);
        }
        else if(storingTemp.size()==1){
            Row row = board.addRow(storingTemp.get(0));
            row.setComponentSpan(storingTemp.get(0),1);
        }

        add(board);
    }

    public void menu()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("96%");
        horizontalLayout.getStyle().set("margin", "0 auto");
        horizontalLayout.getStyle().set("border-bottom-left-radius", "10px");
        horizontalLayout.getStyle().set("border-bottom-right-radius", "10px");

        Button login = new Button("Login");
        horizontalLayout.add(login);
        Button registration = new Button("Registration");
        horizontalLayout.add(registration);
        Button logout = new Button("Logout");
        horizontalLayout.add(logout);
        Button cart = new Button("Cart");
        horizontalLayout.add(cart);
        Button edit = new Button("Edit");
        if(Names.ADMIN.equals(wrappedSession.getAttribute(Names.USERNAME))) {
            horizontalLayout.add(edit);
        }

        Button cartLogo = new Button();
        Image img = new Image("frontend/images/cart.png", "Cant display the image!");
        img.setWidth("55px");
        img.setHeight("45px");
        cartLogo.setIcon(img);
        horizontalLayout.add(cartLogo);
        horizontalLayout.add(counter);

        horizontalLayout.getStyle().set("background-color", "#696969");

        cartLogo.addClickListener( event-> {
            cartLogo.getUI().ifPresent(ui -> ui.navigate(Names.PURCHASE));
        });

//        if(session.getAttribute("username") != null){
//            cartLogo.addClickListener( event-> {
//                cartLogo.getUI().ifPresent(ui -> ui.navigate("purchase"));
//            });
//        } else {
//            cartLogo.addClickListener( event-> {
//                cartLogo.getUI().ifPresent(ui -> ui.navigate("login"));
//            });
//        }

        cart.addClickListener(event-> {
            if(wrappedSession.getAttribute(Names.USERNAME) != null) {
                cart.getUI().ifPresent(ui -> ui.navigate(Names.PURCHASE));
            } else {
                notLoggedInNotification.open();
            }
        });

        edit.addClickListener(event -> edit.getUI().ifPresent(ui -> ui.navigate(Names.ADMIN)));

        login.addClickListener( event-> login.getUI().ifPresent(ui -> ui.navigate(Names.LOGIN)));

        registration.addClickListener( event-> registration.getUI().ifPresent(ui -> ui.navigate(Names.REGISTRATION)));

        logout.addClickListener( event-> {
            if(wrappedSession.getAttribute(Names.USERNAME) != null) {
                logout.getUI().ifPresent(ui -> ui.navigate(Names.LOGIN));
                wrappedSession.invalidate();
                session.close();
                logoutNotification.open();
            } else {
                notLoggedInNotification.open();
            }
        });

        login.getStyle().set("theme", "primary");
        login.getStyle().set("margin", "auto 10px auto 35px");
        login.getStyle().set("color", "#233348");
        registration.getStyle().set("theme", "primary");
        registration.getStyle().set("margin", "auto 10px auto 10px");
        registration.getStyle().set("color", "#233348");
        logout.getStyle().set("theme", "primary");
        logout.getStyle().set("margin", "auto 10px auto 10px");
        logout.getStyle().set("color", "#233348");
        cart.getStyle().set("theme", "primary");
        cart.getStyle().set("margin", "auto 10px auto 10px");
        cart.getStyle().set("color", "#233348");
        edit.getStyle().set("theme", "primary");
        edit.getStyle().set("margin", "auto 10px auto 10px");
        edit.getStyle().set("color", "#233348");
        cartLogo.getStyle().set("margin", "19px 0px 16px 0px");
        cartLogo.getStyle().set("padding", "0px 0px 0px 0px");
        counter.getStyle().set("margin", "0px 0px 0px 0px");
        counter.getStyle().set("text-align", "left");
        counter.getStyle().set("vertical-align", "top");
        counter.getStyle().set("font-weight", "bold");
        counter.getStyle().set("font-size", "20px");
        counter.getStyle().set("color", "#233348");
        this.getStyle().set("background-color", "#233348");
        this.getStyle().set("position", "relative");

        add(horizontalLayout);
    }

    public void footer()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("96%");
        horizontalLayout.getStyle().set("margin", "15px 0px 0px 15px");
        horizontalLayout.getStyle().set("background-color", "#696969");
        horizontalLayout.getStyle().set("position", "absolute");
        horizontalLayout.getStyle().set("bottom", "0");
        horizontalLayout.getStyle().set("border-top-right-radius", "10px");
        horizontalLayout.getStyle().set("border-top-left-radius", "10px");

        Label label = new Label("Footer");
        label.getStyle().set("margin", "10px 10px 10px 50px");

        horizontalLayout.add(label);

        add(horizontalLayout);
    }
}
