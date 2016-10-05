$(document).ready(function() {

    var path = document.location.pathname.substring(1).split(/\//)[0];
    var myurl = "/"+path+"/profile";
    var offset = 0;
    var limit = 2;

    load(offset,limit);

    var section = $("#sec1");


    section.delegate( "table > tbody > tr > td > a#remove", "click", function(event) {
        event.stopPropagation();
        var row = $(this).parent().parent();
        removeProfile(row);
    });

    section.delegate( "table > tbody > tr", "mouseenter", function() {
        $(this).toggleClass("info");
    });

    section.delegate( "table > tbody > tr", "mouseleave", function() {
        $(this).toggleClass("info");
    });

    section.delegate( "table > tbody > tr:not(#loadMore)", "click", function() {
        var row = $(this);
        editProfile(row)
    });

    section.delegate( "table > tbody > tr#loadMore", "click", function() {
        offset += parseInt(limit);
        loadMore(offset,limit)
    });

    section.delegate( "#accept", "click", function() {
        saveProfile();
    });

    section.delegate( "input[type=text].date", "focusin", function(){
        $(this).datepicker({
                               format: "dd-mm-yyyy",
                               orientation: "bottom auto"
                           });
    });

    function saveProfile() {
        $.post(myurl,$("form#editor").serialize())
            .done(function(result) {
            section.html(result);
        });
    }

    function load(offset, limit) {

        $.ajax({
           url: myurl,
           type: 'POST',
           data: {
               action: "list",
               offset: offset,
               limit: limit
           }
        }).done(function(result) {
            section.html(result);
        });
    }
    function loadMore(offset, limit) {
        $.ajax({
           url: myurl,
           type: 'POST',
           data: {
               action: "append",
               offset: offset,
               limit: limit
           }
        }).done(function(result) {
            $(result).insertBefore(("tr#loadMore"));
        });
    }

    function removeProfile(row) {
        var profileId = row.data('target');
        $.ajax({
            url: myurl,
            type: 'POST',
            data: {
                action: "remove",
                userProfileId: profileId
            }
        }).done(function(result) {
            row.remove();
        });
    }

    function addNew() {
        $.ajax({
            url: myurl,
            type: 'POST',
            data: {
                action: "getform"
            }
        }).done(function(result) {
            section.html(result);
        });
    }

    function editProfile(row) {
        var profileId = row.data('target');
        $.ajax({
            url: myurl,
            type: 'POST',
            data: {
                action: "getform",
                userProfileId: profileId
            }
        }).done(function(result) {
            section.html(result);
        });
    }
});