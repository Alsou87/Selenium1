import org.junit.After;
import  org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import  org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;

public  class  Test1 {
    WebDriver driver;
    private String baseUrl;

    @Before
    public  void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver=new ChromeDriver();
        baseUrl="http://www.sberbank.ru/ru/person";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();//развернуть окно
        driver.get(baseUrl);

    }
    @Test
    public void test12() //основной
    {   Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
        //Застраховать себя и имущество, найти элемент и нажать
        driver.findElement(By.xpath("//div[@class = 'sbrf-div-list-inner --area bp-area header_more_nav']//a[@aria-label = 'Раздел Застраховать себя  и имущество']")).click();
        WebElement insuranceOfTravelers = driver.findElement(By.xpath("//div[@class = 'sbrf-div-list-inner --area bp-area header_more_nav']//a[text()='Страхование путешественников']"));
        wait.until(ExpectedConditions.visibilityOf(insuranceOfTravelers)).click();
        //Проверить наличие на странице заголовка – Страхование путешественников
        WebElement title = driver.findElement(By.xpath("//div[@class = 'sbrf-rich-outer']/h1"));
        wait.until(ExpectedConditions.visibilityOf(title));
        assertEquals("Страхование путешественников", title.getText());
        //находим котейнер и нажимаем
        driver.findElement(By.xpath("//*[@id=\"SBRF_TabContainer_sb_bundle-47610460\"]/div/div[1]/div/div[1]/div")).click();
        //переход на новую вкладку
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

        WebElement minSumPad = driver.findElement(By.xpath("//div[text() = 'Минимальная']/.."));
        wait.until(ExpectedConditions.visibilityOf(minSumPad)).click();

        driver.findElement(By.xpath("//span[text() = 'Оформить']")).click();
        //Фамилию и Имя, Дату рождения застрахованных, Данные страхователя: Фамилия, Имя, Отчество, Дата рождения, Пол, Паспортные данные
        fillField(By.name("insured0_surname"), "Ziganshina");
        fillField(By.name("insured0_name"), "Alsou");
        fillField(By.name("insured0_birthDate"), "02.03.1987");

        fillField(By.name("surname"),"Дьяконов");
        fillField(By.name("name"), "Максим");
        fillField(By.name("middlename"), "Сергеевич");
        fillField(By.name("birthDate"), "06.11.1985");
        driver.findElement(By.name("male")).click();
        fillField(By.name("passport_series"), "8006");
        fillField(By.name("passport_number"), "855006");
        fillField(By.name("issueDate"), "06.11.2000");
        fillField(By.name("issuePlace"),"Кировским РУВД города Уфы");
        //проверить, что все поля заполнены правильно
        assertEquals("Ziganshina",driver.findElement(By.name("insured0_surname")).getAttribute("value"));
        assertEquals("Alsou",driver.findElement(By.name("insured0_name")).getAttribute("value"));
        assertEquals("02.03.1987",driver.findElement(By.name("insured0_birthDate")).getAttribute("value"));

        assertEquals("Дьяконов",driver.findElement(By.name("surname")).getAttribute("value"));
        assertEquals("Максим",driver.findElement(By.name("name")).getAttribute("value"));
        assertEquals("Сергеевич",driver.findElement(By.name("middlename")).getAttribute("value"));
        assertEquals("06.11.1985",driver.findElement(By.name("birthDate")).getAttribute("value"));
        assertEquals("8006",driver.findElement(By.name("passport_series")).getAttribute("value"));
        assertEquals("855006",driver.findElement(By.name("passport_number")).getAttribute("value"));
        assertEquals("06.11.2000",driver.findElement(By.name("issueDate")).getAttribute("value"));
        assertEquals("Кировским РУВД города Уфы",driver.findElement(By.name("issuePlace")).getAttribute("value"));
        //Нажать продолжить
        driver.findElement(By.xpath("//span[@ng-click='save()']")).click();

        assertEquals("Заполнены не все обязательные поля",driver.findElement(By.xpath("//DIV[@ng-show='tryNext && myForm.$invalid'][text()='Заполнены не все обязательные поля']")).getText());


    }
    @After
    public void afterTest() {
       // driver.quit();
    }
    private void fillField(By locator, String value){

        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
    }
