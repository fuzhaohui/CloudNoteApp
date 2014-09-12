package com.ces.cloudnote.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * 用于方便注册、注销广播的类。
 * Created by remex on 14-8-4.
 */
public class BroadcastSender {

    public interface OnBoardcastReceive{
        public void onReceive(Context context, Intent intent);
    }

    private ArrayList<BroadcastReceiver> receivers = new ArrayList<BroadcastReceiver>();

    public BroadcastSender(){

    }

    public void sendBroadcast(Context ctx,String action){
        sendBroadcast(ctx, action, null);
    }

    public void sendBroadcast(Context ctx,String action, Bundle bundle){
        Intent intent = new Intent();
        intent.setAction(action);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        ctx.sendBroadcast(intent);
    }

    public void register(Context ctx, final OnBoardcastReceive myReceiver,@NonNull String... actions){
        if(actions == null)
            return;
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                myReceiver.onReceive(context,intent);
            }
        };
        ctx.registerReceiver(receiver, getIntentFliter(actions));

        if(receivers == null){
            receivers = new ArrayList<BroadcastReceiver>();
        }
        receivers.add(receiver);
    }

    public void unRegister(Context ctx){
        if(receivers != null && receivers.size() > 0){
            for(BroadcastReceiver receiver : receivers) {
                ctx.unregisterReceiver(receiver);
            }
            receivers.clear();
        }
    }

    public IntentFilter getIntentFliter(String... actions){
        IntentFilter filter = new IntentFilter();
        for(String action : actions){
            filter.addAction(action);
        }
        return filter;
    }

}
