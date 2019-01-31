<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Aluguel"/>
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
                                    <a href="<c:url value="${pageContext.request.getHeader('Referer')}"/>" class="text-light"><i class="fas fa-chevron-left"></i> Voltar para o Mapa</a>
                                </div>
                            </div>
                            <div class="card bg-secondary shadow">
                                <div class="card-header bg-white border-0">
                                    <div class="row align-items-center">
                                        <div class="col-md-4 offset-md-4 mb-3">
                                            <div id="carousel" class="carousel slide" data-ride="carousel">
                                                <ol class="carousel-indicators">
                                                    <c:if test="${empty advertisement.garageId.imageList}">
                                                        <li data-target="#carousel" data-slide-to="0" class="active">
                                                        </li>
                                                    </c:if>
                                                    <c:forEach items="${advertisement.garageId.imageList}" var="image" varStatus="loop">
                                                        <li data-target="#carousel" data-slide-to="${loop.index}" ${loop.first ? 'class="active"' : ''}>
                                                        </li>
                                                    </c:forEach>
                                                </ol>
                                                <div class="carousel-inner">                                     
                                                    <c:if test="${empty advertisement.garageId.imageList}">
                                                        <div class="carousel-item active">
                                                            <img class="img-fluid" src="<c:url value="/resources/img/no-image.png"/>">
                                                        </div>
                                                    </c:if>
                                                    <c:forEach items="${advertisement.garageId.imageList}" var="image" varStatus="loop">
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
                                    <div class="row align-items-center">
                                        <div class="col-sm-8">
                                            <h4 class="title">Alugar garagem</h4>
                                            <p class="category">Preencha os campos abaixo para alugar uma garagem</p>
                                        </div> 
                                    </div>
                                </div>
                                <div class="card-body">
                                    <form method="POST">
                                        <hr class="my-4" />
                                        <h6 class="heading-small text-muted mb-4">Informações do anúncio</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <a href="<c:url value="/garage/view-garage/${advertisement.garageId.id}"/>" class="btn btn-primary">Mais informações da garagem</a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Anunciante</label>
                                                        <div class="input-group input-group-alternative">
                                                            <input type="text" class="form-control form-control-alternative" value="<c:out value="${advertisement.garageId.userId.name} ${advertisement.garageId.userId.lastName}"/>" disabled>
                                                            <div class="input-group-append">
                                                                <a class="btn btn-primary" href="<c:url value="/user/view-user/${advertisement.garageId.userId.id}"/>">Mais detalhes <i class="fas fa-plus"></i></a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Título</label>
                                                        <input type="text" class="form-control form-control-alternative" value="<c:out value="${advertisement.title}"/>" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-2">
                                                    <div class="form-group">                          
                                                        <label class="form-control-label">Preço</label>   
                                                        <div class="input-group input-group-alternative">
                                                            <input type="text" class="form-control form-control-alternative" value="<c:out value="${advertisement.currency} ${advertisement.price}"/>" disabled>
                                                            <div class="input-group-append">
                                                                <span class="input-group-text">por hora</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>   
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Descrição</label>
                                                        <textarea class="form-control form-control-alternative" rows="3" disabled><c:out value="${advertisement.description}"/></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="vehicleId" class="form-control-label">Veículo</label>
                                                        <div class="input-group input-group-alternative">
                                                            <select class="form-control form-control-alternative ${empty errors.vehicle ? null : "is-invalid"}" id="vehicleId" name="vehicleId" required>
                                                                <option disabled selected value>Selecione um veículo</option>
                                                                <c:forEach items="${vehicleList}" var="vehicle">
                                                                    <option value="${vehicle.id}" ${empty errors.vehicle && rentGarage.vehicle == vehicle.id ? "selected": null}>
                                                                        ${vehicle.plate += ' - ' += vehicle.manufacturer += ', ' += vehicle.model}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>                                              
                                                            <div class="input-group-append">
                                                                <a href="<c:url value="/vehicle/new"/>" class="btn btn-primary"><i class="fas fa-plus"></i></a>
                                                            </div>
                                                        </div>               
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">         
                                                        <label for="dateTime" class="form-control-label">Data para alugar</label>
                                                        <div class="input-group input-group-alternative">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fas fa-calendar-alt"></i></span>
                                                            </div>     
                                                            <input type="datetime-local" class="form-control datetimepicker-input ${empty errors.dateTime ? null : "is-invalid"}" id="dateTime" name="dateTime" data-toggle="datetimepicker" data-target="#dateTime" value="${empty errors.dateTime ? rentGarage.dateTime : null}" required/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr class="my-4" />
                                            <div class="row justify-content-center">
                                                <button id="save" class="btn btn-primary btn-lg" type="submit">Salvar</button>
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
        <script type="text/javascript">
            $('.datetimepicker-input').datetimepicker({
                locale: moment.locale('pt-br'),
                minDate: moment().format("YYYY-MM-DDTHH:mm"),
                format: "YYYY-MM-DDTHH:mm",
                icons: {
                    time: "fas fa-clock"
                }
            });
        </script>
    </body>
</html>
