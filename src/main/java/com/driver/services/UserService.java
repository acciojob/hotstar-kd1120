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

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setAge(user.getAge());
        newUser.setMobNo(user.getMobNo());
        newUser.setSubscription(user.getSubscription());
        User saveUser = userRepository.save(newUser);
        return (Integer) saveUser.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        User user = userRepository.findById(userId).get();
        Integer age = 0;
        if(user.getAge() <= 18){
            age = 18;
        } else{
            age = Integer.MAX_VALUE;
        }
        List<WebSeries> webSeriesList= webSeriesRepository.findAll();

        Integer count=0;

        for(WebSeries webSeries : webSeriesList){
            if( webSeries.getAgeLimit() <= age ) count++;
        }


        return count;
    }


}






//package com.driver.services;
//
//
//import com.driver.model.Subscription;
//import com.driver.model.SubscriptionType;
//import com.driver.model.User;
//import com.driver.model.WebSeries;
//import com.driver.repository.UserRepository;
//import com.driver.repository.WebSeriesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    WebSeriesRepository webSeriesRepository;
//
//
//    public Integer addUser(User user){
//
//    User savedUser  = userRepository.save(user);
//
//    savedUser.setName(user.getName());
//
//    savedUser.setAge(user.getAge());
//    savedUser.setMobNo(user.getMobNo());
//savedUser.setSubscription(user.getSubscription());        //Jut simply add the user to the Db and return the userId returned by the repository
//        return savedUser.getId();
//    }
//
//    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){
//
//        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
//        //Hint: Take out all the Webseries from the WebRepository
//
//        List<WebSeries > webSeriesList = webSeriesRepository.findAll();
//        Optional<User> userOptional = userRepository.findById(userId);
//        User user = userOptional.get();
//        int userAge = user.getAge();
//        SubscriptionType subscriptionType = user.getSubscription().getSubscriptionType();
//        int availabeWebSeries = 0;
////        if(subscriptionType == SubscriptionType.BASIC){
////            for(WebSeries webSeries : webSeriesList){
////                if(webSeries.getSubscriptionType() == subscriptionType && webSeries.getAgeLimit()<= user.getAge()){
////                    availabeWebSeries++;
////                }
////            }
////        }
////        if(subscriptionType == SubscriptionType.PRO){
////            for(WebSeries webSeries: webSeriesList){
////                if((webSeries.getSubscriptionType() == subscriptionType || webSeries.getSubscriptionType()== SubscriptionType.BASIC)&& webSeries.getAgeLimit() <=user.getAge()){
////                    availabeWebSeries++;
////                }
////            }
////        }
////        else{
////            for(WebSeries webSeries: webSeriesList){
////                if(webSeries.getAgeLimit()<=user.getAge()){
////                    availabeWebSeries++;
////                }
////            }
////        }
//        for(WebSeries webSeries : webSeriesList){
//            if(webSeries.getAgeLimit()<= userAge){
//                availabeWebSeries++;
//            }
//        }
//
//        return availabeWebSeries;
//
//
//    }
//
//
//}
