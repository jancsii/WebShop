package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;


@Route("products")
public class Products extends  VerticalLayout {

    @Autowired
    ProductService productService;
    //Product product = new Product();

    //private final ProductRepository productRepository;
    final Grid<Product> grid;

    public Products() {

        //this.productRepository = productRepository;
        this.grid = new Grid<>(Product.class);
        add(grid);

    }

    private void listCustomers() {
        grid.setItems((List)productService.getAllProducts());
    }

    /*
    final Grid grid = new Grid();
    grid.addColumn("icon", Resource.class);
    grid.addRow(new ClassResource("fi.gif"));
     */

    @PostConstruct
    public void init() {

        listCustomers();
    }
/*
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();

        Iterable<Product> pro = productService.getAllProducts();

        List<Product> people = (List)pro;

        Image image = new Image("frontend/images/" + productService.getProduct(1).getImage(),
                "Cant display the image!");
        image.setWidth("150px");

        Image image2 = new Image("frontend/images/" + productService.getProduct(2).getImage(),
                "Cant display the image!");
        image2.setWidth("150px");

        Image image3 = new Image("frontend/images/" + productService.getProduct(3).getImage(),
                "Cant display the image!");
        image3.setWidth("150px");

        Image image4 = new Image("frontend/images/" + productService.getProduct(4).getImage(),
                "Cant display the image!");
        image4.setWidth("150px");


        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        label1.setText(productService.getProduct(1).getName());
        label2.setText(productService.getProduct(1).getDescription());
        label3.setText(String.valueOf(productService.getProduct(1).getQuantity()));

        Label label4 = new Label();
        Label label5 = new Label();
        Label label6 = new Label();
        label4.setText(productService.getProduct(2).getName());
        label5.setText(productService.getProduct(2).getDescription());
        label6.setText(String.valueOf(productService.getProduct(2).getQuantity()));

        Label label7 = new Label();
        Label label8 = new Label();
        Label label9 = new Label();
        label7.setText(productService.getProduct(3).getName());
        label8.setText(productService.getProduct(3).getDescription());
        label9.setText(String.valueOf(productService.getProduct(3).getQuantity()));

        Label label10 = new Label();
        Label label11 = new Label();
        Label label12 = new Label();
        label10.setText(productService.getProduct(4).getName());
        label11.setText(productService.getProduct(4).getDescription());
        label12.setText(String.valueOf(productService.getProduct(4).getQuantity()));

        Board board = new Board();
        Div child1 = new Div();
        Div child2 = new Div();
        Div child3 = new Div();
        Div child4 = new Div();

        Board board2 = new Board();
        Div child5 = new Div();
        Div child6 = new Div();
        Div child7 = new Div();
        Div child8 = new Div();

        Board board3 = new Board();
        Div child9 = new Div();
        Div child10 = new Div();
        Div child11 = new Div();
        Div child12 = new Div();

        Board board4 = new Board();
        Div child13 = new Div();
        Div child14 = new Div();
        Div child15 = new Div();
        Div child16 = new Div();

        child1.add(label1);
        child2.add(label2);
        child3.add(label3);
        child4.add(image);

        child5.add(label4);
        child6.add(label5);
        child7.add(label6);
        child8.add(image2);

        child9.add(label7);
        child10.add(label8);
        child11.add(label9);
        child12.add(image3);

        child13.add(label10);
        child14.add(label11);
        child15.add(label12);
        child16.add(image4);

        board.addRow(child1, child2,child3, child4);
        board.setWidth("500px");

        board2.addRow(child5, child6,child7, child8);
        board2.setWidth("500px");

        board3.addRow(child9, child10, child11, child12);
        board3.setWidth("500px");

        board4.addRow(child13, child14,child15, child16);
        board4.setWidth("500px");

        VerticalLayout col1 = new VerticalLayout(board);
        VerticalLayout col2 = new VerticalLayout(board2);
        VerticalLayout col3 = new VerticalLayout(board3);
        VerticalLayout col4 = new VerticalLayout(board4);
        horizontalLayout.add(col1, col2, col3);
        horizontalLayout2.add(col4);
        verticalLayout.add(horizontalLayout, horizontalLayout2);

        horizontalLayout.getElement().setAttribute("theme", "row-with-gaps");
        horizontalLayout2.getElement().setAttribute("theme", "row-with-gaps");
        col1.getElement().setAttribute("theme", "col-with-padding");
        col2.getElement().setAttribute("theme", "col-with-padding");
        col3.getElement().setAttribute("theme", "col-with-padding");
        col4.getElement().setAttribute("theme", "col-with-padding");

        Label[] labels = new Label[((List<Product>) pro).size() * 3];
        Image[] images = new Image[((List<Product>) pro).size()];
        Board[] boards = new Board[((List<Product>) pro).size()];
        VerticalLayout[] verticalLayouts = new VerticalLayout[((List<Product>) pro).size()];
        HorizontalLayout[] horizontalLayouts = new HorizontalLayout[((List<Product>) pro).size()];
        int i = 0;
        int j = 0;
        for(Product p : pro)
        {
            //labels[i] = new Label();
            labels[i].setText(p.getName());

            //labels[i+1] = new Label();
            labels[i+1].setText(p.getDescription());

            //labels[i+2] = new Label();
            labels[i+2].setText(String.valueOf(p.getPrice()));

            images[j] = new Image("frontend/images/" + productService.getProduct(j).getImage(),
                    "Cant display the image!");
            image.setWidth("150px");

           // boards[j] = new Board();
            boards[j].addRow(labels[i], labels[i+1], labels[i+2], images[j]);
            boards[j].setWidth("500px");

            verticalLayouts[j] = new VerticalLayout(boards[j]);
            i+=3; j++;
        }

        VerticalLayout mainVerticalLayout = new VerticalLayout();
        int k = 0;
        for(int l=0; l<verticalLayouts.length-2; l++)
        {
            horizontalLayouts[k].add(verticalLayouts[l], verticalLayouts[l+1], verticalLayouts[l+2]);
            k++;
        }

        for(int m=0; m<horizontalLayouts.length; m++)
        {
            mainVerticalLayout.add(horizontalLayouts[m]);
        }

        //add(board);
        //add(board2);
        //add(verticalLayout);
        add(mainVerticalLayout);
    }
*/
}
