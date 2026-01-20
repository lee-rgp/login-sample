import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import listeners.ExtentTestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestListener.class)
public class NegativeLoginAuthentTest extends BaseTest {
    /**
     * 1. Negative
     * Kiểm tra Login không thành công khi username đúng và password sai
     * Kiểm tra Login không thành công khi username sai và password đúng
     * Kiểm tra Login không thành công khi username sai và password sai
     * Kiểm tra Login không thành công khi email đúng và password sai
     * Kiểm tra Login không thành công khi email sai và password đúng
     * Kiểm tra Login không thành công khi email sai và password sai
     * 2. Email format
     * Kiểm tra invalid email format
     * 3. Check null
     * Kiểm tra Login khi không điền Username/Email và password
     * Kiểm tra Login khi không điền Username/Email
     * Kiểm tra Login khi không điền password
     * 4. Maxlength
     * Kiểm tra Login khi nhập Username/Email = 1 ký tự
     * Kiểm tra Login khi nhập Username/Email < 128 ký tự
     * Kiểm tra Login khi nhập Username/Email = 128 ký tự
     * Kiểm tra Login khi nhập Username/Email > 128 ký tự
     * Kiểm tra Login khi nhập password = 1 ký tự
     * Kiểm tra Login khi nhập password < 128 ký tự
     * Kiểm tra Login khi nhập password = 128 ký tự
     * Kiểm tra Login khi nhập password > 128 ký tự
     * 5. Lowercase
     * Kiểm tra Login với Username/Email không phân biệt chữ hoa và chữ thường
     * Kiểm tra Login với password phân biệt chữ hoa và chữ thường
     * 6. Trim
     * Kiểm tra Login khi copy&paste Username/Email > 128 ký tự
     * Kiểm tra Login khi dùng account với Username/Email có khoảng trắng đầu/cuối
     * Kiểm tra Login khi truyền Username/Email có chứa khoảng cách
     * Kiểm tra Login khi truyền Username/Email chỉ chứa khoảng cách
     * Kiểm tra Login khi copy&paste password > 128 ký tự
     * Kiểm tra Login khi truyền password có chứa khoảng cách
     * Kiểm tra Login khi truyền password chỉ chứa khoảng cách
     * Kiểm tra Login khi dùng account với password có khoảng trắng đầu/cuối
     * 7. Unicode
     * Kiểm tra Login khi truyền Username/Email với data chứa dấu tiếng Việt
     * Kiểm tra Login khi copy&paste Username/Email với data chứa dấu tiếng Việt
     * Kiểm tra Login khi truyền password với data chứa dấu tiếng Việt
     * Kiểm tra Login khi copy&paste password với data chứa dấu tiếng Việt
     * 8. Security
     * Kiểm tra thực hiện Login cùng account trên nhiều device khác nhau cùng lúc
     * Xác minh khi log in trên devices khác phải nhập OTP
     * Xác minh không thể truy cập trang nội bộ khi chưa Login
     * 9. Script/SQL/Thuật toán/đặc biệt
     * Kiểm tra Login khi truyền password chứa mã HTML
     * Kiểm tra Forgot Password với Username/Email chứa mã script XSS attempt
     * Kiểm tra Login khi truyền password chứa mã script XSS attempt
     * Kiểm tra Forgot Password với Username/Email chứaSQL injection
     * Kiểm tra Login khi truyền password chứa SQL injection
     * Kiểm tra Forgot Password với Username/Email chứacommand injection
     * Kiểm tra Login khi truyền password chứa command injection
     * Kiểm tra Forgot Password với Username/Email chứaJSON injection
     * Kiểm tra Login khi truyền password chứa JSON injection
     * Kiểm tra Forgot Password với Username/Email chứaXML injection
     * Kiểm tra Login khi truyền password chứa XML injection
     * Kiểm tra Forgot Password với Username/Email chứa path traversal
     * Kiểm tra Login khi truyền password chứa path traversal
     * Kiểm tra Forgot Password với Username/Email chứaemoji/ký tự Unicode
     * Kiểm tra Login khi truyền password chứa emoji/ký tự Unicode
     * 10. Message lỗi
     * Kiểm tra làm mới trạng thái cảnh báo khi reload trang (nếu số lần sai < 3)
     * 11.  Limit
     * Kiểm tra khóa tạm thời khi nhập sai 3 lần liên tiếp (Rate Limit 1)
     * Kiểm tra khóa tạm thời khi nhập sai 5 lần liên tiếp (Rate Limit 2)
     */

    /**
     * Test with account's already setup 2fa and trusted device
     */
    private final long _timeOut = 5;
    private final String _loginPageUrl = "https://backoffice.osyamazakiglobel.club/login";
    private final String _baseUrl = "https://stag.osyamazakiglobel.club";

    private final String _validUserName = "admin41";
    private final String _validEmail = "admin41@gmail.com";
    private final String _validPassword = "123456789@Ab";

    private final String _wrongUsername = "abcd41";
    private final String _wrongEmail = "abcd41@gmail.com";
    private final String _wrongPassword = "123456789";

    private final String _invalidFormatEmail = "user@@";

    /**
     * 1. Negative
     * Kiểm tra Login không thành công khi username đúng và password sai
     * Kiểm tra Login không thành công khi username sai và password đúng
     * Kiểm tra Login không thành công khi username sai và password sai
     * Kiểm tra Login không thành công khi email đúng và password sai
     * Kiểm tra Login không thành công khi email sai và password đúng
     * Kiểm tra Login không thành công khi email sai và password sai
     */
    @Test
    public void NegativeLoginTest() {
        ExtentTest extentTest = getExtentTest();
        By byWrongLoginToast = By.xpath("//div[text()='The username or password you entered is incorrect.']");
        // Kiểm tra Login không thành công khi username đúng và password sai
        extentTest.info("Kiểm tra Login không thành công khi username đúng và password sai");
        login(this._validUserName, "", this._wrongPassword, true, true);
        check(byWrongLoginToast, "Login with valid username and wrong password");
        // Kiểm tra Login không thành công khi username sai và password đúng
        extentTest.info("Kiểm tra Login không thành công khi username sai và password đúng");
        login(this._wrongUsername, "", this._validPassword, false, true);
        check(byWrongLoginToast, "Login with wrong username and valid password");
        // Kiểm tra Login không thành công khi username sai và password sai
        extentTest.info("Kiểm tra Login không thành công khi username sai và password sai");
        login(this._wrongUsername, "", this._wrongPassword, false, true);
        check(byWrongLoginToast, "Login with wrong username and wrong password");
        // Kiểm tra Login không thành công khi email đúng và password sai
        extentTest.info("Kiểm tra Login không thành công khi email đúng và password sai");
        login("", this._validEmail, this._wrongPassword, false, true);
        check(byWrongLoginToast, "Login with valid email and wrong password");
        // Kiểm tra Login không thành công khi email sai và password đúng
        extentTest.info("Kiểm tra Login không thành công khi email sai và password đúng");
        login("", this._wrongEmail, this._validPassword, false, true);
        check(byWrongLoginToast, "Login with wrong email and valid password");
        // Kiểm tra Login không thành công khi email sai và password sai
        extentTest.info("Kiểm tra Login không thành công khi email sai và password sai");
        login("", this._wrongEmail, this._wrongPassword, false, true);
        check(byWrongLoginToast, "Login with wrong email and wrong password");
    }

    /**
     * 2. Email format
     * Kiểm tra invalid email format
     */
    @Test
    public void InvalidFormatEmailTest() {
        ExtentTest extentTest = getExtentTest();
        By byInvalidEmailFormatToast = By.xpath("//p[text()='Please enter a valid email address.']");
        // Kiểm tra invalid email format
        extentTest.info("Kiểm tra invalid email format");
        login("", this._invalidFormatEmail, this._validPassword, true, false);
        check(byInvalidEmailFormatToast, "Login with invalid format email");
        // Check disable button LOGIN
        extentTest.info("Check disable LOGIN button");
        By byLoginBtn = By.xpath("//button[span[text()='LOGIN']]");
        checkDisableButton(byLoginBtn, "Disable LOGIN button");
    }

    /**
     * 3. Check null
     * Kiểm tra Login khi không điền Username/Email và password
     *  -> Button Login disable, không thể click
     * Kiểm tra Login khi không điền Username/Email
     *  -> Button Login disable, không thể click
     * Kiểm tra Login khi không điền password
     *  -> Button Login disable, không thể click
     */
    @Test
    public void NullInputTest() {
        ExtentTest extentTest = getExtentTest();
        By byLoginBtn = By.xpath("//button[span[text()='LOGIN']]");

        // Kiểm tra Login khi không điền Username/Email và password
        extentTest.info("Kiểm tra Login khi không điền Username/Email và password");
        login("", "", "", true, false);
        // Check disable button LOGIN
        extentTest.info("Check disable LOGIN button");
        checkDisableButton(byLoginBtn, "Disable LOGIN button");

        // Kiểm tra Login khi không điền Username/Email
        extentTest.info("Kiểm tra Login khi không điền Username/Email");
        login("", "", this._validPassword, false, false);
        // Check disable button LOGIN
        extentTest.info("Check disable LOGIN button");
        checkDisableButton(byLoginBtn, "Disable LOGIN button");

        // Kiểm tra Login khi không điền password
        extentTest.info("Kiểm tra Login khi không điền password");
        login(this._validUserName, "", "", false, false);
        // Check disable button LOGIN
        extentTest.info("Check disable LOGIN button");
        checkDisableButton(byLoginBtn, "Disable LOGIN button");
    }

    private void login(String username, String email, String password, boolean isAccessLoginScreen, boolean isClickLoginBtn) {
        WebDriver webDriver = getDriver();
        ExtentTest extentTest = getExtentTest();

        if (isAccessLoginScreen) {
            // Screen: LOGIN
            extentTest.info("Screen: LOGIN");
            // Step1: Access login screen
            extentTest.info("Step1: Access login screen");
            webDriver.get(this._loginPageUrl);
        }
        String input = "";
        if (email.isEmpty()) { // Login with username
            extentTest.info("Login with username");
            input = username;
        } else { // Login with email
            extentTest.info("Login with email");
            input = email;
        }
        // Step2: Input Email
        extentTest.info("Step2: Input Username/Email");
        By byEmailTxt = By.xpath("//input[@name='usernameOrEmail']");
        sendKeys(webDriver, byEmailTxt, this._timeOut, input);
        // Step3: Input Password
        extentTest.info("Step3: Input Password");
        By byPasswordTxt = By.xpath("//input[@name='password']");
        sendKeys(webDriver, byPasswordTxt, this._timeOut, password);
        if (isClickLoginBtn) {
            // Step4: Click LOGIN button
            extentTest.info("Step4: Click LOGIN button");
            By byLoginBtn = By.xpath("//button[span[text()='LOGIN']]");
            click(webDriver, byLoginBtn, this._timeOut);
        }
    }

    private void check(By locator, String message) {
        WebDriver webDriver = getDriver();
        ExtentTest extentTest = getExtentTest();
        if (isElementVisibility(webDriver, locator, this._timeOut)) {
            extentTest.pass(String.format("%s - SUCCESSFULLY", message));
        } else {
            extentTest.fail(String.format("%s - UN-SUCCESSFULLY", message));
        }
    }

    private void checkDisableButton(By locator, String message) {
        WebDriver webDriver = getDriver();
        if (isDisableButton(webDriver, locator, this._timeOut)) {
            getExtentTest().pass(String.format("%s - SUCCESSFULLY", message));
        } else {
            getExtentTest().fail(String.format("%s - UN-SUCCESSFULLY", message));
        }
    }
}
