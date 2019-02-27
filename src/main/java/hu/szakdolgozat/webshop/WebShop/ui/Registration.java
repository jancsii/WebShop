package hu.szakdolgozat.webshop.WebShop.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Collectors;

@Route("")
public class Registration extends VerticalLayout {

    @Autowired
    UserService userService;

    //private TextField name = new TextField("Name");
    //private PasswordField password = new PasswordField("Password");
    //private TextField address = new TextField("Address");
    //private TextField email = new TextField("Email");
    //private User user = new User();

    FormLayout layoutWithBinder = new FormLayout();
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField userName = new TextField();
    private PasswordField password = new PasswordField();
    private TextField address = new TextField();
    private TextField email = new TextField();
    Label infoLabel = new Label();
    Button save = new Button("Save");
    Button reset = new Button("Reset");
    NativeButton button = new NativeButton("Login");
    private User user = new User();
    HorizontalLayout actions = new HorizontalLayout();

    public Registration() {

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

        save.getElement().setAttribute("theme", "primary");
        actions.add(save, reset);

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
                        "Incorrect address",10,50))
                .bind(User::getAddress, User::setAddress);

        //save.addClickListener(event -> userService.save(user));
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(user)) {
                infoLabel.setText("Saved bean values: " + user);
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
        });

        reset.addClickListener(event -> {
            binder.readBean(null);
            infoLabel.setText("");
        });

        button.addClickListener( e-> {
                    button.getUI().ifPresent(ui -> ui.navigate("login"));
                });

        add(layoutWithBinder, infoLabel, actions, button);
    }
}
