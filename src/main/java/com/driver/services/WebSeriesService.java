package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository ;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo
        WebSeries series1 = webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName());
        if(series1 != null) {
            throw new Exception("Series is already present");
        }
        WebSeries series = new WebSeries();
        series.setSeriesName(webSeriesEntryDto.getSeriesName());
        series.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        series.setRating(webSeriesEntryDto.getRating());
        series.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());

        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();
        productionHouse.getWebSeriesList().add(series);

        List<WebSeries> list = productionHouse.getWebSeriesList();

        double rating = 0;
        for(WebSeries sec : list) {
            rating += sec.getRating();
        }

        productionHouse.setRatings(rating/productionHouse.getWebSeriesList().size());
        series.setProductionHouse(productionHouse);
        WebSeries saveWeb = webSeriesRepository.save(series);
        ProductionHouse save = productionHouseRepository.save(productionHouse);
        return saveWeb.getId();
    }

}



//package com.driver.services;
//
//import com.driver.EntryDto.WebSeriesEntryDto;
//import com.driver.model.ProductionHouse;
//import com.driver.model.WebSeries;
//import com.driver.repository.ProductionHouseRepository;
//import com.driver.repository.WebSeriesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class WebSeriesService {
//
//    @Autowired
//    WebSeriesRepository webSeriesRepository;
//
//    @Autowired
//    ProductionHouseRepository productionHouseRepository;
//
//    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception {
//
//        //Add a webSeries to the database and update the ratings of the productionHouse
//        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
//        //use function written in Repository Layer for the same
//        //Dont forget to save the production and webseries Repo
//        WebSeries webSeries = webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName());
//        if (webSeries != null) {
//            throw new Exception("Series is already present");
//        }
//        WebSeries newSeries = new WebSeries();
//        newSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
//        newSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
//        newSeries.setRating(webSeriesEntryDto.getRating());
//        newSeries.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());
//
//        Optional<ProductionHouse> productionHouseOptional = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId());
//        ProductionHouse productionHouse = productionHouseOptional.get();
//        productionHouse.getWebSeriesList().add(newSeries);
//        newSeries.setProductionHouse(productionHouse);
//
//        List<WebSeries> seriesList = productionHouse.getWebSeriesList();
//
//        double rating = 0;
//        for (WebSeries webSeries1 : seriesList) {
//            rating += webSeries1.getRating();
//        }
//
//        double avg = rating / productionHouse.getWebSeriesList().size();
//        webSeries.setProductionHouse(productionHouse);
//
//        WebSeries series = webSeriesRepository.save(newSeries);
//
//        productionHouse.setRatings(avg);
//
//        ProductionHouse savedProductionHouse = productionHouseRepository.save(productionHouse);
//
//        return savedProductionHouse.getId();
//
//    }
//
//
//}

