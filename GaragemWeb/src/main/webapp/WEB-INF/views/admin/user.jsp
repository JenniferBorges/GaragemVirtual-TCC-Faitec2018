<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Lista de Usuários"/>
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
                                    <h4 class="title">Usuários Pendentes</h4>
                                    <p class="category">Lista de usários aguardando para receber o selo de Usuário Verificado.</p>
                                </div>
                                <div class="table-responsive">
                                    <table id="table" class="table align-items-center table-flush">
                                        <thead class="thead-light">
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">Nome</th>
                                                <th scope="col">Sobrenome</th>
                                                <th scope="col">CPF</th>
                                                <th scope="col"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${userList}" var="user">
                                                <tr>
                                                    <th scope="row">${user.id}</th>
                                                    <td>${user.name}</td>
                                                    <td>${user.lastName}</td>
                                                    <td>${user.identity}</td>
                                                    <td><a href="<c:url value="/user/view-user/${user.id}"/>" class="btn btn-sm btn-primary btn-block">Ver Perfil</a></td>
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