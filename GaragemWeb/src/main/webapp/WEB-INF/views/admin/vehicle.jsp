<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Lista de Veículos"/>
        </jsp:include>
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
                                    <h4 class="title">Veículos Pendentes</h4>
                                    <p class="category">Lista de Veículos que estão aguardando para receber o selo de Veículo Verificado.</p>
                                </div>
                                <div class="table-responsive">
                                    <table id="table" class="table align-items-center table-flush">
                                        <thead class="thead-light">
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">Placa</th>
                                                <th scope="col">Marca</th>
                                                <th scope="col">Modelo</th>
                                                <th scope="col">Ano</th>
                                                <th scope="col"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${vehicleList}" var="vehicle">
                                                <tr>
                                                    <th>${vehicle.id}</th>
                                                    <td>${vehicle.plate}</td>
                                                    <td>${vehicle.manufacturer}</td>
                                                    <td>${vehicle.model}</td>
                                                    <td>${vehicle.year}</td>
                                                    <td><a href="<c:url value="/detalhes/${vehicle.id}/vehicle"/>" class="btn btn-sm btn-primary btn-block">Verificar</a></td>
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