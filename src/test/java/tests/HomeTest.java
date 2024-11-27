package tests;

import com.github.javafaker.Faker;
import org.example.CheckoutPage;
import org.example.HomePage;
import driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomeTest {
    private HomePage homePage;
    private CheckoutPage checkoutPage;
    // Esto es una pruebas
    @BeforeMethod
    public void setUp(){
        // Asegúrate de que el controlador se inicialice correctamente
        DriverManager.getDriver().get("http://logofree.esy.es/");
        homePage = new HomePage();
        checkoutPage = new CheckoutPage(); // Asegúrate de inicializar ambas páginas
    }


    @Test
    public void testGoToCheckout(){
        Faker faker = new Faker();
        String nombre = String.valueOf(faker.name());

        String email = faker.internet().emailAddress();
        String telefono = String.valueOf(faker.phoneNumber());
        String direccion = faker.address().streetAddress();

        // Agregamos el producto al carrito
        homePage.addProductToCart();

        // Vamos al Carrito
        checkoutPage = homePage.goToCart();

        // Procedemos al checkout
        checkoutPage.proceedToCheckout();

        // Completamos el formulario con los datos de usuario
        checkoutPage.enterFirstName(nombre);
        checkoutPage.enterLastName("asdadsadsads");

        // Seleccionamos el país (por ejemplo, "Armenia" como ejemplo, puede estar parametrizado)
        checkoutPage.selectCountry("Armenia");
    }
    @Test
    public void testGoToCheckout1(){
        // Agregamos el producto al carrito
        homePage.addProductToCart();

        // Vamos al Carrito
        checkoutPage = homePage.goToCart();

        // Procedemos al checkout
        checkoutPage.proceedToCheckout();

        // Completamos el formulario con los datos de usuario
        checkoutPage.enterFirstName("asdasd");
        checkoutPage.enterLastName("asdadsadsads");

        // Seleccionamos el país (por ejemplo, "Armenia" como ejemplo, puede estar parametrizado)
        checkoutPage.selectCountry("Armenia");
    }

    @AfterMethod
    public void tearDown(){
        // Cerramos el navegador después de cada prueba
        DriverManager.quitDriver();
    }
}
