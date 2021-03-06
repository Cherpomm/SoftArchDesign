package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class StringPublisher implements Flow.Publisher {

    private List<StringSubscription> subscribers;

    private String state;

    public StringPublisher() {
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        if(subscriber == null || subscribers.contains(subscriber))  subscriber.onError(new IllegalStateException());
        var s = new StringSubscription(subscriber);
        subscribers.add(s);
        subscriber.onSubscribe(s);
    }

    public void publish(String state){
        this.state = state;
        notifySubscribers();
    }

    public void notifySubscribers(){
        for(var sub : subscribers){
              sub.updates(state);
        }
    }
}
