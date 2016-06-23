var site_url = 'http://buysiteads.com/';

$(document).ready(function() {
	KliptuGenerateAds();		
});


function KliptuGenerateAds()
{
	$response = [];
	
	$( ".buysiteads-ad" ).each(function( index ) {
		$widget = $(this);
		
		$id = $widget.data('id');
		$type = $widget.data('type');
		$size = $widget.data('size');
		
		$widget.html('Loading...');
		
		$widget.css('position', 'relative');
		
		$params = 'id='+$id+'&type='+$type+'&size='+$size;
		
		send($widget, $params, $type);
	});		
}

function send($widget, $params, $type)
{
	$.getJSON(site_url+'api/'+$type+'/?callback=?', $params, function(data) {
		jsonParse($widget, data.html, $type);
	});
}

function jsonParse($widget, html, $type){	
	$widget.html(html);
	
}

