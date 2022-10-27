package pl.restmeans.service;

import org.springframework.stereotype.Service;
import pl.restmeans.exceptions.WebScrapingException;
import pl.restmeans.model.GradesAndMeans;
import pl.restmeans.model.MeanOfSemester;
import pl.restmeans.model.MeanOfYear;
import pl.restmeans.utils.WebScraper;

import java.util.List;

@Service
public class WebScraperServiceImpl implements WebScraperService {

    @Override
    public GradesAndMeans getAllMeans(String login, String password) throws WebScrapingException {
            return WebScraper.getGradesAndMeans(login, password);
    }

    @Override
    public List<MeanOfSemester> getListOfSemestersMeans(String login, String password) throws WebScrapingException {
        return WebScraper.getGradesAndMeans(login, password).getMeansOfSemesters();
    }

    @Override
    public List<MeanOfYear> getAllYearsMeans(String login, String password) throws WebScrapingException {
        return WebScraper.getGradesAndMeans(login, password).getMeansOfYears();
    }
}
