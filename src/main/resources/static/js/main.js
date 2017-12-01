/**
 * Created by Fer on 1/9/17.
 */
// $(document).ready(function(){

    $(".dropdown-button").dropdown();
    $('.materialboxed').materialbox();
    $(".sideNav-btn").sideNav();
    $(".button-collapse").sideNav();
    $('.parallax').parallax();
    $('select').material_select();

    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,basicWeek,basicDay',
        },
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true,
        events: {
            url: '/getPostsDates.json',
            type: 'GET',
            error: function(){
                alert("There was an error getting the event dates");
            },
            color: '#37474f',
            textColor: 'white'
        }
    })

    //toasts
    // var toastError = $("<span class='error'>I am toast content</span>");
    //
    // $(".submit").click(function(){
    //     Materialize.toast($toastContent, 5000);
    // });


    //Tags

    var tags = getTagsJSON();

    $('.chips').material_chip();
    // $('.chips-initial').material_chip({
    //     data: [{
    //         tag: 'Apple',
    //     }, {
    //         tag: 'Microsoft',
    //     }, {
    //         tag: 'Google',
    //     }],
    // });
    // $('.chips-placeholder').material_chip({
    //     placeholder: 'Enter a tag',
    //     secondaryPlaceholder: '+Tag',
    // });


    function getPostsJSON(){
        $.ajax({
            url: "/posts.json",
            method: "GET"
        }).done(function(posts){
            return posts;
        });
    }

    function getTagsJSON(){
        $.ajax({
            url: "/tags.json",
            method: "GET"
        }).done(function(data){

            var chipsData = {};

            data.forEach(function(el){
                chipsData[el.name] = null;
            });

            // $('.chips-autocomplete').material_chip({
            //     autocompleteOptions: {
            //         data: chipsData,
            //         limit: Infinity,
            //         minLength: 1
            //     }
            // });

            return data;
        });
    }


    function recreatePosts(){
        var posts = getPostsJSON();

        html = "";

        for (i = 0; i < posts.length; i++) {
            html += "<div><h1>" + posts[i].title + "</h1><p>" + posts[i].body + "</p></div>";
        }

        console.log(html);
    }

// });
