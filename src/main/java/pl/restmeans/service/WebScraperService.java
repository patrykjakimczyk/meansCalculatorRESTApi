package pl.restmeans.service;

import pl.restmeans.exceptions.WebScrapingException;
import pl.restmeans.model.GradesAndMeans;
import pl.restmeans.model.MeanOfSemester;
import pl.restmeans.model.MeanOfYear;

import java.util.List;

public interface WebScraperService {
    GradesAndMeans getAllMeans(String login, String password) throws WebScrapingException;

    List<MeanOfSemester> getListOfSemestersMeans(String login, String password) throws WebScrapingException;

    List<MeanOfYear> getAllYearsMeans(String login, String password) throws WebScrapingException;
}
