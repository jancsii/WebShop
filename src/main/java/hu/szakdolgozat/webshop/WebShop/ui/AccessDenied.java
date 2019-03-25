package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route("denied")
public class AccessDenied extends VerticalLayout {

    Label label = new Label("Access Denied!!!");

    public AccessDenied() {}

    @PostConstruct
    public void init(){
        add(label);
    }
}
