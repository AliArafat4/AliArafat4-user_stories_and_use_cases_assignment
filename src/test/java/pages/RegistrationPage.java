package pages;

import com.aventstack.extentreports.Status;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ExtentReportManager;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class RegistrationPage extends BasePage {

    /*
    Weekend Homework Assignment:
    URL: https://claruswaysda.github.io/Registration.html

    You will be provided with user stories and acceptance criteria for the registration form above. Your tasks are as follows:
    - Select any two user stories from the provided list
    - Write test cases based on the selected user stories and their acceptance criteria
    - Create an automated test framework to execute these test cases

    Execute your test cases and generate:
    - Extent Report
    - Allure Report
    - Bug Report (if you find any defects during testing)

    Deliverables:
    - Test cases documentation
    - Automated test framework code
    - Extent and Allure reports
    - Bug report (if applicable)

    Submission:
    - Push your completed project to your GitHub account
    - Share the GitHub repository link with me

    */

    By ssnBy = By.id("ssn");
    By firstnameBy = By.id("firstName");
    By lastNameBy = By.id("lastName");
    By maleBy = By.xpath("//label[@for='male']");
    By femaleBy = By.xpath("//label[@for='female']");
    By otherBy = By.xpath("//label[@for='other']");
    By jobSelectBy = By.id("job");
    By cvBy = By.id("cv");
    By usernameBy = By.id("username");
    By emailBy = By.id("email");
    By passwordBy = By.id("password");
    By passwordStrengthBy = By.id("passwordStrength");
    By registerButtonBy = By.xpath("//button[@type='submit']");


    public WebElement ssnByField() {
        return safeFindElement(ssnBy);
    }

    public WebElement firstNameField() {
        return safeFindElement(firstnameBy);
    }

    public WebElement lastNameField() {
        return safeFindElement(lastNameBy);
    }

    public WebElement maleByField() {
        return safeFindElement(maleBy);
    }

    public WebElement femaleByField() {
        return safeFindElement(femaleBy);
    }

    public WebElement otherByField() {
        return safeFindElement(otherBy);
    }

    public WebElement jobSelectByField() {
        return safeFindElement(jobSelectBy);
    }

    public WebElement cvByField() {
        return safeFindElement(cvBy);
    }

    public WebElement usernameByField() {
        return safeFindElement(usernameBy);
    }

    public WebElement emailByField() {
        return safeFindElement(emailBy);
    }

    public WebElement passwordByField() {
        return safeFindElement(passwordBy);
    }

    public WebElement passwordStrengthByField() {
        return safeFindElement(passwordStrengthBy);
    }

    public WebElement registerButton() {
        return safeFindElement(registerButtonBy);
    }


    @Test
    public RegistrationPage AssertFieldsDisplayed() {
        Assert.assertTrue(isElementDisplayed(ssnByField()));
        Assert.assertTrue(isElementDisplayed(firstNameField()));
        Assert.assertTrue(isElementDisplayed(lastNameField()));
        Assert.assertTrue(isElementDisplayed(maleByField()));
        Assert.assertTrue(isElementDisplayed(femaleByField()));
        Assert.assertTrue(isElementDisplayed(otherByField()));
        Assert.assertTrue(isElementDisplayed(jobSelectByField()));
        Assert.assertTrue(isElementDisplayed(cvByField()));
        Assert.assertTrue(isElementDisplayed(usernameByField()));
        Assert.assertTrue(isElementDisplayed(emailByField()));
        Assert.assertTrue(isElementDisplayed(passwordByField()));
        Assert.assertTrue(isElementDisplayed(registerButton()));
        return this;
    }

    public RegistrationPage enterSsn(String ssn) {
        safeSendKeys(ssnByField(), ssn);
        return this;
    }

    public RegistrationPage enterFirstName(String firstName) {
        safeSendKeys(firstNameField(), firstName);
        return this;
    }

    public RegistrationPage enterLastName(String lastName) {
        safeSendKeys(lastNameField(), lastName);
        return this;
    }

    public RegistrationPage selectGender(String gender) {
        switch (gender.toLowerCase()) {
            case "male" -> safeClick(maleByField());
            case "female" -> safeClick(femaleByField());
            case "other" -> safeClick(otherByField());
        }
        return this;
    }

    public RegistrationPage SelectJob(String job) {
        Select select = new Select(jobSelectByField());
        select.selectByVisibleText(job);
        return this;
    }

    public RegistrationPage uploadCv(String sysPath, String folder, String pdfFile) throws AWTException {

        Path filePath = Path.of(System.getProperty(sysPath), folder, pdfFile);
        String fileSTR = filePath.toString();

        safeSendKeys(cvByField(), fileSTR);

        return this;
    }

    public RegistrationPage enterUsername(String username) {
        safeSendKeys(usernameByField(), username);
        return this;
    }

    public RegistrationPage enterEmail(String email) {
        safeSendKeys(emailByField(), email);
        return this;
    }

    public RegistrationPage enterPassword(String password) {
        safeSendKeys(passwordByField(), password);
        return this;
    }

    public RegistrationPage isPasswordStrengthLocationBelowPassword() {

        int strongPassY = passwordStrengthByField().getLocation().getY();
        int passwordY = passwordByField().getLocation().getY();
        Assert.assertTrue(strongPassY > passwordY);

        return this;
    }

    public RegistrationPage clickRegisterButton() {
        safeClick(registerButton());

        return this;
    }

    public RegistrationPage getStrengthIndicatorProgress(String value) {
        Assert.assertEquals(value, passwordStrengthByField().getAttribute("value"));

        return this;
    }

    // ------------------------------- verifications -------------------------------

    public RegistrationPage assertJobDropDownOptions(ArrayList<String> expectedOptions) {
        Select select = new Select(jobSelectByField());

        select.getOptions().forEach((option) -> {
            try {
                Assert.assertTrue(expectedOptions.contains(option.getText()));
            } catch (AssertionError e) {
                ExtentReportManager.log(Status.FAIL, "Option not found: " + option.getText());
                throw e;
            }
        });
        ExtentReportManager.log(Status.PASS, "job options checked, " + expectedOptions);

        return this;
    }

    public RegistrationPage assertDefaultJobDropDownOption(String expectedOption) {
        Select select = new Select(jobSelectByField());
        try {
            Assert.assertEquals(expectedOption, select.getFirstSelectedOption().getText());
            ExtentReportManager.log(Status.PASS, "default option checked");

        } catch (AssertionError e) {
            ExtentReportManager.log(Status.FAIL, "Default option is not as expected. Expected: " + expectedOption + "Found: " + select.getFirstSelectedOption().getText());
            throw e;
        }

        return this;
    }

    public RegistrationPage assertCVFileFormat(String expectedFormat) {
        String acceptValue = cvByField().getAttribute("accept");
        Assert.assertEquals(expectedFormat, acceptValue);

        return this;
    }

    public RegistrationPage assertPasswordFileIsRequired() {
        String acceptValue = passwordByField().getAttribute("required");

        Assert.assertNotNull(acceptValue);

        return this;
    }

    public RegistrationPage assertPasswordFileIsMasked(String expectedFormat) {
        String acceptValue = passwordByField().getAttribute("type");

        //if the type is password, password is masked
        Assert.assertEquals(expectedFormat, acceptValue);

        return this;
    }

    public RegistrationPage assertPasswordPlaceholderText(String expectedFormat) {
        String acceptValue = passwordByField().getAttribute("placeholder");

        //if the type is password, password is masked
        Assert.assertEquals(expectedFormat, acceptValue);

        return this;
    }

    public RegistrationPage assertSuccessfulRegister() {
        Alert alert = Driver.getDriver().switchTo().alert();
        Assert.assertTrue(alert.getText().contains("Form submitted!"));
        alert.accept();
        return this;
    }

    public RegistrationPage assertAlert(String expectedMessage) {
        Alert alert = Driver.getDriver().switchTo().alert();
        Assert.assertTrue(alert.getText().contains(expectedMessage));
        alert.accept();
        return this;
    }

    public void passwordFieldValidator(String expectedMessage) {
        jsExecutorValidator(passwordBy, expectedMessage);
    }

    public void jobFieldValidator(String expectedMessage) {
        jsExecutorValidator(jobSelectBy, expectedMessage);
    }

    public void cvFieldValidator(String expectedMessage) {
        jsExecutorValidator(cvBy, expectedMessage);
    }

    // ------------------------------- verifications -------------------------------
}