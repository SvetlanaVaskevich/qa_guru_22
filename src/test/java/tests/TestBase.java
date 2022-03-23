package tests;

import com.codeborne.selenide.Configuration;
import drivers.BrowserstackMobileDriver;
import drivers.LocalMobileDriver;
import drivers.RealDeviceDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.Attach.getSessionId;

public class TestBase {

    @BeforeAll
    public static void setup(){
        addListener("AllureSelenide",new AllureSelenide());

        String deviceHost = System.getProperty("deviceHost");

        if(deviceHost.equals("browserstack")){
            Configuration.browser = BrowserstackMobileDriver.class.getName();
        }
        else if(deviceHost.equals("emulator")){
            Configuration.browser = LocalMobileDriver.class.getName();
        }
        else if(deviceHost.equals("realDevice")){
            Configuration.browser = RealDeviceDriver.class.getName();
        }

        Configuration.browserSize = null;
    }

    @BeforeEach
    public void startDriver(){
        open();
    }

    @AfterEach
    public void afterEach(){
        String sessionId = getSessionId();

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        closeWebDriver();

        Attach.video(sessionId);
    }
}
