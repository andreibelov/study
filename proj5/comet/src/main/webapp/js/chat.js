$(document).ready(function() {
    $("#send").on("click", send);
    var msg = $("#msg");
    msg.keydown(function(e){

        if(e.shiftKey && e.keyCode == 13){
            return;
        } else if(e.keyCode == 13){
            send();
            return false;
        }
    });
    function send() {
        var str = JSON.stringify({"text":msg.val().trim()});
        $.post( "chat",str).done( function(response) {
                var list = document.getElementById('list');
                var from = document.createElement("dt");
                from.innerHTML = response["from"]["nickname"];
                var text = document.createElement("dd");
                text.innerHTML = response["text"];
                list.appendChild(from);
                list.appendChild(text);
        });
        msg.val("");
        msg.focus();
        var d = $('#chat');
        d.animate({scrollTop: d.prop("scrollHeight")}, 250);
    }
});
