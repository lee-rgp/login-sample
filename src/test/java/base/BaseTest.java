package base;

import com.aventstack.extentreports.ExtentTest;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ApiClient;
import utils.ExtentTestManager;
import utils.JsonParser;
import utils.OTPGenerator;

import java.util.Objects;

import static org.testng.AssertJUnit.assertEquals;

public class BaseTest extends BasePage {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected String accessToken = "";

    @BeforeMethod
    public void setUp(ITestResult result) {
        initDriver();
    }

    @AfterMethod
    public void tearDown() {
        quitDriver();
    }

    protected void initDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Enable headless mode for CI/CD
        String headless = System.getProperty("headless");
        if ("true".equals(headless)) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        driver.set(new ChromeDriver(options));
        getDriver().manage().window().maximize();
    }

    protected void quitDriver() {
        if (getDriver() != null) {
            // Sleep 3s
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            getDriver().quit();
            driver.remove();
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    protected ExtentTest getExtentTest() {
        return ExtentTestManager.getTest();
    }

    public String getOtpCode(String secretKey) throws CodeGenerationException {
        OTPGenerator otpGenerator = new OTPGenerator();
        return otpGenerator.generateOTP(secretKey);
    }

    public String getAuthLoginInfo(String baseUrl, String email, String oldPassword, String newPassword) throws Exception {
        ExtentTest extentTest = getExtentTest();
        ApiClient apiClient = new ApiClient();
        String authLoginInfo = apiClient.login(baseUrl, email, oldPassword);
        if (JsonParser.getValue(authLoginInfo, "success").equals("false")) {
            authLoginInfo = apiClient.login(baseUrl, email, newPassword);
        }
        this.accessToken = getAccessToken(authLoginInfo);
        extentTest.info("Access Token: " + accessToken);
        return authLoginInfo;
    }

    public String getAccessToken(String jsonResponse) {
        return JsonParser.getNestedValue(jsonResponse, "data", "accessToken");
    }

    public String getAuthLogoutInfo(String baseUrl, String accessToken) throws Exception {
        ExtentTest extentTest = getExtentTest();
        ApiClient apiClient = new ApiClient();
        String authLogoutInfo = apiClient.logout(baseUrl, accessToken);
        extentTest.info("authLogoutInfo: " + authLogoutInfo);
        return authLogoutInfo;
    }

    public String getMeInfo(String baseUrl, String accessToken) throws Exception {
        ApiClient apiClient = new ApiClient();
        return apiClient.getMe(baseUrl, accessToken);
    }

    public String getMailOtpsInfo(String baseUrl, String accessToken) throws Exception {
        ApiClient apiClient = new ApiClient();
        return apiClient.getEmailOtps(baseUrl, accessToken);
    }

    public String getTotpSecretKey(String baseUrl, String accessToken) throws Exception {
        ExtentTest extentTest = getExtentTest();
        String meInfo = getMeInfo(baseUrl, accessToken);
        extentTest.info("meInfo: " + meInfo);
        return JsonParser.getTotpSecret(meInfo);
    }

    public String getMustChangePassword(String authLoginInfo) {
        return JsonParser.getNestedValue(authLoginInfo, "data", "mustChangePassword");
    }

    public String getRequires2faChallenge(String authLoginInfo) {
        return JsonParser.getNestedValue(authLoginInfo, "data", "requires2faChallenge");
    }

    public boolean isMustChangePassword(String authLoginInfo) {
        return Objects.equals(getMustChangePassword(authLoginInfo), "true");
    }

    public boolean isTrustedDevice(String authLoginInfo) {
        return Objects.equals(getRequires2faChallenge(authLoginInfo), "false");
    }

    public String getUserId(String baseUrl, String accessToken) throws Exception {
        ExtentTest extentTest = getExtentTest();
        String meInfo = getMeInfo(baseUrl, accessToken);
        extentTest.info("meInfo: " + meInfo);
        return JsonParser.getUserId(meInfo);
    }

    public String resendMailOtp(String baseUrl, String accessToken) throws Exception {
        ExtentTest extentTest = getExtentTest();
        ApiClient apiClient = new ApiClient();
        String userId = getUserId(baseUrl, accessToken);
        extentTest.info("User Id: " + userId);
        return apiClient.resendMailOtp(baseUrl, accessToken, userId);
    }

    public String getMailCode(String baseUrl, String accessToken) throws Exception {
        ExtentTest extentTest = getExtentTest();
        String mailOtpsInfo = getMailOtpsInfo(baseUrl, accessToken);
        extentTest.info("Mail Otps Info: " + mailOtpsInfo);
        return JsonParser.getMailOtpsCode(mailOtpsInfo);
    }

    public void assertEqualsWithReport(String actual, String expected, String message) {
        try {
            assertEquals(expected, actual);
            getExtentTest().pass(message + " - SUCCESSFULLY");
        } catch (AssertionError e) {
            getExtentTest().fail(message + " - Expected: " + expected + ", Actual: " + actual);
            throw e;
        }
    }


}
