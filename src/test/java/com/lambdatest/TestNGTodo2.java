package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.lambdatest.tunnel.Tunnel;

public class TestNGTodo2 {

    private RemoteWebDriver driver;
    private String Status = "failed";
    private Tunnel t;

    @BeforeSuite
    public void setUpTunnel() throws Exception {
        String username = System.getenv("LT_USERNAME");
        String access_key = System.getenv("LT_ACCESS_KEY");

        t = new Tunnel();
        HashMap<String, String> options = new HashMap<>();
        options.put("user", username);
        options.put("key", access_key);
        options.put("tunnelName", "MavenSingle");
        t.start(options);
        System.out.println("✅ LambdaTest Tunnel started.");
    }

    @AfterSuite
    public void stopTunnel() throws Exception {
        if (t != null) {
            t.stop();
            System.out.println("🛑 LambdaTest Tunnel stopped.");
        }
    }

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "kabirk";
        String access_key = "LT_RhMHqS2TJ4lYNmnzOfTYRaNYNdbFdvoDAKSFTVknI2UQBth";

        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "TestNG With Java Tunnel");
        caps.setCapability("name", m.getName());
        caps.setCapability("plugin", "git-testng");
        caps.setCapability("tunnel", true);  // This tells LambdaTest to use the tunnel

        String[] tags = new String[] { "TunnelTest", "Maven", "TestNG" };
        caps.setCapability("tags", tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + hub), caps);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("🌐 Loading URL...");
        driver.get("https://lambdatest.github.io/sample-todo-app/");

        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li2")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li4")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 6");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 7");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 8");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li7")).click();
        driver.findElement(By.name("li8")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys("Get Taste of Lambda and Stick to It");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.name("li9")).click();

        String spanText = driver.findElementByXPath("/html/body/div/div/div/ul/li[9]/span").getText();
        Assert.assertEquals("Get Taste of Lambda and Stick to It", spanText);
        Status = "passed";

        System.out.println("✅ Test finished");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.executeScript("lambda-status=" + Status);
            driver.quit();
        }
    }
}
