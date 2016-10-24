$(document).ready(function() {

    var main = $("#main-column");
    var path = document.location.pathname.substring(1).split(/\//)[0];
    var profile = "/"+path+"/profile";
    var friends = "/"+path+"/friends";

    var friendlyButton = $("button.nonfriendly");

    $.ajaxSetup({
        url: profile,
        type: 'POST'
    });

    main.delegate( "form#editor", "submit", function(e) {
        e.preventDefault();
        saveProfile();
    });

    function saveProfile() {
        $.ajax({
            data: $("form#editor").serialize()
        }).done(function() {
            window.location = profile;
        });
    }

    friendlyButton.on("click",function() {
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