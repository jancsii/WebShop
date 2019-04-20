package hu.szakdolgozat.webshop.WebShop.validation;

import java.util.ArrayList;

public class NewProductValidation {

    private ArrayList<String> errors = new ArrayList<>();

    public NewProductValidation() {}

    public ArrayList<String> newProductValidation(String productName, String productImage, String productCategory,
                                                   String productPrice, String productQuantity, String productDescription) {
        if (!(productName.trim().length() > 0) || !(productName.trim().length() <= 50))
            errors.add("Name must be between 1 and 50 characters long!");

        if (!(productImage.trim().length() > 0) || !(productImage.trim().length() <= 150))
            errors.add("Image must be between 1 and 150 characters long!");

        if (!(productCategory.trim().length() > 0) || !(productCategory.trim().length() <= 50))
            errors.add("Category must be between 1 and 50 characters long!");

        if (!(productPrice.length() > 0))
            errors.add("Price must be greater than 0!");

        if(!productPrice.matches("[0-9]+"))
            errors.add("Price must contain only numbers!");

        if(!(productQuantity.matches("[0-9]+")))
            errors.add("Quantity must contain only numbers!");

        if (!(productQuantity.length() > 0))
            errors.add("Quantity must be greater than 0!");

        if (!(productDescription.trim().length() > 0) || !(productDescription.trim().length() <= 250))
            errors.add("Description must be between 1 and 250 characters long!");

        return errors;
    }

    public boolean productModify(String productData, int minL, int maxL) {
        return (productData.trim().length() > minL && productData.trim().length() < maxL) ? true : false;
    }

    public boolean productModifyNum(String productData) {
        return (productData.trim().length() > 0 && productData.matches("[0-9]+")) ? true : false;
    }
}
