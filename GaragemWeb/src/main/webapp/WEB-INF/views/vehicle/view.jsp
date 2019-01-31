<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="${vehicle.plate}"/>
        </jsp:include>  
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="main-content">
                <div class="container-fluid my-5">
                    <div class="row">
                        <div class="col-xl-12 order-xl-1">
                            <div class="card bg-secondary shadow">
                                <div class="card-header bg-white border-0">
                                    <div class="row align-items-center">
                                        <div class="col-md-4 offset-md-4 mb-3">
                                            <div id="carousel" class="carousel slide" data-ride="carousel">
                                                <ol class="carousel-indicators">
                                                    <c:if test="${empty vehicle.imageList}">
                                                        <li data-target="#carousel" data-slide-to="0" class="active">
                                                        </li>
                                                    </c:if>
                                                    <c:forEach items="${vehicle.imageList}" var="image" varStatus="loop">
                                                        <li data-target="#carousel" data-slide-to="${loop.index}" ${loop.first ? 'active' : ''}}>
                                                        </li>
                                                    </c:forEach>
                                                </ol>
                                                <div class="carousel-inner">                                                
                                                    <c:if test="${empty vehicle.imageList}">
                                                        <div class="carousel-item active">
                                                            <img class="img-fluid" src="<c:url value="/resources/img/no-image.png"/>">
                                                        </div>
                                                    </c:if>
                                                    <c:forEach items="${vehicle.imageList}" var="image" varStatus="loop">
                                                        <div class="carousel-item ${loop.first ? 'active' : ''}">
                                                            <img class="img-fluid" src="<c:url value="/photo/${image.src}"/>">
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <a href="#carousel" class="carousel-control-prev" role="button" data-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                                <a href="#carousel" class="carousel-control-next" role="button" data-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <h6 class="heading-small text-muted mb-4">Informações do veículo</h6>
                                    <div class="pl-lg-4">
                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="form-group">
                                                    <label class="form-control-label">Proprietário</label>
                                                    <div class="input-group input-group-alternative">
                                                        <input type="text" class="form-control form-control-alternative" value="<c:out value="${vehicle.userId.name} ${vehicle.userId.lastName}"/>" disabled>
                                                        <div class="input-group-append">
                                                            <a href="<c:url value="/user/view-user/${vehicle.userId.id}"/>" class="btn btn-primary">Mais detalhes <i class="fas fa-plus"></i></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-3">
                                                <div class="form-group">
                                                    <label class="form-control-label">Placa</label>
                                                    <input type="text" class="form-control form-control-alternative" value="${vehicle.plate}" disabled>
                                                </div>
                                            </div>
                                            <div class="col-lg-3">
                                                <div class="form-group">
                                                    <label class="form-control-label">Tipo</label>
                                                    <input type="text" class="form-control form-control-alternative" value="${vehicle.type}" disabled>             
                                                </div>
                                            </div>
                                            <div class="col-lg-3">
                                                <div class="form-group">
                                                    <label class="form-control-label">Marca</label>
                                                    <input type="text" class="form-control form-control-alternative" value="${vehicle.manufacturer}" disabled>                           
                                                </div>
                                            </div>
                                            <div class="col-lg-3">
                                                <div class="form-group">
                                                    <label class="form-control-label">Modelo</label>
                                                    <input type="text" class="form-control form-control-alternative" value="${vehicle.model}" disabled>                           
                                                </div>
                                            </div>
                                            <div class="col-lg-3">
                                                <div class="form-group">
                                                    <label class="form-control-label">Ano</label>
                                                    <input type="text" class="form-control form-control-alternative" value="${vehicle.year}" disabled>                                        
                                                </div>
                                            </div>
                                            <div class="col-lg-3">
                                                <div class="form-group">
                                                    <label class="form-control-label">Cor</label>
                                                    <input type="text" class="form-control form-control-alternative" value="${vehicle.color}" disabled>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>  
                </div>
            </div>
        </div>
        <jsp:include page="../common/wrapper.jsp"/>
    </body>
</html>
