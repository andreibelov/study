$(document).ready(function() {
    var main = $("#main-column");

    main.delegate( "form#editor", "submit", function(e) {
        e.preventDefault();
    });

});