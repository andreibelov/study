$(document).ready(function() {

    var pathname = document.location.pathname.substring(1);
    var parts = pathname.split(/\//);
    var myurl = "/"+parts[0]+"/profile";

    load();

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

    section.delegate( "table > tbody > tr#addNew", "click", function() {
        addNew();
    });

    section.delegate( "#accept", "click", function() {

        $.post(myurl, $("#editor").serialize())
            .done(function(result) {
                section.empty();
                section.append(result);
            });

    });


    function load() {
        $.post( myurl, function( data ) {
            $("#sec1").html( data );
        });
    }

    function removeProfile(row) {
        var profileId = row.data('target');
        $.ajax({
            url: myurl,
            type: 'POST',
            data: {
                action: "remove",
                idUserProfile: profileId
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
            $("#profileForm").remove();
            section.append(result);
        });
    }
});