<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="${garage.addressId.number}, ${garage.addressId.street} - ${garage.addressId.city}"/>
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
                                                    <c:if test="${empty garage.imageList}">
                                                        <li data-target="#carousel" data-slide-to="0" class="active">
                                                        </li>
                                                    </c:if>
                                                    <c:forEach items="${garage.imageList}" var="image" varStatus="loop">
                                                        <li data-target="#carousel" data-slide-to="${loop.index}" ${loop.first ? 'class="active"' : ''}>
                                                        </li>
                                                    </c:forEach>
                                                </ol>
                                                <div class="carousel-inner">                                                
                                                    <c:if test="${empty garage.imageList}">
                                                        <div class="carousel-item active">
                                                            <img class="img-fluid" src="<c:url value="/resources/img/no-image.png"/>">
                                                        </div>
                                                    </c:if>
                                                    <c:forEach items="${garage.imageList}" var="image" varStatus="loop">
                                                        <div class="carousel-item ${loop.first ? 'active' : ''}">
                                                            <img class="img-fluid" src="<c:url value="/photo/${image.src}"/>">
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                                <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <form method="POST">
                                        <h6 class="heading-small text-muted mb-4">Informações da localização</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <div class="form-group">
                                                        <label for="street" class="form-control-label">Endereço</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${garage.addressId.street}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="form-group">
                                                        <label for="number" class="form-control-label">Número</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${garage.addressId.number}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="neighborhood" class="form-control-label">Bairro</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${garage.addressId.neighborhood}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="city" class="form-control-label">Cidade</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${garage.addressId.city}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="state" class="form-control-label">Estado</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${garage.addressId.state}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="zip" class="form-control-label">CEP</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${garage.addressId.zip}" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="my-4" />
                                        <h6 class="heading-small text-muted mb-4">Informações da garagem</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Proprietário</label>
                                                        <div class="input-group input-group-alternative">
                                                            <input type="text" class="form-control form-control-alternative" value="<c:out value="${garage.userId.name} ${garage.userId.lastName}"/>" disabled>
                                                            <div class="input-group-append">
                                                                <a class="btn btn-primary" href="<c:url value="/user/view-user/${garage.userId.id}"/>">Mais detalhes <i class="fas fa-plus"></i></a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-4">
                                                    <div class="form-group">
                                                        <label for="height" class="form-control-label">Altura</label>
                                                        <input type="number" class="form-control form-control-alternative" value="${garage.height}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4">
                                                    <div class="form-group">
                                                        <label for="width" class="form-control-label">Largura</label>
                                                        <input type="number" class="form-control form-control-alternative" value="${garage.width}" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4">
                                                    <div class="form-group">
                                                        <label for="length" class="form-control-label">Comprimento</label>
                                                        <input type="number" class="form-control form-control-alternative" value="${garage.length}" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-12 mb-3">
                                                    <div class="form-group">
                                                        <label for="access"class="form-control-label">Acesso</label>
                                                        <input type="text" class="form-control form-control-alternative" value="${garage.access}" disabled>
                                                    </div>
                                                </div>                            
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Cobertura</label>
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasRoof eq true}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label" for="roofY">Sim</label>
                                                                    </div>    
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasRoof eq false}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6 mb-3">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Câmera</label>
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasCam eq true}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label">Sim</label>
                                                                    </div>    
                                                                </div>
                                                            </div>                                                        
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasCam eq false}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6 mb-3">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Seguro</label>
                                                        <div class="row">                                                        
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasIndemnity eq true}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label">Sim</label>
                                                                    </div>   
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3"> 
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasIndemnity eq false}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6 mb-3">
                                                    <div class="form-group">
                                                        <label class="form-control-label">Portão Eletrônico</label>
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasElectronicGate eq true}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label">Sim</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="custom-control custom-radio mb-3">
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="custom-control-input" type="radio"
                                                                               <c:if test="${garage.hasElectronicGate eq false}">
                                                                                   checked
                                                                               </c:if> disabled>
                                                                        <label class="custom-control-label">Não</label>
                                                                    </div>                                       
                                                                </div>
                                                            </div>
                                                        </div>
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
    </body>
</html>
