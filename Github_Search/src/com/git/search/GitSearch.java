package com.git.search;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GitSearch {
	WebDriver driver = new ChromeDriver();
	JavascriptExecutor js = null;
	
	void githubSearch(String emailid, String pc) throws InterruptedException, IOException{
		driver.get("https://github.com/search?q=location%3Amumbai&type=Users&ref=advsearch&l=");
		Thread.sleep(1000);
		if (driver instanceof JavascriptExecutor) {
		    js = (JavascriptExecutor)driver;
		}
		long count =  (Long) js.executeScript("return $('.gravatar').length");
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
		GitSearch obj = new GitSearch();
		obj.githubSearch(args[0],args[1]);
	}
}
