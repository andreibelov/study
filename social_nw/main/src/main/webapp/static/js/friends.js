$(document).ready(function() {

    var path = document.location.pathname.substring(1).split(/\//)[0];
    var profile = "/"+path+"/profile";
    var friends = "/"+path+"/friends";


    $("button.nonfriendly").on("click",function() {
        var button = $(this);
        addToFriends(button);
    });

    function addToFriends(button) {
        var profileId = button.data('target');
        $.ajax({
           url: friends,
           type: 'POST',
           data: {
               action: "send",
               id: profileId
           }
        }).done(function(result) {
            button.toggleClass("disabled");
            button.toggleClass("btn-primary","btn-default");
            button.prop("disabled",true);
        });
    }

});