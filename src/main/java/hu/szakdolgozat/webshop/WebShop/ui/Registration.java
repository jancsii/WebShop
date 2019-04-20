package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import hu.szakdolgozat.webshop.WebShop.Names;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(Names.REGISTRATION)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@UIScope
public class Registration extends VerticalLayout {

    @Autowired
    UserService userService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    FormLayout layoutWithBinder = new FormLayout();
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField userName = new TextField();
    private PasswordField password = new PasswordField();
    private TextField address = new TextField();
    private TextField email = new TextField();
    Label infoLabel = new Label();
    Button save = new Button("Sign up");
    Button reset = new Button("Reset");
    Button login = new Button("Login");
    private User user = new User();
    HorizontalLayout form = new HorizontalLayout();
    HorizontalLayout actions = new HorizontalLayout();
    private boolean isAlreadyRegistered = false;

    Notification registrationNotification = new Notification(
            "Username already exists!", 2000, Notification.Position.TOP_CENTER);

    public Registration() {
    }

    @PostConstruct
    public void init() {

        firstName.focus();
        Binder<User> binder = new BeanValidationBinder<>(User.class);  //new Binder<>();
        binder.bindInstanceFields(this);
        binder.setBean(user);


        firstName.setValueChangeMode(ValueChangeMode.EAGER);

        lastName.setValueChangeMode(ValueChangeMode.EAGER);

        userName.setValueChangeMode(ValueChangeMode.EAGER);

        password.setValueChangeMode(ValueChangeMode.EAGER);

        address.setValueChangeMode(ValueChangeMode.EAGER);

        email.setValueChangeMode(ValueChangeMode.EAGER);


        layoutWithBinder.addFormItem(firstName, "First name");
        layoutWithBinder.addFormItem(lastName, "Last name");
        layoutWithBinder.addFormItem(userName, "Username");
        layoutWithBinder.addFormItem(password, "Password");
        layoutWithBinder.addFormItem(address, "Address");
        layoutWithBinder.addFormItem(email, "E-mail");

        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);
        userName.setRequiredIndicatorVisible(true);
        password.setRequiredIndicatorVisible(true);
        email.setRequiredIndicatorVisible(true);
        address.setRequiredIndicatorVisible(true);

        binder.forField(firstName)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 2, 25))
                .bind(User::getFirstName, User::setFirstName);
        binder.forField(lastName)
                .withValidator(new StringLengthValidator(
                        "Please add the last name", 2, 25))
                .bind(User::getLastName, User::setLastName);
        binder.forField(userName)
                .withValidator(new StringLengthValidator(
                        "Username length must be min 4 max 25",  4,25))
                .bind(User::getUserName, User::setUserName);
        binder.forField(password)
                .withValidator(new StringLengthValidator(
                        "Password length must be min 7 and max 25", 7, 25))
                .bind(User::getPassword, User::setPassword);
        binder.forField(email)
                .withValidator(new EmailValidator(
                        "Incorrect email address"))
                .bind(User::getEmail, User::setEmail);
        binder.forField(address)
                .withValidator(new StringLengthValidator(
                        "Incorrect address(Min length:10)",10,50))
                .bind(User::getAddress, User::setAddress);

        //save.addClickListener(event -> userService.save(user));
        save.addClickListener(event -> {
            ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
            for(int i=0; i<users.size(); i++)
            {
                if(userName.getValue().trim().equals(users.get(i).getUserName()))
                    isAlreadyRegistered = true;
            }

            if(!isAlreadyRegistered) {
                if (binder.writeBeanIfValid(user)) {
                    infoLabel.setText("Thank you for sign up");
                    user.setPassword(passwordEncoder.encode(password.getValue()));
                    user.setUserName(userName.getValue().trim());
                    userService.save(user);
                } else {
                    BinderValidationStatus<User> validate = binder.validate();
                    String errorText = validate.getFieldValidationStatuses()
                            .stream().filter(BindingValidationStatus::isError)
                            .map(BindingValidationStatus::getMessage)
                            .map(Optional::get).distinct()
                            .collect(Collectors.joining(", "));
                    infoLabel.setText("There are errors: " + errorText);
                }
            }
            else {
                registrationNotification.open();
                isAlreadyRegistered = false;
            }
        });

        reset.addClickListener(event -> {
            binder.readBean(null);
            infoLabel.setText("");
        });

        login.addClickListener( e-> {
            login.getUI().ifPresent(ui -> ui.navigate(Names.LOGIN));
        });

        form.add(layoutWithBinder);

        style();

        add(form, infoLabel, reset, actions);
    }

    public void style() {
        this.getStyle().set("width", "700px");
        this.getStyle().set("margin", "0px auto");

        save.getElement().setAttribute("theme", "primary");
        login.getElement().setAttribute("theme", "primary");

        //infoLabel.getStyle().set("margin-left", "50px");
        infoLabel.getStyle().set("width", "650px");
        infoLabel.getStyle().set("color", "#F6544C");
        reset.getStyle().set("margin-left", "595px");
        save.getStyle().set("margin-right", "30px");

        form.getStyle().set("margin-top", "40px");

        actions.add(save, login);
        actions.getStyle().set("margin", "40px 0px 0px 285px");
    }
}
