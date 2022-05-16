package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogInWithTestNG {
	
	WebDriver driver;
	String browser;
	String url;
	
	//Element List
	By USER_NAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGN_IN_BUTTON_FIELD = By.xpath("//button[@type='submit']") ;
	By DASHBOARD_FIELD = By.xpath("//div[@id='wrapper']/div/div[2]/descendant::h2") ;
	By CUSTOMER_MENU_LOCATOR = By.xpath("By.xpath(\"//body[@class='fixed-nav']/section/div/nav/div/ul/li[3]/a/span[1]\")");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("By.xpath(\"//a[contains(text( ),'Add Customer')]\")");
	By ADD_CONTACT_HEADER_LOCATOR = By.xpath("//div[@id='page-wrapper']/div[3]/div/div/div/div/descendant::h5");
	By FULL_NAME_LOCATOR = By.xpath("By.xpath(\"//input[@id='account']\"");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@id='cid']");
	By EMAIL_LOCATOR = By.xpath("//input[@id='email']");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='country']");
	
	
	
	//Test Data
	String userName = "demo@techfios.com";
	String password = "abc123";
	
	@BeforeClass
	public void readConfig() {
		
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser=prop.getProperty("browser");
			System.out.println("Broser used: "+browser);
			url=prop.getProperty("url");
		}catch (IOException e){
			e.getStackTrace();
		}
	}
	
	@BeforeMethod
	public void init() {
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver","drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver","drivers\\geckodriver.exe" );
			driver = new FirefoxDriver();
		}
		
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	}
	
	@Test(priority=2)
	public void loginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGN_IN_BUTTON_FIELD).click();
		
		Assert.assertEquals(driver.findElement(DASHBOARD_FIELD).getText(),"Dashboard","Wrong Page!!");
		
		
	}
	
	//@Test(priority=1)
	public void negLoginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGN_IN_BUTTON_FIELD).click();
		
		Assert.assertEquals(driver.findElement(DASHBOARD_FIELD).getText(),"Dashboard","Wrong Page!!");
		
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
