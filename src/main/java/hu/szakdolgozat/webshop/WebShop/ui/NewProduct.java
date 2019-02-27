package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import hu.szakdolgozat.webshop.WebShop.entity.Product;
import hu.szakdolgozat.webshop.WebShop.service.ProductService;
import hu.szakdolgozat.webshop.WebShop.validation.NewProductValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Route("admin/newproduct")
public class NewProduct extends VerticalLayout {

    @Autowired
    ProductService productService;

    NewProductValidation newProductValidation = new NewProductValidation();

    private Button products = new Button("Back to products");
    private Button add = new Button("Add");
    private TextField productName = new TextField();
    private TextField productImage = new TextField();
    private TextField productCategory = new TextField();
    private TextField productPrice = new TextField();
    private TextField productQuantity = new TextField();
    private TextArea productDescription = new TextArea();
    private Label productNameLabel = new Label("Product Name: ");
    private Label productImageLabel = new Label("Product Image: ");
    private Label productCategoryLabel = new Label("Product Category: ");
    private Label productPriceLabel = new Label("Product Price: ");
    private Label productQuantityLabel = new Label("Product Quantity: ");
    private Label productDescriptionLabel = new Label("Product Description: ");
    private Label errorLabel = new Label();

    @PostConstruct
    public void init()
    {
        products.addClickListener( event-> {
            products.getUI().ifPresent(ui -> ui.navigate("admin"));
        });

        add.addClickListener( event -> {
            ArrayList<String> errors = newProductValidation.productNameValidation(productName.getValue(),
                    productImage.getValue(), productCategory.getValue(), productPrice.getValue(),
                    productQuantity.getValue(), productDescription.getValue());

            if(!(errors.isEmpty())) {
                errorLabel.setText("");
                System.out.println(joiningErrors(errors));
                errorLabel.setText(joiningErrors(errors));
            } else {
                errorLabel.setText("");
                Product product = new Product();
                product.setName(productName.getValue());
                product.setImage(productImage.getValue());
                product.setCategory(productCategory.getValue());
                product.setPrice(Integer.parseInt(productPrice.getValue()));
                product.setQuantity(Integer.parseInt(productQuantity.getValue()));
                product.setDescription(productDescription.getValue());
                productService.save(product);
                System.out.println(product);
            }

        });

        productName.setMaxLength(50);
        productImage.setMaxLength(150);
        productCategory.setMaxLength(50);
        productDescription.setMaxLength(250);


        add(products);

        settingComponents();

        add(add);

        add(errorLabel);
    }

    private String joiningErrors(ArrayList<String> errors) {

        String errorContainer = "";

        errorContainer = String.join("\n", errors);

        return errorContainer;

    }

    public void settingComponents() {

        HorizontalLayout horizontalLayoutName = new HorizontalLayout();
        HorizontalLayout horizontalLayoutImage = new HorizontalLayout();
        HorizontalLayout horizontalLayoutCategory = new HorizontalLayout();
        HorizontalLayout horizontalLayoutPrice = new HorizontalLayout();
        HorizontalLayout horizontalLayoutQuantity = new HorizontalLayout();
        HorizontalLayout horizontalLayoutDescription = new HorizontalLayout();

        productNameLabel.getStyle().set("width", "170px");
        productNameLabel.getStyle().set("vertical-align", "center");
        productNameLabel.getStyle().set("margin-left", "100px");
        productName.getStyle().set("width", "200px");
        horizontalLayoutName.add(productNameLabel, productName);

        productImageLabel.getStyle().set("width", "170px");
        productImageLabel.getStyle().set("vertical-align", "center");
        productImageLabel.getStyle().set("margin-left", "100px");
        productImage.getStyle().set("width", "200px");
        horizontalLayoutImage.add(productImageLabel, productImage);

        productCategoryLabel.getStyle().set("width", "170px");
        productCategoryLabel.getStyle().set("margin-left", "100px");
        productCategory.getStyle().set("width", "200px");
        horizontalLayoutCategory.add(productCategoryLabel, productCategory);

        productPriceLabel.getStyle().set("width", "170px");
        productPriceLabel.getStyle().set("margin-left", "100px");
        productPrice.getStyle().set("width", "200px");
        horizontalLayoutPrice.add(productPriceLabel, productPrice);

        productQuantityLabel.getStyle().set("width", "170px");
        productQuantityLabel.getStyle().set("margin-left", "100px");
        productQuantity.getStyle().set("width", "200px");
        horizontalLayoutQuantity.add(productQuantityLabel, productQuantity);

        productDescriptionLabel.getStyle().set("width", "170px");
        productDescriptionLabel.getStyle().set("margin-left", "100px");
        productDescription.getStyle().set("width", "200px");
        horizontalLayoutDescription.add(productDescriptionLabel, productDescription);


        add(horizontalLayoutName, horizontalLayoutImage, horizontalLayoutCategory, horizontalLayoutPrice,
                horizontalLayoutQuantity, horizontalLayoutDescription);

    }
}