$(document).ready(function() {

    var path = document.location.pathname.substring(1).split(/\//)[0];
    var profile = "/"+path+"/profile";
    var friends = "/"+path+"/friends";


    $("button#editProfile").on("click",function()
    {
        document.location.href = profile+"?action=edit";
    });

    $("button.befriendly").on("click",function() {
        var button = $(this);
        addToFriends(button);
    });

    $("button.unfriend").on("click",function() {
        var button = $(this);
        removeFriend(button);
    });

    $("button.unblock").on("click",function() {
        var button = $(this);
        unblockUser(button);
    });

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

    function removeFriend(button) {
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
            button.toggleClass("btn-primary","btn-default");
            button.prop("disabled",true);
        });
    }

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