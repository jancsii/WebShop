package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.EventObject;

@Route("admin")
public class Admin extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    ProductService productService;

    private Button newProduct = new Button("Adding new product");

    @PostConstruct
    public void init()
    {
        if("admin".equals(UI.getCurrent().getSession().getAttribute("username"))) {
            ArrayList<Product> products = (ArrayList<Product>) productService.getAllProducts();

            listingProducts(products);

            newProduct.addClickListener( event-> {
                newProduct.getUI().ifPresent(ui -> ui.navigate("admin/newproduct"));
            });
        } else {
            getUI().ifPresent(ui -> ui.navigate("login"));
        }

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!"admin".equals(UI.getCurrent().getSession().getAttribute("username"))) {
            beforeEnterEvent.rerouteTo("login");
        }
    }

    public void listingProducts(ArrayList<Product> products) {

        int productsNum = products.size();
        Board board = new Board();
        ArrayList<ColumnLay> storingTemp = new ArrayList<>();
        int productId;

        for(int i=0; i<productsNum; i++)
        {
            //productId = productService.findByName(products.get(i).getName()).getId();
            ColumnLay columnLay = new ColumnLay(products.get(i).getId(), products.get(i).getName(), products.get(i).getPrice(),
                    products.get(i).getQuantity(), products.get(i).getImage(), products.get(i).getCategory(), products.get(i).getDescription(),
                    productService);
            //ColumnLay columnLay = new ColumnLay(products.get(i).getName(), "frontend/images/" +
            //        products.get(i).getImage(), products.get(i).getPrice(), products.get(i).getQuantity());
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
        add(newProduct);
    }
}
