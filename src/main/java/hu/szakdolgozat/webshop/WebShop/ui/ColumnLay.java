package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hu.szakdolgozat.webshop.WebShop.ApplicationContextHolder;
import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.entity.Orders;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.OrdersService;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@VaadinSessionScope
@Component
public class ColumnLay extends VerticalLayout {

    ProductService productService = ApplicationContextHolder.getContext().getBean(ProductService.class);

    UserService userService = ApplicationContextHolder.getContext().getBean(UserService.class);

    CartService cartService = ApplicationContextHolder.getContext().getBean(CartService.class);

    OrdersService ordersService = ApplicationContextHolder.getContext().getBean(OrdersService.class);

    private Label name = new Label();
    private Div image = new Div();
    private Label price = new Label();
    private Label quantity = new Label();
    private Label category = new Label();
    private Div container = new Div();
    private Div purchaseContainer = new Div();
    private Button addToCart = new Button("Add to cart");
    private Button buy = new Button("Buy");
    private Button delete = new Button("Delete");
    private Products p = new Products();
    //private Purchase purchase = new Purchase();
    private Cart cart = new Cart();
    private Orders order = new Orders();
    private Product product = new Product();

    private TextField namet = new TextField();
    private TextField pricet = new TextField();
    private TextField quantityt = new TextField();
    private TextField imaget = new TextField();
    private TextField categoryt = new TextField();
    private TextArea descriptiont = new TextArea();
    private Button modify = new Button("Modify");
    VaadinSession session = UI.getCurrent().getSession();
    WrappedSession wrappedSession;

    public ColumnLay() {}

    public ColumnLay(int idt, String namet, int pricet, int quantityt, String imaget, String categoryt, String descriptiont,
                     ProductService productService) {

        this.namet.setPlaceholder(namet);
        this.pricet.setPlaceholder(String.valueOf(pricet));
        this.quantityt.setPlaceholder(String.valueOf(quantityt));
        this.imaget.setPlaceholder(String.valueOf(imaget));
        this.categoryt.setPlaceholder(String.valueOf(categoryt));
        this.descriptiont.setPlaceholder(String.valueOf(descriptiont));

        this.descriptiont.setMaxLength(250);
        this.imaget.setMaxLength(150);
        this.namet.setMaxLength(50);
        this.categoryt.setMaxLength(50);
        this.pricet.setMaxLength(10);
        this.quantityt.setMaxLength(10);

        this.container.add(this.namet, this.imaget, this.categoryt, this.pricet, this.quantityt, this.descriptiont, this.modify, this.delete);

        this.modify.addClickListener(e -> {

            Product product = productService.getProduct(idt);

            if(this.namet.getValue().isEmpty()) {
                product.setName(this.namet.getPlaceholder());
                productService.save(product);
            } else {
                product.setName(this.namet.getValue());
                productService.save(product);
            }
            if(this.imaget.getValue().isEmpty()) {
                product.setImage(this.imaget.getPlaceholder());
                productService.save(product);
            } else {
                product.setImage(this.imaget.getValue());
                productService.save(product);
            }
            if(this.categoryt.getValue().isEmpty()) {
                product.setCategory(this.categoryt.getPlaceholder());
                productService.save(product);
            } else {
                product.setCategory(this.categoryt.getValue());
                productService.save(product);
            }
            if(this.pricet.getValue().isEmpty()) {
                product.setPrice(Integer.parseInt(this.pricet.getPlaceholder()));
                productService.save(product);
            } else {
                product.setPrice(Integer.parseInt(this.pricet.getValue()));
                productService.save(product);
            }
            if(this.quantityt.getValue().isEmpty()) {
                product.setQuantity(Integer.parseInt(this.quantityt.getPlaceholder()));
                productService.save(product);
            } else {
                product.setQuantity(Integer.parseInt(this.quantityt.getValue()));
                productService.save(product);
            }
            if(this.descriptiont.getValue().isEmpty()) {
                product.setDescription(this.descriptiont.getPlaceholder());
                productService.save(product);
            } else {
                product.setDescription(this.descriptiont.getValue());
                productService.save(product);
            }
        });

        this.delete.addClickListener(e -> {
            productService.delete(productService.getProduct(idt));
            UI.getCurrent().getPage().reload();
        });

        add(this.container);
    }

    public ColumnLay(int id, String name, String image, int price, int quantity, String description)
    {
        wrappedSession = session.getSession();
        this.name.setText(name);
        Image img = new Image(image, "Cant display the image!");
        img.setWidth("185px");
        img.setHeight("110px");
        this.image.add(img);
        this.image.getStyle().set("margin-left", "32px");
        this.price.setText(String.valueOf(price) + "€");
        this.quantity.setText(String.valueOf(quantity));
        this.quantity.getStyle().set("margin-left", "120px");
        this.container.add(this.name, this.image, this.price, this.quantity, this.addToCart);
        this.container.getElement().setProperty("title", description);

        this.addToCart.addClickListener( event-> {
            ColumnLay columnLay = new ColumnLay(this.name.getText(), this.image.getText(),
                    this.price.getText(), this.quantity.getText(),1);
            System.out.println("Probaaaa" + wrappedSession.getAttribute("username"));
            try {
                if(wrappedSession.getAttribute("username") != null) {
                    cart.setProductId(id);
                    cart.setUserId(userService.findByUserName(wrappedSession.getAttribute("username").toString()).getId());
                    cartService.save(cart);
                    System.out.println("Before ---- ");
                    p.counterInc();
                    System.out.println("After ---- ");
                }
            } catch(java.lang.NullPointerException e) {
                System.out.println(e);
            }

            System.out.println("ColumnLay ---- " + name);
            System.out.println("UserService in ColumnLay: " + userService);
            //purchase.listElements(columnLay);

        });
        this.container.getStyle().set("width", "250px");
        this.container.getStyle().set("background-color", "lightGray");

        add(this.container);
    }

    public ColumnLay(String name, String image, String price, String quantity, int temp)
    {
        this.name.setText(name);
        Image img = new Image(image, "Cant display the image!");
        img.setWidth("185px");
        img.setHeight("110px");
        this.image.add(img);
        this.image.getStyle().set("margin-left", "32px");
        this.price.setText(price);
        //this.price.setText(String.valueOf(price) + "€");
        this.quantity.setText(quantity);
        //this.quantity.setText(String.valueOf(quantity));
        this.quantity.getStyle().set("margin-left", "120px");
        this.purchaseContainer.add(this.name, this.image, this.price, this.quantity);
        this.purchaseContainer.getStyle().set("width", "250px");
        this.purchaseContainer.getStyle().set("background-color", "lightGray");

        System.out.println("Adding purchaseContainer ---- " + name + "---" + this.name.getText());

        add(this.purchaseContainer);
    }

    Notification outOfStock = new Notification(
            "This product is out of stock!", 3000, Notification.Position.TOP_CENTER);
    Notification thankYou = new Notification(
            "Thank you for your purchase!", 3000, Notification.Position.TOP_CENTER);

    public ColumnLay(String name, String category, int price, String image, int quantity, int productId, int userId,
                     int cartId) {
        this.name.setText(name);
        Image img = new Image("frontend/images/" + image, "Cant display the image!");
        img.setWidth("185px");
        img.setHeight("110px");
        this.image.add(img);
        this.image.getStyle().set("margin-left", "32px");
        this.price.setText(String.valueOf(price));
        this.category.setText(category);

        this.buy.addClickListener( event-> {
            if(quantity > 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                order.setProductId(productId);
                order.setUserId(userId);
                order.setDate(dateFormat.format(date));
                ordersService.save(order);
                product = productService.getProduct(productId);
                product.setQuantity(product.getQuantity() - 1);
                productService.save(product);
                thankYou.open();
                cartService.delete(cartService.getCart(cartId));
                UI.getCurrent().getPage().reload();
            } else {
                outOfStock.open();
            }
        });

        this.delete.addClickListener( e -> {
            cartService.delete(cartService.getCart(cartId));
            UI.getCurrent().getPage().reload();
        });

        this.purchaseContainer.add(this.name, this.image, this.price, this.category, this.buy, this.delete);

        this.purchaseContainer.getStyle().set("width", "250px");
        this.purchaseContainer.getStyle().set("background-color", "lightGray");

        add(this.purchaseContainer);
    }

    @Override
    public String toString() {
        return "ColumnLay{" +
                "name=" + name +
                ", image=" + image.getText() +
                ", price=" + price +
                ", quantity=" + quantity.getText() +
                '}';
    }
}
