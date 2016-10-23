$(document).ready(function() {
    var main = $("#main-column");
    var path = document.location.pathname.substring(1).split(/\//)[0];
    var mypath = "/"+path+"/profile";
    $.ajaxSetup({
                    url: mypath,
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
            window.location = mypath;
        });
    }
});