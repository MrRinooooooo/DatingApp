
importScripts('https://www.gstatic.com/firebasejs/10.7.1/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/10.7.1/firebase-messaging-compat.js');

const firebaseConfig = {
  apiKey: "AIzaSyD1VkdD6ZmiHSzFpO0Q6dsmDXE1EYfzxsE",
  authDomain: "datingapp-emiliano-dc9ac.firebaseapp.com",
  projectId: "datingapp-emiliano-dc9ac",
  storageBucket: "datingapp-emiliano-dc9ac.appspot.com",
  messagingSenderId: "495268141226",
  appId: "1:495268141226:web:cacd2a77327898d06cd8d3"
};


firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();

messaging.onBackgroundMessage(function(payload) {
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
        body: payload.notification.body,
        icon: '/firebase-logo.png'
    };

    self.registration.showNotification(notificationTitle, notificationOptions);
});
