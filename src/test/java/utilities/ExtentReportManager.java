package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    // ThreadLocal to maintain separate ExtentTest instances per thread
    private static ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>();
    private static ExtentReports extent;
    private static String reportPath;

    // Initialize ExtentReports with configuration
    static {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timeStamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Team", "SDA");
    }

    // Private constructor to prevent instantiation
    private ExtentReportManager() {
    }

    // Create a test in the report
    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTestThread.set(test);
    }

    // Log test steps
    public static void log(Status status, String message) {
        if (extentTestThread.get() != null) {
            extentTestThread.get().log(status, message);
        }
    }

    // Log test results with screenshot on failure
    public static void logResult(ITestResult result) {
        ExtentTest test = extentTestThread.get();
        if (test == null) return;

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                test.log(Status.PASS, "Test passed");
                break;
            case ITestResult.FAILURE:
                test.log(Status.FAIL, "Test failed: " + result.getThrowable());
                // Capture screenshot on failure
                String screenshotPath = CaptureScreenshot.takeScreenshot(Driver.getDriver(), result.getName());
                test.addScreenCaptureFromPath(screenshotPath);
                break;
            case ITestResult.SKIP:
                test.log(Status.SKIP, "Test skipped");
                break;
            default:
                test.log(Status.INFO, "Test ended with status: " + result.getStatus());
        }
    }

    // Flush the report
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Get the current ExtentTest instance
    public static ExtentTest getTest() {
        return extentTestThread.get();
    }
}