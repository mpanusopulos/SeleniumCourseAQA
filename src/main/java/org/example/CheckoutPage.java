package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import driver.DriverManager;

public class CheckoutPage {
    private WebDriver driver;

    // Definir los localizadores que se encuentran en la pagina del checkout
    private By firstNameField = By.xpath("//input[@name='billing_first_name']");
    private By lastNameField = By.xpath("//input[@name='billing_last_name']");
    private By countryDropdown = By.id("billing_country");
    private By proceedToCheckoutBtn = By.xpath("//a[@class='checkout-button button alt wc-forward']");


    // Constructor
    public CheckoutPage(){
        this.driver = DriverManager.getDriver();
    }

    public void enterFirstName(String firstName){
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName){
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void selectCountry(String country){
        WebElement dropdown = driver.findElement(countryDropdown);
        Select select = new Select(dropdown);
        select.selectByVisibleText(country);
    }

    public void proceedToCheckout(){
        driver.findElement(proceedToCheckoutBtn).click();
    }

}
