<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>

<nav class="navbar navbar-default flex-shrink-0">
    <div class="container">
        <a href="<c:url value="/"/>" class="navbar-brand" title="Início">
            <img src="<c:url value="/resources/img/logo.png"/>" width="36" height="36" alt="Garagem Virtual">
            Garagem Virtual
        </a>
        <ul class="nav ml-auto"> 
            <li class="nav-item align-items-baseline d-lg-block">
                <a href="javascript:decreaseZoom();" class="nav-link pr-2 d-inline-block" style="font-size: 14px;" title="Diminuir zoom">A-</a>
                <a href="javascript:increaseZoom();" class="nav-link pr-2 d-inline-block" style="font-size: 20px;" title="Aumentar zoom">A+</a>
            </li>
            <c:if test="${empty userLogged}">
                <li class="nav-item dropdown">                
                    <a href="#" class="nav-link pr-2" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" title="Acessar o Garagem Virtual">
                        <i class="fas fa-lg fa-user"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
                        <div class="container" style="margin-top: 1rem">
                            <form role="form" method="POST" action="<c:url value="/login"/>" autocomplete="on">
                                <fieldset>
                                    <div class="form-group mb-3">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-user fa-fw"></i></span>
                                            </div>
                                            <input type="email" name="email" id="loginEmail" class="form-control" placeholder="Email" title="Digite seu email" autofocus required/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-key fa-fw"></i></span>
                                            </div>
                                            <input type="password" name="password" id="loginPassword" class="form-control input-lg" placeholder="Senha" title="Digite sua senha" required/>
                                        </div>
                                    </div>
                                    <div class="custom-control custom-control-alternative custom-checkbox mb-3" title="Mantenha-me conectado">
                                        <input class="custom-control-input form-check-input" id="loginRemember" type="checkbox" name="remember">
                                        <label class="custom-control-label" for="loginRemember">
                                            <span class="text-muted">Mantenha-me conectado</span>
                                        </label>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-6">
                                            <a href="<c:url value="/request-password"/>" class="btn btn-sm btn-secondary" title="Esqueceu sua senha?"><small>Esqueceu sua senha?</small></a>
                                        </div>
                                        <div class="col-6 text-right">
                                            <a href="<c:url value="/register"/>" class="btn btn-sm btn-secondary" title="Crie uma nova conta"><small>Crie uma nova conta</small></a>
                                        </div>
                                    </div>
                                    <div class="text-center">
                                        <button class="btn btn-primary mb-3" name="login" type="submit" title="Entrar">Entrar <i class="fa fa-sign-in-alt"></i></button>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </ul>
                </li>
            </c:if>
            <c:if test="${not empty userLogged}">
                <c:if test="${userLogged.isAdmin == false}">
                    <li class="nav-item">
                        <a href="<c:url value="/advertisement/new"/>" class="nav-link pr-2" title="Cadastrar um anúncio de garagem">
                            <i class="fas fa-cart-plus"></i>
                        </a>
                    </li>
                    <li class="nav-item dropdown">                
                        <a href="#" class="nav-link pr-2" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" title="Notificações">
                            <i class="fas fa-lg fa-bell">                          
                                <span class="badge badge-pill border-radius badge-danger" id="notification-count"></span>                       
                            </i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
                            <li class="notification-head">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12 col-12">
                                        <span>Notificações</span>
                                    </div>
                                </div>
                            </li>
                            <div id="notification-list" class="notification-content">
                            </div>
                            <script>
                                function getNotification() {
                                    var xhttp = new XMLHttpRequest();
                                    xhttp.onreadystatechange = function () {
                                        if (this.readyState === 4 && this.status === 200) {
                                            var response = JSON.parse(this.responseText);
                                            createNotification(response.data);
                                        } else if (this.readyState === 4) {
                                            console.log(this);
                                        }
                                    };
                                    xhttp.open("GET", "<c:url value="/notification"/>", true);
                                    xhttp.send();
                                }

                                function createNotification(data) {
                                    var list = document.getElementById("notification-list");
                                    list.innerHTML = "";
                                    var count = document.getElementById("notification-count");
                                    if (data.length > 0) {
                                        count.innerHTML = data.length;
                                    } else {
                                        count.innerHTML = "";
                                    }
                                    for (i = 0; i < data.length; i++) {
                                        console.log(i);
                                        var li = document.createElement("li");
                                        var divNotification = document.createElement("div");
                                        divNotification.classList.add("row");

                                        var divImage = document.createElement("div");
                                        divImage.classList.add("col-lg-3");
                                        divImage.classList.add("col-sm-3");
                                        divImage.classList.add("col-3");
                                        divImage.classList.add("text-center");

                                        var img = document.createElement("img");
                                        img.classList.add("w-50");
                                        img.classList.add("rounded-circle");
                                        if (typeof data[i].vehicle.user.src === "undefined") {
                                            img.src = "<c:url value="/resources/img/no-image-profile.png"/>";
                                        } else {
                                            img.src = "<c:url value="/photo/"/>" + data.vehicle.user.src;
                                        }

                                        divImage.appendChild(img);

                                        var divInfo = document.createElement("div");
                                        divInfo.classList.add("col-lg-8");
                                        divInfo.classList.add("col-sm-8");
                                        divInfo.classList.add("col-8");

                                        var strong = document.createElement("strong");
                                        strong.classList.add("text-info");

                                        divInfo.appendChild(strong);

                                        var div4 = document.createElement("div");
                                        div4.innerHTML = "Nova solicitação de aluguel para " + " " + data[i].vehicle.user.name + " " + data[i].vehicle.user.lastName;

                                        divInfo.appendChild(div4);

                                        var small = document.createElement("small");
                                        small.classList.add("text-warning");
                                        small.innerHTML = data[i].dateTime;

                                        divInfo.appendChild(small)

                                        divNotification.appendChild(divImage);
                                        divNotification.appendChild(divInfo);
                                        li.appendChild(divNotification);

                                        li.style.cursor = "pointer";
                                        li.addEventListener("click", function () {
                                            redirect(this);
                                        });
                                        function redirect(event) {
                                            window.location = "<c:url value="/rent-garage/received"/>";
                                        }

                                        list.appendChild(li);
                                    }
                                }

                                getNotification();
                                var interval = window.setInterval(getNotification, 30000);
                            </script>
                            <li class="notification-footer text-center">
                                <a href="">Ver tudo</a>
                            </li>
                        </ul>
                    </li>       
                </c:if>
                <c:if test="${userLogged.isAdmin == true}">                    
                    <li class="nav-item">
                        <a href="<c:url value="/admin"/>" class="nav-link pr-2" title="Painel de controle">
                            <i class="fas fa-tachometer-alt"></i>
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a href="#" class="nav-link pr-2" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="Verificar documentos">
                            <i class="fas fa-check"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right"  aria-labelledby="dropdown">
                            <div class=" dropdown-header noti-title">
                                <h6 class="text-overflow m-0">Verificar Documentos</h6>
                            </div>
                            <a href="<c:url value="/admin/advertisement"/>" class="dropdown-item" title="Verificar Anúncios">
                                <i class="fas fa-clock"></i>
                                Verificar Anúncios
                            </a>
                            <a href="<c:url value="/admin/user"/>" class="dropdown-item" title="Verificar Usuários">
                                <i class="fas fa-user"></i>
                                Verificar Usuários
                            </a>   
                            <a href="<c:url value="/admin/vehicle"/>" class="dropdown-item" title="Verificar Veículos">
                                <i class="fas fa-car-side"></i>
                                Verificar Veículos
                            </a>
                        </div>
                    </li>           
                    <li class="nav-item">
                        <a href="<c:url value="/admin/report"/>" class="nav-link pr-2" title="Relatório">
                            <i class="fas fa-file-alt"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/admin/gerenciar"/>" class="nav-link pr-2" title="Gerenciar atividades dos usuários">
                            <i class="fas fa-user"></i>
                        </a>
                    </li>
                </c:if>
                <li class="nav-item dropdown">
                    <a href="#" class="nav-link pr-2" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="Menu do Usuário">
                        <div class="media align-items-center">
                            <c:set var="userPhoto"><c:url value="/resources/img/no-image-profile.png"/></c:set>
                            <c:forEach items="${userLogged.imageList}" var="image">
                                <c:if test="${image.type eq EImage.USER_PHOTO.type}">
                                    <c:set var="userPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                </c:if>
                            </c:forEach>
                            <span class="avatar avatar-sm rounded-circle">
                                <img alt="Image placeholder" src="<c:out value="${userPhoto}"/>">
                            </span>
                            <div class="media-body ml-2 d-none d-lg-block">
                                <span class="mb-0 text-sm  font-weight-bold"><c:out value="${userLogged.name} ${userLogged.lastName}" /></span>
                            </div>
                        </div>
                    </a>
                    <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right"  aria-labelledby="dropdown">
                        <div class=" dropdown-header noti-title">
                            <h6 class="text-overflow m-0">Menu</h6>
                        </div>
                        <a href="<c:url value="/user/edit/${userLogged.id}"/>" class="dropdown-item" title="Meu Perfil">
                            <i class="fas fa-user"></i>
                            Meu Perfil
                        </a>   
                        <a href="<c:url value="/rent-garage/request"/>" class="dropdown-item" title="Solicitações Enviadas">
                            <i class="fas fa-clock"></i>
                            Solicitações Enviadas
                        </a>
                        <a href="<c:url value="/rent-garage/received"/>" class="dropdown-item" title="Solicitações Recebidas">
                            <i class="fas fa-coins"></i>
                            Solicitações Recebidas
                        </a>       
                        <a href="<c:url value="/advertisement"/>" class="dropdown-item" title="Meus Anúncios">
                            <i class="fas fa-map-marker-alt"></i>
                            Meus Anúncios
                        </a>    
                        <a href="<c:url value="/garage"/>" class="dropdown-item" title="Minhas Garagens">
                            <i class="fas fa-home"></i>
                            Minhas Garagens
                        </a>     
                        <a href="<c:url value="/vehicle"/>" class="dropdown-item" title="Meus Veículos">
                            <i class="fas fa-car-side"></i>
                            Meus Veiculos
                        </a>      
                        <div class="dropdown-divider"></div>
                        <a href="<c:url value="/logout"/>" class="dropdown-item" title="Sair">
                            <i class="fas fa-sign-out-alt"></i>
                            Sair
                        </a>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>  
