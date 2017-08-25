package com.thingple.message;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MessageService {

    public static final String MESSAGE_SEND_ACTION = "com.thingple.messageprovider.send";
    public static final String MESSAGE_SEND_PERMISSION = "com.thingple.message.permission.send";

    public static final String MESSAGE_COMMAND_ACTION = "com.thingple.messageprovider.command";
    public static final String MESSAGE_COMMAND_PERMISSION = "com.thingple.message.permission.command";

    private static MessageService ins;

    private Context context;

    public static final MessageService shareInstance() {
        if (ins == null) {
            throw new NullPointerException();
        }
        return ins;
    }

    public static void init(Context context) {
        ins = new MessageService(context);
    }

    private MessageService(Context context) {
        this.context = context;
    }

    public void sendMessage(MQTTMessage message) {
        Intent intent = new Intent(MESSAGE_SEND_ACTION);
        Bundle bundle = new Bundle();
        bundle.putString("topic", message.topic);
        bundle.putString("content", message.content);
        intent.putExtras(bundle);
        context.sendBroadcast(intent, MESSAGE_SEND_PERMISSION);
    }

    public void changeSetting(String broker, String client, String password) {
        Intent intent = new Intent(MESSAGE_COMMAND_ACTION);
        intent.putExtra("id", "mqtt_setting");
        Bundle bundle = new Bundle();
        bundle.putString("broker", broker);
        bundle.putString("client_id", client);
        bundle.putString("password", password);
        intent.putExtras(bundle);
        context.sendBroadcast(intent, MESSAGE_COMMAND_PERMISSION);
    }

    public void openSettings(Activity context) {
        openView(context, "com.thingple.messageprovider", "com.thingple.messageprovider.view.MainActivity", null);
    }

    private void openView(Activity context, String packageName, String className, Bundle bundle) {

        Intent intent = new Intent();
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
