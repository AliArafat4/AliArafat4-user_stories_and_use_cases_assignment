package tests;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;
import pages.RegistrationPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ExtentReportManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

@Listeners(utilities.MyTestListener.class)

public class US05_JobSelectionAndCVUploadTestCases {

    /**
     * User Story 5: Job Selection and CV Upload
     */

    /*
    As a job applicant
    I want to specify my job role and upload my CV
    So that my professional information is included in my registration

    Acceptance Criteria:
    - Job dropdown includes options: Developer, Tester, Designer, Manager, Other
    - Job field displays "Select a job" as default placeholder
    - Job selection is required
    - CV upload field accepts only PDF files
    - CV field shows "CV (PDF only)" label
    - Both Job and CV fields are displayed side-by-side (48% width each)
    - CV upload is required for form submission
    - System validates that uploaded file has .pdf extension
     */

    RegistrationPage registrationPage;

    String ssn = "123-45-6789";
    String firstName = "Name";
    String lastName = "LastName";
    String gender = "Male";
    String jobOption = "Tester";
    String sysPath = "user.dir";
    String folderName = "resources";
    String fileName = "test.pdf";
    String fileNameWrongFormat = "wrong format.png";
    String fileWrongPath = "";
    String username = "username";
    String email = "email@email.com";
    String password = "ABCDefg12";

    @BeforeClass
    public void beforeClass() {
        ExtentReportManager.createTest(this.getClass().getSimpleName());

    }

    @BeforeMethod
    public void setUp() {
        registrationPage = new RegistrationPage();
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that job dropdown contains correct options")
    public void TC01_validateJobDeopDownOptions() {
        ArrayList<String> jobOptions = new ArrayList<String>(Arrays.asList("Select a job", "Developer", "Tester", "Designer", "Manager", "Other"));
        registrationPage.assertJobDropDownOptions(jobOptions);

    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that job dropdown default option is 'Select a job'")
    public void TC02_validateJobDeopDownDefaultOption() {
        registrationPage.assertDefaultJobDropDownOption("Select a job");

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that job selection is required")
    public void TC03_jobEmptyTest() throws AWTException {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .uploadCv(sysPath, folderName, fileName)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton()
                .jobFieldValidator("Please select an item in the list.");

        ExtentReportManager.log(Status.PASS, "Job selection is required validation passed.");

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that CV upload field accepts only PDF files")
    public void TC04_validateCVAcceptType() {
        registrationPage.assertCVFileFormat(".pdf");
        ExtentReportManager.log(Status.PASS, "CV upload field accepts only PDF files validation passed.");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that uploading a non-PDF file shows an error message")
    public void TC05_cvWrongFormatTest() throws AWTException {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileNameWrongFormat)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton()
                .assertAlert("Please select a PDF file for CV.");
        ExtentReportManager.log(Status.PASS, "Non-PDF file upload validation passed.");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that uploading a file from a wrong path shows an error message")
    public void TC06_cvWrongPathTest() throws AWTException {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileWrongPath)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton()
                .assertAlert("Please select a PDF file for CV.");
        ExtentReportManager.log(Status.PASS, "Wrong path file upload validation passed.");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that CV upload is required")
    public void TC07_cvNotFilledTest() {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton()
                .cvFieldValidator("Please select a file.");
        ExtentReportManager.log(Status.PASS, "CV upload is required validation passed.");
    }
}