$(document).ready(function() {
    load();
    $("#send").on("click", send);
    $("#clc").on("click",clc);
    //$("#attch").on("click",concatenate);
    var msg = $("#msg");
    var flag = true;
    msg.on("focus",function () {
        if (flag == true){
            msg.val("");
            flag = false;
        }
    });
    msg.on("focusout", function () {
        if(msg.val()== ""){
            msg.val("Type your message here...");
            flag = true;
        }
    } );
    msg.keydown(function(e){
        if(e.ctrlKey && e.keyCode == 13){
            send();
        }
    });
    function load() {
        $.ajax({
            type: 'GET',
            url: '/xml/xml.php',
            cache: false,
            dataType: "xml",
            success : function (result) {
                parser(result);
            },
            error : function () {
                alert('Load - NOT OK!');
            }
        });
    }

    function send() {
        if (flag == true) return;
        var text = '<![CDATA['+msg.val().trim()+']]>';
        var chuid = "chatuser1";
        if(text == ""){
            msg.val("");
            return;
        }
        var xmlDoc = $($.parseXML('<?xml version="1.0" encoding="utf-8" ?><root />'));
        var obj = $('root',xmlDoc).append($('<message />', xmlDoc));
        $('message',obj).append($('<chuid />', xmlDoc).text(chuid));
        $('message',obj).append($('<text />', xmlDoc).html(text));
        var str = (new XMLSerializer()).serializeToString(xmlDoc.context);
        $.ajax({
            type: 'POST',
            url: '/xml/xml.php',
            cache: false,
            dataType: "xml",
            data: str,
            processData: false,
            contentType: "application/xml; charset=UTF-8",
            success : function (result) {
                parser(result);
            },
            error : function () {
                alert('Load - NOT OK!');
            }
        });
        msg.val("");
        msg.focus();
    }

    function clc() {
        $('dl').empty();
    }

    function parser(result) {
        var list = $('dl');
        list.empty();
        $(result).find('message').each(function (index, element) {
            var message = $(element);
            var chuid = message.find('chuid').text();
            var text = message.find('text').text().replace(/\r\n|\r|\n/g, "<br />")
                .replace("<![CDATA[", "").replace("]]>","");
            var id = message.find('id').text();
            list.append('<dt id="' + id + '">' + chuid + ': </dt>')
                .append('<dd id="' + id + '">' + text + '</dd>');
        });
        var d = $('#chat');
        d.animate({scrollTop: d.prop("scrollHeight")}, 250);
    }

});