package hu.szakdolgozat.webshop.WebShop;

import com.vaadin.flow.server.VaadinServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServlet;

@SpringBootApplication
public class WebShopApplication extends VaadinServlet {

	public static void main(String[] args) {
		SpringApplication.run(WebShopApplication.class, args);
	}
}
