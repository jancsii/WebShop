package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import hu.szakdolgozat.webshop.WebShop.Names;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Route(Names.ADMIN)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@UIScope
public class Admin extends VerticalLayout  {

    @Autowired
    ProductService productService;

    private Button newProduct = new Button("Adding new product");
    private Button watchProducts = new Button("Watch all products");
    private Label productNameLabel = new Label("Name: ");
    private Label productImageLabel = new Label("Image: ");
    private Label productCategoryLabel = new Label("Category: ");
    private Label productPriceLabel = new Label("Price: ");
    private Label productQuantityLabel = new Label("Quantity: ");
    private Label productDescriptionLabel = new Label("Description: ");
    VerticalLayout vl = new VerticalLayout();
    HorizontalLayout hl = new HorizontalLayout();
    HorizontalLayout buttons = new HorizontalLayout();

    VaadinSession session = UI.getCurrent().getSession();
    WrappedSession wrappedSession;

    @PostConstruct
    public void init()
    {
        this.getStyle().set("padding", "0px");
        this.getStyle().set("background-color", "#233348");

        style();

        if(Names.ADMIN.equals(wrappedSession.getAttribute(Names.USERNAME))) {
            ArrayList<Product> products = (ArrayList<Product>) productService.getAllProducts();
            listingProducts(products);

            newProduct.addClickListener( event-> newProduct.getUI().ifPresent(ui -> ui.navigate(Names.NEWPRODUCT)));

            watchProducts.addClickListener( event -> watchProducts.getUI().ifPresent(ui -> ui.navigate(Names.PRODUCTS)));
        } else {
            UI.getCurrent().navigate(Names.DENIED);
            UI.getCurrent().getPage().executeJavaScript("location.reload();");
        }
    }

    public void listingProducts(ArrayList<Product> products) {

        int productsNum = products.size();
        Board board = new Board();
        ArrayList<ColumnLay> storingTemp = new ArrayList<>();
        int productId;

        for(int i=0; i<productsNum; i++)
        {
            ColumnLay columnLay = new ColumnLay(products.get(i).getId(), products.get(i).getName(), products.get(i).getPrice(),
                    products.get(i).getQuantity(), products.get(i).getImage(), products.get(i).getCategory(), products.get(i).getDescription(),
                    productService);
            storingTemp.add(columnLay);

            if(storingTemp.size()==4)
            {
                board.addRow(storingTemp.get(0), storingTemp.get(1), storingTemp.get(2), storingTemp.get(3));
                board.getStyle().set("margin", "35px 0px 0px 10px");
                storingTemp.clear();
            }
        }

        if(storingTemp.size()==3) {
            Row row = board.addRow(storingTemp.get(0), storingTemp.get(1), storingTemp.get(2));
            row.setComponentSpan(storingTemp.get(0),1);
            row.setComponentSpan(storingTemp.get(1),1);
            row.setComponentSpan(storingTemp.get(2),2);
        } else if(storingTemp.size()==2){
            Row row = board.addRow(storingTemp.get(0), storingTemp.get(1));
            row.setComponentSpan(storingTemp.get(0),1);
            row.setComponentSpan(storingTemp.get(1),3);
        } else if(storingTemp.size()==1){
            Row row = board.addRow(storingTemp.get(0));
            row.setComponentSpan(storingTemp.get(0),1);
        }

        hl.add(vl, board);
        add(hl);
        add(buttons);
    }

    public void style() {
        buttons.add(newProduct, watchProducts);
        buttons.getStyle().set("margin", "0px 0px 0px 12px");
        newProduct.getStyle().set("margin", "0px 0px 10px 150px");
        watchProducts.getStyle().set("margin", "0px 0px 10px 15px");
        wrappedSession = session.getSession();

        vl.add(productNameLabel, productImageLabel, productCategoryLabel, productPriceLabel, productQuantityLabel,
                productDescriptionLabel);

        vl.getStyle().set("width", "90px");
        hl.setWidth("96%");
        hl.getStyle().set("margin", "0 auto");
        productNameLabel.getStyle().set("color", "#53A8FF");
        productImageLabel.getStyle().set("color", "#53A8FF");
        productCategoryLabel.getStyle().set("color", "#53A8FF");
        productPriceLabel.getStyle().set("color", "#53A8FF");
        productQuantityLabel.getStyle().set("color", "#53A8FF");
        productDescriptionLabel.getStyle().set("color", "#53A8FF");
        productNameLabel.getStyle().set("margin", "51px 0px 0px 0px");
        productImageLabel.getStyle().set("margin", "16px 0px 0px 0px");
        productCategoryLabel.getStyle().set("margin", "17px 0px 0px 0px");
        productPriceLabel.getStyle().set("margin", "19px 0px 0px 0px");
        productQuantityLabel.getStyle().set("margin", "20px 0px 0px 0px");
        productDescriptionLabel.getStyle().set("margin", "21px 0px 0px 0px");
    }
}
