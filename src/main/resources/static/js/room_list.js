$(document).ready(function() {
  var mapOptions = {
    center: new google.maps.LatLng(37.564464, -122.422419),
    zoom: 16,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

  // add the ">>" to the last td when mouse enter tr, and set others
  $('tr').mouseenter(function() {
    // clear the ">>" when mouse leave the tr
    $('tr td:nth-child(3)').text('');
    $('tr.active').removeClass('active');

    $(this).addClass('active');
    $(this).find(':nth-child(3)').text(">>");
    $('.panel-title').text($(this).find(':nth-child(4)').text());
    $('#roomDesc').text($(this).find(':nth-child(7)').text());
    placeMarker($(this).find(':nth-child(5)').text(), $(this).find(':nth-child(6)').text(), map);
  });
  $('tbody tr:first').mouseenter();
});

function placeMarker(lat, lng, map) {
  var latlng = new google.maps.LatLng(lat, lng);
  map.setCenter(latlng);
  var marker = new google.maps.Marker({
      position: latlng,
      url: '/',
      animation: google.maps.Animation.DROP
    });

    marker.setMap(map);
}
