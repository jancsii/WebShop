package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.html.Label;
import hu.szakdolgozat.webshop.WebShop.entity.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Route("purchase")
public class Purchase extends VerticalLayout {

    private Label label = new Label("Heyyy");
    private ArrayList<String> container = new ArrayList<>();
    private Board containerBoard = new Board();
    private VerticalLayout verticalLayout = new VerticalLayout();

    public Purchase() {

    }

    @PostConstruct
    public void init()
    {
        menu();

        this.getStyle().set("border-style", "groove");
        this.getStyle().set("height", "555px");

    }

    public void listElements(ColumnLay product) {

        containerBoard.addRow(product);
        add(containerBoard);


        System.out.println("Purchase...");
//        this.container.add(s);
//        label.setText(label.getText() + " " + this.container.get(this.container.size()-1));
//        add(label);

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

        horizontalLayout.getStyle().set("background-color", "grey");

        cartLogo.addClickListener( event-> {
            cartLogo.getUI().ifPresent(ui -> ui.navigate("purchase"));
        });

        login.addClickListener( event-> {
            login.getUI().ifPresent(ui -> ui.navigate("login"));
        });

        registration.addClickListener( event-> {
            registration.getUI().ifPresent(ui -> ui.navigate(""));
        });

        login.getStyle().set("margin-left", "auto");
        login.getStyle().set("theme", "primary");
        login.getStyle().set("margin-top", "17.5px");
        login.getStyle().set("margin-bottom", "17.5px");
        registration.getStyle().set("theme", "primary");
        registration.getStyle().set("margin-top", "10px");
        registration.getStyle().set("margin-bottom", "10px");
        registration.getStyle().set("margin-right", "20px");
        cartLogo.getStyle().set("margin", "19px 10px 16px 0px");
        cartLogo.getStyle().set("padding", "0px 0px 0px 0px");

        add(horizontalLayout);
    }

}
