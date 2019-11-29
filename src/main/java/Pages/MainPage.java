package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
import lombok.Getter;
 
@Getter
public class MainPage 
{
	private WebElement catMenu;
 
    public MainPage(WebDriver driver) 
    {
        try{
        	catMenu = driver.findElement(By.xpath("//a[@class='nav-menu-categories-link']"));
            
        }catch(NoSuchElementException | ScriptTimeoutException | TimeoutException ignored) {}
    }

	public WebElement getCatMenu() {
		return catMenu;
	}
}
