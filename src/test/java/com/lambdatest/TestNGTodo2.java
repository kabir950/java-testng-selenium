package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo2 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "ankitclambdatest";
        String authkey = "LT_OnEL30AtoPH0BkcY83K0VnpQOcfFpX71axsfQgHGLhvNfUP";
        String hub = "@hub.lambdatest.com/wd/hub";

        SafariOptions browserOptions = new SafariOptions();
        HashMap<String, Object> ltOptions = new HashMap<>();

        ltOptions.put("username", username);
        ltOptions.put("accessKey", authkey);
        ltOptions.put("platformName", "macOS Sonoma");
        ltOptions.put("browserName", "Safari");
        ltOptions.put("browserVersion", "latest");
        ltOptions.put("build", "Build");
        ltOptions.put("name", m.getName() + " - " + this.getClass().getSimpleName());
        ltOptions.put("w3c", true);

        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions);
    }

    @Test(invocationCount = 1)
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url for iteration...");
        driver.get("https://di-review.tmp.phenom.pub/admin/journals");

        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id='username']")).sendKeys("ankit");

        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("1234");

        System.out.println("Current URL: " + driver.getCurrentUrl());

        Status = "passed";
        System.out.println("Iteration finished successfully");
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Test Result and Closing Browser\", \"level\": \"info\"}}");
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
