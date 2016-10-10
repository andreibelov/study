$(document).ready(function() {

    var path = document.location.pathname.substring(1).split(/\//)[0];
    var myurl = "/"+path+"/profile";
    var offset = 0;
    var limit = 2;
    var section = $("#sec1");

    $.ajaxSetup({
        url: myurl,
        type: 'POST'
    });
    $(document).ajaxError(function(event, request, settings) {
        if (request.getResponseHeader("Location") != null) {
            window.location = request.getResponseHeader("Location");
        }
    });

    load(offset,limit);

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
        loadMore()
    });

    section.delegate( "button#cancel", "click", function() {
        offset = 0;
        limit = 3;
        load()
    });
    section.delegate( "form#editor", "submit", function(e) {
        e.preventDefault();
        saveProfile();
    });

    section.delegate( "input[type=text].date", "focusin", function(){
        $(this).datepicker({
                               format: "dd-mm-yyyy",
                               orientation: "bottom auto"
                           });
    });

    function saveProfile() {
        $.ajax({
            data: $("form#editor").serialize()
        }).done(function(result) {
            section.html(result);
        });
    }

    function load() {
        $.ajax({
           data: {
               action: "list",
               offset: offset,
               limit: limit
           }
        }).done(function(result) {
            section.html(result);
        });
    }
    function loadMore() {
        $.ajax({
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
            data: {
                action: "getform",
                userProfileId: profileId
            }
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
            if (jqXHR.getResponseHeader('Location') != null)
            {
                window.Location= jqXHR.getResponseHeader('Location');
            }
            // other conditions in failure situation.
        }).done(function(result) {
            section.html(result);
        });
    }
});