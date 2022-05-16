package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CustomerForm {
	

	WebDriver driver;
	String browser;
	String url;

	// Element List
	By USER_NAME_FIELD = By.xpath("//*[@id=\"username\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By ADD_CONTACT_HEADER_LOCATOR = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULL_NAME_LOCATOR = By.xpath("//*[@id=\"account\"]");
	By COMPANY_DROPDOWN_LOCATOR = By.xpath("//select[@id=\"cid\"]");
	By EMAIL_LOCATOR = By.xpath("//*[@id=\"email\"]");
	By PHONE_FIELD = By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@id='address']");
	By CITY_FIELD = By.xpath("//input[@id='city']");
	By STATE_FIELD = By.xpath("//input[@id='state']");
	By ZIP_CODE_FIELD =By.xpath("//input[@id='zip']");
	By COUNTRY_LOCATOR = By.xpath("//select[@id=\"country\"]");
	By TAGS_DROPDOWN_FIELD = By.xpath("//select[@id='tags']");
	By CURRENCY_DROPDOWN_FIELD = By.xpath("//select[@id='currency']");
	By GROUP_DROPDOWN_FIELD = By.xpath("//select[@id='group']");
	By FORM_PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By CONFIRM_FORM_PASSWORD_FIELD = By.xpath("//input[@id='cpassword']");
	By WELCOME_EMAIL_FIELD = By.xpath("//*[@id=\"rform\"]/div[1]/div[2]/div[5]/div/div/div/label[1]");
	By SAVE_BOTTON_FIELD = By.xpath("//button[@id='submit']");
	
	// Test Data
	String userName = "demo@techfios.com";
	String password = "abc123";
	
	@BeforeClass
	public void readConfig() {

		// FileReader //Scanner //InputStream //BufferedReader

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {
			e.getStackTrace();
		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

//	@Test(priority=2)
	public void loginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Wrong page!!!");

	}

	//@Test(priority=1)
	public void negLoginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		// Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(),
		// "Dashboard", "Wrong page!!!");

	}
	
	@Test
	public void addCustomerTest() {

		loginTest();

		driver.findElement(CUSTOMER_MENU_LOCATOR).click();

		waitForElement(driver, 5, ADD_CUSTOMER_MENU_LOCATOR);

		driver.findElement(ADD_CUSTOMER_MENU_LOCATOR).click();
		Assert.assertEquals(driver.findElement(ADD_CONTACT_HEADER_LOCATOR).getText(), "Add Contact", "Wrong page!!!");

		enterData(FULL_NAME_LOCATOR, "December Selenium" + generateRandomNo(999));

		selectFromDropdown(COMPANY_DROPDOWN_LOCATOR, "Techfios");

		enterData(EMAIL_LOCATOR, generateRandomNo(9999) + "abc@techfios.com");

		enterData(PHONE_FIELD, "0123456" + generateRandomNo(789));
		enterData(ADDRESS_FIELD, "2741 E Belt Line Road");

		enterData(CITY_FIELD, "Carrolton");
		enterData(STATE_FIELD, "Texas");

		selectFromDropdown(TAGS_DROPDOWN_FIELD, "IT Training");

		enterData(ZIP_CODE_FIELD, "75006");

		selectFromDropdown(COUNTRY_LOCATOR, "Algeria");
		selectFromDropdown(CURRENCY_DROPDOWN_FIELD, "USD");
		selectFromDropdown(GROUP_DROPDOWN_FIELD, "None");

		enterData(FORM_PASSWORD_FIELD, "abcd1234");
		enterData(CONFIRM_FORM_PASSWORD_FIELD, "abcd1234");

		Actions action = new Actions(driver);
		action.doubleClick(driver.findElement(WELCOME_EMAIL_FIELD)).build().perform();
		action.click(driver.findElement(SAVE_BOTTON_FIELD)).build().perform();

	}
	

	public void enterData(By locator, String data) {
		driver.findElement(locator).sendKeys(data);
	}
	

	public int generateRandomNo(int boundaryNo) {
		
		Random rnd = new Random();
		int generatedNo = rnd.nextInt(boundaryNo);
		return generatedNo;
		
	}

	public void selectFromDropdown(By locator, String visibleText) {
		
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(visibleText);
		
	}

	public void waitForElement(WebDriver driver, int timeInSeconds, By locator) {
		
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	
//	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}



