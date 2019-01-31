<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Mapa"/>
        </jsp:include>  
    </head> 
    <body>     
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div id="map" class="flex-grow-1"></div> 
        </div>
        <jsp:include page="../common/wrapper.jsp"/>
        <script type="text/javascript">
            function initialize() {
                var pos = {
                    lat: <c:out value="${location.lat}" default="-22.260292"/>,
                    lng: <c:out value="${location.lng}" default="-45.7026855"/>
                };

                var map = new google.maps.Map(document.getElementById('map'), {
                    center: {lat: pos.lat, lng: pos.lng},
                    zoom: 15,
                    disableDefaultUI: true,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });

                // Create the search box and link it to the UI element.
                var search = new Search();
                var dateTime = new DateTime();
                var inputAddress = search.querySelector('input');
                var inputDateTime = dateTime.querySelector('input');

                map.controls[google.maps.ControlPosition.TOP_LEFT].push(search);
                map.controls[google.maps.ControlPosition.TOP_LEFT].push(dateTime);

                var searchBox = new google.maps.places.SearchBox(inputAddress);
                var allMarkers = [];
                addMarkers(${empty garageArray ? "[]" : garageArray});
                var currentPosMarker, circle;

                if (!navigator.geolocation) {
                    // Browser doesn't support Geolocation
                    handleLocationError(false);
                } else {
                    navigator.geolocation.watchPosition((position) => {
                        console.log(position);

                        pos = {
                            lat: position.coords.latitude,
                            lng: position.coords.longitude
                        };

                        if (!currentPosMarker) {
                            currentPosMarker = new google.maps.Marker({
                                clickable: !1,
                                cursor: "pointer",
                                draggable: !1,
                                flat: !0,
                                icon: {
                                    path: google.maps.SymbolPath.CIRCLE,
                                    fillColor: "#4285F4",
                                    fillOpacity: 1,
                                    scale: 6,
                                    strokeColor: "white",
                                    strokeWeight: 2
                                },
                                optimized: !1,
                                position: pos,
                                title: "Current location",
                                zIndex: 2,
                                map: map
                            });

                            circle = new google.maps.Circle({
                                clickable: !1,
                                radius: position.coords.accuracy,
                                fillColor: "#C8D6EC",
                                fillOpacity: .3,
                                strokeWeight: 1,
                                strokeColor: "#1F88E4",
                                strokeOpacity: .1,
                                zIndex: 1,
                                center: pos,
                                map: map
                            });
                        } else {
                            currentPosMarker.setPosition(pos);
                            circle.setCenter(pos);
                            circle.setRadius(position.coords.accuracy);
                        }
                    });
                }

                // Bias the SearchBox results towards current map's viewport.
                map.addListener('bounds_changed', function () {
                    searchBox.setBounds(map.getBounds());
                });

                // Listen for the event fired when the user selects a prediction and retrieve
                // more details for that place.
                searchBox.addListener('places_changed', function () {
                    var places = searchBox.getPlaces();

                    if (places.length === 0) {
                        return;
                    }

                    changeURL();

                    // For each place, get the icon, name and location.
                    var bounds = new google.maps.LatLngBounds();
                    places.forEach(function (place) {
                        if (!place.geometry) {
                            console.log("Returned place contains no geometry");
                            return;
                        }

                        if (place.geometry.viewport) {
                            // Only geocodes have viewport.
                            bounds.union(place.geometry.viewport);
                        } else {
                            bounds.extend(place.geometry.location);
                        }
                    });
                    map.fitBounds(bounds);
                });

                function changeURL() {
                    var newurl = window.location.protocol + "//" + window.location.host + window.location.pathname;
                    if (inputAddress.value.length > 0)
                        newurl = updateQueryStringParameter(newurl, "address", inputAddress.value);
                    if (inputDateTime.value.length > 0)
                        newurl = updateQueryStringParameter(newurl, "dateTime", inputDateTime.value);
                    window.history.pushState(history.state, null, newurl);
                }

                function updateQueryStringParameter(uri, key, value) {
                    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
                    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
                    if (uri.match(re)) {
                        return uri.replace(re, '$1' + key + "=" + value + '$2');
                    } else {
                        return uri + separator + key + "=" + value;
                    }
                }

                function Search() {
                    var addressGroup = document.createElement("div");
                    addressGroup.id = "addressGroup";
                    addressGroup.classList.add("input-group");
                    addressGroup.classList.add("input-group-alternative");

                    var div = document.createElement("div");
                    div.classList.add("input-group-prepend");

                    var span = document.createElement("span");
                    span.classList.add("input-group-text");

                    div.appendChild(span);

                    var i = document.createElement("i");
                    i.classList.add("fas");
                    i.classList.add("fa-map-marked-alt");

                    span.appendChild(i);

                    addressGroup.appendChild(div);

                    var input = document.createElement("input");
                    input.type = "text";
                    input.classList.add("form-control");
                    input.id = "inputAddress";
                    input.name = "address";
                    input.placeholder = "Digite o estado, bairro, cep ou rua";
                    input.title = "Digite o estado, bairro, cep ou rua";
                    input.value = "<c:out value="${param.address}"/>";
                    input.required = true;

                    addressGroup.appendChild(input);

                    return addressGroup;
                }

                function DateTime() {
                    var dateGroup = document.createElement("div");
                    dateGroup.id = "dateGroup";
                    dateGroup.classList.add("input-group");
                    dateGroup.classList.add("input-group-alternative");

                    var div = document.createElement("div");
                    div.classList.add("input-group-prepend");

                    var span = document.createElement("span");
                    span.classList.add("input-group-text");

                    div.appendChild(span);

                    var i = document.createElement("i");
                    i.classList.add("fas");
                    i.classList.add("fa-calendar-alt");

                    span.appendChild(i);

                    dateGroup.appendChild(div);

                    var input = document.createElement("input");
                    input.type = "datetime-local";
                    input.classList.add("form-control");
                    input.classList.add("datetimepicker-input");
                    input.id = "inputDateTime";
                    input.name = "dateTime";
                    input.dataset.toggle = "datetimepicker";
                    input.dataset.target = "#inputDateTime";
                    input.style.borderTopRightRadius = "0.375rem";
                    input.style.borderBottomRightRadius = "0.375rem";
                    input.title = "Escolha uma data";
                    input.required = true;

                    dateGroup.appendChild(input);

                    var date = "<c:out value="${param.dateTime}"/>" || moment().toDate();
                    $(input).datetimepicker({
                        locale: moment.locale('pt-br'),
                        format: "YYYY-MM-DDTHH:mm",
                        minDate: moment().format("YYYY-MM-DDTHH:mm"),
                        date: date,
                        icons: {
                            time: "fas fa-clock"
                        }
                    });

                    var count = 0;
                    $(input).on("change.datetimepicker", function (event) {
                        if (count === 1) {
                            $.ajax('<c:url value="/search/date"/>', {
                                method: "GET",
                                data: {
                                    dateTime: this.value
                                },
                                success: function (response) {
                                    for (var i = allMarkers.length; i--; ) {
                                        allMarkers[i].setMap(null);
                                        allMarkers.splice(i, 1);
                                    }
                                    if (Object.keys(response).length !== 0)
                                        addMarkers(response.data);
                                },
                                error: function (response) {
                                    console.log(response);
                                }
                            });
                            count = 0;
                            changeURL();
                        } else {
                            count++;
                        }
                    });

                    return dateGroup;
                }

                function addMarkers(markers) {
                    markers.forEach(function (marker) {
                        var id = marker.id;
                        marker = new google.maps.Marker({
                            map: map,
                            title: marker.title,
                            animation: google.maps.Animation.DROP,
                            position: marker.location
                        });
                        marker.addListener("click", function () {
                            redirect(this);
                        });
                        function redirect(event) {
                            window.location = "<c:url value="/rent-garage/"/>" + id;
                        }
                        allMarkers.push(marker);
                    });
                }

                function handleLocationError(browserHasGeolocation) {
                    console.log(browserHasGeolocation ?
                            'Error: The Geolocation service failed.' :
                            'Error: Your browser doesn\'t support geolocation.');
                }
            }
        </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=${Constants.GOOGLE_MAPS_KEY}&libraries=places&callback=initialize" async defer></script>
    </body>
</html>
