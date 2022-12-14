package pl.restmeans.utils;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;
import pl.restmeans.exceptions.WebScrapingException;
import pl.restmeans.messages.Messages;
import pl.restmeans.model.Grade;
import pl.restmeans.model.GradesAndMeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class WebScraper {

    private final Messages messages;

    public GradesAndMeans getGradesAndMeans(String login, String password) throws WebScrapingException {
        WebDriver webDriver = getWebDriver(login, password);

        return readGrades(webDriver);
    }

    public GradesAndMeans readGrades(WebDriver webDriver) {
        String semester = webDriver.findElement(By.cssSelector(".lblSemestrRok")).getText();
        int semesterInt = Integer.parseInt(String.valueOf(semester.charAt(9)));
        Map<Integer, List<Grade>> myGrades = new HashMap<>();

        for (int i = semesterInt; i >= 1; i-- ) {
            List<Grade> listOfGrades = new ArrayList<>();
            int j = 1;
            int sum = 0;
            final String rowXpath = "//tbody/tr[@style='background-color:#FFFF00;'][";

            while(sum != 30) {
                String grade = "";

                int k = 6;
                try {
                    while (grade.equals("")) {
                        grade = webDriver.findElement(By.xpath(rowXpath + j +"]/td[" + k + "]/span[1]"))
                                .getText();
                        k++;
                    }
                } catch (Exception e) {
                    break;
                }

                String nameOfSubject = webDriver.findElement(By.xpath(rowXpath + j +"]/td[1]")).getText();
                String ects = webDriver.findElement(By.xpath(rowXpath + j +"]/td[11]")).getText();

                if(!grade.equals("zal")) {
                    Grade newGrade = new Grade(nameOfSubject,
                            Double.parseDouble(grade.replace(",", ".")), Integer.parseInt(ects));
                    listOfGrades.add(newGrade);
                }

                sum += Integer.parseInt(ects);
                j++;
            }

            if (!listOfGrades.isEmpty()) {
                myGrades.put(i, listOfGrades);
            }

            try {
                webDriver.findElement(By.xpath("//div[@class = 'actions_panel']/" +
                        "input[@value = 'Poprzedni']")).click();
            } catch (Exception ignored) {}
        }

        GradesAndMeans gradesAndMeans = new GradesAndMeans(myGrades);
        gradesAndMeans.setMeansOfSemesters(Calculator.calculateMeansOfSemesters(myGrades));
        gradesAndMeans.setMeansOfYears(Calculator.calculateMeansOfYear(gradesAndMeans.getMeansOfSemesters()));

        return gradesAndMeans;
    }

    private WebDriver getWebDriver(String login, String password) throws WebScrapingException {
        WebDriver webDriver = new HtmlUnitDriver();
        webDriver.get(messages.getMessage("scraper.address"));
        webDriver.findElement(By.name("ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$txtIdent"))
                .sendKeys(login);
        webDriver.findElement(By.name("ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$txtHaslo"))
                .sendKeys(password);
        webDriver.findElement(By.name("ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$butLoguj")).click();

        try {
            webDriver.findElement(By.cssSelector(".rmRootGroup.rmHorizontal"))
                    .findElement(By.xpath("//li[2]/a")).click();
        } catch (RuntimeException e) {
            throw new WebScrapingException(messages.getMessage("webscraping.exception"));
        }

        return webDriver;
    }
}
