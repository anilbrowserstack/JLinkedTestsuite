package com.git.search;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SearchLinkedIn {
	static void searchInLinkedIn(String s, WebDriver driver, JavascriptExecutor js) throws IOException, InterruptedException {
		driver.get("http://www.linkedin.com");
		String list[] = s.split("\\s+");
		if (list.length == 2) {	
			System.out.println(list[0] + " " + list[1]);
			js.executeScript("document.getElementById('first').value=\""+list[0]+"\"");
			js.executeScript("document.getElementById('last').value=\""+list[1]+"\"");
		}
		Thread.sleep(1000);
        driver.findElement(By.className("btn-secondary")).click();
		long count=(Long) js.executeScript("return document.getElementsByClassName('profile-photo').length");
		System.out.println("No:of profiles = "+count);
		for(int result=0;result<count;result++) {
			String link = (String) js.executeScript("return document.getElementsByClassName('profile-photo')["+result+"].href");
			FileWrite.fileWrite(link, "link");
		}
		FileWrite.fileWrite("---------------------------------------------------------------\n", "link");
    }
}
