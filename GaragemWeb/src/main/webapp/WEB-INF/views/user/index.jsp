<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Meu Perfil"/>
        </jsp:include>  
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="main-content">
                <div class="container-fluid my-5">
                    <div class="row">
                        <div class="col-xl-4 order-xl-2 mb-5 mb-xl-0">
                            <div class="card card-profile shadow">
                                <div class="row justify-content-center">
                                    <div class="col-lg-3 order-lg-2">
                                        <div class="card-profile-image">
                                            <input type='file' id="userPhoto" accept=".png, .jpg, .jpeg" />
                                            <label for="userPhoto">      
                                                <button class="card-profile-image-delete" id="deleteUserPhoto"><i class="fas fa-times"></i></button>          
                                                    <c:set var="userPhoto"><c:url value="/resources/img/no-image-profile.png"/></c:set>
                                                    <c:forEach items="${user.imageList}" var="image">
                                                        <c:if test="${image.type eq EImage.USER_PHOTO.type}">
                                                            <c:set var="userPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:set var="verified" value=""></c:set>
                                                    <c:if test="${user.isAuth}">
                                                        <c:set var="verified" value="verified"></c:set>
                                                        <button type="button" class="card-profile-image-verified" data-toggle="tooltip" data-placement="left" title="Usuário verificado"><i class="fas fa-shield-alt"></i></button>          
                                                    </c:if>  
                                                <img class="rounded-circle ${verified}" id="photo" src="${userPhoto}">
                                            </label>
                                            <script>
                                                document.getElementById("deleteUserPhoto").addEventListener("click", function (event) {
                                                    event.preventDefault();
                                                    var img = event.target.parentElement.parentElement.querySelector("img");
                                                    var src = img.src;
                                                    if (img.src.indexOf("/photo/") > 0) {
                                                        var src = src.substring(img.src.indexOf("/photo/") + 7);
                                                        img.src = "<c:url value="/resources/img/loading.gif"/>";
                                                        var formData = new FormData();
                                                        formData.append("src", src);
                                                        $.ajax('<c:url value="/photo/delete"/>', {
                                                            method: "POST",
                                                            data: formData,
                                                            processData: false,
                                                            contentType: false,
                                                            success: function (response) {
                                                                document.getElementById("photo").src = "<c:url value="/resources/img/no-image-profile.png"/>";
                                                                document.querySelector(".avatar img").src = "<c:url value="/resources/img/no-image-profile.png"/>";
                                                                console.log(response);
                                                            },
                                                            error: function (response) {
                                                                console.log(response);
                                                            }
                                                        });
                                                    }
                                                });


                                                document.getElementById("userPhoto").addEventListener("change", function (event) {
                                                    document.getElementById("photo").src = "<c:url value="/resources/img/loading.gif"/>";
                                                    var file = event.target.files[0];
                                                    var formData = new FormData();
                                                    formData.append("file", file);
                                                    formData.append("type", ${EImage.USER_PHOTO.type});
                                                <c:if test="${not empty user.id}">
                                                    formData.append("id", <c:out value="${user.id}" />);
                                                </c:if>
                                                    $.ajax('<c:url value="/photo/upload"/>', {
                                                        method: "POST",
                                                        data: formData,
                                                        processData: false,
                                                        contentType: false,
                                                        success: function (response) {
                                                            document.getElementById("photo").src = "<c:url value="/photo/"/>" + response.image.src;
                                                            document.querySelector(".avatar img").src = "<c:url value="/photo/"/>" + response.image.src;
                                                            console.log(response);
                                                        },
                                                        error: function (response) {
                                                            console.log(response);
                                                        }
                                                    });
                                                });
                                            </script>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-header text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4">
                                    <div class="d-flex justify-content-between">
                                    </div>
                                </div>
                                <div class="card-body pt-0 pt-md-4">
                                    <div class="row">
                                        <div class="col">
                                            <div class="card-profile-stats d-flex justify-content-center mt-md-5">
                                                <div>
                                                    <a href="<c:url value="/garage"/>">
                                                        <span class="heading"><c:out value="${user.garageList.size()}"/></span>
                                                        <span class="description">Garagens</span>
                                                    </a>
                                                </div>
                                                <div>
                                                    <a href="<c:url value="/vehicle"/>">
                                                        <span class="heading"><c:out value="${user.vehicleList.size()}"/></span>
                                                        <span class="description">Veículos</span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-center">
                                        <h3>
                                            <c:out value="${user.name}"/> <span class="font-weight-light"><c:out value="${user.lastName}"/></span>
                                        </h3>
                                        <div class="stars-outer">
                                            <div class="stars-inner"></div>
                                        </div>
                                        <hr class="my-4" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-8 order-xl-1">
                            <div class="card bg-secondary shadow">
                                <div class="card-header bg-white border-0">
                                    <div class="row align-items-center">
                                        <div class="col-sm-8">
                                            <h4 class="title">Meu perfil</h4>
                                            <p class="category">Veja as informações do seu perfil</p>
                                        </div>     
                                        <c:if test="${not empty user.identity && not user.isAuth}">
                                            <div class="col-sm-4 text-right">
                                                <button type="button" class="btn btn-primary">
                                                    <i class="fas fa-clock"></i> Aguardando verificação
                                                </button>
                                            </div>
                                        </c:if>  
                                        <c:if test="${user.isAuth}">
                                            <div class="col-sm-4 text-right">
                                                <button type="button" class="btn btn-primary">
                                                    <i class="fas fa-check"></i> Verificado
                                                </button>
                                            </div>
                                        </c:if>  
                                        <c:if test="${empty user.identity}">
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
                                                                                        <p>Digite seu CPF no campo abaixo para que possamos verificar o seu perfil</p> 
                                                                                    </div>                           
                                                                                    <div class="form-group">
                                                                                        <label class="form-control-label float-left" for="identity">CPF</label> 
                                                                                        <input type="text" class="form-control form-control-alternative ${empty errors.identity ? null : "is-invalid"}" id="identity" name="identity" placeholder="999.999.999-99" value="${user.identity}" required>
                                                                                    </div>
                                                                                </div>  
                                                                            </div>
                                                                        </section> 
                                                                        <h4></h4>
                                                                        <section class="doc-upload">
                                                                            <div class="row">   
                                                                                <div class="col-md-12">                                
                                                                                    <div class="text-center mb-3">
                                                                                        <p>Agora tire uma foto da parte de trás do seu documento</p> 
                                                                                    </div>           
                                                                                    <div class="form-group row">   
                                                                                        <c:set var="backDocPhoto"><c:url value="/resources/img/no-image.png"/></c:set>
                                                                                        <c:forEach items="${user.imageList}" var="image">
                                                                                            <c:if test="${image.type eq EImage.USER_DOC_BACK.type}">
                                                                                                <c:set var="backDocPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                                                                            </c:if>
                                                                                        </c:forEach>
                                                                                        <img src="${backDocPhoto}" id="backDocPhoto" class="rounded mx-auto d-block img-fluid">                                                
                                                                                    </div>
                                                                                    <div class="form-group row">
                                                                                        <input type="file" class="form-control-file" name="backDoc" id="backDoc" accept="image/jpg,image/jpeg,image/png" required>                                    
                                                                                        <label for="backDoc">
                                                                                            <i class="fas fa-camera"></i>
                                                                                            <span>Escolha uma imagem</span>
                                                                                        </label>
                                                                                    </div>
                                                                                </div>        
                                                                            </div>
                                                                            <script>
                                                                                document.getElementById("backDoc").addEventListener("change", function (event) {
                                                                                    document.getElementById("backDocPhoto").src = "<c:url value="/resources/img/loading.gif"/>";
                                                                                    var file = event.target.files[0];
                                                                                    var formData = new FormData();
                                                                                    formData.append("file", file);
                                                                                    formData.append("type", ${EImage.USER_DOC_BACK.type});
                                                                                <c:if test="${not empty user.id}">
                                                                                    formData.append("id", <c:out value="${user.id}" />);
                                                                                </c:if>
                                                                                    $.ajax('<c:url value="/photo/upload"/>', {
                                                                                        method: "POST",
                                                                                        data: formData,
                                                                                        processData: false,
                                                                                        contentType: false,
                                                                                        success: function (response) {
                                                                                            document.getElementById("backDocPhoto").src = "<c:url value="/photo/"/>" + response.image.src;
                                                                                            console.log(response);
                                                                                        },
                                                                                        error: function (response) {
                                                                                            console.log(response);
                                                                                        }
                                                                                    });
                                                                                });
                                                                            </script>
                                                                        </section>
                                                                        <h4></h4>
                                                                        <section class="doc-upload">
                                                                            <div class="row">
                                                                                <div class="col-md-12">                                
                                                                                    <div class="text-center mb-3">
                                                                                        <p>E foto da parte da frente...</p> 
                                                                                    </div>     
                                                                                    <div class="form-group row">  
                                                                                        <c:set var="frontDocPhoto"><c:url value="/resources/img/no-image.png"/></c:set>
                                                                                        <c:forEach items="${user.imageList}" var="image">
                                                                                            <c:if test="${image.type eq EImage.USER_DOC_FRONT.type}">
                                                                                                <c:set var="frontDocPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                                                                            </c:if>
                                                                                        </c:forEach>
                                                                                        <img src="${frontDocPhoto}" id="frontDocPhoto" class="rounded mx-auto d-block img-fluid">                                                   
                                                                                    </div>
                                                                                    <div class="form-group row">
                                                                                        <input type="file" class="form-control-file" name="frontDoc" id="frontDoc" accept="image/jpg,image/jpeg,image/png" required>
                                                                                        <label for="frontDoc">
                                                                                            <i class="fas fa-camera"></i>
                                                                                            <span>Escolha uma imagem</span>
                                                                                        </label>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <script>
                                                                                document.getElementById("frontDoc").addEventListener("change", function (event) {
                                                                                    document.getElementById("frontDocPhoto").src = "<c:url value="/resources/img/loading.gif"/>";
                                                                                    var file = event.target.files[0];
                                                                                    var formData = new FormData();
                                                                                    formData.append("file", file);
                                                                                    formData.append("type", ${EImage.USER_DOC_FRONT.type});
                                                                                <c:if test="${not empty user.id}">
                                                                                    formData.append("id", <c:out value="${user.id}" />);
                                                                                </c:if>
                                                                                    $.ajax('<c:url value="/photo/upload"/>', {
                                                                                        method: "POST",
                                                                                        data: formData,
                                                                                        processData: false,
                                                                                        contentType: false,
                                                                                        success: function (response) {
                                                                                            document.getElementById("frontDocPhoto").src = "<c:url value="/photo/"/>" + response.image.src;
                                                                                            console.log(response);
                                                                                        },
                                                                                        error: function (response) {
                                                                                            console.log(response);
                                                                                        }
                                                                                    });
                                                                                });
                                                                            </script>
                                                                        </section>
                                                                        <h4></h4>
                                                                        <section class="doc-upload">
                                                                            <div class="row">
                                                                                <div class="col-md-12">                                
                                                                                    <div class="text-center mb-3">
                                                                                        <p>Pra finalizar, uma foto da sua habilitação</p> 
                                                                                    </div>  
                                                                                    <div class="form-group row">   
                                                                                        <c:set var="habDocPhoto"><c:url value="/resources/img/no-image.png"/></c:set>
                                                                                        <c:forEach items="${user.imageList}" var="image">
                                                                                            <c:if test="${image.type eq EImage.USER_DOC_HAB.type}">
                                                                                                <c:set var="habDocPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                                                                            </c:if>
                                                                                        </c:forEach>
                                                                                        <img src="${habDocPhoto}" id="habDocPhoto" class="rounded mx-auto d-block img-fluid">                                                    
                                                                                    </div>
                                                                                    <div class="form-group row">
                                                                                        <input type="file" class="form-control-file" name="habDoc" id="habDoc" accept="image/jpg,image/jpeg,image/png" required>                                    
                                                                                        <label for="habDoc">
                                                                                            <i class="fas fa-camera"></i>
                                                                                            <span>Escolha uma imagem</span>
                                                                                        </label>
                                                                                    </div>
                                                                                </div> 
                                                                            </div>
                                                                            <script>
                                                                                document.getElementById("habDoc").addEventListener("change", function (event) {
                                                                                    document.getElementById("habDocPhoto").src = "<c:url value="/resources/img/loading.gif"/>";
                                                                                    var file = event.target.files[0];
                                                                                    var formData = new FormData();
                                                                                    formData.append("file", file);
                                                                                    formData.append("type", ${EImage.USER_DOC_HAB.type});
                                                                                <c:if test="${not empty user.id}">
                                                                                    formData.append("id", <c:out value="${user.id}" />);
                                                                                </c:if>
                                                                                    $.ajax('<c:url value="/photo/upload"/>', {
                                                                                        method: "POST",
                                                                                        data: formData,
                                                                                        processData: false,
                                                                                        contentType: false,
                                                                                        success: function (response) {
                                                                                            document.getElementById("habDocPhoto").src = "<c:url value="/photo/"/>" + response.image.src;
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
                                    </div>
                                </div>
                                <div class="card-body">
                                    <form method="POST">
                                        <h6 class="heading-small text-muted mb-4">Informações do usuário</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label" for="name">Nome</label>
                                                        <input type="text" class="form-control form-control-alternative ${empty errors.name ? null : "is-invalid"}" id="name" name="name" placeholder="Nome" value="${user.name}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label" for="lastName">Sobrenome</label>
                                                        <input type="text" class="form-control form-control-alternative ${empty errors.lastName ? null : "is-invalid"}" id="lastName" name="lastName" placeholder="Sobrenome" value="${user.lastName}" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <label class="form-control-label" for="gender">Sexo</label>
                                                    <div class="row">
                                                        <div class="col-lg-6">
                                                            <div class="custom-control custom-radio mb-3">
                                                                <input  type="radio" class="custom-control-input ${empty errors.gender ? null : "is-invalid"}" name="gender" id="genderM" value="M" 
                                                                        <c:if test="${user.gender eq 'M'}">
                                                                            checked
                                                                        </c:if>>
                                                                <label class="custom-control-label" for="genderM">Masculino</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <div class="custom-control custom-radio mb-3">
                                                                <input type="radio" class="custom-control-input ${empty errors.gender ? null : "is-invalid"}" name="gender" id="genderF" value="F" 
                                                                       <c:if test="${user.gender eq 'F'}">
                                                                           checked
                                                                       </c:if>>
                                                                <label class="custom-control-label" for="genderF">Feminino</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="my-4" />
                                        <h6 class="heading-small text-muted mb-4">Informação de contato</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label" for="email">Email</label>
                                                        <input type="email" class="form-control form-control-alternative ${empty errors.email ? null : "is-invalid"}" id="email" name="email" placeholder="seu@email.com" value="${user.email}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label" for="phones">Telefone</label>
                                                        <div id="phoneList">
                                                            <c:forEach items="${user.userPhoneList}" var="phone" varStatus="loop">
                                                                <div class="phone-input input-group mb-3">
                                                                    <input type="text" class="form-control form-control-alternative ${empty errors.phoneList ? null : "is-invalid"}" name="userPhoneList[${loop.index}].number" placeholder="(99) 99999-9999" value="${phone.number}">
                                                                    <div class="input-group-append">
                                                                        <button class="btn btn-primary deletePhone">
                                                                            <i class="fas fa-minus"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                        <button id="addPhone" class="btn btn-primary"><i class="fas fa-plus"></i></button>
                                                        <script>
                                                            document.querySelectorAll("#phoneList .deletePhone").forEach(function (item, index) {
                                                                item.addEventListener("click", function (event) {
                                                                    event.preventDefault();
                                                                    this.parentElement.parentElement.remove();
                                                                    orderPhones();
                                                                });
                                                            });

                                                            function orderPhones() {
                                                                var phoneList = document.getElementById("phoneList");
                                                                var phoneListIndex = phoneList.querySelectorAll(".phone-input");
                                                                phoneListIndex.forEach(function (item, index) {
                                                                    item.querySelector("input[name$='number']").name = "userPhoneList[" + index + "].number";
                                                                });
                                                            }

                                                            document.getElementById("addPhone").addEventListener("click", function (event) {
                                                                event.preventDefault();

                                                                var phoneList = document.getElementById("phoneList");

                                                                var index = phoneList.querySelectorAll(".phone-input").length;

                                                                var phoneInput = document.createElement("div");
                                                                phoneInput.classList.add("phone-input");
                                                                phoneInput.classList.add("input-group");
                                                                phoneInput.classList.add("mb-3");

                                                                var number = document.createElement("input");
                                                                number.type = "text";
                                                                number.classList.add("form-control");
                                                                number.classList.add("form-control-alternative");
                                                                number.name = "userPhoneList[" + index + "].number";
                                                                number.placeholder = "(99) 99999-9999";
                                                                phoneInput.appendChild(number);

                                                                var inputGroup = document.createElement("div");
                                                                inputGroup.classList.add("input-group-append");

                                                                var deletePhoto = document.createElement("button");
                                                                deletePhoto.addEventListener("click", function (event) {
                                                                    event.preventDefault();
                                                                    this.parentElement.parentElement.remove();
                                                                    orderPhones();
                                                                });
                                                                deletePhoto.classList.add("btn");
                                                                deletePhoto.classList.add("btn-primary");
                                                                deletePhoto.classList.add("deletePhoto");

                                                                var icon = document.createElement("i");
                                                                icon.classList.add("fas");
                                                                icon.classList.add("fa-minus");

                                                                deletePhoto.appendChild(icon);
                                                                inputGroup.appendChild(deletePhoto);
                                                                phoneInput.appendChild(inputGroup);
                                                                phoneList.appendChild(phoneInput);
                                                            });
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="my-4" />
                                        <div class="pl-lg-4">
                                            <div class="row">    
                                                <div class="mb-3">
                                                    <button id="changePassword" class="btn btn-primary">Alterar Senha</button>
                                                </div>      
                                                <script>
                                                    document.getElementById("changePassword").addEventListener("click", function (event) {
                                                        event.preventDefault();
                                                        var c = document.getElementById("passwordContainer");
                                                        var x = document.getElementById("oldPassword");
                                                        var y = document.getElementById("password");
                                                        var z = document.getElementById("confirmPassword");
                                                        if (c.classList.contains("d-none")) {
                                                            c.classList.remove("d-none");
                                                            this.innerHTML = "Cancelar";
                                                            x.disabled = false;
                                                            y.disabled = false;
                                                            z.disabled = false;
                                                        } else {
                                                            c.classList.add("d-none");
                                                            this.innerHTML = "Alterar Senha";
                                                            x.disabled = true;
                                                            y.disabled = true;
                                                            z.disabled = true;
                                                        }
                                                    });
                                                </script>
                                            </div>
                                            <div id="passwordContainer" class="d-none">
                                                <div class="row">
                                                    <div class="col-md-6">                                            
                                                        <div class="form-group">
                                                            <label class="form-control-label" for="oldPassword">Senha Antiga</label>
                                                            <input type="password" class="form-control form-control-alternative ${empty errors.oldPassword ? null : "is-invalid"}" id="oldPassword" name="oldPassword" placeholder="Senha Antiga" required disabled>
                                                        </div>
                                                    </div>                                                
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">                                            
                                                        <div class="form-group">
                                                            <label class="form-control-label" for="password">Senha Nova</label>
                                                            <input type="password" class="form-control form-control-alternative ${empty errors.password ? null : "is-invalid"}" id="password" name="password" placeholder="Senha Nova" required disabled>
                                                        </div>
                                                    </div>                                                
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">                                            
                                                        <div class="form-group">
                                                            <label class="form-control-label" for="confirmPassword">Confirmar Senha Nova</label>  
                                                            <input type="password" class="form-control form-control-alternative ${empty errors.confirmPassword ? null : "is-invalid"}" id="confirmPassword" name="confirmPassword" placeholder="Confirmar Senha Nova" required disabled>
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
        <!-- JQuery Steps -->
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.steps.min.js"/>">
        </script>
        <script>
            $(function () {
                $("#wizard").steps({
                    headerTag: "h4",
                    bodyTag: "section",
                    transitionEffect: "fade",
                    transitionEffectSpeed: 500,
                    onStepChanging: function (event, currentIndex, newIndex) {
                        if (newIndex < currentIndex) {
                            return true;
                        } else {
                            var identity = document.getElementById("identity");
                            var backDoc = document.getElementById("backDoc");
                            var frontDoc = document.getElementById("frontDoc");
                            var habDoc = document.getElementById("habDoc");
                            switch (currentIndex) {
                                case 0:
                                    if (identity.value !== "") {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                    break;
                                case 1:
                                    var img = event.target.parentElement.parentElement.querySelector("img");
                                    var src = img.src;
                                    if (backDoc.files.length > 0 || src.indexOf("/photo/") >= 0) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                    break;
                                case 2:
                                    var img = event.target.parentElement.parentElement.querySelector("img");
                                    var src = img.src;
                                    if (frontDoc.files.length > 0 || src.indexOf("/photo/") >= 0) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                    break;
                                case 3:
                                    var img = event.target.parentElement.parentElement.querySelector("img");
                                    var src = img.src;
                                    if (habDoc.files.length > 0 || src.indexOf("/photo/") >= 0) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                    break;
                                default:
                                    return false;
                            }
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

            <c:set var="rating" value="0"></c:set>
            <c:set var="total" value="0"></c:set>
            <c:forEach items="${userLogged.ratingList}" var="r">
                <c:set var="rating" value="${rating + r.rating}"></c:set>
                <c:set var="total" value="${total + 1}"></c:set>
            </c:forEach>
            const starPercentage = (${rating / total} / 5) * 100;
            const starPercentageRounded = (Math.round(starPercentage / 10) * 10) + '%';
            document.querySelector('.stars-inner').style.width = starPercentageRounded;
        </script>
    </body>
</html>
