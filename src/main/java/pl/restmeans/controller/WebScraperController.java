package pl.restmeans.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.restmeans.model.GradesAndMeans;
import pl.restmeans.model.MeanOfSemester;
import pl.restmeans.model.MeanOfYear;
import pl.restmeans.utils.WebScraper;

import java.util.List;

@RestController
@RequestMapping(path="myMean")
public class WebScraperController {

    @GetMapping("/allMeans")
    public GradesAndMeans getAllMeans(
            @RequestParam String login,
            @RequestParam String password) {

        return WebScraper.getGradesAndMeans(login, password);
    }

    @GetMapping("/semesterMeans")
    public List<MeanOfSemester> getSemesterMeans(
            @RequestParam String login,
            @RequestParam String password) {

        return WebScraper.getGradesAndMeans(login, password).getMeansOfSemesters();
    }

    @GetMapping("/yearlyMeans")
    public List<MeanOfYear> getallYearsMeans(
            @RequestParam String login,
            @RequestParam String password) {

        return WebScraper.getGradesAndMeans(login, password).getMeansOfYears();
    }

}
