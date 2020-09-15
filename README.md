# Android-Sending-Push-Notifications-From-Server
## Follow the Steps:

 ### [1. Firebase Setup](https://firebase.google.com/docs/android/setup)
 ### [2. Create an API's](https://github.com/Dinesh2510/Android-Sending-Push-Notifications-From-Server/tree/master/Notification%20API's)
 ### [3. Demo (Scroll Down)]()

# Everything from generating tokens to receiving and displaying notifications
 <h4> Notifications are important! I can’t recall using an app that didn’t have notifications excluding, of course, those apps that don’t serve a purpose like these.
Notifications play a vital role in your app success. They give you a platform to reach out to your users, interact with them, give them updates, update the app data and much more.
While notification is an important part of the whole app development process, it somehow is typical to integrate as compared to the normal android apis. The reason? Well, while normal apis need configuration inside the app only, notifications need more than that — outside configuration like handling user tokens, and sending notifications using those tokens.</h4>

<pre>String token = FirebaseInstanceId.getInstance().getToken();</pre>
<h4>Now we need to generate token using FirebaseInstanceIdService class and send it to the server to be stored. Create a new class InstanceIdService and extend it with FirebaseInstanceIdService. We now need to add a constructor and onTokenRefresh methods inside this class. We will take help of predefined tools in Android Studio. Click inside the class block and go to Code in the toolbar and select override methods. Select the desired methods and click ok. After the methods are added, we need to get the generated token everytime onTokenRefresh method is called by the framework</h4>



<hr>
  <img width="500" height="250" src="ss_1.jpg">
  <img width="500" height="300" src="ss_2.jpg">
  <img width="500" height="200" src="ss_3.jpg">
  <img width="500" height="200" src="ss_4.jpg">
