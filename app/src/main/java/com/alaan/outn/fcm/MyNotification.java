package com.alaan.outn.fcm;


import androidx.annotation.NonNull;

import com.alaan.outn.utils.Preference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyNotification extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Preference.INSTANCE.setIdPushNotfcation(s);
    }
}

