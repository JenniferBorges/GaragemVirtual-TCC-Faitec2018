<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="${user.name} ${user.lastName}"/>
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
                                            <label for="userPhoto">      
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
                                                    <a href="#">
                                                        <span class="heading"><c:out value="${user.garageList.size()}"/></span>
                                                        <span class="description">Garagens</span>
                                                    </a>
                                                </div>
                                                <div>
                                                    <a href="#">
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
                                            <h4 class="title">Perfil</h4>
                                            <p class="category">Veja as informações do perfil</p>
                                        </div>                                       
                                    </div>
                                </div>
                                <div class="card-body">
                                    <form method="POST">
                                        <h6 class="heading-small text-muted mb-4">Informações do usuário</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Nome</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${user.name}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Sobrenome</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${user.lastName}" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <label class="form-control-label">Sexo</label>
                                                    <div class="row">
                                                        <div class="col-lg-6">
                                                            <div class="custom-control custom-radio mb-3">
                                                                <input class="custom-control-input" type="radio"
                                                                       <c:if test="${user.gender eq 'M'}">
                                                                           checked
                                                                       </c:if> disabled>
                                                                <label class="custom-control-label">Masculino</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <div class="custom-control custom-radio mb-3">
                                                                <input class="custom-control-input" type="radio"
                                                                       <c:if test="${user.gender eq 'F'}">
                                                                           checked
                                                                       </c:if> disabled>
                                                                <label class="custom-control-label">Feminino</label>
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
                                                        <label class="form-control-label">Email</label>
                                                        <input type="email" class="form-control form-control-alternative" value="${user.email}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label" for="phones">Telefone</label>
                                                        <c:if test="${empty user.userPhoneList}">
                                                            <input type="text" class="form-control form-control-alternative mb-3" value="Nenhum telefone cadastrado" disabled>
                                                        </c:if>
                                                        <c:forEach items="${user.userPhoneList}" var="phone">
                                                            <input type="text" class="form-control form-control-alternative mb-3" value="${phone.number}" disabled>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
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
            <c:set var="rating" value="0"></c:set>
            <c:set var="total" value="0"></c:set>
            <c:forEach items="${user.ratingList}" var="r">
                <c:set var="rating" value="${rating + r.rating}"></c:set>
                <c:set var="total" value="${total + 1}"></c:set>
            </c:forEach>
            const starPercentage = (${rating / total} / 5) * 100;
            const starPercentageRounded = (Math.round(starPercentage / 10) * 10) + '%';
            document.querySelector('.stars-inner').style.width = starPercentageRounded;
        </script>
    </body>
</html>
