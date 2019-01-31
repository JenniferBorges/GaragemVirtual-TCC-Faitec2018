<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<%@page import="com.academico.garagem.model.constant.Constants"%>
<%@page import="com.academico.garagem.model.enumeration.EDays"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Minhas Solicitações"/>
        </jsp:include>  
        <style>
            .rating {
                unicode-bidi: bidi-override;
                direction: rtl;
            }
            .rating:not(:checked) > input {
                position:absolute;
                top:-9999px;
                clip:rect(0,0,0,0);
            }
            .rating:not(:checked) > label {
                padding:0 .1em;
                overflow:hidden;
                white-space:nowrap;
                cursor:pointer;
                color:#ddd;
            }
            .rating > input:checked ~ label {
                color: #6E0080;
            }
            .rating:not(:checked) > label:hover,
            .rating:not(:checked) > label:hover ~ label {
                color: #AC00EE;
            }
            .rating > input:checked + label:hover,
            .rating > input:checked + label:hover ~ label,
            .rating > input:checked ~ label:hover,
            .rating > input:checked ~ label:hover ~ label,
            .rating > label:hover ~ input:checked ~ label {
                color: #DA7AFF;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="main-content">  
                <div class="container-fluid my-5">
                    <div class="row">
                        <div class="col">
                            <div class="card shadow">
                                <div class="card-header border-0">
                                    <h3 class="mb-0">Minhas Solicitações</h3>
                                </div>
                                <div class="table-responsive">
                                    <table id="table" class="table align-items-center table-flush">
                                        <thead class="thead-light">
                                            <tr>                
                                                <th scope="col">Data</th> 
                                                <th scope="col">Preço</th>
                                                <th scope="col">Anúncio</th>
                                                <th scope="col">Veículo</th>
                                                <th scope="col">Status</th>
                                                <th scope="col"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${rentGarageList}" var="rentGarage" varStatus="loop">
                                                <c:set var="time" value="0"/>
                                                <c:if test="${not empty rentGarage.finalDateTime}">
                                                    <c:set var="time"><fmt:formatNumber value="${(((rentGarage.finalDateTime.time - rentGarage.initialDateTime.time) / 1000) / 60)}" type="number" pattern="#"/></c:set>
                                                </c:if>
                                                <tr>
                                                    <th scope="row">
                                                        <div class="media align-items-center">
                                                            <div class="media-body">
                                                                <span class="mb-0 text-sm"><fmt:formatDate value="${rentGarage.dateTime}" type="date" pattern="dd/MM/yyyy HH:mm"/></span>
                                                            </div>
                                                        </div>
                                                    </th>
                                                    <td>
                                                        <c:out value="${rentGarage.advertisementId.currency} ${time * rentGarage.advertisementId.price}"/>
                                                    </td>
                                                    <td>
                                                        <a href="<c:url value="/rent-garage/${rentGarage.advertisementId.id}"/>"><c:out value="${rentGarage.advertisementId.title}"/></a>
                                                    </td>
                                                    <td>
                                                        <a href="<c:url value="/vehicle/view-vehicle/${rentGarage.vehicleId.id}"/>"><c:out value="${rentGarage.vehicleId.plate}"/></a>
                                                    </td>
                                                    <td>
                                                        <span class="badge badge-dot mr-4">
                                                            <c:if test="${empty rentGarage.isAuth}">
                                                                <i class="bg-warning"></i> Aguardando
                                                            </c:if>
                                                            <c:if test="${not empty rentGarage.isAuth}">
                                                                <c:if test="${rentGarage.isAuth}">
                                                                    <c:if test="${empty rentGarage.initialDateTime}">
                                                                        <i class="bg-success"></i> Autorizado
                                                                    </c:if>
                                                                    <c:if test="${not empty rentGarage.initialDateTime and empty rentGarage.finalDateTime}">
                                                                        <i class="bg-info"></i> Na Garagem
                                                                    </c:if>
                                                                    <c:if test="${not empty rentGarage.finalDateTime}">
                                                                        <i class="bg-danger"></i> Finalizado
                                                                    </c:if>
                                                                </c:if>
                                                                <c:if test="${not rentGarage.isAuth}">
                                                                    <i class="bg-danger"></i> Recusado
                                                                </c:if>
                                                            </c:if>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <c:if test="${rentGarage.advertisementId.garageId.userId eq userLogged}">
                                                            <c:if test="${rentGarage.messageList.size() > 0}">
                                                                <a href="<c:url value="/chat/received?id=${rentGarage.id}"/>" class="btn btn-default btn-sm"><i class="fas fa-envelope"></i> Enviar Mensagem</a>
                                                            </c:if>
                                                            <c:if test="${empty rentGarage.isAuth or rentGarage.isAuth and empty rentGarage.finalDateTime}">
                                                                <div class="text-right">
                                                                    <div class="dropdown">
                                                                        <a href="#" class="btn btn-sm btn-icon-only text-light" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                            <i class="fas fa-ellipsis-v"></i>
                                                                        </a>
                                                                        <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
                                                                            <c:if test="${empty rentGarage.isAuth}">
                                                                                <a href="<c:url value="/rent-garage/confirm/${rentGarage.id}"/>" class="dropdown-item"><i class="fas fa-check"></i> Confirmar</a>  
                                                                                <a href="<c:url value="/rent-garage/cancel/${rentGarage.id}"/>" class="dropdown-item"><i class="fas fa-times"></i> Recusar</a>           
                                                                            </c:if>
                                                                            <c:if test="${rentGarage.isAuth}">
                                                                                <c:if test="${empty rentGarage.initialDateTime}">
                                                                                    <a href="<c:url value="/rent-garage/start/${rentGarage.id}"/>" class="dropdown-item"><i class="fas fa-play"></i> Iniciar cobrança</a>
                                                                                </c:if>         
                                                                                <c:if test="${not empty rentGarage.initialDateTime and empty rentGarage.finalDateTime}">
                                                                                    <a href="<c:url value="/rent-garage/finish/${rentGarage.id}"/>" class="dropdown-item"><i class="fas fa-pause"></i> Finilizar cobrança</a>   
                                                                                </c:if>
                                                                            </c:if>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${rentGarage.vehicleId.userId eq userLogged}">
                                                            <c:if test="${empty rentGarage.isAuth}">
                                                                <div class="text-right">
                                                                    <div class="dropdown">
                                                                        <a href="#" class="btn btn-sm btn-icon-only text-light" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                            <i class="fas fa-ellipsis-v"></i>
                                                                        </a>
                                                                        <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
                                                                            <c:if test="${not rentGarage.isAuth}">                                     
                                                                                <a href="<c:url value="/rent-garage/cancel/${rentGarage.id}"/>" class="dropdown-item"><i class="fas fa-times"></i> Cancelar</a>                                           
                                                                            </c:if>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${rentGarage.isAuth and empty rentGarage.finalDateTime}">
                                                                <a href="<c:url value="/chat/request?id=${rentGarage.id}"/>" class="btn btn-default btn-sm"><i class="fas fa-envelope"></i> Enviar Mensagem</a>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${rentGarage.isAuth and not empty rentGarage.finalDateTime}">
                                                            <c:set var="showRating" value="true"></c:set>
                                                            <c:forEach items="${rentGarage.ratingList}" var="rating">
                                                                <c:if test="${rating.user eq userLogged}">
                                                                    <div class="stars-outer">
                                                                        <div class="stars-inner" style="width:${(rating.rating / 5) * 100}%"></div>
                                                                    </div>
                                                                    <c:set var="showRating" value="false"></c:set>
                                                                </c:if>
                                                            </c:forEach>
                                                            <c:if test="${showRating}">
                                                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#rating-${loop.index}">
                                                                    <i class="fas fa-star-half-alt"></i> Avaliar
                                                                </button>     
                                                                <div class="modal fade" id="rating-${loop.index}" tabindex="-1" role="dialog" aria-labelledby="ratingLabel-${loop.index}" aria-hidden="true">
                                                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                                                        <div class="modal-content">
                                                                            <form method="POST" action="<c:url value="/rent-garage/rating/${rentGarage.id}"/>">
                                                                                <div class="modal-header">
                                                                                    <h2 class="modal-title" id="ratingLabel-${loop.index}">Avalie sua experiência</h2>
                                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                        <span aria-hidden="true">&times;</span>
                                                                                    </button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <div class="row">
                                                                                        <div class="col-lg-12">
                                                                                            <div class="form-group">
                                                                                                <label class="form-control-label">Nota</label>
                                                                                                <div class="rating text-center">
                                                                                                    <input type="radio" id="star5" name="rating" value="5" /><label for="star5"><i class="fas fa-star fa-2x"></i></label>
                                                                                                    <input type="radio" id="star4" name="rating" value="4" /><label for="star4"><i class="fas fa-star fa-2x"></i></label>
                                                                                                    <input type="radio" id="star3" name="rating" value="3" /><label for="star3"><i class="fas fa-star fa-2x"></i></label>
                                                                                                    <input type="radio" id="star2" name="rating" value="2" /><label for="star2"><i class="fas fa-star fa-2x"></i></label>
                                                                                                    <input type="radio" id="star1" name="rating" value="1" /><label for="star1"><i class="fas fa-star fa-2x"></i></label>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="row">
                                                                                        <div class="col-lg-12">
                                                                                            <div class="form-group">
                                                                                                <label for="title" class="form-control-label">Mensagem</label>
                                                                                                <textarea type="text" class="form-control form-control-alternative" id="message" name="message" placeholder="Deixe sua mensagem de recomendação" rows="3" ></textarea>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                                                                                    <button type="submit" class="btn btn-primary">Enviar</button>
                                                                                </div>
                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:if>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
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
