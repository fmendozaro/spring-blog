"use strict";

(function(){

    let txt = '<app developer="Fernando Mendoza" />';
    let el = document.querySelector('#credit');
    let i = 0;
    let speed = 100;

    setInterval(function(){
        el.innerHTML = "";
        i = 0;
        type();
    }, txt.length * 180);

    function type(){

        if(i < txt.length){
            el.innerHTML += txt.charAt(i);
            i++;
            setTimeout(type, speed);
        }
    }

    type();
})();