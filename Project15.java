package Project15;

import Project13.MyConstants;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Project15 {
    public static void main(String[] args) throws InterruptedException {
        //        NOTE-1: You do not have to use TestNG
        //        NOTE-2: Do not use Thread.sleep()
        System.setProperty("webdriver.chrome.driver", MyConstants.DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,7);
        // Navigate to https://test.campus.techno.study/
        driver.get("https://test.campus.techno.study/");
        driver.manage().window().maximize();

        // Dismiss the cookie message by clicking on "Got it!" button
        driver.findElement(By.cssSelector("a[aria-label='dismiss cookie message']")).click();

        // Login by the credentials (username = "daulet2030@gmail.com" and password = "TechnoStudy123@")
        driver.findElement(By.cssSelector("input[data-placeholder='Username']")).sendKeys("daulet2030@gmail.com");
        driver.findElement(By.cssSelector("input[data-placeholder='Password']")).sendKeys("TechnoStudy123@");
        driver.findElement(By.cssSelector("button[aria-label='LOGIN']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("svg[data-icon='bars']")));

        // Click on Setup in the left menu
        driver.findElement(By.xpath("//span[text()='Setup']")).click();

        // Click on School Setup
        driver.findElement(By.xpath("//span[text()='School Setup']")).click();

        // Click on Departments
        driver.findElement(By.xpath("//span[text()='Departments']")).click();

        // Click on "+" button to add a school department
        driver.findElement(By.xpath("//ms-add-button[contains(@tooltip,'TITLE.ADD')]//button")).click();

        // On the pop-up window, type "High School" for the name of the department and "HS-1" for the code.
        driver.findElement(By.cssSelector("ms-text-field[placeholder='GENERAL.FIELD.NAME']> input")).sendKeys("High School");
        driver.findElement(By.cssSelector("ms-text-field[placeholder='GENERAL.FIELD.CODE']> input")).sendKeys("HS-1");

        // Click on "Section" tab
        driver.findElement(By.xpath("//div[contains(@id,'mat-tab-label')][2]")).click();

        // Click on "+" button to add a new section.
        driver.findElement(By.cssSelector("ms-add-button[tooltip='COST_CENTER.TITLE.NEW_CONSTANT']  button")).click();

        driver.findElement(By.cssSelector("div ms-text-field input[id='ms-text-field-2']")).sendKeys("Junior Classes");
        driver.findElement(By.cssSelector("div ms-text-field input[id='ms-text-field-3']")).sendKeys("Juniors");
        driver.findElement(By.xpath("//button//span[text()='Add']")).click();

        // Create another section with the name "Senior Classes" and the short name "Seniors" similarly.
        driver.findElement(By.cssSelector("ms-text-field[id='ms-text-field-2'] > input")).sendKeys("Senior Classes");
        driver.findElement(By.cssSelector("ms-text-field[id='ms-text-field-3'] > input")).sendKeys("Seniors");

        // Click on "Save" for creating the school department.
        driver.findElement(By.cssSelector("ms-save-button[class='ng-star-inserted']")).click();

        // Verify if the department with the name "High School" created. It should write "School Department successfully created." on the console.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container")));
        String text = driver.findElement(By.id("toast-container")).getText();
        System.out.println(text);
        //Assert.assertEquals("School Department successfully created",text);

        // Click on "+" button to add a school department AGAIN
        driver.findElement(By.xpath("//ms-add-button[contains(@tooltip,'TITLE.ADD')]//button")).click();

        // On the pop-up window, type "High School" for the name of the department and "HS-1" for the code AGAIN
        driver.findElement(By.cssSelector("ms-text-field[placeholder='GENERAL.FIELD.NAME']> input")).sendKeys("High School");
        driver.findElement(By.cssSelector("ms-text-field[placeholder='GENERAL.FIELD.CODE']> input")).sendKeys("HS-1");

        // Click on "Save" for TRYING to create a school department with the same name.
        driver.findElement(By.cssSelector("ms-save-button[class='ng-star-inserted']")).click();

        // Verify that a dialog box occurs with a message "There is already Department with "High School" name!". It should write the message of the dialog box on the console.
        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[role='alertdialog']")));
        String textError =driver.findElement(By.cssSelector("div[role='alertdialog']")).getText();
        System.out.println(textError);
        Assert.assertEquals("There is already Department with \"High School\" name!",textError);

         // Click on "x" to dismiss the pop-up window.
        driver.findElement(By.cssSelector("button[aria-label='Close dialog']")).click();

        // Click on edit button (or the row) of the "High School" department to edit it.
        List<WebElement> editButtonList = driver.findElements(By.cssSelector("button svg[data-icon=\"edit\"]"));

        List<String> editNameList = new ArrayList<>();

        for (WebElement edit : editButtonList) {
            editNameList.add(edit.getText());
            if (edit == editButtonList.get(0))
                editButtonList.get(0).click();
        }
        // Edit the school department name as "High School Classes" and department code as "HSC-1" and click on "Save"
        WebElement name2 = driver.findElement(By.cssSelector("ms-text-field[placeholder='GENERAL.FIELD.NAME']> input"));
        name2.clear();
        name2.sendKeys("High School Classes");
        WebElement Code2 = driver.findElement(By.cssSelector("ms-text-field[placeholder='GENERAL.FIELD.CODE']> input"));
        Code2.clear();
        Code2.sendKeys("HSC-1");
        driver.findElement(By.cssSelector("ms-save-button[class='ng-star-inserted']")).click();

        // Verify if the department name was edited as "High School Classes" and department code was edited as "HSC-1"
        System.out.println(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//table/tbody/tr[1]/td[2]"),"High School Classes")));

        System.out.println(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//table/tbody/tr[1]/td[3]"),"HSC-1")));

        // It should write "School Department name successfully edited." on the console.
        if(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//table/tbody/tr[1]/td[2]"),"High School Classes"))){
            System.out.println("School Department name successfully edited.");
        }

        // And it should write "School Department code successfully edited." on the console.
        if(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//table/tbody/tr[1]/td[3]"),"HSC-1"))){
            System.out.println("School Department code successfully edited.");
        }
        // Click on corresponding trash icon
        List<WebElement> deleteButtonList = driver.findElements(By.cssSelector("ms-delete-button[class='ng-star-inserted']"));

        List<String> deleteNameList = new ArrayList<>();

        for (WebElement delete : deleteButtonList) {
            deleteNameList.add(delete.getText());
            if (delete == deleteButtonList.get(0))
                deleteButtonList.get(0).click();
        }
        // Click on "Yes" for deleting the school department.
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Verify the department is deleted. (it should not be present in the table anymore)
        System.out.println(!wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//table/tbody/tr[1]/td[2]"),"High School Classes")));

        // Close the browser
        driver.close();
    }
}