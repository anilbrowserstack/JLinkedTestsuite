package com.git.search;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GitSearch {
	//WebDriver driver = new ChromeDriver();
	//WebDriver driver = new FirefoxDriver();
	WebDriver driver = null;
	JavascriptExecutor js = null;
	
	void githubSearch(String emailid, String pc) throws InterruptedException, IOException{
		//DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		DesiredCapabilities capabilities = new DesiredCapabilities();
//	    capabilities.setVersion("7");
	    capabilities.setPlatform(Platform.WINDOWS);
	    capabilities.setBrowserName("internet explorer");
	    //capabilities.setPlatform(Platform.MAC);
	    //capabilities.setCapability("browserstack.debug","true");
	    capabilities.setCapability("build", "HUB_SEL1_TEST");
	    capabilities.setCapability("project", "HUB_SEL1_TEST");
		//hub.browserstack
	    driver = new RemoteWebDriver(new URL("http://AnilKumar4:88a9f5607d78f757481450d52f7e4cb7@hub.browserstack.com:4444/wd/hub"), capabilities);
		//my m/c
	    //driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		//dhimil m/c
		//driver = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), capabilities);
	    //sauce labs
	    //driver = new RemoteWebDriver(new URL("http://utsavk:2fb97838-fac2-4780-b4f0-586b7f061944@ondemand.saucelabs.com:80/wd/hub"), capabilities);
	    //fuhub.browserstack with wtf credentials
	    //driver = new RemoteWebDriver(new URL("http://anil8:ff73fcee5c3179fe480e3b27e8a7254b@fuhub.browserstack.com:4444/wd/hub"), capabilities);
	    //fuhub anil.keralite
	    //driver = new RemoteWebDriver(new URL("http://anil:eea464767f429ab13bd3e4d3330db67c@fuhub.browserstack.com:4444/wd/hub"), capabilities);
	    //fuhub automationbs+6@gmail.com
	    //driver = new RemoteWebDriver(new URL("http://BSAutomation8:5c7b1ebc567b22a05359d210be4e1ff6@fuhub.browserstack.com:4444/wd/hub"), capabilities);

		driver.get("https://github.com/search?q=location%3Amumbai&type=Users&ref=advsearch&l=");
		//Thread.sleep(10000);
		if (driver instanceof JavascriptExecutor) {
		    js = (JavascriptExecutor)driver;
		}
		//long count =  (Long) js.executeScript("return $('.gravatar').length");
		long count =  (Long) js.executeScript("return document.getElementsByClassName('gravatar').length");
		String current_url = driver.getCurrentUrl();
		System.out.println("Current URL: "+current_url);
		String cwd = System.getProperty("user.dir");
		System.out.println("CWD = "+cwd);
		File file = new File("github_userlinks.txt");
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
		FileWrite.fileWrite("----------------------------------------------------------\n", "link");
		String personal_website = "";
		String email_addr = "";
		String name = "";
		//for(int pagecount=0;pagecount<2;pagecount++) {
		for(int pagecount=0;pagecount<Integer.parseInt(pc);pagecount++) {
			for(int usercount=0;usercount<count;usercount++) {
				js.executeScript("return $('.gravatar')["+usercount+"].click()");
				name = (String) js.executeScript("return $('.avatared h1 span').text()");
				//If email exists
            	long email = (Long) js.executeScript("return document.getElementsByClassName('email').length");
            	if (email > 0) {
            		email_addr =(String) js.executeScript("return document.getElementsByClassName('email')[0].text");
            	}
            	//If url exists
            	long url = (Long) js.executeScript("return document.getElementsByClassName('url').length");
            	if (url > 0) {
            		personal_website =(String) js.executeScript("return document.getElementsByClassName('url')[0].text");
            	}
            	//If stats exists
            	long stats = (Long) js.executeScript("return document.getElementsByClassName('stats')[0].getElementsByTagName('strong').length");
            	if (stats == 3) {
            		String followers =(String) js.executeScript("return document.getElementsByClassName('stats')[0].getElementsByTagName('strong').item(0).innerHTML");
            		String starred =(String) js.executeScript("return document.getElementsByClassName('stats')[0].getElementsByTagName('strong').item(1).innerHTML");
            		String following =(String) js.executeScript("return document.getElementsByClassName('stats')[0].getElementsByTagName('strong').item(2).innerHTML");
            		FileWrite.fileWrite("Name: "+name+","+"Email: "+email_addr+","+"Personal Website: "+personal_website+","+"Followers: "+followers+","+"Starred: "+starred+","+"Following: "+following,"link");
            	}
            	SearchLinkedIn.searchInLinkedIn(name,driver, js);
            	driver.get(current_url);
				Thread.sleep(1000);
			}
			if (pagecount+1<Integer.parseInt(pc))
				js.executeScript("return $('.next_page').click()");
			Thread.sleep(5000);
			current_url = driver.getCurrentUrl();
			System.out.println("Current URL: "+current_url);
		}
		Thread.sleep(5000);
		System.out.println("DONE");
		SendMail.sendemail(emailid);
		FileWrite.fileWrite("----------------------------------------------------------\n", "link");
		driver.quit();
	}
	public static void main(String[] args) throws InterruptedException, IOException{
		long startTime = System.currentTimeMillis();
		GitSearch obj = new GitSearch();
		obj.githubSearch(args[0],args[1]);
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Total time taken by the test: "+elapsedTime);
	    String name = "Total time taken by the test: "+elapsedTime;
	    FileWrite.fileWrite(name, "link");
	}
}
