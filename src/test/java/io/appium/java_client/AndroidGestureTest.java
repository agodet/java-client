/*
 +Copyright 2014 Appium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package io.appium.java_client;

import static org.junit.Assert.*;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Test Mobile Driver features
 */
public class AndroidGestureTest {
  private AppiumDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("src/test/java/io/appium/java_client");
    File app = new File(appDir, "ApiDemos-debug.apk");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void MultiGestureSingleActionTest() throws InterruptedException {
    //the underlying java library for Appium doesn't like multi-gestures with only a single action.
    //but java-client should handle it, silently falling back to just performing a single action.

    MultiTouchAction multiTouch = new MultiTouchAction(driver);
    TouchAction action0 = new TouchAction(driver).tap(100,300);
    multiTouch.add(action0).perform();
  }

  @Test
  public void dragNDropTest() {
	  driver.scrollToExact("Views").click();
	  driver.findElement(MobileBy.AndroidUIAutomator("description(\"Drag and Drop\")")).click();
	  WebElement actionBarTitle = driver.findElement(By.id("android:id/action_bar_title"));

	  assertEquals("Wrong title.", "Views/Drag and Drop", actionBarTitle.getText());
	  WebElement dragDot1 = driver.findElement(By.id("com.example.android.apis:id/drag_dot_1"));
	  WebElement dragDot3 = driver.findElement(By.id("com.example.android.apis:id/drag_dot_3"));

	  WebElement dragText = driver.findElement(By.id("com.example.android.apis:id/drag_text"));
	  assertEquals("Drag text not empty", "", dragText.getText());

	  TouchAction dragNDrop = new TouchAction(driver).longPress(dragDot1).moveTo(dragDot3).release();
	  dragNDrop.perform();

	  assertNotEquals("Drag text empty", "", dragText.getText());
  }

  @Test
  public void TapSingleFingerTest() {
    driver.tap(1,100,200,1000);
  }
}
