"use strict";

// (function () {
    var stompClient = null;

    function setConnected(connected) {
        // $("#connect").prop("disabled", connected);
        // $("#disconnect").prop("disabled", !connected);
        // if (connected) {
        //     $("#conversation").show();
        // }
        // else {
        //     $("#conversation").hide();
        // }
        // $("#greetings").html("");
    }

    connect();

    function connect() {
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName(name) {
        console.log("sendName received " + name);
        stompClient.send("/app/hello", {}, JSON.stringify({'name': name}));
    }

    function showGreeting(message) {
        console.log("message received " + message);
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $( "#connect" ).click(function() { connect(); });
        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendName(); });
    });

    $("#create-btn").click(function(e){
        sendName("fernando");
        //comment
    });

    //fer

// })();