<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.advertisementm.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Lista de Garagens"/>
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
                                    <h4 class="title">Anúncios Pendentes</h4>
                                    <p class="category">Lista de Anúncios que estão aguardando para serem autorizados.</p>
                                </div>
                                <div class="table-responsive">
                                    <table id="table" class="table align-items-center table-flush">
                                        <thead class="thead-light">
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">Cidade</th>
                                                <th scope="col">Estado</th>
                                                <th scope="col">Proprietário</th>
                                                <th scope="col"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${advertisementList}" var="advertisement">
                                                <tr>
                                                    <th scope="row">${advertisement.id}</th>
                                                    <td>${advertisement.addressId.city}</td>
                                                    <td>${advertisement.addressId.state}</td>
                                                    <td>${advertisement.userId.name}</td>
                                                    <td><a href="<c:url value="/detalhes/${advertisement.id}/advertisement"/>" class="btn btn-sm btn-primary btn-block">Analizar</a></td>
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