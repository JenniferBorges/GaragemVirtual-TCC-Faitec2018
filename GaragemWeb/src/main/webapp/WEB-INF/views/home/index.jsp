<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Início"/>
        </jsp:include>  
        <style>
            video {
                object-fit: fill;
                position: absolute;
                width: 100%;
                height: 100%;
            }
            .video-overlay {
                position: absolute;
                top: 0px; 
                left: 0px; 
                width: 100%; 
                height: 100%;
            }
        </style>
    </head> 
    <body>
        <div class="video-wrapper">
            <div style="opacity: 0.8; transition-property: opacity; transition-duration: 2000ms;">
                <video autoplay muted loop>
                    <source src="<c:url value="/resources/video/intro.mp4"/>" type="video/mp4">
                    <source src="<c:url value="/resources/video/intro.ogg"/>" type="video/ogg">
                    Your browser does not support HTML5 video.
                </video>
            </div>
            <div class="video-overlay"></div>
        </div> 
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="row flex-grow-1 justify-content-center align-self-center">
                <header class="masthead w-100 justify-content-center align-self-center">
                    <div class="container">
                        <div class="row text-center">
                            <div class="col-lg-12 mx-auto">
                                <h1>
                                    <strong>Endereço de destino:</strong>
                                </h1>
                                <hr>
                            </div>
                            <div class="col-lg-8 mx-auto">
                                <form method="GET" action="<c:url value="/search"/>">
                                    <div class="form-row">
                                        <div class="form-group col-md-10">
                                            <div class="input-group input-group-alternative">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-map-marked-alt"></i></span>
                                                </div>     
                                                <input id="address" class="form-control" type="text" name="address" placeholder="Digite o estado, bairro, cep ou rua"  title="Digite o estado, bairro, cep ou rua" value="${param.address}">
                                            </div>
                                        </div>
                                        <div class="form-group col-md-2">  
                                            <button id="search" type="submit" class="btn btn-primary btn-block mb-2" title="Pesquisar"><i class="fas fa-search"></i></button>
                                        </div>
                                    </div>                           
                                </form>
                            </div>
                        </div>
                    </div>
                </header>
            </div>
        </div>
        <jsp:include page="../common/wrapper.jsp"/>
        <script>
            var autocomplete;
            var geocoder;
            var componentForm = {
                street_number: 'number',
                route: 'street',
                administrative_area_level_2: 'city',
                political: 'neighborhood',
                administrative_area_level_1: 'state',
                postal_code: 'zip'
            };

            function initialize() {
                // Create the autocomplete object, restricting the search to geographical
                // location types.
                autocomplete = new google.maps.places.Autocomplete(
                        /** @type {!HTMLInputElement} */
                                (document.getElementById('address')),
                                {types: ['geocode']});

                geocoder = new google.maps.Geocoder();

                // Try HTML5 geolocation.
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        codeLatLng(position.coords.latitude, position.coords.longitude);
                    }, function () {
                        handleLocationError(true);
                    });
                } else {
                    // Browser doesn't support Geolocation
                    handleLocationError(false);
                }

                function codeLatLng(lat, lng) {
                    var latlng = new google.maps.LatLng(lat, lng);
                    geocoder.geocode({
                        'latLng': latlng
                    }, function (results, status) {
                        if (status === google.maps.GeocoderStatus.OK) {
                            if (results[1]) {
                                var address = results[1].address_components;
                                var addressText = "";
                                for (var item in address) {
                                    addressText += address[item].long_name + ", ";
                                }
                                document.getElementById('address').value = addressText;
                            } else {
                                alert('No results found');
                            }
                        } else {
                            alert('Geocoder failed due to: ' + status);
                        }
                    });
                }
            }
        </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=${Constants.GOOGLE_MAPS_KEY}&libraries=places&callback=initialize" async defer></script>
    </body>
</html>
