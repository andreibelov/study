$(document).ready(function() {

    var pathname = document.location.pathname.substring(1);
    var parts = pathname.split(/\//);
    var myurl = "/"+parts[0]+"/profile";
    var offset = 0;
    var limit = 3;

    load(offset,limit);

    var section = $("#sec1");


    section.delegate( "table > tbody > tr > td > a#remove", "click", function() {
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
        offset = 0;
        limit = 6;
        load(offset,limit)
    });

    section.delegate( "#accept", "click", function() {

        $.post(myurl, $("#editor").serialize())
            .done(function(result) {
                section.empty();
                section.append(result);
            });

    });


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