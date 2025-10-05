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

public class US07_PasswordValidationOnSubmitTestCases {

    /**
     * User Story 7: Password Creation with Strength Indicator
     */

    /*
    As a user
    I want to create a secure password and see its strength in real-time
    So that I can ensure my account is protected

    Acceptance Criteria:
     - Password field is required and masked (type="password")
     - Minimum password length is 8 characters
     - Password strength meter displays below the password field
     - Strength indicator updates in real-time as user types
     - Strength is calculated based on:
     - Length ≥ 8 characters (1 point)
     - Contains uppercase letter (1 point)
     - Contains lowercase letter (1 point)
     - Contains digit (1 point)
     - Progress bar shows strength level from 0 to 4
     - Placeholder text reads "Enter your password"
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
    String shortPassword = " ";

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
    @Description("check if password field is required and masked")
    public void TC01_passwordIsRequiredAndMaskedTest() {
        ExtentReportManager.log(Status.INFO, "Checking if password field is required and masked");

        registrationPage.assertPasswordFileIsMasked("password")
                .assertPasswordFileIsRequired();
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that password must be at least 8 characters")
    public void TC02_shortPasswordTest() throws AWTException {
        ExtentReportManager.log(Status.INFO, "Testing short password submission");

        registrationPage.AssertFieldsDisplayed()
                .enterSsn(ssn)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .selectGender(gender)
                .SelectJob(jobOption)
                .uploadCv(sysPath, folderName, fileName)
                .enterUsername(username)
                .enterEmail(email)
                .enterPassword(shortPassword)
                .clickRegisterButton()
                .passwordFieldValidator("Please lengthen this text to 8 characters or more");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that password strength indicator is below password field")
    public void TC03_strengthLocationTest() {
        registrationPage.isPasswordStrengthLocationBelowPassword();
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verify that password strength indicator updates correctly from 0 to 4")
    public void TC04_strengthIndicatorProgressTest() {
        ExtentReportManager.log(Status.INFO, "Checking if password strength updated on user input correctly");

        registrationPage.getStrengthIndicatorProgress("0")
                .enterPassword("A")
                .getStrengthIndicatorProgress("1")
                .enterPassword("Ab")
                .getStrengthIndicatorProgress("2")
                .enterPassword("Ab123")
                .getStrengthIndicatorProgress("3")
                .enterPassword("Ab123456")
                .getStrengthIndicatorProgress("4");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that password field has correct placeholder text")
    public void TC05_placeholderTextTest() {
        registrationPage.assertPasswordPlaceholderText("Enter your password");
    }


}