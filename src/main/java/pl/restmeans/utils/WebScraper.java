package pl.restmeans.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import pl.restmeans.model.Grade;
import pl.restmeans.model.GradesAndMeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebScraper {

    private WebScraper() {}

    public static GradesAndMeans getGradesAndMeans(String login, String password) {
        WebDriver webDriver = getGradesWebDriver(login, password);

        return readGrades(webDriver);
    }

    public static GradesAndMeans readGrades(WebDriver webDriver) {
        String semestr = webDriver.findElement(By.cssSelector(".lblSemestrRok")).getText();
        int sem = Integer.parseInt(String.valueOf(semestr.charAt(9)));
        Map<Integer, List<Grade>> myGrades = new HashMap<>();

        for (int i = sem; i >= 1; i-- ) {
            List<Grade> listOfGrades = new ArrayList<>();
            int j = 1;
            int sum = 0;

            while(sum != 30) {
                String grade = "";

                int k = 6;
                try {
                    while (grade.equals("")) {
                        grade = webDriver.findElement(By.xpath("//tbody/tr[@style='background-color:#FFFF00;'][" + j +"]/td[" + k + "]/span[1]")).getText();
                        k++;
                    }
                } catch (Exception e) {
                    break;
                }

                String nameOfSubject = webDriver.findElement(By.xpath("//tbody/tr[@style='background-color:#FFFF00;'][" + j +"]/td[1]")).getText();
                String ects = webDriver.findElement(By.xpath("//tbody/tr[@style='background-color:#FFFF00;'][" + j +"]/td[11]")).getText();

                if(!grade.equals("zal")) {
                    Grade newGrade = new Grade(nameOfSubject, Double.parseDouble(grade.replace(",", ".")), Integer.parseInt(ects));
                    listOfGrades.add(newGrade);
                }

                sum += Integer.parseInt(ects);
                j++;
            }

            if (!listOfGrades.isEmpty()) {
                myGrades.put(i, listOfGrades);
            }

            try {
                webDriver.findElement(By.xpath("//div[@class = 'actions_panel']/input[@value = 'Poprzedni']")).click();
            } catch (Exception ignored) {
            }
        }

        GradesAndMeans gradesAndMeans = new GradesAndMeans(myGrades);
        gradesAndMeans.setMeansOfSemesters(Calculator.calculateMeansOfSemesters(myGrades));
        gradesAndMeans.setMeansOfYears(Calculator.calculateMeansOfYear(gradesAndMeans.getMeansOfSemesters()));

        return gradesAndMeans;
    }

    public static WebDriver getGradesWebDriver(String login, String password) {
        WebDriver webDriver = new HtmlUnitDriver();
        webDriver.get("https://edziekanat.zut.edu.pl/WU/");
        webDriver.findElement(By.name("ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$txtIdent")).sendKeys(login);
        webDriver.findElement(By.name("ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$txtHaslo")).sendKeys(password);
        webDriver.findElement(By.name("ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$butLoguj")).click();
        webDriver.findElement(By.cssSelector(".rmRootGroup.rmHorizontal")).findElement(By.xpath("//li[2]/a")).click();

        return webDriver;
    }
}
