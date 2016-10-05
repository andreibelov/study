$(document).ready(function() {

    var path = document.location.pathname.substring(1).split(/\//)[0];
    var myurl = "/"+path+"/lastlog";
    var section = $("#sec1");

    section.delegate( "i#fa-refresh", "click", function() {
        load();
    });
    section.delegate( "a#clear", "click", function() {
        $.ajax({
                   url: myurl,
                   type: 'POST',
                   data: {
                       action: "clear"
                   }
               }).done(function(result) {
            section.html(result);
        });
    });

    load();

    function load() {
        $.post(myurl).done(function(result) {
            section.html(result);
        });
    }

});