package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.ArrayList;


//@StyleSheet("frontend/css/products.css")
@Route("products")
public class Products extends VerticalLayout {

    @Autowired
    ProductService productService;

    private Label counter = new Label("0");

    public Products() {
        //this.productRepository = productRepository;
        //this.grid = new Grid<>(Product.class);
        //add(grid);
        //this.getStyle().set("width", "95%");
    }

    public void counterInc()
    {
        System.out.println("Current Vaadin Session: " + VaadinSession.getCurrent());
        System.out.println(counter.getText());
        int temp = Integer.parseInt(counter.getText());//(this.counter.getText());
        System.out.println(temp);
        temp++;
        System.out.println(temp);
        counter.setText(String.valueOf(temp));//this.counter.setText(String.valueOf(temp));
        counter = new Label("1");

        System.out.println(counter.getText());
        System.out.println("Hellllo");

    }

    @PostConstruct
    public void init()
    {
        menu();

        ArrayList<Product> products = (ArrayList<Product>) productService.getAllProducts();

        listingProducts(products);

        footer();
    }

    public void listingProducts(ArrayList<Product> products) {

        int productsNum = products.size();
        Board board = new Board();
        ArrayList<ColumnLay> storingTemp = new ArrayList<>();

        for(int i=0; i<productsNum; i++)
        {
            ColumnLay columnLay = new ColumnLay(products.get(i).getName(), "frontend/images/" +
                    products.get(i).getImage(), products.get(i).getPrice(), products.get(i).getQuantity());
            storingTemp.add(columnLay);
            System.out.println("This: " + this);

            if(storingTemp.size()==4)
            {
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
        horizontalLayout.getStyle().set("margin", "auto");
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
        counter.getStyle().set("margin-left", "10px");
        counter.getStyle().set("margin-right", "20px");
        counter.getStyle().set("text-align", "center");
        counter.getStyle().set("font-weight", "bold");
        counter.getStyle().set("font-size", "40px");
        counter.getStyle().set("color", "black");

        add(horizontalLayout);
    }

    public void footer()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("96%");
        horizontalLayout.getStyle().set("margin", "auto");

        Label label = new Label("Footer");
        label.getStyle().set("margin", "10px 10px 10px 50px");

        horizontalLayout.getStyle().set("background-color", "grey");


        horizontalLayout.add(label);

        add(horizontalLayout);
    }

}
