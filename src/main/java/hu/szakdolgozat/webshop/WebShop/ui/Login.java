package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Route("login")
public class Login extends VerticalLayout {

    @Autowired
    UserService userService;

    private TextField userName = new TextField("Username");
    //private TextField lastName = new TextField("Last name");
    private PasswordField password = new PasswordField("Password");
    private User user = new User();
    NativeButton button = new NativeButton("Go to Products");
    NativeButton testButton = new NativeButton("Test");

    public Login() {

    }

    @PostConstruct
    public void init()
    {
        List<User> users = new ArrayList<>();
        users=(List)userService.getAllUsers();

        System.out.println(users);

        testButton.addClickListener(e -> {
            testUser();
            });

        button.addClickListener( event-> {
            button.getUI().ifPresent(ui -> ui.navigate("products"));
            });
        button.setVisible(false);

        add(userName, password, testButton, button);
    }

    private void testUser() {

        user = userService.findByUserName(userName.getValue());

        if (user!=null) {
            if(user.getUserName().equals(userName.getValue()) && user.getPassword().equals(password.getValue())) {
                System.out.println("Nice");
                System.out.println(user.getFirstName() + user.getLastName() + user.getUserName() + user.getPassword());
                System.out.println(userName.getValue() + password.getValue());
                button.setVisible(true);
                //String sessionID = ((VaadinServletRequest) VaadinService.getCurrentRequest())
                        //.getHttpServletRequest().getSession().getId();

            }
        } else {
            System.out.println("Nope");
        }
    }
}
