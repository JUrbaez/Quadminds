package FrontEndTests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

import Pages.MainPage;

public class FE_Tests {
	
	static String Url;
	static WebDriver driver;
	static MainPage mlPage;
	
	@BeforeClass
	@Parameters("browser")
	public void setUpTests(String browser) 
	{
		Url = "https://www.mercadolibre.com.ar";
		
		if(browser.equalsIgnoreCase("chrome")) 
		{
			WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();	        
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		
	}
	
	@AfterTest
	private static void closeDriver() 
	{
		driver.quit();
	}
	
	private static void mouseAction(WebDriver driver, WebElement el) 
	{
		 Actions action = new Actions(driver);
     	 action.moveToElement(el).perform();	
	}
	
	private static void newExplicitWait(WebDriver driver, String thePath) 
	{
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(thePath)));
	}
	
	@Test(description = "Verificar categoria - Climatizacion")
    public static void verifyCategoryClimatizacion() 
    {
       try
       {
           driver.get(Url);
           mlPage = new MainPage(driver);
           WebElement catMenu = mlPage.getCatMenu();
           mouseAction(driver, catMenu);
       	   
       	   newExplicitWait(driver, "//a[contains(text(),'Hogar y Electrodomésticos')]");
       	       	   
       	   WebElement subCat = driver.findElement(By.xpath("//a[contains(text(),'Hogar y Electrodomésticos')]"));	
       	   mouseAction(driver, subCat);
           driver.findElement(By.xpath("//a[contains(text(),'Climatización')]")).click();
           
           String catTitle = driver.findElement(By.xpath("//h1[contains(@class,'breadcrumb__title')]")).getText();
           String catNumber = driver.findElement(By.xpath("//div[@class='quantity-results']")).getText();
           String [] catNumber2 = catNumber.split(" ", 0);
         
           final SoftAssert softAssert = new SoftAssert();
		   softAssert.assertTrue(catTitle.equalsIgnoreCase("Climatización"));
		   softAssert.assertTrue(Double.parseDouble(catNumber2[0])>0);
		   softAssert.assertAll();
       }
       catch(Exception e) 
       {
           System.out.println(e);
       }
    }
	
	@Test(description = "Verificar la grilla de Celulares y Smartphones")
    public static void verifySmartCellsGrid() 
    {
       try
       {
           driver.get(Url);
           mlPage = new MainPage(driver);
           WebElement catMenu = mlPage.getCatMenu();
           mouseAction(driver, catMenu);
       	   
           newExplicitWait(driver, "//a[contains(text(),'Tecnología')]");
	       	   
       	   WebElement subCat = driver.findElement(By.xpath("//a[contains(text(),'Tecnología')]"));	
       	   mouseAction(driver, subCat);
    	   
           driver.findElement(By.xpath("//a[contains(text(),'Celulares y Smartphones')]")).click();
           driver.findElement(By.xpath("//div[@class='ico view-option-grid']")).click();
           
           System.out.println(driver.findElements(By.xpath("//ol[@id='searchResults']//form")).size());
       }
       catch(Exception e) 
       {
           System.out.println(e);
       }
    }
	
	@Test(description = "Verificar el mayor y el menor precio de una categoria")
    public static void verifyPrices() 
    {
       try
       {
    	   driver.get(Url);
    	   mlPage = new MainPage(driver);
           WebElement catMenu = mlPage.getCatMenu();
           mouseAction(driver, catMenu);
       	   
           newExplicitWait(driver, "//a[contains(text(),'Herramientas e Industrias')]");
       	   
       	   WebElement subCat = driver.findElement(By.xpath("//a[contains(text(),'Herramientas e Industrias')]"));	
       	   
       	   mouseAction(driver, subCat);
    	   
           driver.findElement(By.xpath("//a[contains(text(),'Industria Textil')]")).click();
           driver.findElement(By.xpath("//div[@class='ui-dropdown__indicator']")).click();
           driver.findElement(By.xpath("//a[contains(text(),'Mayor precio')]")).click();
           
           int itemCount = driver.findElements(By.xpath("//ol[@id='searchResults']//form")).size();
           List<WebElement> itemList = driver.findElements(By.xpath("//span[@class='price__fraction']"));
           
           WebElement el1 = itemList.get(0);
           System.out.println("Industri Textil - Mayor precio - >  " + el1.getText());
           
           WebElement el2 = itemList.get(itemCount-1);
           System.out.println("Industri Textil - Menor precio - >  " + el2.getText());
           
           //Verificar que el mayor precio en realidad sea mayor que el menor precio
        
           final SoftAssert softAssert = new SoftAssert();
		   softAssert.assertTrue(Double.parseDouble(el1.getText())>Double.parseDouble(el2.getText().replace(".", "")));
		   softAssert.assertAll();
           
       }
       catch(Exception e) 
       {
           System.out.println(e);
       }
    }
	
	@Test(description = "Verify Free Shipping Bar")
    public static void ejercicio2() 
    {
       try
       {
           driver.get(Url);
           mlPage = new MainPage(driver);
           WebElement catMenu = mlPage.getCatMenu();
           mouseAction(driver, catMenu);
       	   
           newExplicitWait(driver, "//a[contains(text(),'Tecnología')]");
       	   
       	   WebElement subCat = driver.findElement(By.xpath("//a[contains(text(),'Tecnología')]"));	
       	   
       	   mouseAction(driver, subCat);
    	   
           driver.findElement(By.xpath("//a[contains(text(),'Celulares y Smartphones')]")).click();
           
           List<WebElement> itemList = driver.findElements(By.xpath("//div[@class='item__info item--show-right-col ']"));
           WebElement el1 = itemList.get(0).findElement(By.xpath("//div[@class='item__info item--show-right-col ']//h2"));
           System.out.println(el1.getText());
           
           WebElement el2 = itemList.get(0).findElement(By.xpath("//div[@class='item__info item--show-right-col ']//span[@class='price__fraction']"));
           System.out.println(el2.getText());
           
           String el1Text = el1.getText();
           String el2Text = el2.getText();
           
           el1.click();
           
           WebElement el3 = driver.findElement(By.xpath("//h1[@class='ui-pdp-title']"));
           System.out.println(el3.getText());
           
           WebElement el4 = driver.findElements(By.xpath("//span[@class='price-tag-fraction']")).get(0);
           System.out.println(el4.getText());
           
           String el3Text = el3.getText();
           String el4Text = el4.getText();
           
           final SoftAssert softAssert = new SoftAssert();
		   softAssert.assertTrue(el1Text.equalsIgnoreCase(el3Text));
		   softAssert.assertTrue(el2Text.equalsIgnoreCase(el4Text));
		   softAssert.assertAll();
       }
       catch(Exception e) 
       {
           System.out.println(e);
       }
    }
}
