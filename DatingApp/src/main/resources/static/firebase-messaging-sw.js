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

// ==================== GESTIONE MESSAGGI BACKGROUND ====================

// Gestione messaggi in background - con opzioni avanzate per forzare notifiche di sistema
messaging.onBackgroundMessage(function(payload) {
  console.log('[Service Worker] Background message ricevuto:', payload);
  
  const notificationTitle = payload.notification.title || 'Nuova notifica';
  const notificationOptions = {
    body: payload.notification.body || 'Hai ricevuto un nuovo messaggio!',
    icon: payload.notification.icon || '/firebase-logo.png',
    badge: '/firebase-logo.png',
    
    // Opzioni per forzare la visibilità cross-application
    tag: 'dating-app-notification', // Raggruppa notifiche simili
    renotify: true, // Ri-notifica anche se esiste già una notifica con lo stesso tag
    requireInteraction: true, // Rimane visibile finché l'utente non interagisce
    silent: false, // Assicura che non sia silenziosa
    
    // Timestamp per ordine cronologico
    timestamp: Date.now(),
    
    // Dati extra che puoi usare
    data: {
      url: payload.data?.url || '/',
      userId: payload.data?.userId,
      messageId: payload.data?.messageId,
      type: payload.data?.type || 'message',
      timestamp: Date.now(),
      source: 'background',
      ...payload.data
    },
    
    // Azioni personalizzate (opzionale)
    actions: [
      {
        action: 'open',
        title: 'Apri App',
        icon: '/open-icon.png'
      },
      {
        action: 'close',
        title: 'Chiudi',
        icon: '/close-icon.png'
      }
    ],
    
    // Vibrazione per dispositivi mobili
    vibrate: [200, 100, 200],
    
    // Immagine aggiuntiva se presente
    image: payload.notification.image
  };

  console.log('[Service Worker] Creazione notifica con opzioni:', notificationOptions);

  // Forza la notifica di sistema - QUESTO È CRUCIALE per YouTube/altri siti
  return self.registration.showNotification(notificationTitle, notificationOptions);
});

// ==================== GESTIONE CLICK NOTIFICHE ====================

// Gestione click sulla notifica - MOLTO IMPORTANTE per il comportamento cross-application
self.addEventListener('notificationclick', function(event) {
  console.log('[Service Worker] Notifica cliccata:', event.notification);
  console.log('[Service Worker] Azione cliccata:', event.action);
  
  // Chiudi la notifica
  event.notification.close();
  
  // Gestisci azioni specifiche
  if (event.action === 'close') {
    console.log('[Service Worker] Azione close - non faccio nulla');
    return; // Non fare nulla, solo chiudi
  }
  
  // Per 'open' o click generico sulla notifica
  const urlToOpen = event.notification.data?.url || '/';
  const dataReceived = event.notification.data || {};
  
  console.log('[Service Worker] Aprendo URL:', urlToOpen);
  console.log('[Service Worker] Dati notifica:', dataReceived);
  
  event.waitUntil(
    clients.matchAll({
      type: 'window',
      includeUncontrolled: true // IMPORTANTE: include finestre non controllate
    }).then(function(clientList) {
      console.log('[Service Worker] Client trovati:', clientList.length);
      
      // Cerca se c'è già una finestra aperta della tua app
      for (let i = 0; i < clientList.length; i++) {
        const client = clientList[i];
        
        console.log('[Service Worker] Controllo client:', client.url);
        
        // Controlla se è il tuo dominio (adatta questi URL al tuo caso)
        if (client.url.includes('datingapp-emiliano-dc9ac') || 
            client.url.includes('localhost') || 
            client.url.includes('127.0.0.1') ||
            client.url.includes('your-domain.com')) { // Aggiungi il tuo dominio di produzione
          
          console.log('[Service Worker] Finestra trovata, porto in primo piano');
          
          // Porta la finestra in primo piano e naviga se necessario
          if (urlToOpen !== '/' && urlToOpen !== client.url) {
            console.log('[Service Worker] Navigazione a:', urlToOpen);
            client.navigate(urlToOpen);
          }
          
          // Invia dati alla finestra se necessario
          if (Object.keys(dataReceived).length > 0) {
            client.postMessage({
              type: 'notification-clicked',
              data: dataReceived
            });
          }
          
          return client.focus();
        }
      }
      
      // Se non c'è una finestra aperta, aprila
      console.log('[Service Worker] Nessuna finestra trovata, ne apro una nuova');
      if (clients.openWindow) {
        return clients.openWindow(urlToOpen);
      }
    }).catch(error => {
      console.error('[Service Worker] Errore gestione click:', error);
    })
  );
});

// ==================== GESTIONE CHIUSURA NOTIFICHE ====================

// Gestione chiusura notifica (opzionale)
self.addEventListener('notificationclose', function(event) {
  console.log('[Service Worker] Notifica chiusa:', event.notification.tag);
  
  // Puoi aggiungere analytics qui se necessario
  // trackNotificationClosed(event.notification.data);
});

// ==================== EVENTI SERVICE WORKER ====================

// Debug: log quando il service worker si attiva
self.addEventListener('activate', function(event) {
  console.log('[Service Worker] Attivato per notifiche Firebase');
  
  // Pulisci eventuali cache vecchie se necessario
  event.waitUntil(
    // Operazioni di cleanup se necessarie
    Promise.resolve()
  );
});

// Debug: log quando il service worker si installa
self.addEventListener('install', function(event) {
  console.log('[Service Worker] Installato per notifiche Firebase');
  
  // Forza l'attivazione immediata
  event.waitUntil(self.skipWaiting());
});

// Debug: log messaggi ricevuti dalla pagina
self.addEventListener('message', function(event) {
  console.log('[Service Worker] Messaggio ricevuto dalla pagina:', event.data);
  
  // Puoi gestire messaggi specifici dalla tua app qui
  if (event.data && event.data.type) {
    switch (event.data.type) {
      case 'get-registration-info':
        // Invia info sulla registrazione
        event.ports[0].postMessage({
          scope: self.registration.scope,
          active: !!self.registration.active
        });
        break;
        
      case 'clear-notifications':
        // Pulisci tutte le notifiche
        self.registration.getNotifications().then(notifications => {
          notifications.forEach(notification => notification.close());
        });
        break;
        
      default:
        console.log('[Service Worker] Tipo messaggio sconosciuto:', event.data.type);
    }
  }
});

// ==================== GESTIONE ERRORI ====================

// Gestione errori globali nel service worker
self.addEventListener('error', function(event) {
  console.error('[Service Worker] Errore:', event.error);
});

// Gestione errori di promise non gestite
self.addEventListener('unhandledrejection', function(event) {
  console.error('[Service Worker] Promise rejettata:', event.reason);
});

// ==================== FUNZIONI UTILITY ====================

// Funzione per verificare se una URL è del tuo dominio
function isOwnDomain(url) {
  const ownDomains = [
    'datingapp-emiliano-dc9ac',
    'localhost',
    '127.0.0.1',
    'your-domain.com' // Aggiungi il tuo dominio di produzione
  ];
  
  return ownDomains.some(domain => url.includes(domain));
}

// Funzione per logging strutturato (opzionale)
function logNotificationEvent(type, data) {
  const logData = {
    timestamp: new Date().toISOString(),
    type: type,
    data: data,
    userAgent: navigator.userAgent
  };
  
  console.log(`[Service Worker] ${type}:`, logData);
  
  // Qui potresti inviare analytics al tuo backend se necessario
  // sendAnalytics(logData);
}

// Log inizializzazione
console.log('[Service Worker] Firebase Messaging Service Worker caricato e pronto');
console.log('[Service Worker] Scope:', self.registration.scope);
console.log('[Service Worker] Timestamp:', new Date().toISOString());