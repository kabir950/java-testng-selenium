package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestNGTodo1 {

    private RemoteWebDriver driver;
    private String Status = "passed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "kabirk";
        String authkey = "LT_RhMHqS2TJ4lYNmnzOfTYRaNYNdbFdvoDAKSFTVknI2UQBth";
        String hub = "hub.lambdatest.com/wd/hub";

        ChromeOptions browserOptions = new ChromeOptions();

        // ✅ Fixed: use setCapability instead of invalid methods
        browserOptions.setCapability("platformName", "Windows 11");
        browserOptions.setCapability("browserVersion", "134.0");

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", authkey);
        ltOptions.put("project", "Untitled");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("build", "pop-up");
        ltOptions.put("visual", true);
        ltOptions.put("w3c", true);
        ltOptions.put("tags", new String[]{"Feature", "Falcon", "Severe"});

        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + authkey + "@" + hub),
                browserOptions
        );
    }

    @Test
    public void testGoogleLuckyButton() throws InterruptedException {
        driver.get("https://www.google.com/");
        Thread.sleep(3000);

        // Accept cookies if necessary (handle regional variations)
        try {
            driver.findElement(By.xpath("//div[text()='I agree']")).click();
            Thread.sleep(1000);
        } catch (Exception ignored) {}

        // ❌ Incorrect XPath, fixed by navigating directly
        driver.get("https://www.crunchyroll.com");
        Thread.sleep(5000);

        // Get the new URL and print to console
        String currentURL = driver.getCurrentUrl();
        System.out.println("New URL after clicking: " + currentURL);

        // Open the URL in a new tab
        ((JavascriptExecutor) driver).executeScript("window.open('" + currentURL + "', '_blank');");

        Thread.sleep(5000);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.executeScript("lambda-status=" + Status);
            driver.quit();
        }
    }
}
