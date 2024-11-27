package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import driver.DriverManager;

public class HomePage {
    private WebDriver driver;

    // Localizadores que vamos a utilizar en el HomePage.
    private By addToCartButton = By.xpath("(//a[@class='button product_type_simple add_to_cart_button ajax_add_to_cart'])[1]");
    private By viewCartButton = By.xpath("//a[@title='View cart']");


    // Constructor
    public HomePage(){
        this.driver = DriverManager.getDriver();
    }

    public void addProductToCart(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        driver.findElement(addToCartButton).click();
    }

    public CheckoutPage goToCart(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton));
        driver.findElement(viewCartButton).click();
        return new CheckoutPage();
    }

}
