/**
 * Created by Fer on 1/9/17.
 */
// $(document).ready(function(){

    console.log('fsAPIKey', fsAPIKey);

    $(".dropdown-button").dropdown();
    $('.materialboxed').materialbox();
    $(".sideNav-btn").sideNav();
    $('select').material_select();
    $('.parallax').parallax();

    // Event listeners
    $("#create-btn").click(function(e){
        e.preventDefault();
        // refreshFeed("New post created");
        $(this).parent().parent("form").submit();
    });

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
    });

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
        var posts = null;

        $.ajax({
            url: "/posts.json",
            method: "GET",
            async: false
        }).done(function(data){
            posts = data;
        });

        return posts;
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
        $pContainer = $("#postsContainer");
        $pContainer.empty();
        $pContainer.load("/posts/feed")
    }

// });
