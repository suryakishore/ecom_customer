package com.customer.fivecanale.mqtt_chat;

import io.reactivex.Observable;
import io.reactivex.Observer;
import org.json.JSONObject;

/**
 * <h>ChatDataObervable</h>
 * Created by Ali on 12/22/2017.
 */

public class ChatDataObervable extends Observable<JSONObject>
{
    private static ChatDataObervable chatDataObervable;

    Observer<?super JSONObject> observer;

    public static ChatDataObervable getInstance()
    {
        if(chatDataObervable==null)
        {
            chatDataObervable  = new ChatDataObervable();
            return chatDataObervable;
        }
        else
        {
            return chatDataObervable;
        }
    }
    @Override
    protected void subscribeActual(Observer<? super JSONObject> observer)
    {
        this.observer = observer;
    }
    public void emitData(JSONObject cData)
    {
        observer.onNext(cData);
        observer.onComplete();
    }

}
