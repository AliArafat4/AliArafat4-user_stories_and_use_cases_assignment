package utilities;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

public class MyTestListener implements ITestListener, IRetryAnalyzer, IAnnotationTransformer {

    private static final int maxRetryCount = 0; // NUMBER OF RETRIES
    // Automatically retries failed test scenarios one more time
    // IRetryAnalyzer method is implemented
    // This method will be automatically invoked to RETRY FAILED TEST SCENARIOS
    private int retryCount = 0;

    @Attachment(value = "Failed Screen", type = "image/png")
    public static byte[] addFailedScreenshot() {
        return ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " started.");
        ExtentReportManager.createTest(this.getClass().getSimpleName() + " - " + result.getMethod().getMethodName());
        ExtentReportManager.log(Status.INFO, "Test started at: " + LocalTime.now());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getName() + " is SUCCESS!");
        ExtentReportManager.log(Status.INFO, "Test finished at: " + LocalTime.now());
        ExtentReportManager.logResult(result);
        ExtentReportManager.flushReport();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getName() + " is FAILED!!!");
        ExtentReportManager.log(Status.INFO, "Test finished at: " + LocalTime.now());
        ExtentReportManager.logResult(result);
        ExtentReportManager.flushReport();
        addFailedScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getName() + " is SKIPPED!!!");
        ExtentReportManager.log(Status.INFO, "Test finished at: " + LocalTime.now());
        ExtentReportManager.logResult(result);
        ExtentReportManager.flushReport();
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test started at: " + context.getStartDate());

        Path allureResults = Paths.get("allure-results");

        if (Files.exists(allureResults)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(allureResults)) {
                for (Path entry : stream) {
                    Files.deleteIfExists(entry);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test finished at: " + context.getEndDate());
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder().command("cmd.exe", "/c", "allure serve").start();//Windows
            } else {
                new ProcessBuilder().command("bash", "-c", "allure serve").start();//Mac - Linux
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Driver.closeDriver();
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }

    // IAnnotationTransformer method is added
    // This automatically retries failed test scenarios using TestNG XML files
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(MyTestListener.class);
    }

}