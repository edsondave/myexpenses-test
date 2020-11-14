package co.edu.uniandes.miso4208.myexpenses.test.commons;

import co.edu.uniandes.miso4208.myexpenses.model.Transaction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AndroidDriverManager {

    private static final String DEVICE_NAME = "Android Emulator";
    private static final String AUTOMATION_NAME = "UiAutomator2";
    private static final String APP_WAIT_ACTIVITY = "org.totschnig.myexpenses.activity.OnboardingActivity";

    private static AndroidDriverManager instance;

    private AndroidDriver<WebElement> driver;
    private WebDriverWait wait;

    private AndroidDriverManager() {
    }

    public static AndroidDriverManager getInstance() {
        if (instance == null) {
            instance = new AndroidDriverManager();
            instance.setup();
        }
        return instance;
    }

    private void setup() {

        TestConfiguration config = TestConfiguration.getInstance();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", DEVICE_NAME);
        capabilities.setCapability("automationName", AUTOMATION_NAME);
        capabilities.setCapability("app", config.getAppUnderTestPath());
        capabilities.setCapability("appWaitActivity", APP_WAIT_ACTIVITY);

        driver = new AndroidDriver <>(config.getAppiumServerUrl(), capabilities);
        wait = new WebDriverWait(driver, 5);

    }

    public void quit() {
        driver.quit();
    }

    public void skipOnboardingWizard() {
        waitAndClick("org.totschnig.myexpenses:id/suw_navbar_next");
        waitAndClick("org.totschnig.myexpenses:id/suw_navbar_next");
        waitAndClick("org.totschnig.myexpenses:id/suw_navbar_done");
    }

    public void goToNewTransactionForm() {
        waitAndClick("org.totschnig.myexpenses:id/CREATE_COMMAND");
    }

    public void fillNewTransactionForm(Transaction transaction) {
        if (transaction.getAmount() > 0) {
            waitAndClick("org.totschnig.myexpenses:id/TaType");
        }
        driver.findElementById("org.totschnig.myexpenses:id/AmountEditText").sendKeys(
                String.valueOf(Math.abs(transaction.getAmount())));
    }

    public void clickTickButton() {
        waitAndClick("org.totschnig.myexpenses:id/CREATE_COMMAND");
    }

    public boolean existsTransactionInMainActivity(Transaction transaction) {

        StringBuilder sb = new StringBuilder();
        if (transaction.getAmount() < 0) {
            sb.append("-");
        }
        String expected = sb.append("$").append(Math.abs(transaction.getAmount())).toString();

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
                "android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/" +
                "androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/" +
                "android.widget.RelativeLayout/androidx.viewpager.widget.ViewPager/android.widget.LinearLayout/" +
                "android.widget.FrameLayout/android.widget.ListView/android.view.ViewGroup[1]/" +
                "android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView[3]")));

        return element.isDisplayed();
    }

    private void waitAndClick(String elementId) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(elementId))).click();
    }
}