//package com.example.android_project
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import android.util.Log
//import androidx.core.app.NotificationCompat
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    // Этот метод вызывается, когда приходит push-уведомление
////    override fun onMessageReceived(remoteMessage: RemoteMessage) {
////        // Проверяем, есть ли уведомление и отправляем его
////        remoteMessage.notification?.let {
////            sendNotification(it.title ?: "Уведомление", it.body ?: "")
////        }
////    }
////
////    // Этот метод будет показывать уведомление
////    fun sendNotification(title: String, message: String) {
////        val channelId = "default_channel_id"
////        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
////
////        // Создаем канал уведомлений для Android 8.0+
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            val channel = NotificationChannel(
////                channelId, "Default Channel",
////                NotificationManager.IMPORTANCE_DEFAULT
////            )
////            notificationManager.createNotificationChannel(channel)
////        }
////
////        // Строим уведомление
////        val notificationBuilder = NotificationCompat.Builder(this, channelId)
////            .setContentTitle(title)
////            .setContentText(message)
//////            .setSmallIcon(R.drawable.ic_notification) // Используйте свою иконку уведомления
////            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
////
////        notificationManager.notify(0, notificationBuilder.build())
////    }
////
////    // Метод для получения нового токена устройства
////    override fun onNewToken(token: String) {
////        super.onNewToken(token)
////        // Логируем токен для использования на сервере
////        Log.d("FCM", "New token: $token")
////    }
//}
