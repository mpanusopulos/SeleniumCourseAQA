package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;

import java.io.File;
import java.io.IOException;
public class TestListener implements ITestListener {

    private ExtentReports extent;

    @Override
    public void onStart(ITestContext context) {
        // Inicializar el reporte Extent y configurar la ruta del reporte
        extent = ExtentManager.createInstance("test-output/ExtentReport.html");
        System.out.println("Inicio del contexto de prueba: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Crear un nuevo test en el informe usando ExtentManager
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        ExtentManager.setTest(test);
        test.log(Status.INFO, "Inicio del test: " + result.getMethod().getMethodName());
        System.out.println("Inicio del test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Marcar el test como exitoso
        ExtentManager.getTest().log(Status.PASS, "El test pasó exitosamente.");
        System.out.println("Test exitoso: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Marcar el test como fallido y agregar el mensaje de error
        ExtentManager.getTest().log(Status.FAIL, "El test falló: " + result.getThrowable().getMessage());
        System.out.println("Test fallido: " + result.getMethod().getMethodName());

        // Obtener la ruta de la captura de pantalla
        String screenshotPath = captureScreenshot(DriverManager.getDriver(), result.getMethod().getMethodName());

        if (screenshotPath != null) {
            // Agregar la captura de pantalla al informe
            // Usamos la ruta relativa a la carpeta de los informes
            String relativePath = "screenshots/" + screenshotPath; // Ruta relativa
            ExtentManager.getTest().fail("Captura de pantalla del error: ",
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
        } else {
            ExtentManager.getTest().log(Status.FAIL, "No se pudo tomar la captura de pantalla.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Marcar el test como omitido
        ExtentManager.getTest().log(Status.SKIP, "El test fue omitido.");
        System.out.println("Test omitido: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Finalizar el reporte de Extent
        extent.flush();
        System.out.println("Fin del contexto de prueba: " + context.getName());
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        // Capturar la imagen de la pantalla
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

        // Usar un identificador único para evitar conflictos entre pruebas en paralelo
        long threadId = Thread.currentThread().getId();
        String uniqueScreenshotName = screenshotName + "_" + threadId;

        // Directorio para guardar las capturas (asegurarse de que esté en la ruta correcta)
        String screenshotDir = System.getProperty("user.dir") + "/test-output/screenshots/" + threadId + "/";
        File screenshotFolder = new File(screenshotDir);

        // Crear el directorio si no existe
        if (!screenshotFolder.exists()) {
            if (!screenshotFolder.mkdirs()) {
                System.err.println("No se pudo crear el directorio para capturas de pantalla.");
                return null;
            }
        }

        // Crear el archivo de destino con el nombre único
        File destinationFile = new File(screenshotDir + uniqueScreenshotName + ".png");

        try {
            // Copiar el archivo de la captura al directorio de destino
            FileUtils.copyFile(srcFile, destinationFile);
            System.out.println("Captura de pantalla guardada en: " + destinationFile.getAbsolutePath());
            return threadId + "/" + uniqueScreenshotName + ".png"; // Devolver la ruta relativa
        } catch (IOException e) {
            System.err.println("Error al guardar la captura de pantalla: " + e.getMessage());
            return null;
        }
    }
}
