package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

    User savedUser  = userRepository.save(user);

        //Jut simply add the user to the Db and return the userId returned by the repository
        return savedUser.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository

        List<WebSeries > webSeriesList = webSeriesRepository.findAll();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();
        int userAge = user.getAge();
        SubscriptionType subscriptionType = user.getSubscription().getSubscriptionType();
        int availabeWebSeries = 0;
        if(subscriptionType == SubscriptionType.BASIC){
            for(WebSeries webSeries : webSeriesList){
                if(webSeries.getSubscriptionType() == subscriptionType && webSeries.getAgeLimit()<= user.getAge()){
                    availabeWebSeries++;
                }
            }
        }
        if(subscriptionType == SubscriptionType.PRO){
            for(WebSeries webSeries: webSeriesList){
                if((webSeries.getSubscriptionType() == subscriptionType || webSeries.getSubscriptionType()== SubscriptionType.BASIC)&& webSeries.getAgeLimit() <=user.getAge()){
                    availabeWebSeries++;
                }
            }
        }
        else{
            for(WebSeries webSeries: webSeriesList){
                if(webSeries.getAgeLimit()<=user.getAge()){
                    availabeWebSeries++;
                }
            }
        }
        return availabeWebSeries;


    }


}
