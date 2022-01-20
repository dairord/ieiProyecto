package selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForChrome {

	private static WebDriver driver = null;

	public static void Chrome() {
		String exePath = "C:\\Users\\pauma\\git\\ieiProyecto\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		driver.get("https://maps.google.com");
		// driver.quit();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		
		WebElement element = driver.findElement(By.xpath("/html/body/c-wiz/div/div/div/div[2]/div[1]"));
		if (element!=null) {
			element.click();
		}
		
		WebElement input = driver.findElement(By.xpath("/html/body/div[3]/div[9]/div[3]/div[1]/div[1]/div[1]/div[2]/form/div"));
		input.sendKeys("AVENIDA BOTANICO CAVANILLES 11 20, VALENCIA");
		input.sendKeys(Keys.ENTER);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until( ExpectedConditions.urlContains("@"));
				
		String url = driver.getCurrentUrl();
		System.out.println(url);
		
		/*
		 * WebElement ventanaCookies =
		 * driver.findElement(By.xpath("/html/body/aside/div/button")); if
		 * (ventanaCookies != null){ System.out.println("Detectado caja de cookies");
		 * ventanaCookies.click(); }
		 * 
		 * WebElement cajaBusqueda = driver.findElement(By.id("Fnac_Search"));
		 * cajaBusqueda.sendKeys("Cafeteras Bosch"); cajaBusqueda.submit();
		 * 
		 * 
		 * 
		 * Esperar a que se muestre el elemento Cafeteras WebDriverWait wait = new
		 * WebDriverWait(driver, Duration.ofSeconds(10));
		 * 
		 * //WebDriverWait waiting; //waiting = new WebDriverWait(driver, 10);
		 * 
		 * wait.until( ExpectedConditions.presenceOfElementLocated(
		 * By.cssSelector(".n2 > li:nth-child(3) > span:nth-child(1)")));
		 * 
		 * WebElement element =
		 * driver.findElement(By.cssSelector(".n2 > li:nth-child(3) > span:nth-child(1)"
		 * ));
		 * 
		 * if (element != null){ element.click();
		 * System.out.println("Pulsado sobre cafeteras"); }
		 */

	}

}
