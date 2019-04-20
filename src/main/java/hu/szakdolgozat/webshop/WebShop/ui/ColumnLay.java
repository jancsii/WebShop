package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hu.szakdolgozat.webshop.WebShop.ApplicationContextHolder;
import hu.szakdolgozat.webshop.WebShop.Names;
import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.entity.Orders;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.OrdersService;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import hu.szakdolgozat.webshop.WebShop.validation.NewProductValidation;
import org.springframework.stereotype.Component;

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
    private Label description = new Label();
    private Div container = new Div();
    private Div purchaseContainer = new Div();
    private Button addToCart = new Button("Add to cart");
    private Button buy = new Button("Buy");
    private Button delete = new Button("Delete");
    private VerticalLayout vname = new VerticalLayout();
    private VerticalLayout vaddToCart = new VerticalLayout();
    private HorizontalLayout vdatas = new HorizontalLayout();
    private HorizontalLayout vbuttons = new HorizontalLayout();
    private HorizontalLayout vimagedesc = new HorizontalLayout();
    private Products p = new Products();
    private Cart cart = new Cart();
    private Orders order = new Orders();
    private Product product = new Product();
    NewProductValidation newProductValidation = new NewProductValidation();

    private TextField nameChange = new TextField();
    private TextField priceChange = new TextField();
    private TextField quantityChange = new TextField();
    private TextField imageChange = new TextField();
    private TextField categoryChange = new TextField();
    private TextArea descriptionChange = new TextArea();
    private Button modify = new Button("Modify");
    VaadinSession session = UI.getCurrent().getSession();
    WrappedSession wrappedSession;

    Notification outOfStock = new Notification(
            "This product is out of stock!", 3000, Notification.Position.TOP_CENTER);
    Notification thankYou = new Notification(
            "Thank you for your purchase!", 3000, Notification.Position.TOP_CENTER);
    Notification addedToCart = new Notification(
            "Added to cart!", 2000, Notification.Position.TOP_CENTER);
    Notification success = new Notification(
            "Successfully changed!", 2000, Notification.Position.TOP_CENTER);
    Notification changed = new Notification(
            "", 3000, Notification.Position.TOP_CENTER);
    Notification loginTo = new Notification(
            "Sign in to purchase a product!", 2000, Notification.Position.TOP_CENTER);

    public ColumnLay() {}

    public ColumnLay(int idt, String namet, int pricet, int quantityt, String imaget, String categoryt, String descriptiont,
                     ProductService productService) {

        this.nameChange.setPlaceholder(namet);
        this.priceChange.setPlaceholder(String.valueOf(pricet));
        this.quantityChange.setPlaceholder(String.valueOf(quantityt));
        this.imageChange.setPlaceholder(String.valueOf(imaget));
        this.categoryChange.setPlaceholder(String.valueOf(categoryt));
        this.descriptionChange.setPlaceholder(String.valueOf(descriptiont));

        this.descriptionChange.setMaxLength(250);
        this.imageChange.setMaxLength(150);
        this.nameChange.setMaxLength(50);
        this.categoryChange.setMaxLength(50);
        this.priceChange.setMaxLength(10);
        this.quantityChange.setMaxLength(10);

        this.descriptionChange.getStyle().set("height", "70px");
        this.delete.getStyle().set("margin", "0px 0px 0px 37px");
        this.container.getStyle().set("width", "250px");
        this.getStyle().set("width", "260px");

        this.container.add(this.nameChange, this.imageChange, this.categoryChange, this.priceChange, this.quantityChange,
                this.descriptionChange, this.modify, this.delete);

        this.modify.addClickListener(e -> {

            Product product = productService.getProduct(idt);

            if(this.nameChange.getValue().isEmpty()) {
                product.setName(this.nameChange.getPlaceholder());
                productService.save(product);
            } else if(newProductValidation.productModify(this.nameChange.getValue(), 3, 50)){
                product.setName(this.nameChange.getValue());
                productService.save(product);
                success.open();
            } else {
                changed.setText("Name must be between 3 and 50 characters long!");
                changed.open();
            }
            if(this.imageChange.getValue().isEmpty()) {
                product.setImage(this.imageChange.getPlaceholder());
                productService.save(product);
            } else if(newProductValidation.productModify(this.imageChange.getValue(), 3, 150)){
                product.setImage(this.imageChange.getValue());
                productService.save(product);
                success.open();
            } else {
                changed.setText("Image must be between 3 and 150 characters long!");
                changed.open();
            }
            if(this.categoryChange.getValue().isEmpty()) {
                product.setCategory(this.categoryChange.getPlaceholder());
                productService.save(product);
            } else if(newProductValidation.productModify(this.categoryChange.getValue(), 3, 50)){
                product.setCategory(this.categoryChange.getValue());
                productService.save(product);
                success.open();
            } else {
                changed.setText("Category must be between 3 and 50 characters long!");
                changed.open();
            }
            if(this.priceChange.getValue().isEmpty()) {
                product.setPrice(Integer.parseInt(this.priceChange.getPlaceholder()));
                productService.save(product);
            } else if(newProductValidation.productModifyNum(this.priceChange.getValue())){
                product.setPrice(Integer.parseInt(this.priceChange.getValue()));
                productService.save(product);
                success.open();
            } else {
                changed.setText("Price can't be empty and must contain only numbers!");
                changed.open();
            }
            if(this.quantityChange.getValue().isEmpty()) {
                product.setQuantity(Integer.parseInt(this.quantityChange.getPlaceholder()));
                productService.save(product);
            } else if(newProductValidation.productModifyNum(this.quantityChange.getValue())){
                product.setQuantity(Integer.parseInt(this.quantityChange.getValue()));
                productService.save(product);
                success.open();
            } else {
                changed.setText("Quantity can't be empty and must contain only numbers!");
                changed.open();
            }
            if(this.descriptionChange.getValue().isEmpty()) {
                product.setDescription(this.descriptionChange.getPlaceholder());
                productService.save(product);
            } else if(newProductValidation.productModify(this.descriptionChange.getValue(), 3, 250)){
                product.setDescription(this.descriptionChange.getValue());
                productService.save(product);
                success.open();
            } else {
                changed.setText("Description must be between 3 and 250 characters long!");
                changed.open();
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
        vname.add(this.name);
        vaddToCart.add(this.addToCart);
        this.name.setText(name);
        Image img = new Image(image, "Cant display the image!");
        img.getStyle().set("border-radius", "10px");
        img.getStyle().set("margin-top", "5px");
        img.setWidth("185px");
        img.setHeight("110px");
        this.image.add(img);
        this.image.getStyle().set("margin-left", "32px");
        this.price.setText("€" + String.valueOf(price));
        this.quantity.setText("Qty: " + String.valueOf(quantity));
        this.container.add(this.vname, this.image, this.price, this.quantity, this.vaddToCart);
        this.container.getElement().setProperty("title", description);

        this.addToCart.addClickListener( event-> {
            ColumnLay columnLay = new ColumnLay(this.name.getText(), this.image.getText(),
                    this.price.getText(), this.quantity.getText(),1);
            try {
                if(wrappedSession.getAttribute(Names.USERNAME) != null) {
                    cart.setProductId(id);
                    cart.setUserId(userService.findByUserName(wrappedSession.getAttribute(Names.USERNAME).toString()).getId());
                    cartService.save(cart);
                    p.counterInc();
                    addedToCart.open();
                    UI.getCurrent().getPage().reload();
                } else {
                    loginTo.open();
                }
            } catch(java.lang.NullPointerException e) {
                System.out.println(e);
            }
        });

        this.name.getStyle().set("margin", "14px auto 0px auto");
        addToCart.getStyle().set("theme", "secondary");
        this.vname.getStyle().set("width", "225px");
        this.vname.getStyle().set("margin", "0 auto");
        this.vname.getStyle().set("padding", "0 0 0 0");
        this.name.getStyle().set("color", "#233348");
        this.price.getStyle().set("color", "#233348");
        this.quantity.getStyle().set("color", "#233348");
        this.price.getStyle().set("margin-left", "30px");
        this.quantity.getStyle().set("margin-left", "111px");
        this.price.getStyle().set("font-weight", "bold");
        this.addToCart.getStyle().set("width", "120px");
        this.addToCart.getStyle().set("color", "#233348");
        this.vaddToCart.getStyle().set("width", "120px");
        this.vaddToCart.getStyle().set("margin", "0 auto");
        this.vaddToCart.getStyle().set("padding", "0 0 0 0");
        this.container.getStyle().set("width", "250px");
        this.container.getStyle().set("background-color", "#696969");
        this.container.getStyle().set("border-radius", "20px");

        add(this.container);
    }

    public ColumnLay(String name, String image, String price, String quantity, int temp)
    {
        this.name.setText(name);
        Image img = new Image(image, "Cant display the image!");
        img.getStyle().set("border-radius", "10px");
        img.getStyle().set("margin-top", "5px");
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

        add(this.purchaseContainer);
    }

    public ColumnLay(String name, String category, int price, String image, int quantity, String description,
                     int productId, int userId, int cartId) {
        this.name.setText(name);
        Image img = new Image("frontend/images/" + image, "Cant display the image!");
        img.getStyle().set("border-radius", "10px");
        img.getStyle().set("margin-top", "5px");
        img.setWidth("185px");
        img.setHeight("110px");
        this.image.add(img);
        this.image.getStyle().set("margin-left", "32px");
        this.price.setText("€" + String.valueOf(price));
        this.category.setText(category);
        this.description.setText(description);

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

        this.name.getStyle().set("margin", "14px 0px 0px 17px");
        this.name.getStyle().set("color", "#233348");
        this.category.getStyle().set("margin", "0px 0px 0px 72px");
        this.price.getStyle().set("color", "#233348");
        this.quantity.getStyle().set("color", "#233348");
        this.category.getStyle().set("color", "#233348");
        this.description.getStyle().set("color", "#233348");
        this.buy.getStyle().set("color", "#233348");
        this.delete.getStyle().set("color", "#233348");
        this.price.getStyle().set("margin-left", "30px");
        this.quantity.getStyle().set("margin-left", "111px");
        this.price.getStyle().set("font-weight", "bold");
        this.vbuttons.getStyle().set("margin", "0px 0px 15px 315px");
        this.purchaseContainer.getStyle().set("width", "500px");
        this.purchaseContainer.getStyle().set("background-color", "#696969");
        this.purchaseContainer.getStyle().set("border-radius", "20px");
        this.purchaseContainer.getStyle().set("margin", "15px 0px 0px 25px");

        vimagedesc.add(this.image, this.description);
        vdatas.add(this.price, this.category);
        vbuttons.add(this.buy, this.delete);

        this.purchaseContainer.add(this.name, this.vimagedesc, this.vdatas, this.vbuttons);

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
