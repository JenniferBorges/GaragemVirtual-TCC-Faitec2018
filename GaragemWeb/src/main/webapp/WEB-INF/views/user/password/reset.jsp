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
        <div class="container"> 
            <div class="row">                   
                <div class="col-md-12 order-md-1">
                    <h4 class="mb-3">Digite sua nova senha</h4>
                    <form role="form" method="POST" autocomplete="on">
                        <fieldset>
                            <div class="form-group">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="basic-addon1">
                                            <i class="fa fa-key fa-fw"></i>
                                        </span>
                                    </div>
                                    <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Nova Senha" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="basic-addon1">
                                            <i class="fa fa-key fa-fw"></i>
                                        </span>
                                    </div>
                                    <input type="password" name="confirmPassword" id="confirmPassword" class="form-control input-lg" placeholder="Confirme a nova senha" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-lg btn-primary btn-block" name="reset" type="submit">Recuperar Senha <i class="fas fa-key"></i></button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="../../common/wrapper.jsp"/>
    </body>
</html>