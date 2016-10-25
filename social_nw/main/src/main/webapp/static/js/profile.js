$(document).ready(function() {

    var main = $("#main-column");
    var path = document.location.pathname.substring(1).split(/\//)[0];
    var profile = "/"+path+"/profile";
    var friends = "/"+path+"/friends";

    var friendlyButton = $("button.befriendly");

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

    $("button.unblock").on("click",function() {
        var button = $(this);
        unblockUser(button);
    });

    function unblockUser(button) {
        var profileId = button.data('target');
        $.ajax({
                   url: friends,
                   type: 'POST',
                   data: {
                       action: "unblock",
                       id: profileId
                   }
               }).done(function(result) {
            button.toggleClass("disabled");
            button.prop("disabled",true);
        });
    }

    $("button.blockUser").on("click",function() {
        var button = $(this);
        blockUser(button);
    });

    function blockUser(button) {
        var profileId = button.data('target');
        $.ajax({
                   url: friends,
                   type: 'POST',
                   data: {
                       action: "block",
                       id: profileId
                   }
               }).done(function(result) {
            button.toggleClass("disabled");
            button.prop("disabled",true);
        });
    }

    $("button.unfriend").on("click",function() {
        var button = $(this);
        removeFriend(button);
    });

    function removeFriend(button) {
        var profileId = button.data('target');
        $.ajax({
                   url: friends,
                   type: 'POST',
                   data: {
                       action: "remove",
                       id: profileId
                   }
               }).done(function(result) {
            button.toggleClass("disabled");
            button.toggleClass("btn-primary","btn-default");
            button.prop("disabled",true);
        });
    }

    $("button#editProfile").on("click",function()
                            {
                                document.location.href = profile+"?action=edit";
                            });


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