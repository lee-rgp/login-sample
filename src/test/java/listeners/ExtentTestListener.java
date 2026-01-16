package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import base.BaseTest;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());

        ExtentTestManager.setTest(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().pass("Test PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTestManager.getTest().fail(result.getThrowable());

        Object testInstance = result.getInstance();

        if (testInstance instanceof BaseTest) {

            WebDriver driver = ((BaseTest) testInstance).getDriver();

            if (driver != null) {
                String screenshotPath =
                        ScreenshotUtils.takeScreenshot(
                                driver,
                                result.getMethod().getMethodName()
                        );

                ExtentTestManager.getTest()
                        .addScreenCaptureFromPath(screenshotPath);
            } else {
                ExtentTestManager.getTest()
                        .warning("WebDriver is null. Screenshot not captured.");
            }
        } else {
            ExtentTestManager.getTest()
                    .warning("Test class does not extend BaseTest.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
