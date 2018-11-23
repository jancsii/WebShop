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

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private PasswordField password = new PasswordField("Password");
    private User user = new User();
    NativeButton button = new NativeButton("Go to Products");
    NativeButton testButton = new NativeButton("Test");

    public Login() {


        /*String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath +
                "/WEB-INF/images/videocard1.png"));

        Image image = new Image("",resource);
        addComponent(image);
        /*setSizeFull();

        Button button = new Button("Go to Main View",
                (Button.ClickListener) event -> navigator.navigateTo(MAINVIEW));
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        */



    }

    @PostConstruct
    public void init()
    {


        //user = userService.findByFirstName("Proba");//(firstName.getValue());
        List<User> users = new ArrayList<>();
        users=(List)userService.getAllUsers();

        //System.out.println(user);
        System.out.println(users);

        //if(!firstName.getValue().isEmpty() && !lastName.getValue().isEmpty() && !password.getValue().isEmpty()) {
            testButton.addClickListener(e -> {
            testUser();
            //} else {
            //System.out.println("Nope");
            //}
            });
        //}

        //if(user.getFirstName()!=null && user.getPassword().equals(String.valueOf(password)))
        //{
            button.addClickListener( event-> {
                button.getUI().ifPresent(ui -> ui.navigate("products"));
            });
            button.setVisible(false);
        //}
        //else
        //{
            //dialog.open();
        //}


        add(firstName, lastName, password, testButton, button);


    }

    private void testUser() {

        user = userService.findByFirstName(firstName.getValue());

        if (user!=null) {
            if(user.getLastName().equals(lastName.getValue()) && user.getPassword().equals(password.getValue())) {
                System.out.println("Nice");
                System.out.println(user.getFirstName() + user.getLastName() + user.getPassword());
                System.out.println(firstName.getValue() + lastName.getValue() + password.getValue());
                button.setVisible(true);
                String sessionID = ((VaadinServletRequest) VaadinService.getCurrentRequest())
                        .getHttpServletRequest().getSession().getId();

            }
        } else {
            System.out.println("Nope");
        }
    }
}
