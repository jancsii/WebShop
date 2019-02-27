package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.scopes.VaadinSessionScope;

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
            ColumnLay columnLay = new ColumnLay(this.name.getText(), this.image.getText(),
                    this.price.getText(), this.quantity.getText(),1);
            System.out.println("ColumnLay ---- " + name);
            purchase.listElements(columnLay);
            //((Purchase) purchase.getUI()).listElements(columnLay);
            p.counterInc();
            System.out.println(this.name.getText() + " Current Vaadin Session: " +VaadinSession.getCurrent());
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
