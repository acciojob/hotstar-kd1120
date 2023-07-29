package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import jdk.internal.loader.AbstractClassLoaderValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay

        Optional<User> optionalUser = userRepository.findById(subscriptionEntryDto.getUserId());
        User user = optionalUser.get();


        int total =0;
        SubscriptionType subscriptionType = subscriptionEntryDto.getSubscriptionType();
        int noOfScreens = subscriptionEntryDto.getNoOfScreensRequired();

        if(subscriptionType == SubscriptionType.BASIC){
            total = 500 + (200 * noOfScreens);
        }
        if(subscriptionType == SubscriptionType.PRO){
            total = 800 + ( 250 * noOfScreens);
        }
        else{
            total = 1000 + ( 300 * noOfScreens);
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        subscription.setTotalAmountPaid(total);

        user.setSubscription(subscription);

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        return savedSubscription.getTotalAmountPaid();

    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();


        Subscription subscription = user.getSubscription();
        if(subscription.getSubscriptionType() == SubscriptionType.ELITE){
            throw new Exception("Already the best Subscription");

        }
        int currPrice = subscription.getTotalAmountPaid();
        int priceDiff = 0;
        int noOfScreens = subscription.getNoOfScreensSubscribed();
        if(subscription.getSubscriptionType()== SubscriptionType.BASIC){
            subscription.setSubscriptionType(SubscriptionType.PRO);
            int newPrice = 800 + (250 * noOfScreens);
        priceDiff = newPrice - currPrice;
        subscription.setTotalAmountPaid(newPrice + currPrice);
        }
        else{
            subscription.setSubscriptionType(SubscriptionType.ELITE);
            int newPrice = 1000 + ( 300 * noOfScreens);
            priceDiff = newPrice - currPrice;
            subscription.setTotalAmountPaid(newPrice + currPrice);
        }

        user.setSubscription(subscription);

        subscriptionRepository.save(subscription);




        return priceDiff;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        int total = 0;
        List<User> users = userRepository.findAll();
        for(User user:users){
            Subscription subscription = user.getSubscription();
            if(subscription!=null){
                total += subscription.getTotalAmountPaid();
            }
        }
        return total;
    }

}
