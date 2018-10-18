"use strict";

// (function () {
//     var stompClient = null;
//
//     connect();
//
//     function connect() {
//         var socket = new SockJS('/gs-guide-websocket');
//         stompClient = Stomp.over(socket);
//         stompClient.connect({}, function (frame) {
//             console.log('Connected: ' + frame);
//             stompClient.subscribe('/topic/greetings', function (greeting) {
//                 recreatePosts();
//             });
//         });
//     }
//
//     function refreshFeed(name) {
//         console.log("refreshFeed received " + name);
//         stompClient.send("/app/hello", {}, JSON.stringify({'name': name}));
//     }

// })();