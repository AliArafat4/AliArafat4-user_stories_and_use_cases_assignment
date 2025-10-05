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


@Listeners(utilities.MyTestListener.class)

public class US08_PasswordValidationOnSubmitTestCases {


    /**
     * User Story 8: Password Validation On Submit
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
    String username = "username";
    String email = "email@email.com";
    String password = "ABCDefg12";
    String onlyNumbersPassword = "123456789";
    String onlyLowerCaseCharactersPassword = "abcdefghi";
    String onlyUpperCaseCharactersPassword = "ABCDEFGHI";

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

    @Severity(SeverityLevel.CRITICAL)
    @Description("This is the login function.")
    @Test
    public void TC01_positiveRegistrationTest() throws AWTException {
        ExtentReportManager.log(Status.INFO, "Filling out the form with valid data and submitting it.");

        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileName)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton()
                .assertSuccessfulRegister();

        ExtentReportManager.log(Status.PASS, "Form has been submitted:");
    }

    @Test
    public void TC02_emptyPasswordTest() throws AWTException {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileName)
                .enterUsername(username)
                .enterEmail(email)
                .clickRegisterButton()
                .passwordFieldValidator("Please fill in this field.");
    }

    @Test
    public void TC03_onlyNumbersPasswordTest() throws AWTException {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileName)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(onlyNumbersPassword)
                .clickRegisterButton()
                .assertAlert("Password must contain at least 8 characters including at least one uppercase letter, one lowercase letter, and one digit.");
    }

    @Test
    public void TC04_onlyLowerCaseCharactersPasswordTest() throws AWTException {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileName)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(onlyLowerCaseCharactersPassword)
                .clickRegisterButton()
                .assertAlert("Password must contain at least 8 characters including at least one uppercase letter, one lowercase letter, and one digit.");
    }

    @Test
    public void TC05_onlyUpperCaseCharactersPasswordTest() throws AWTException {
        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileName)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(onlyUpperCaseCharactersPassword)
                .clickRegisterButton()
                .assertAlert("Password must contain at least 8 characters including at least one uppercase letter, one lowercase letter, and one digit.");
    }
}