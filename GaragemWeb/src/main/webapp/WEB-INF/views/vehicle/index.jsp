<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Veículo"/>
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
                                    <a href="<c:url value="/vehicle"/>" class="text-light"><i class="fas fa-chevron-left"></i> Meus Veículos</a>
                                </div>
                            </div>
                            <div class="card bg-secondary shadow">
                                <div class="card-header bg-white border-0">
                                    <div class="row align-items-center">
                                        <div class="col-sm-8">
                                            <h4 class="title">Novo Veículo</h4>
                                            <p class="category">Preencha os campos abaixo para cadastrar um novo veículo</p>
                                        </div>     
                                        <c:if test="${not empty vehicle.id}">
                                            <c:if test="${not empty vehicle.chassis && not vehicle.isAuth}">
                                                <div class="col-sm-4 text-right">
                                                    <button type="button" class="btn btn-primary">
                                                        <i class="fas fa-clock"></i> Aguardando verificação
                                                    </button>
                                                </div>
                                            </c:if>  
                                            <c:if test="${vehicle.isAuth}">
                                                <div class="col-sm-4 text-right">
                                                    <button type="button" class="btn btn-primary">
                                                        <i class="fas fa-check"></i> Verificado
                                                    </button>
                                                </div>
                                            </c:if>  
                                            <c:if test="${empty vehicle.chassis}">
                                                <div class="col-sm-4 text-right">
                                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#verify">
                                                        <i class="fas fa-check"></i> Verificar
                                                    </button>
                                                    <div class="modal fade" id="verify" tabindex="-1" role="dialog" aria-labelledby="verify" aria-hidden="true">
                                                        <div class="modal-dialog modal-dialog-centered" role="document">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <form method="POST">
                                                                        <div id="wizard">
                                                                            <h4></h4>
                                                                            <section>
                                                                                <div class="row">
                                                                                    <div class="col-md-12">                                            
                                                                                        <div class="text-center mb-3">
                                                                                            <p>Digite o chassi no campo abaixo para que possamos verificar o seu veículo</p> 
                                                                                        </div>                           
                                                                                        <div class="form-group">
                                                                                            <label class="form-control-label float-left" for="chassis">Chassi</label> 
                                                                                            <input type="text" class="form-control form-control-alternative" id="chassis" name="chassis" placeholder="9XX999XX99C999999" value="${vehicle.chassis}" required>
                                                                                        </div>
                                                                                    </div>  
                                                                                </div>
                                                                            </section> 
                                                                            <h4></h4>
                                                                            <section class="doc-upload">
                                                                                <div class="row">   
                                                                                    <div class="col-md-12">                                
                                                                                        <div class="text-center mb-3">
                                                                                            <p>Agora tire uma foto do documento do carro</p> 
                                                                                        </div>           
                                                                                        <div class="form-group row">   
                                                                                            <c:set var="vehicleDocPhoto"><c:url value="/resources/img/no-image.png"/></c:set>
                                                                                            <c:forEach items="${vehicle.imageList}" var="image">
                                                                                                <c:if test="${image.type eq EImage.VEHICLE_DOC.type}">
                                                                                                    <c:set var="vehicleDocPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                                                                                </c:if>
                                                                                            </c:forEach>
                                                                                            <img src="${vehicleDocPhoto}" id="vehicleDocPhoto" class="rounded mx-auto d-block img-fluid">                                                
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <input type="file" class="form-control-file" name="vehicleDoc" id="vehicleDoc" accept="image/jpg,image/jpeg,image/png" required>                                    
                                                                                            <label for="vehicleDoc">
                                                                                                <i class="fas fa-camera"></i>
                                                                                                <span>Escolha uma imagem</span>
                                                                                            </label>
                                                                                        </div>
                                                                                    </div>        
                                                                                </div>
                                                                                <script>
                                                                                    document.getElementById("vehicleDoc").addEventListener("change", function (event) {
                                                                                        document.getElementById("vehicleDocPhoto").src = "<c:url value="/resources/img/loading.gif"/>";
                                                                                        var file = event.target.files[0];
                                                                                        var formData = new FormData();
                                                                                        formData.append("file", file);
                                                                                        formData.append("type", ${EImage.VEHICLE_DOC.type});
                                                                                    <c:if test="${not empty vehicle.id}">
                                                                                        formData.append("id", <c:out value="${vehicle.id}" />);
                                                                                    </c:if>
                                                                                        $.ajax('<c:url value="/photo/upload"/>', {
                                                                                            method: "POST",
                                                                                            data: formData,
                                                                                            processData: false,
                                                                                            contentType: false,
                                                                                            success: function (response) {
                                                                                                document.getElementById("vehicleDocPhoto").src = "<c:url value="/photo/"/>" + response.image.src;
                                                                                                console.log(response);
                                                                                            },
                                                                                            error: function (response) {
                                                                                                console.log(response);
                                                                                            }
                                                                                        });
                                                                                    });
                                                                                </script>
                                                                            </section>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:if>
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
                                                                <c:forEach items="${vehicle.imageList}" var="image" varStatus="loop">
                                                                    <c:if test="${image.type eq EImage.VEHICLE_PHOTO.type}">   
                                                                        <li data-target="#photoList" data-slide-to="${loop.index}" ${loop.first ? 'class="active"' : ''}>
                                                                        </li>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </ul>
                                                            <!-- The slideshow -->
                                                            <div class="carousel-inner">
                                                                <c:forEach items="${vehicle.imageList}" var="image" varStatus="loop">
                                                                    <c:if test="${image.type eq EImage.VEHICLE_PHOTO.type}">
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
                                                                formData.append("type", ${EImage.VEHICLE_PHOTO.type});
                                                            <c:if test="${not empty vehicle.id}">
                                                                formData.append("id", <c:out value="${vehicle.id}" />);
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
                                        <h6 class="heading-small text-muted mb-4">Informações do veículo</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <label for="plate" class="form-control-label">Placa</label>
                                                        <input type="text" class="form-control form-control-alternative" id="plate" name="plate" placeholder="Placa" value="${vehicle.plate}" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <label for="type" class="form-control-label">Tipo</label>
                                                        <select class="form-control form-control-alternative" id="type" name="type">
                                                            <option disabled selected value>Selecione um tipo de veículo</option>
                                                            <option data-id="1" value="Carro"
                                                                    <c:if test="${vehicle.type eq '1'}">
                                                                        selected
                                                                    </c:if>>Carro</option>
                                                            <option data-id="2" value="Moto" 
                                                                    <c:if test="${vehicle.type eq '2'}">
                                                                        selected
                                                                    </c:if>>Moto</option>     
                                                            <option data-id="3" value="Caminhão" 
                                                                    <c:if test="${vehicle.type eq '3'}">
                                                                        selected
                                                                    </c:if>>Caminhão</option>
                                                        </select>                 
                                                    </div>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <label for="manufacturer" class="form-control-label">Marca</label>
                                                        <select class="form-control form-control-alternative" id="manufacturer" name="manufacturer" disabled>
                                                            <option disabled selected value>Marca</option>
                                                        </select>                 
                                                    </div>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <label for="model" class="form-control-label">Modelo</label>
                                                        <select class="form-control form-control-alternative" id="model" name="model" disabled>
                                                            <option disabled selected value>Modelo</option>
                                                        </select>                 
                                                    </div>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <label for="year" class="form-control-label">Ano</label>
                                                        <select class="form-control form-control-alternative" id="year" name="year" disabled>
                                                            <option disabled selected value>Ano</option>
                                                        </select>                 
                                                    </div>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <label for="color" class="form-control-label">Cor</label>
                                                        <input type="text" class="form-control form-control-alternative" id="color" name="color" placeholder="Cor" value="${vehicle.color}">
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
        <!-- JQuery Steps -->
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.steps.min.js"/>">
        </script>
        <script>
            $(function () {
                $("#wizard").steps({
                    headerTag: "h4",
                    bodyTag: "section",
                    transitionEffect: "fade",
                    forceMoveForward: true,
                    enableKeyNavigation: false,
                    transitionEffectSpeed: 500,
                    onStepChanging: function (event, currentIndex, newIndex) {
                        var chassis = document.getElementById("chassis");
                        var vehicleDoc = document.getElementById("vehicleDoc");
                        switch (currentIndex) {
                            case 0:
                                if (chassis.value !== "") {
                                    return true;
                                } else {
                                    return false;
                                }
                                break;
                            case 1:
                                var img = event.target.parentElement.parentElement.querySelector("img");
                                var src = img.src;
                                if (vehicleDoc.files.length > 0 || src.indexOf("/photo/") >= 0) {
                                    return true;
                                } else {
                                    return false;
                                }
                                break;
                            default:
                                return false;
                        }
                    },
                    onFinishing: function (event, currentIndex) {
                        event.target.parentElement.submit();
                        return true;
                    },
                    labels: {
                        finish: "<i class='fas fa-check'></i>",
                        next: "<i class='fas fa-angle-right'></i>",
                        previous: "<i class='fas fa-angle-left'></i>"
                    }
                });
            });
        </script>
        <script>
            <c:if test="${not empty vehicle}">
            document.addEventListener("DOMContentLoaded", function (event) {
                var type = "${vehicle.type}";
                var manufacturer = "${vehicle.manufacturer}";
                var model = "${vehicle.model}";
                var year = "${vehicle.year}";

                var inputType = document.getElementById("type");
                inputType.value = type;
                type = inputType.options[inputType.selectedIndex].getAttribute("data-id");
                switch (type) {
                    case "1":
                        type = "carros";
                        break;
                    case "2":
                        type = "motos";
                        break;
                    case "3":
                        type = "caminhoes";
                        break;
                    default:
                        type = null;
                }
                ajax("http://fipeapi.appspot.com/api/1/" + type + "/marcas.json", "GET", function (response) {
                    var select = document.getElementById("manufacturer");
                    select.innerHTML = "<option disabled selected value>Marca</option>";
                    fillManufacturer(select, response);
                    select.disabled = false;

                    var inputManufacturer = document.getElementById("manufacturer");
                    inputManufacturer.value = manufacturer;
                    manufacturer = inputManufacturer.options[inputManufacturer.selectedIndex].getAttribute("data-id");
                    ajax("http://fipeapi.appspot.com/api/1/" + type + "/veiculos/" + manufacturer + ".json", "GET", function (response) {
                        var select = document.getElementById("model");
                        select.innerHTML = "<option disabled selected value>Modelo</option>";
                        fillModel(select, response);
                        select.disabled = false;

                        var inputModel = document.getElementById("model");
                        inputModel.value = model;
                        model = inputModel.options[inputModel.selectedIndex].getAttribute("data-id");
                        ajax("http://fipeapi.appspot.com/api/1/" + type + "/veiculo/" + manufacturer + "/" + model + ".json", "GET", function (response) {
                            var select = document.getElementById("year");
                            select.innerHTML = "<option disabled selected value>Ano</option>";
                            fillYear(select, response);
                            select.disabled = false;

                            var inputYear = document.getElementById("year");
                            inputYear.value = year;
                        });
                    });
                });
            });
            </c:if>

            function fillManufacturer(select, manufacturer) {
                manufacturer.forEach(function (item, index) {
                    var option = document.createElement("option");
                    option.setAttribute("data-id", item.id);
                    option.value = item.name;
                    option.innerHTML = item.name;
                    select.append(option);
                });
            }

            function fillModel(select, model) {
                model.forEach(function (item, index) {
                    var option = document.createElement("option");
                    option.setAttribute("data-id", item.id);
                    option.value = item.name;
                    option.innerHTML = item.name;
                    select.append(option);
                });
            }

            function fillYear(select, year) {
                year.forEach(function (item, index) {
                    var option = document.createElement("option");
                    option.setAttribute("data-id", item.id);
                    option.value = item.name;
                    option.innerHTML = item.name;
                    select.append(option);
                });
            }

            document.getElementById("type").addEventListener("change", function (event) {
                var type = event.target.selectedOptions[0].getAttribute("data-id");
                switch (type) {
                    case "1":
                        type = "carros";
                        break;
                    case "2":
                        type = "motos";
                        break;
                    case "3":
                        type = "caminhoes";
                        break;
                    default:
                        type = null;
                }

                ajax("http://fipeapi.appspot.com/api/1/" + type + "/marcas.json", "GET", function (response) {
                    var select = document.getElementById("manufacturer");
                    select.innerHTML = "<option disabled selected value>Marca</option>";
                    fillManufacturer(select, response);
                    select.disabled = false;
                    var model = document.getElementById("model");
                    model.innerHTML = "<option disabled selected value>Modelo</option>";
                    model.disabled = true;
                    var year = document.getElementById("year");
                    year.innerHTML = "<option disabled selected value>Ano</option>";
                    year.disabled = true;
                });
            });

            document.getElementById("manufacturer").addEventListener("change", function (event) {
                var type = document.getElementById("type");
                type = type.options[type.selectedIndex].getAttribute("data-id");
                switch (type) {
                    case "1":
                        type = "carros";
                        break;
                    case "2":
                        type = "motos";
                        break;
                    case "3":
                        type = "caminhoes";
                        break;
                    default:
                        type = null;
                }

                ajax("http://fipeapi.appspot.com/api/1/" + type + "/veiculos/" + event.target.selectedOptions[0].getAttribute("data-id") + ".json", "GET", function (response) {
                    var select = document.getElementById("model");
                    select.innerHTML = "<option disabled selected value>Modelo</option>";
                    fillModel(select, response);
                    select.disabled = false;
                    var year = document.getElementById("year");
                    year.innerHTML = "<option disabled selected value>Ano</option>";
                    year.disabled = true;
                });
            });

            document.getElementById("model").addEventListener("change", function (event) {
                var type = document.getElementById("type");
                type = type.options[type.selectedIndex].getAttribute("data-id");
                var manufacturer = document.getElementById("manufacturer");
                manufacturer = manufacturer.options[manufacturer.selectedIndex].getAttribute("data-id");
                switch (type) {
                    case "1":
                        type = "carros";
                        break;
                    case "2":
                        type = "motos";
                        break;
                    case "3":
                        type = "caminhoes";
                        break;
                    default:
                        type = null;
                }

                ajax("http://fipeapi.appspot.com/api/1/" + type + "/veiculo/" + manufacturer + "/" + event.target.selectedOptions[0].getAttribute("data-id") + ".json", "GET", function (response) {
                    var select = document.getElementById("year");
                    select.innerHTML = "<option disabled selected value>Ano</option>";
                    fillYear(select, response);
                    select.disabled = false;
                });
            });

            function ajax(url, method, callvehicle) {
                $.ajax(url, {
                    method: method,
                    success: function (response) {
                        callvehicle(response);
                    },
                    error: function (response) {
                        console.log(response);
                    }
                });
            }
        </script>
    </body>
</html>
