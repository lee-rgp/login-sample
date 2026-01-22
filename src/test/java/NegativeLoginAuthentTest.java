import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import listeners.ExtentTestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Instant;

import static org.testng.Assert.assertEquals;

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
     * Kiểm tra Login khi truyền password với data chứa dấu tiếng Việt
     * 8.  Limit
     * Kiểm tra khóa tạm thời khi nhập sai 3 lần liên tiếp (Rate Limit 1)
     * Kiểm tra khóa tạm thời khi nhập sai 5 lần liên tiếp (Rate Limit 2)
     */

    /**
     * Test with account's already setup 2fa and trusted device
     */
    private final long _timeOut = 5;
    private final int _maxLengthText = 128;
    private final String _loginPageUrl = "https://backoffice.osyamazakiglobel.club/login";
    private final String _baseUrl = "https://stag.osyamazakiglobel.club";

    // Chọn account đã thiết lập 2fa
    private final String _validUserName = "admin12";
    private final String _validEmail = "admin12@gmail.com";
    private final String _validPassword = "12345678@Ab";

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
//    @Test
    public void negativeLoginTest() {
        String validUserName = "admin100";
        String validEmail = "admin100@gmail.com";
        ExtentTest extentTest = getExtentTest();
        By byWrongLoginToast = By.xpath("//div[text()='The username or password you entered is incorrect.']");
        // Kiểm tra Login không thành công khi username đúng và password sai
        extentTest.info("Kiểm tra Login không thành công khi username đúng và password sai");
        // Correct Username with click login button
        login(validUserName, "", this._wrongPassword, true, true);
        checkElementVisibility(byWrongLoginToast, "Login with valid username and wrong password");
        // Kiểm tra Login không thành công khi username sai và password đúng
        extentTest.info("Kiểm tra Login không thành công khi username sai và password đúng");
        login(this._wrongUsername, "", this._validPassword, false, true);
        checkElementVisibility(byWrongLoginToast, "Login with wrong username and valid password");
        // Kiểm tra Login không thành công khi username sai và password sai
        extentTest.info("Kiểm tra Login không thành công khi username sai và password sai");
        login(this._wrongUsername, "", this._wrongPassword, false, true);
        checkElementVisibility(byWrongLoginToast, "Login with wrong username and wrong password");
        // Kiểm tra Login không thành công khi email đúng và password sai
        extentTest.info("Kiểm tra Login không thành công khi email đúng và password sai");
        // Correct Email with click login button
        login("", validEmail, this._wrongPassword, false, true);
        checkElementVisibility(byWrongLoginToast, "Login with valid email and wrong password");
        // Kiểm tra Login không thành công khi email sai và password đúng
        extentTest.info("Kiểm tra Login không thành công khi email sai và password đúng");
        login("", this._wrongEmail, this._validPassword, false, true);
        checkElementVisibility(byWrongLoginToast, "Login with wrong email and valid password");
        // Kiểm tra Login không thành công khi email sai và password sai
        extentTest.info("Kiểm tra Login không thành công khi email sai và password sai");
        login("", this._wrongEmail, this._wrongPassword, false, true);
        checkElementVisibility(byWrongLoginToast, "Login with wrong email and wrong password");
    }

    /**
     * 2. Email format
     * Kiểm tra invalid email format
     */
//    @Test
    public void invalidFormatEmailTest() {
        ExtentTest extentTest = getExtentTest();
        By byInvalidEmailFormatToast = By.xpath("//p[text()='Please enter a valid email address.']");
        // Kiểm tra invalid email format
        extentTest.info("Kiểm tra invalid email format");
        login("", this._invalidFormatEmail, this._validPassword, true, false);
        checkElementVisibility(byInvalidEmailFormatToast, "Login with invalid format email");
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
//    @Test
    public void nullInputTest() {
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
        // Check disable button LOGIN
        extentTest.info("Check disable LOGIN button");
        checkDisableButton(byLoginBtn, "Disable LOGIN button");
    }

    /**
     * 4. Maxlength
     * Kiểm tra Login khi nhập Username/Email = 1 ký tự
     *  -> Nhập thành công, Button Login enable
     * Kiểm tra Login khi nhập Username/Email < 128 ký tự
     *  -> Nhập thành công, Button Login enable
     * Kiểm tra Login khi nhập Username/Email = 128 ký tự
     *  -> Nhập thành công, Button Login enable
     * Kiểm tra Login khi nhập Username/Email > 128 ký tự
     *  -> Chỉ có thể nhập đến 128 kí tự, Button Login enable
     * Kiểm tra Login khi nhập password = 1 ký tự
     *  -> Nhập thành công, Button Login enable
     * Kiểm tra Login khi nhập password < 128 ký tự
     *  -> Nhập thành công, Button Login enable
     * Kiểm tra Login khi nhập password = 128 ký tự
     *  -> Nhập thành công, Button Login enable
     * Kiểm tra Login khi nhập password > 128 ký tự
     *  -> Chỉ có thể nhập đến 128 kí tự, Button Login enable
     */
//    @Test
    public void maxLengthTest() {
        ExtentTest extentTest = getExtentTest();
        By byLoginBtn = By.xpath("//button[span[text()='LOGIN']]");
        String username = "a";
        String password = "a";

        // Kiểm tra Login khi nhập Username/Email = 1 ký tự
        extentTest.info("Kiểm tra Login khi nhập Username/Email = 1 ký tự");
        login(username, "", password, true, false);
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");

        // Kiểm tra Login khi nhập Username/Email < 128 ký tự
        extentTest.info("Kiểm tra Login khi nhập Username/Email < 128 ký tự");
        login("abc", "", password, false, false);
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");

        // Kiểm tra Login khi nhập Username/Email = 128 ký tự
        extentTest.info("Kiểm tra Login khi nhập Username/Email = 128 ký tự");
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < this._maxLengthText; i++) {
            longString.append("a");
        }
        login(longString.toString(), "", password, false, false);
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");

        // Kiểm tra Login khi nhập Username/Email > 128 ký tự
        extentTest.info("Kiểm tra Login khi nhập Username/Email > 128 ký tự");
        StringBuilder exceedMaxLengthText = new StringBuilder();
        for (int i = 0; i <= this._maxLengthText; i++) {
            exceedMaxLengthText.append("a");
        }
        login(exceedMaxLengthText.toString(), "", password, false, false);
        // Check input Username/Email value display with max length 128 character
        extentTest.info("Check input Username/Email value display with max length 128 characters");
        By byEmailTxt = By.xpath("//input[@name='usernameOrEmail']");
        checkInputDisplayWithMaxLength(byEmailTxt, "Input Username/Email value display with max length 128 characters");
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");

        // Kiểm tra Login khi nhập password = 1 ký tự
        extentTest.info("Kiểm tra Login khi nhập password = 1 ký tự");
        login(username, "", password, false, false);
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");

        // Kiểm tra Login khi nhập password < 128 ký tự
        extentTest.info("Kiểm tra Login khi nhập password < 128 ký tự");
        login(username, "", "abc", false, false);
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");

        // Kiểm tra Login khi nhập password = 128 ký tự
        extentTest.info("Kiểm tra Login khi nhập password = 128 ký tự");
        login(username, "", longString.toString(), false, false);
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");

        // Kiểm tra Login khi nhập password > 128 ký tự
        extentTest.info("Kiểm tra Login khi nhập password > 128 ký tự");
        login(username, "", exceedMaxLengthText.toString(), false, false);
        // Check input Password value display with max length 128 character
        extentTest.info("Check input Password value display with max length 128 characters");
        By byPasswordTxt = By.xpath("//input[@name='password']");
        checkInputDisplayWithMaxLength(byPasswordTxt, "Input Password value display with max length 128 characters");
        // Check enable button LOGIN
        extentTest.info("Check enable LOGIN button");
        checkEnableButton(byLoginBtn, "Enable LOGIN button");
    }

    /**
     * 5. Lowercase
     * Kiểm tra Login với Username/Email không phân biệt chữ hoa và chữ thường
     *  -> Login successfully
     * Kiểm tra Login với password phân biệt chữ hoa và chữ thường
     *  -> Login unsuccessfully
     *  -> Display toast with xpath: //div[text()='The username or password you entered is incorrect.'
     */
//    @Test
    public void caseSensitiveLoginTest() {
        String validUsername = "admin99";
        String upperCaseUsername = this._validUserName.toUpperCase();
        String upperCasePassword = this._validPassword.toUpperCase();
        ExtentTest extentTest = getExtentTest();
        By byWrongLoginToast = By.xpath("//div[text()='The username or password you entered is incorrect.']");

        // Kiểm tra Login với Username/Email không phân biệt chữ hoa và chữ thường
        // -> Login successfully
        extentTest.info("Kiểm tra Login với Username/Email không phân biệt chữ hoa và chữ thường");
        login(upperCaseUsername, "", this._validPassword, true, true);
        verifyAccessBackOffice();

        // Kiểm tra Login với password phân biệt chữ hoa và chữ thường
        // -> Login unsuccessfully
        extentTest.info("Kiểm tra Login với password phân biệt chữ hoa và chữ thường");
        // Correct Username with click login button
        login(validUsername, "", upperCasePassword, false, true);
        checkElementVisibility(byWrongLoginToast, "Login with valid username and case sensitive password");
    }

    /**
     * 6. Trim
     * Kiểm tra Login khi copy&paste Username/Email > 128 ký tự (Đã thực hiện ở [4. Maxlength])
     * Kiểm tra Login khi dùng account với Username/Email có khoảng trắng đầu/cuối (SKIP: Hệ thống không cho phép Username/Email nhập khoảng trắng)
     *  -> Kiểm tra login thành công
     * Kiểm tra Login khi truyền Username/Email có chứa khoảng cách ở giữa (SKIP: Hệ thống không cho phép Username/Email nhập khoảng trắng)
     *  -> Kiểm tra login không thành công (Display toast with xpath: //div[text()='The username or password you entered is incorrect.')
     *  -> Kiểm tra không trim mất khoảng trắng ở giữa (giá trị bằng giá trị nhập ban đầu)
     * Kiểm tra Login khi truyền Username/Email chỉ chứa khoảng cách (SKIP: Hệ thống không cho phép Username/Email nhập khoảng trắng)
     *  -> Kiểm tra login không thành công (Display toast with xpath: //div[text()='The username or password you entered is incorrect.')
     *  -> Kiểm tra tự động trim khoảng trắng, xem như chưa nhập (nội dung input với giá trị là rỗng)
     * Kiểm tra Login khi copy&paste password > 128 ký tự (Đã thực hiện ở [4. Maxlength])
     * Kiểm tra Login khi dùng account với password có khoảng trắng đầu/cuối
     *  -> Kiểm tra login không thành công (Display toast with xpath: //div[text()='The username or password you entered is incorrect.')
     *  -> Kiểm tra không trim mất khoảng trắng (giá trị bằng giá trị nhập ban đầu)
     * Kiểm tra Login khi truyền password có chứa khoảng cách ở giữa
     *  -> Kiểm tra login không thành công (Display toast with xpath: //div[text()='The username or password you entered is incorrect.')
     *  -> Kiểm tra không trim mất khoảng trắng ở giữa (giá trị bằng giá trị nhập ban đầu)
     * Kiểm tra Login khi truyền password chỉ chứa khoảng cách
     *  -> Kiểm tra login không thành công (Display toast with xpath: //div[text()='The username or password you entered is incorrect.')
     *  -> Kiểm tra không trim mất khoảng trắng (giá trị bằng giá trị nhập ban đầu)
     */
//    @Test
    public void trimLoginTest() {
        String validUsernameFirst = "admin98";
        String validUsernameSecond = "admin97";
        String validUsernameThird = "admin96";
        String spaceAmongCharacterText = "admin 123456789";
        ExtentTest extentTest = getExtentTest();
        By byWrongLoginToast = By.xpath("//div[text()='The username or password you entered is incorrect.']");
        By byFailedForFieldPassword = By.xpath("//div[text()=\"Validation failed for field 'Password'\"]");
        By byPasswordTxt = By.xpath("//input[@name='password']");

        // Kiểm tra Login khi dùng account với password có khoảng trắng đầu/cuối
        // -> Kiểm tra login không thành công
        extentTest.info("Kiểm tra Login khi dùng account với password có khoảng trắng đầu/cuối");
        // Correct Username with click login button
        login(validUsernameFirst, "", "   " + this._validPassword + "   ", true, true);
        checkElementVisibility(byWrongLoginToast, "Login with valid username and password has space before/after");
        // -> Kiểm tra không trim mất khoảng trắng (giá trị bằng giá trị nhập ban đầu)
        assertEqualsWithReport(getInputText(byPasswordTxt)
                                , "   " + this._validPassword + "   "
                                , "Không trim mất khoảng trắng 2 đầu");

        // Kiểm tra Login khi truyền password có chứa khoảng cách ở giữa
        // -> Kiểm tra login không thành công
        extentTest.info("Kiểm tra Login khi truyền password có chứa khoảng cách ở giữa");
        // Correct Username with click login button
        login(validUsernameSecond, "", spaceAmongCharacterText, false, true);
        checkElementVisibility(byWrongLoginToast, "Login with valid username and password has space between");
        // -> Kiểm tra không trim mất khoảng trắng ở giữa (giá trị bằng giá trị nhập ban đầu)
        assertEqualsWithReport(getInputText(byPasswordTxt), spaceAmongCharacterText, "Không trim mất khoảng trắng ở giữa");

        // Kiểm tra Login khi truyền password chỉ chứa khoảng cách
        // -> Kiểm tra login không thành công
        extentTest.info("Kiểm tra Login khi truyền password chỉ chứa khoảng cách");
        // Correct Username with click login button
        login(validUsernameThird, "", "   ", false, true);
        checkElementVisibility(byFailedForFieldPassword, "Login with valid username and password has all spaces");
        // -> Kiểm tra không trim mất khoảng trắng (giá trị bằng giá trị nhập ban đầu)
        assertEqualsWithReport(getInputText(byPasswordTxt), "   ", "Không trim mất khoảng trắng");
    }

    /**
     * 7. Unicode
     * Kiểm tra Login khi truyền Username/Email với data chứa dấu tiếng Việt
     *  -> Tự convert sang mất dấu. VD: tết -> tt
     * Kiểm tra Login khi truyền password với data chứa dấu tiếng Việt
     *  -> Tự convert sang mất dấu. VD: tết -> tet
     */
//    @Test
    public void unicodeLoginTest() {
        ExtentTest extentTest = getExtentTest();
        By byEmailTxt = By.xpath("//input[@name='usernameOrEmail']");
        By byPasswordTxt = By.xpath("//input[@name='password']");

        String unicodeText = "tết";
        login(unicodeText, "", unicodeText, true, false);
        // Kiểm tra Login khi truyền Username/Email với data chứa dấu tiếng Việt
        extentTest.info("Kiểm tra Login khi truyền Username/Email với data chứa dấu tiếng Việt");
        assertEqualsWithReport(getInputText(byEmailTxt), "tt", "Username/Email convert to non-unicode characters");
        // Kiểm tra Login khi truyền password với data chứa dấu tiếng Việt
        extentTest.info("Kiểm tra Login khi truyền password với data chứa dấu tiếng Việt");
        assertEqualsWithReport(getInputText(byPasswordTxt), "tet", "Password convert to non-unicode characters");
    }

    /**
     * 8.  Limit
     * Kiểm tra khóa tạm thời khi nhập sai 3 lần liên tiếp (Rate Limit 1)
     * Kiểm tra khóa tạm thời khi nhập sai 5 lần liên tiếp (Rate Limit 2)
     */
//    @Test
    public void lockAccountLoginTest() throws InterruptedException {
        WebDriver webDriver = getDriver();
        ExtentTest extentTest = getExtentTest();

        By byLoginBtn = By.xpath("//button[span[text()='LOGIN']]");

        String username = "admin19";
        String invalidPassword = "123456";
        boolean isAccessLoginScreen = true;
        for (int i = 1; i <= 4; i++) {
            waitMillis(webDriver, 1000);
            extentTest.info(String.format("Login %d time", i));
            isAccessLoginScreen = true;
            if (i > 1) {
                isAccessLoginScreen = false;
            }
            login(username, "", invalidPassword, isAccessLoginScreen, true);
        }

        // Check display message chứa nội dung "You have made too many unsuccessful login attempts. Please try again in 05 minutes"
        extentTest.info("Check display message chứa nội dung \"You have made too many unsuccessful login attempts. Please try again in 05 minutes\"");
        By byTooManyLoginMessage = By.xpath("//div[contains(text(), 'You have made too many unsuccessful login attempts')]");
        checkElementVisibility(byTooManyLoginMessage, "Login with invalid password 3 times successively");

        // Check disable button LOGIN
        extentTest.info("Check disable LOGIN button");
        checkDisableButton(byLoginBtn, "Disable LOGIN button");

        // Sleep 5 minutes
        extentTest.info("Sleep 5 minutes");
        extentTest.info("timestamp now: " + Instant.now());
        for (int i = 1; i <= 5; i++) {
            waitMillis(webDriver, 60000);
            extentTest.info(i + " minutes");
            extentTest.info("timestamp now: " + Instant.now());
        }

        quitDriver();
        initDriver();

        for (int i = 1; i <= 6; i++) {
            Thread.sleep(1000);
            extentTest.info(String.format("Login %d time", i));
            isAccessLoginScreen = true;
            if (i > 1) {
                isAccessLoginScreen = false;
            }
            login(username, "", invalidPassword, isAccessLoginScreen, true);
        }

        // Check display message chứa nội dung "Your account has been locked due to too many failed login attempts. Please contact an administrator to unlock your account."
        extentTest.info("Check display message chứa nội dung \"Your account has been locked due to too many failed login attempts. Please contact an administrator to unlock your account.\"");
        By byLockAccountMessage = By.xpath("//div[contains(text(), 'Your account has been locked due to too many failed login attempts. Please contact an administrator to unlock your account.')]");
        checkElementVisibility(byLockAccountMessage, "Login with invalid password 5 times successively");

        // Check disable button LOGIN
        extentTest.info("Check disable LOGIN button");
        checkDisableButton(byLoginBtn, "Disable LOGIN button");
    }

    private String getInputText(By locator) {
        WebDriver webDriver = getDriver();
        return getTextInInputText(webDriver, locator, this._timeOut);
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

    private void checkElementVisibility(By locator, String message) {
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

    private void checkEnableButton(By locator, String message) {
        WebDriver webDriver = getDriver();
        if (!isDisableButton(webDriver, locator, this._timeOut)) {
            getExtentTest().pass(String.format("%s - SUCCESSFULLY", message));
        } else {
            getExtentTest().fail(String.format("%s - UN-SUCCESSFULLY", message));
        }
    }

    private void checkInputDisplayWithMaxLength(By locator, String message) {
        WebDriver webDriver = getDriver();
        int length = getLengthCharacterInInputText(webDriver, locator, this._timeOut);
        if (length == this._maxLengthText) {
            getExtentTest().pass(String.format("%s - SUCCESSFULLY", message));
        } else {
            getExtentTest().fail(String.format("%s - UN-SUCCESSFULLY", message));
        }
    }

    private void verifyAccessBackOffice() {
        WebDriver webDriver = getDriver();
        ExtentTest extentTest = getExtentTest();

        // Screen: BACK OFFICE
        extentTest.info("Screen: BACK OFFICE");
        // VP1: Verify accessing BACK OFFICE screen successfully
        extentTest.info("VP1: Verify accessing BACK OFFICE screen successfully");
        By byLogoutBtn = By.xpath("//button[span[text()='Logout']]");
        if (isElementVisibility(webDriver, byLogoutBtn, this._timeOut)) {
            extentTest.pass("Verify accessing BACK OFFICE screen successfully");
            // Click LOGOUT button
            logout();
        } else {
            extentTest.fail("Verify accessing BACK OFFICE screen unsuccessfully");
        }
    }

    private void logout() {
        WebDriver webDriver = getDriver();
        ExtentTest extentTest = getExtentTest();

        By byLogoutBtn = By.xpath("//button[span[text()='Logout']]");
        extentTest.info("Click LOGOUT button");
        click(webDriver, byLogoutBtn, this._timeOut);
    }
}
