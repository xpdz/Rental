$(document).ready(function() {
  // init date picker
  $('#startDate').datepicker({
    format: "yyyymmdd",
    clearBtn: true,
    autoclose: true,
    todayHighlight: true
  });
  $('#endDate').datepicker({
    format: "yyyymmdd",
    clearBtn: true,
    autoclose: true,
    todayHighlight: true
  });

  var autocomplete = new google.maps.places.Autocomplete((document.getElementById('roomAddress')), { types: ['geocode'] });
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
    var latlng = autocomplete.getPlace().geometry.location;
    $('#lat').val(latlng.lat());
    $('#lng').val(latlng.lng());
  });

  // setup image upload plugin
  $.fn.MultiFile.options.preview = 'true';
  $('#img-uploader').MultiFile({
    list: '#img-previewer',
    maxfile: 8192
  });
});
