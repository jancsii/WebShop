package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.service.CartService;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class ColumnLay extends VerticalLayout {

    //@Autowired
    //ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    private Label name = new Label();
    private Div image = new Div();
    private Label price = new Label();
    private Label quantity = new Label();
    private Div container = new Div();
    private Div purchaseContainer = new Div();
    private Button buy = new Button("Buy");
    private Products p = new Products();
    private Purchase purchase = new Purchase();
    private Cart cart = new Cart();

    private TextField namet = new TextField();
    private TextField pricet = new TextField();
    private TextField quantityt = new TextField();
    private TextField imaget = new TextField();
    private TextField categoryt = new TextField();
    private TextArea descriptiont = new TextArea();
    private Button modify = new Button("Modify");
    private Button delete = new Button("Delete");
    //UI ui = getUI().get();

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

        //System.out.println(productService.getProduct(5));

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
        this.name.setText(name);
        Image img = new Image(image, "Cant display the image!");
        img.setWidth("185px");
        img.setHeight("110px");
        this.image.add(img);
        this.image.getStyle().set("margin-left", "32px");
        this.price.setText(String.valueOf(price) + "€");
        this.quantity.setText(String.valueOf(quantity));
        this.quantity.getStyle().set("margin-left", "120px");
        this.container.add(this.name, this.image, this.price, this.quantity, this.buy);
        this.container.getElement().setProperty("title", description);

        this.buy.addClickListener( event-> {
            ColumnLay columnLay = new ColumnLay(this.name.getText(), this.image.getText(),
                    this.price.getText(), this.quantity.getText(),1);

//            try {
//                if (userService.getUser() != null) {
//                    System.out.println("User.getId" + userService.getUser().getId());
//                    cart.setProductId(id);
//                    cart.setUserId(userService.getUser().getId());
//                }
//            } catch(java.lang.NullPointerException e) {
//                System.out.println(e);
//            }

//            System.out.println("ColumnLay ---- " + name);
//            purchase.listElements(columnLay);
//            ////((Purchase) purchase.getUI()).listElements(columnLay);
//            p.counterInc();
//            System.out.println(this.name.getText() + " Current Vaadin Session: " +VaadinSession.getCurrent());
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
