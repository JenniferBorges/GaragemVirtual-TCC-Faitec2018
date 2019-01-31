<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../../common/head.jsp" flush="true">
            <jsp:param name="title" value="Recuperar senha"/>
        </jsp:include>  
    </head>
    <body>
        <jsp:include page="../../common/nav.jsp"/>
        <div class="container mt-8 pb-5">
            <div class="row justify-content-center">
                <div class="col-lg-10 col-md-8">
                    <div class="card bg-secondary shadow border-0">
                        <div class="card-body px-lg-5 py-lg-5">
                            <div class="text-muted mb-4">
                                <small>Digite seu email abaixo para enviamos um link de recuperação de senha.</small>
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
                                <div class="text-center">
                                    <button class="btn btn-lg btn-primary" name="reset" type="submit">Recuperar Senha <i class="fas fa-key"></i></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../../common/wrapper.jsp"/>
    </body>
</html>