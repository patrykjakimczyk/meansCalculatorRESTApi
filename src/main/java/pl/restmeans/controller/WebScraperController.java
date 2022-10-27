package pl.restmeans.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.restmeans.exceptions.WebScrapingException;
import pl.restmeans.model.GradesAndMeans;
import pl.restmeans.model.MeanOfSemester;
import pl.restmeans.model.MeanOfYear;
import pl.restmeans.service.WebScraperService;

import java.util.List;

@RestController
@RequestMapping(path="myMean")
@RequiredArgsConstructor
public class WebScraperController {

    private final WebScraperService webScraperService;

    @PostMapping("/allMeans")
    public GradesAndMeans getAllMeans(
            @RequestParam String login,
            @RequestParam String password) throws WebScrapingException {

        return webScraperService.getAllMeans(login, password);
    }

    @PostMapping("/semesterMeans")
    public List<MeanOfSemester> getSemesterMeans(
            @RequestParam String login,
            @RequestParam String password) throws WebScrapingException {

        return webScraperService.getListOfSemestersMeans(login, password);
    }

    @PostMapping("/yearlyMeans")
    public List<MeanOfYear> getAllYearsMeans(
            @RequestParam String login,
            @RequestParam String password) throws WebScrapingException {

        return webScraperService.getAllYearsMeans(login, password);
    }
}
