package de.fynnhenck.chats;

import de.fynnhenck.util.Chat;
import de.fynnhenck.util.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;

public class iLuvo extends Chat{

    private WebDriver driver;

    boolean messageAvailable;
    private boolean stop = false;

    private final String user;
    private final String pass;

    Message msg;

    private String url = "https://iluvo.de/login/azchat";

    public iLuvo(String user, String pass){
        super(1, user, pass);
        this.user = user;
        this.pass = pass;
    }

    @Override
    public void open(){
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("btn"));

        emailField.sendKeys(user);
        passwordField.sendKeys(pass);
        loginButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.get("https://iluvo.de/azchat/chat"); //redirect to chat
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        checkForMessage();

    }

    @Override
    public void checkForMessage(){

            System.out.println("Checking for messages...");
            msg = null;

            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofHours(4));
            //wait.until(d -> );
            WebElement msgs = driver.findElement(By.name("msgbody")); //id timer

            if(driver.findElement(By.name("msgbody")).isDisplayed()){
                System.out.println("msg found");
                messageAvailable = true;

                WebElement msg = driver.findElement(By.id("nachrichtencontent"));
                this.msg = new Message(msg.getText());
                WebElement profile_c = driver.findElement(By.className("profilekunde"));
                WebElement name_c = profile_c.findElement(By.tagName("p"));
                this.msg.setName_c(name_c.getText());
                this.msg.setBio_c( profile_c.findElement(By.tagName("hr")).getText());
                this.msg.setLockbook_c(profile_c.findElement(By.className("notes")).getText()); //Langzeit notizen



            }
    }

    //DEMO
    Scanner scanner = new Scanner(System.in);
    private void openScanner(){
        System.out.println("Res:");
        respond(scanner.nextLine());
    }


    @Override
    public Message getMessage(){
        if(messageAvailable){
            return msg;
        }

        return null;
    }

    @Override
    public void respond(String message){
        if(messageAvailable){
            WebElement sendButton = driver.findElement(By.cssSelector("btn btn-success"));
            WebElement input = driver.findElement(By.name("msgbody"));
            input.sendKeys(message);
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            sendButton.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
            messageAvailable = false;
            if(!stop){
                checkForMessage();
            }
        }
    }

    public void stopChat(){ //logout etc....
        stop = true;
        driver.quit();
    }

    @Override
    public boolean messageAvailable(){
        return messageAvailable;
    }

}
