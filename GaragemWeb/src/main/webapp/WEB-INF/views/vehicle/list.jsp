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
            <jsp:param name="title" value="Meus Veículos"/>
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
                                    <h3 class="mb-3">Meus Veículos</h3>
                                    <a href="<c:url value="/vehicle/new"/>" class="btn btn-primary"><i class="fas fa-plus"></i> Novo</a>
                                </div>
                                <div class="table-responsive">
                                    <table id="table" class="table align-items-center table-flush">
                                        <thead class="thead-light">
                                            <tr>                
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
                                                    <th scope="row">
                                                        <div class="media align-items-center">
                                                            <div class="media-body">
                                                                <span class="mb-0 text-sm"><c:out value="${vehicle.plate}"/></span>
                                                            </div>
                                                        </div>
                                                    </th>
                                                    <td>
                                                        <c:out value="${vehicle.manufacturer}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${vehicle.model}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${vehicle.year}"/>
                                                    </td>
                                                    <td class="text-right">
                                                        <div class="dropdown">
                                                            <a href="#" class="btn btn-sm btn-icon-only text-light" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                <i class="fas fa-ellipsis-v"></i>
                                                            </a>
                                                            <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
                                                                <a href="<c:url value="/vehicle/edit/${vehicle.id}"/>" class="dropdown-item"><i class="fas fa-edit"></i> Editar</a>
                                                                <a href="<c:url value="/vehicle/delete/${vehicle.id}"/>" class="dropdown-item"><i class="fas fa-times"></i> Apagar</a>
                                                            </div>
                                                        </div>
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
