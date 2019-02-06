package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import elemental.css.CSSStyleDeclaration;

public class ColumnLay extends VerticalLayout {

    private Label name = new Label();
    private Div image = new Div();
    private Label price = new Label();
    private Label quantity = new Label();
    private Div container = new Div();
    private Div purchaseContainer = new Div();
    private Button buy = new Button("Buy");
    private Products p = new Products();
    private Purchase purchase = new Purchase();

    public ColumnLay(String name, String image, int price, int quantity)
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
        this.buy.addClickListener( event-> {
            ColumnLay columnLay = new ColumnLay(name, image, price, quantity,1);
            purchase.listElements(columnLay);
            p.counterInc();
            System.out.println(this.name.getText());
        });
        this.container.getStyle().set("width", "250px");
        this.container.getStyle().set("background-color", "lightGray");

        add(this.container);
    }

    public ColumnLay(String name, String image, int price, int quantity, int temp)
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
        this.purchaseContainer.add(this.name, this.image, this.price, this.quantity);
        this.purchaseContainer.getStyle().set("width", "250px");
        this.purchaseContainer.getStyle().set("background-color", "lightGray");

        add(this.purchaseContainer);
    }
}
