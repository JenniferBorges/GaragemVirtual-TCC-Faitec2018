<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Login"/>
        </jsp:include>  
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="container my-5">
                <div class="row justify-content-center align-self-center">
                    <div class="col-lg-10 col-md-8">
                        <div class="card bg-secondary shadow border-0">
                            <div class="card-body px-lg-5 py-lg-5">
                                <div class="text-muted mb-4">
                                    <small>Preencha os campos abaixo para logar-se</small>
                                </div>
                                <form method="POST">
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">
                                                    <i class="fa fa-envelope fa-fw"></i>
                                                </span>
                                            </div>
                                            <input type="email" name="email" id="email" class="form-control form-control-alternative" placeholder="Email" autofocus required/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">
                                                    <i class="fa fa-key fa-fw"></i>
                                                </span>
                                            </div>
                                            <input type="password" name="password" id="password" class="form-control form-control-alternative" placeholder="Senha" required/>
                                        </div>
                                    </div> 
                                    <div class="custom-control custom-control-alternative custom-checkbox">
                                        <input class="custom-control-input form-check-input" id="remember" type="checkbox" name="remember">
                                        <label class="custom-control-label" for="remember">
                                            <span class="text-muted">Mantenha-me conectado</span>
                                        </label>
                                    </div>
                                    <div class="text-center">
                                        <button class="btn btn-primary my-4" name="login" type="submit" title="Entrar">Entrar <i class="fa fa-sign-in-alt"></i></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-6">
                                <a href="<c:url value="/request-password"/>" class="text-light"><small>Esqueceu sua senha?</small></a>
                            </div>
                            <div class="col-6 text-right">
                                <a href="<c:url value="/register"/>" class="text-light"><small>Crie uma nova conta</small></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../common/wrapper.jsp"/>
    </body>
</html>
