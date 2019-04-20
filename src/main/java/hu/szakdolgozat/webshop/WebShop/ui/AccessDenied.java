package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import hu.szakdolgozat.webshop.WebShop.Names;

import javax.annotation.PostConstruct;

@Route(Names.DENIED)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@UIScope
public class AccessDenied extends VerticalLayout {

    Label denied = new Label("Access Denied!!");
    Label permission = new Label("You have no permission to see this page!!");
    Label smile = new Label(":(");
    Button products = new Button("Go to products");
    VerticalLayout vl = new VerticalLayout();
    HorizontalLayout hl = new HorizontalLayout();

    public AccessDenied() {}

    @PostConstruct
    public void init(){
        style();

        products.addClickListener(event -> products.getUI().ifPresent(ui -> ui.navigate(Names.PRODUCTS)));

        hl.add(products, smile);
        vl.add(denied, permission, hl);

        add(vl);
    }

    public void style() {
        vl.getStyle().set("width", "700px");
        vl.getStyle().set("margin", "50px auto");
        vl.getStyle().set("border", "4px solid #696969");
        vl.getStyle().set("border-radius", "10px");
        denied.getStyle().set("color", "#580000");
        denied.getStyle().set("margin", "20px 0px 0px 20px");
        denied.getStyle().set("font-weight", "bold");
        denied.getStyle().set("font-size", "26px");
        permission.getStyle().set("color", "#580000");
        permission.getStyle().set("margin", "40px 0px 0px 60px");
        permission.getStyle().set("font-weight", "bold");
        permission.getStyle().set("font-size", "21px");
        smile.getStyle().set("color", "#580000");
        smile.getStyle().set("margin", "10px 0px 0px 425px");
        smile.getStyle().set("font-weight", "bold");
        smile.getStyle().set("font-size", "60px");
        products.getStyle().set("color", "#696969");
        products.getStyle().set("margin", "170px 0px 20px 30px");
        products.getStyle().set("width", "145px");
    }
}
