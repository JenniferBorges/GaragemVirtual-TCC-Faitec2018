<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Garagem"/>
        </jsp:include>  
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="main-content">
                <div class="container-fluid my-5">
                    <div class="row">
                        <div class="col-xl-12 order-xl-1">
                            <div class="row mb-3">
                                <div class="col-6">
                                    <a href="<c:url value="/garage"/>" class="text-light"><i class="fas fa-chevron-left"></i> Minhas Garagens</a>
                                </div>
                            </div>
                            <div class="card bg-secondary shadow">
                                <div class="card-header bg-white border-0">
                                    <div class="row align-items-center">
                                        <div class="col-sm-8">
                                            <h4 class="title">Nova Garagem</h4>
                                            <p class="category">Preencha os campos abaixo para cadastrar uma nova garagem</p>
                                        </div>     
                                    </div>
                                </div>
                                <div class="card-body">
                                    <form method="POST">
                                        <h6 class="heading-small text-muted mb-4">Fotos</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="form-group">
                                                        <div id="photoList" class="carousel slide mb-3" data-ride="carousel" data-interval="false" data-wrap="false">
                                                            <!-- Indicators -->
                                                            <ul class="carousel-indicators">
                                                                <c:forEach items="${garage.imageList}" var="image" varStatus="loop">
                                                                    <c:if test="${image.type eq EImage.GARAGE_PHOTO.type}">   
                                                                        <li data-target="#photoList" data-slide-to="${loop.index}" ${loop.first ? 'class="active"' : ''}>
                                                                        </li>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </ul>
                                                            <!-- The slideshow -->
                                                            <div class="carousel-inner">
                                                                <c:forEach items="${garage.imageList}" var="image" varStatus="loop">
                                                                    <c:if test="${image.type eq EImage.GARAGE_PHOTO.type}">
                                                                        <div class="carousel-item ${loop.first ? 'active' : ''}">
                                                                            <img class="img-fluid" src="<c:url value="/photo/${image.src}"/>">
                                                                            <input type="hidden" class="d-none" name="imageList[${loop.index}].src" value="${image.src}">
                                                                            <input type="hidden" class="d-none" name="imageList[${loop.index}].type" value="${image.type}">
                                                                            <div class="carousel-caption">
                                                                                <button class="btn btn-secondary"><i class="fas fa-times"></i></button>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </div>
                                                            <!-- Left and right controls -->
                                                            <a href="#photoList" class="carousel-control-prev" data-slide="prev">
                                                                <span class="carousel-control-prev-icon"></span>
                                                            </a>
                                                            <a href="#photoList" class="carousel-control-next" data-slide="next">
                                                                <span class="carousel-control-next-icon"></span>
                                                            </a>
                                                        </div>
                                                        <input type="file" class="d-none" id="inputPhoto" name="photo" accept="image/jpg,image/jpeg,image/png">
                                                        <label class="btn btn-primary" for="inputPhoto"><i class="fas fa-plus"></i></label>
                                                        <script>
                                                            var carousel = document.getElementById("photoList");
                                                            var indicator = document.querySelector(".carousel-indicators");
                                                            var inner = document.querySelector(".carousel-inner");

                                                            carousel.querySelectorAll("button").forEach(function (item, index) {
                                                                item.addEventListener("click", function (event) {
                                                                    event.preventDefault();
                                                                    var img = this.parentElement.parentElement.querySelector("img");
                                                                    var src = img.src;
                                                                    var src = src.substring(img.src.indexOf("/photo/") + 7);
                                                                    var formData = new FormData();
                                                                    formData.append("src", src);
                                                                    $.ajax('<c:url value="/photo/delete"/>', {
                                                                        method: "POST",
                                                                        data: formData,
                                                                        processData: false,
                                                                        contentType: false,
                                                                        success: function (response) {
                                                                            console.log(response);
                                                                        },
                                                                        error: function (response) {
                                                                            console.log(response);
                                                                        }
                                                                    });
                                                                    var li = indicator.children.item([...inner.children].indexOf(this.parentElement.parentElement));
                                                                    var pos = [...indicator.children].indexOf(li);
                                                                    inner.children[pos].remove();
                                                                    li.remove();

                                                                    var childElementCount = indicator.childElementCount;
                                                                    if (childElementCount > 0) {
                                                                        if (pos > childElementCount - 1) {
                                                                            pos = childElementCount - 1;
                                                                        }
                                                                        indicator.children[pos].classList.add("active");
                                                                        inner.children[pos].classList.add("active");

                                                                        for (var i = 0; i < childElementCount; i++) {
                                                                            var item = inner.children[i];
                                                                            var src = item.querySelector("input[name$='.src']");
                                                                            src.name = "imageList[" + i + "].src";
                                                                            var type = item.querySelector("input[name$='.type']");
                                                                            type.name = "imageList[" + i + "].type";
                                                                        }
                                                                    }
                                                                });
                                                            });

                                                            document.getElementById("inputPhoto").addEventListener("change", function (event) {
                                                                event.preventDefault();
                                                                var _indicator = carousel.querySelectorAll(".carousel-indicators > li");
                                                                var _inner = carousel.querySelectorAll(".carousel-inner > .carousel-item");

                                                                var formData = new FormData();
                                                                formData.append("file", event.target.files[0]);
                                                                formData.append("type", ${EImage.GARAGE_PHOTO.type});
                                                            <c:if test="${not empty garage.id}">
                                                                formData.append("id", <c:out value="${garage.id}" />);
                                                            </c:if>
                                                                $.ajax('<c:url value="/photo/upload"/>', {
                                                                    method: "POST",
                                                                    data: formData,
                                                                    processData: false,
                                                                    contentType: false,
                                                                    success: function (response) {
                                                                        indicator.querySelector("li[id='loading']").remove();
                                                                        inner.querySelector("div[id='loading']").remove();
                                                                        loadImage(response);
                                                                    },
                                                                    error: function (response) {
                                                                        console.log(response);
                                                                    }
                                                                });

                                                                var li = document.createElement("li");
                                                                li.id = "loading";
                                                                li.dataset.target = "#" + carousel.id;
                                                                li.dataset.slideTo = "0";
                                                                li.classList.add("active");
                                                                indicator.insertBefore(li, indicator.firstChild);

                                                                _indicator.forEach(function (item, index) {
                                                                    item.classList.remove("active");
                                                                    item.dataset.slideTo = index + 1;
                                                                });

                                                                var div = document.createElement("div");
                                                                div.id = "loading";
                                                                div.classList.add("carousel-item");
                                                                div.classList.add("active");

                                                                var img = document.createElement("img");
                                                                img.classList.add("img-fluid");
                                                                img.src = "<c:url value="/resources/img/loading.gif"/>";

                                                                div.appendChild(img);
                                                                inner.insertBefore(div, inner.firstChild);

                                                                _inner.forEach(function (item, index) {
                                                                    item.classList.remove("active");
                                                                });

                                                                event.target.value = "";
                                                            });

                                                            function loadImage(response) {
                                                                var _indicator = carousel.querySelectorAll(".carousel-indicators > li");
                                                                var _inner = carousel.querySelectorAll(".carousel-inner > .carousel-item");

                                                                var li = document.createElement("li");
                                                                li.dataset.target = "#" + carousel.id;
                                                                li.dataset.slideTo = "0";
                                                                li.classList.add("active");
                                                                indicator.insertBefore(li, indicator.firstChild);

                                                                _indicator.forEach(function (item, index) {
                                                                    item.classList.remove("active");
                                                                    item.dataset.slideTo = index + 1;
                                                                });

                                                                var item = document.createElement("div");
                                                                item.classList.add("carousel-item");
                                                                item.classList.add("active");

                                                                var img = document.createElement("img");
                                                                img.classList.add("img-fluid");
                                                                img.src = "<c:url value="/photo/"/>" + response.image.src;

                                                                var src = document.createElement("input");
                                                                src.type = "hidden";
                                                                src.classList.add("d-none");
                                                                src.name = "imageList[0].src";
                                                                src.value = response.image.src;

                                                                var type = document.createElement("input");
                                                                type.type = "hidden";
                                                                type.classList.add("d-none");
                                                                type.name = "imageList[0].type";
                                                                type.value = response.image.type;

                                                                var div = document.createElement("div");
                                                                div.classList.add("carousel-caption");

                                                                var btn = document.createElement("button");
                                                                btn.classList.add("btn");
                                                                btn.classList.add("btn-secondary");
                                                                btn.innerHTML = "<i class=\"fas fa-times\"></i>";
                                                                btn.addEventListener("click", function (event) {
                                                                    event.preventDefault();
                                                                    var img = this.parentElement.parentElement.querySelector("img");
                                                                    var src = img.src;
                                                                    var src = src.substring(img.src.indexOf("/photo/") + 7);
                                                                    var formData = new FormData();
                                                                    formData.append("src", src);
                                                                    $.ajax('<c:url value="/photo/delete"/>', {
                                                                        method: "POST",
                                                                        data: formData,
                                                                        processData: false,
                                                                        contentType: false,
                                                                        success: function (response) {
                                                                            console.log(response);
                                                                        },
                                                                        error: function (response) {
                                                                            console.log(response);
                                                                        }
                                                                    });
                                                                    var li = indicator.children.item([...inner.children].indexOf(this.parentElement.parentElement));
                                                                    var pos = [...indicator.children].indexOf(li);
                                                                    inner.children[pos].remove();
                                                                    li.remove();

                                                                    var childElementCount = indicator.childElementCount;
                                                                    if (childElementCount > 0) {
                                                                        if (pos > childElementCount - 1) {
                                                                            pos = childElementCount - 1;
                                                                        }
                                                                        indicator.children[pos].classList.add("active");
                                                                        inner.children[pos].classList.add("active");

                                                                        for (var i = 0; i < childElementCount; i++) {
                                                                            var item = inner.children[i];
                                                                            var src = item.querySelector("input[name$='.src']");
                                                                            src.name = "imageList[" + i + "].src";
                                                                            var type = item.querySelector("input[name$='.type']");
                                                                            type.name = "imageList[" + i + "].type";
                                                                        }
                                                                    }
                                                                });

                                                                div.appendChild(btn);

                                                                item.appendChild(img);
                                                                item.appendChild(src);
                                                                item.appendChild(type);
                                                                item.appendChild(div);
                                                                inner.insertBefore(item, inner.firstChild);

                                                                _inner.forEach(function (item, index) {
                                                                    var newIndex = index + 1;
                                                                    item.classList.remove("active");
                                                                    var src = item.querySelector("input[name$='.src']");
                                                                    src.name = "imageList[" + newIndex + "].src";
                                                                    var type = item.querySelector("input[name$='.type']");
                                                                    type.name = "imageList[" + newIndex + "].type";
                                                                });
                                                            }
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="my-4" />
                                        <h6 class="heading-small text-muted mb-4">Informações da localização</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <div class="form-group">
                                                        <label for="street" class="form-control-label">Endereço</label>
                                                        <input type="text" class="form-control form-control-alternative" id="street" name="street" placeholder="Endereço" value="${garage.addressId.street}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="form-group">
                                                        <label for="number" class="form-control-label">Número</label>
                                                        <input type="text" class="form-control form-control-alternative" id="number" name="number" placeholder="Número" value="${garage.addressId.number}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="neighborhood" class="form-control-label">Bairro</label>
                                                        <input type="text" class="form-control form-control-alternative" id="neighborhood" name="neighborhood" placeholder="Bairro" value="${garage.addressId.neighborhood}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="city" class="form-control-label">Cidade</label>
                                                        <input type="text" class="form-control form-control-alternative" id="city" name="city" placeholder="Cidade" value="${garage.addressId.city}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="state" class="form-control-label">Estado</label>
                                                        <input type="text" class="form-control form-control-alternative" id="state" name="state" placeholder="Estado" value="${garage.addressId.state}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="zip" class="form-control-label">CEP</label>
                                                        <input type="text" class="form-control form-control-alternative" id="zip" name="zip" placeholder="CEP" value="${garage.addressId.zip}" required>
                                                    </div>
                                                </div>
                                                <input type="hidden" class="d-none" hidden id="longitude" name="longitude" value="${garage.addressId.longitude}" required step="any">
                                                <input type="hidden" class="d-none" hidden id="latitude" name="latitude" value="${garage.addressId.latitude}" required step="any">
                                            </div>
                                        </div>
                                        <hr class="my-4" />
                                        <h6 class="heading-small text-muted mb-4">Informações da garagem</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-4">
                                                    <div class="form-group">
                                                        <label for="height" class="form-control-label">Altura</label>
                                                        <div class="input-group input-group-alternative">
                                                            <input type="number" class="form-control form-control-alternative" id="height" name="height" placeholder="0,00" step="0.01" value="${garage.height}" required>
                                                            <div class="input-group-append">
                                                                <span class="input-group-text">m</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4">
                                                    <div class="form-group">
                                                        <label for="width" class="form-control-label">Largura</label>
                                                        <div class="input-group input-group-alternative">
                                                            <input type="number" class="form-control form-control-alternative" id="width" name="width" placeholder="0,00" step="0.01" value="${garage.width}" required>
                                                            <div class="input-group-append">
                                                                <span class="input-group-text">m</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4">
                                                    <div class="form-group">
                                                        <label for="length" class="form-control-label">Comprimento</label>
                                                        <div class="input-group input-group-alternative">
                                                            <input type="number" class="form-control form-control-alternative" id="length" name="length" placeholder="0,00" step="0.01" value="${garage.length}" required>
                                                            <div class="input-group-append">
                                                                <span class="input-group-text">m</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-12 mb-3">
                                                    <div class="form-group">
                                                        <label for="access"class="form-control-label">Acesso</label>
                                                        <input type="text" class="form-control form-control-alternative" id="access" name="access" placeholder="Acesso" value="${garage.access}" required>
                                                    </div>
                                                </div>                            
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="roof" class="form-control-label">Cobertura</label>
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasRoof" id="roofY" value="1" required
                                                                               <c:if test="${garage.hasRoof eq true}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="roofY">Sim</label>
                                                                    </div>    
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasRoof" id="roofN" value="0" required
                                                                               <c:if test="${garage.hasRoof eq false}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="roofN">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6 mb-3">
                                                    <div class="form-group">
                                                        <label for="cam" class="form-control-label">Câmera</label>
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasCam" id="camY" value="1" required
                                                                               <c:if test="${garage.hasCam eq true}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="camY">Sim</label>
                                                                    </div>    
                                                                </div>
                                                            </div>                                                        
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasCam" id="camN" value="0" required
                                                                               <c:if test="${garage.hasCam eq false}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="camN">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6 mb-3">
                                                    <div class="form-group">
                                                        <label for="indemnity" class="form-control-label">Seguro</label>
                                                        <div class="row">                                                        
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasIndemnity" id="indemnityY" value="1" required
                                                                               <c:if test="${garage.hasIndemnity eq true}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="indemnityY">Sim</label>
                                                                    </div>   
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3"> 
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasIndemnity" id="indemnityN" value="0" required
                                                                               <c:if test="${garage.hasIndemnity eq false}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="indemnityN">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6 mb-3">
                                                    <div class="form-group">
                                                        <label for="electronic_gate" class="form-control-label">Portão Eletrônico</label>
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasElectronicGate" id="electronicGateY" value="1" required
                                                                               <c:if test="${garage.hasElectronicGate eq true}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="electronicGateY">Sim</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio" name="hasElectronicGate" id="electronicGateN" value="0" required
                                                                               <c:if test="${garage.hasElectronicGate eq false}">
                                                                                   checked
                                                                               </c:if>>
                                                                        <label class="custom-control-label" for="electronicGateN">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="my-4" />
                                        <div class="row justify-content-center">
                                            <button id="save" class="btn btn-primary btn-lg" type="submit">Salvar</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>  
                </div>
            </div>  
        </div>
        <jsp:include page="../common/wrapper.jsp"/>
        <script>
            var autocomplete;
            var componentForm = {
                street_number: 'number',
                route: 'street',
                administrative_area_level_2: 'city',
                political: 'neighborhood',
                administrative_area_level_1: 'state',
                postal_code: 'zip'
            };

            document.getElementById("save").addEventListener("click", function (event) {
                var valid = document.getElementsByTagName("form")[0].reportValidity();
                if (valid) {
                    event.preventDefault();
                    var address = '';
                    for (var component in componentForm) {
                        address += document.getElementById(componentForm[component]).value + " ";
                    }
                    address = encodeURI(address);

                    getGeoCode(address);
                }
            });

            function getGeoCode(address) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        var response = JSON.parse(this.responseText);
                        fillInAddress(response.results[0]);
                        document.getElementsByTagName("form")[0].submit();
                    }
                };
                xhttp.open("GET", "https://maps.googleapis.com/maps/api/geocode/json?key=${Constants.GOOGLE_MAPS_KEY}&address=" + address, true);
                xhttp.send();
            }

            function fillInAddress(place) {
                for (var component in componentForm) {
                    document.getElementById(componentForm[component]).value = '';
                }
                document.getElementById('latitude').value = '';
                document.getElementById('longitude').value = '';

                // Get each component of the address from the place details
                // and fill the corresponding field on the form.
                for (var i = 0; i < place.address_components.length; i++) {
                    var addressType = place.address_components[i].types[0];
                    if (componentForm[addressType]) {
                        var val = place.address_components[i]['long_name'];
                        document.getElementById(componentForm[addressType]).value = val;
                    }
                }
                document.getElementById('latitude').value = place.geometry.location.lat;
                document.getElementById('longitude').value = place.geometry.location.lng;
            }
        </script>
    </body>
</html>
