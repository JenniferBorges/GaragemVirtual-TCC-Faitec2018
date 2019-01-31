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
            <jsp:param name="title" value="Anúncio"/>
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
                                    <a href="<c:url value="/advertisement"/>" class="text-light"><i class="fas fa-chevron-left"></i> Meus Anúncios</a>
                                </div>
                            </div>
                            <div class="card bg-secondary shadow">
                                <div class="card-header bg-white border-0">
                                    <div class="row align-items-center">
                                        <div class="col-sm-8">
                                            <h4 class="title">Novo Anúncio</h4>
                                            <p class="category">Preencha os campos abaixo para cadastrar um novo anúncio</p>
                                        </div>     
                                    </div>
                                </div>
                                <div class="card-body">
                                    <form method="POST">
                                        <h6 class="heading-small text-muted mb-4">Informações do anúncio</h6>
                                        <div class="pl-lg-4">
                                            <div class="row">
                                                <label class="col-lg-2 form-control-label">Dias</label>
                                                <div class="col-lg-10">
                                                    <div id="disponibilityList">
                                                        <c:forEach items="${advertisement.disponibilityList}" var="disponibility" varStatus="loop">
                                                            <div class="row disponibility">
                                                                <div class="col-lg-3">
                                                                    <div class="form-group">
                                                                        <label class="form-control-label" for="day-${loop.index}">Dia</label>
                                                                        <select class="form-control form-control-alternative" id="day-${loop.index}" name="disponibilityList[${loop.index}].day" required>
                                                                            <option disabled selected value>Selecione um dia</option>
                                                                            <c:forEach items="${EDays.values()}" var="day">
                                                                                <option value="${day.value}"
                                                                                        <c:if test="${disponibility.day eq day.value}">
                                                                                            selected
                                                                                        </c:if>>${day.name}</option>
                                                                            </c:forEach>
                                                                        </select>      
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-3">
                                                                    <div class="form-group">
                                                                        <label class="form-control-label" for="startsAt-${loop.index}">Inicio</label>
                                                                        <input type="time" class="form-control form-control-alternative datetimepicker-input" id="startsAt-${loop.index}" data-toggle="datetimepicker" data-target="#startsAt-${loop.index}" name="disponibilityList[${loop.index}].startsAt" value="<fmt:formatDate value="${disponibility.startsAt}" pattern="HH:mm"/>" required>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-3">
                                                                    <div class="form-group">
                                                                        <label class="form-control-label" for="endsAt-${loop.index}">Fim</label>
                                                                        <input type="time" class="form-control form-control-alternative datetimepicker-input" id="endsAt-${loop.index}" data-toggle="datetimepicker" data-target="#endsAt-${loop.index}" name="disponibilityList[${loop.index}].endsAt" value="<fmt:formatDate value="${disponibility.endsAt}" pattern="HH:mm"/>" required>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-3">
                                                                    <div class="form-group mt-4">
                                                                        <button class="btn btn-primary deleteDisponibility"><i class="fas fa-minus"></i></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                    <button id="addDisponibility" class="btn btn-primary"><i class="fas fa-plus"></i></button>
                                                    <script>
                                                        document.querySelectorAll("#disponibilityList .deleteDisponibility").forEach(function (item, index) {
                                                            item.addEventListener("click", function (event) {
                                                                event.preventDefault();
                                                                this.parentElement.parentElement.parentElement.remove();
                                                                orderList();
                                                            });
                                                        });

                                                        function orderList() {
                                                            var disponibilityList = document.getElementById("disponibilityList");
                                                            var disponibilityListIndex = disponibilityList.querySelectorAll(".disponibility");
                                                            disponibilityListIndex.forEach(function (item, index) {
                                                                item.querySelector("select[name$='.day']").name = "disponibilityList[" + index + "].day";
                                                                item.querySelector("input[name$='.startsAt']").name = "disponibilityList[" + index + "].startsAt";
                                                                item.querySelector("input[name$='.endsAt']").name = "disponibilityList[" + index + "].endsAt";
                                                            });
                                                        }

                                                        document.getElementById("addDisponibility").addEventListener("click", function (event) {
                                                            event.preventDefault();

                                                            var disponibilityList = document.getElementById("disponibilityList");

                                                            var index = disponibilityList.querySelectorAll(".disponibility").length;

                                                            var disponibility = document.createElement("div");
                                                            disponibility.classList.add("row");
                                                            disponibility.classList.add("disponibility");

                                                            var divDay = document.createElement("div");
                                                            divDay.classList.add("col-lg-3");

                                                            var divFormDay = document.createElement("div");
                                                            divFormDay.classList.add("form-group");

                                                            var labelDay = document.createElement("label");
                                                            labelDay.classList.add("form-control-label");
                                                            labelDay.for = "day-" + index;
                                                            labelDay.innerHTML = "Dia";

                                                            var day = document.createElement("select");
                                                            day.classList.add("form-control");
                                                            day.classList.add("form-control-alternative");
                                                            day.id = "day-" + index;
                                                            day.name = "disponibilityList[" + index + "].day";
                                                            day.required = true;

                                                            var option = document.createElement("option");
                                                            option.disabled = true;
                                                            option.selected = true;
                                                            option.value = "";
                                                            option.innerHTML = "Selecione um dia";

                                                            day.appendChild(option);

                                                        <c:forEach items="${EDays.values()}" var="day" varStatus="loop">
                                                            var option${loop.index} = document.createElement("option");
                                                            option${loop.index}.value = "<c:out value="${day.value}"/>";
                                                            option${loop.index}.innerHTML = "<c:out value="${day.name}"/>";

                                                            day.appendChild(option${loop.index});
                                                        </c:forEach>

                                                            divFormDay.appendChild(labelDay);
                                                            divFormDay.appendChild(day);
                                                            divDay.appendChild(divFormDay);
                                                            disponibility.appendChild(divDay);

                                                            var divStartsAt = document.createElement("div");
                                                            divStartsAt.classList.add("col-lg-3");

                                                            var divFormStartsAt = document.createElement("div");
                                                            divFormStartsAt.classList.add("form-group");

                                                            var labelStartsAt = document.createElement("label");
                                                            labelStartsAt.classList.add("form-control-label");
                                                            labelStartsAt.for = "startsAt-" + index;
                                                            labelStartsAt.innerHTML = "Início";

                                                            var startsAt = document.createElement("input");
                                                            startsAt.type = "time";
                                                            startsAt.classList.add("form-control");
                                                            startsAt.classList.add("form-control-alternative");
                                                            startsAt.classList.add("datetimepicker-input");
                                                            startsAt.id = "startsAt-" + index;
                                                            startsAt.dataset.toggle = "datetimepicker";
                                                            startsAt.dataset.target = "#startsAt-" + index;
                                                            startsAt.name = "disponibilityList[" + index + "].startsAt";
                                                            startsAt.required = true;
                                                            
                                                            $(startsAt).datetimepicker({
                                                                locale: moment.locale('pt-br'),
                                                                format: "HH:mm",
                                                                icons: {
                                                                    time: "fas fa-clock"
                                                                }
                                                            });

                                                            divFormStartsAt.appendChild(labelStartsAt);
                                                            divFormStartsAt.appendChild(startsAt);
                                                            divStartsAt.appendChild(divFormStartsAt);
                                                            disponibility.appendChild(divStartsAt);

                                                            var divEndssAt = document.createElement("div");
                                                            divEndssAt.classList.add("col-lg-3");

                                                            var divFormEndsAt = document.createElement("div");
                                                            divFormEndsAt.classList.add("form-group");

                                                            var labelEndsAt = document.createElement("label");
                                                            labelEndsAt.classList.add("form-control-label");
                                                            labelEndsAt.for = "endsAt-" + index;
                                                            labelEndsAt.innerHTML = "Fim";

                                                            var endsAt = document.createElement("input");
                                                            endsAt.type = "time";
                                                            endsAt.classList.add("form-control");
                                                            endsAt.classList.add("form-control-alternative");
                                                            endsAt.classList.add("datetimepicker-input");
                                                            endsAt.id = "endsAt-" + index;
                                                            endsAt.dataset.toggle = "datetimepicker";
                                                            endsAt.dataset.target = "#endsAt-" + index;
                                                            endsAt.name = "disponibilityList[" + index + "].endsAt";
                                                            endsAt.required = true;
                                                            
                                                            $(endsAt).datetimepicker({
                                                                locale: moment.locale('pt-br'),
                                                                format: "HH:mm",
                                                                icons: {
                                                                    time: "fas fa-clock"
                                                                }
                                                            });
                                                            
                                                            divFormEndsAt.appendChild(labelEndsAt);
                                                            divFormEndsAt.appendChild(endsAt);
                                                            divEndssAt.appendChild(divFormEndsAt);
                                                            disponibility.appendChild(divEndssAt);

                                                            var divButton = document.createElement("div");
                                                            divButton.classList.add("col-lg-3");

                                                            var divFormButton = document.createElement("div");
                                                            divFormButton.classList.add("form-group");
                                                            divFormButton.classList.add("mt-4");

                                                            var button = document.createElement("button");
                                                            button.classList.add("btn");
                                                            button.classList.add("btn-primary");
                                                            button.classList.add("deleteDisponibility");
                                                            button.addEventListener("click", function (event) {
                                                                event.preventDefault();
                                                                this.parentElement.parentElement.parentElement.remove();
                                                                orderList();
                                                            });

                                                            var icon = document.createElement("i");
                                                            icon.classList.add("fas");
                                                            icon.classList.add("fa-minus");

                                                            button.appendChild(icon);
                                                            divFormButton.appendChild(button);
                                                            divButton.appendChild(divFormButton);
                                                            disponibility.appendChild(divButton);

                                                            disponibilityList.appendChild(disponibility);
                                                        });
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="title" class="form-control-label">Título</label>
                                                        <input type="text" class="form-control form-control-alternative" id="title" name="title" placeholder="Título" value="${advertisement.title}" required>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="price" class="form-control-label">Preço</label>
                                                        <div class="input-group input-group-alternative">
                                                            <div class="input-group-prepend">
                                                                <select class="form-control" id="currency" name="currency" required>
                                                                    <option value="R$" checked>R$</option>
                                                                    <option value="US$">US$</option>
                                                                </select>                                             
                                                            </div>
                                                            <input type="number" class="form-control" id="price" name="price" placeholder="Preço" value="${advertisement.price}" min="0" step="0.01" required>
                                                            <div class="input-group-append">
                                                                <span class="input-group-text">por hora</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="form-group">
                                                        <label for="title" class="form-control-label">Descrição</label>
                                                        <textarea type="text" class="form-control form-control-alternative" id="description" name="description" placeholder="Descrição" rows="3"  required><c:out value="${advertisement.description}" /></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="form-group">
                                                        <label for="garageId" class="form-control-label">Garagem</label>     
                                                        <div class="input-group input-group-alternative">
                                                            <select class="form-control form-control-alternative" id="garageId" name="garageId" required>
                                                                <option disabled selected value>Selecione uma garagem</option>
                                                                <c:forEach items="${garageList}" var="garage">
                                                                    <option value="${garage.id}"
                                                                            <c:if test="${garage.id eq advertisement.garageId.id}">
                                                                                selected
                                                                            </c:if>>
                                                                        <c:out value="${garage.addressId.number += ', ' += garage.addressId.street += ' - ' += garage.addressId.city}"/>
                                                                    </option>
                                                                </c:forEach>
                                                            </select>                                            
                                                            <div class="input-group-append">
                                                                <a href="<c:url value="/garage/new"/>" class="btn btn-primary"><i class="fas fa-plus"></i></a>
                                                            </div>
                                                        </div>               
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="my-4" />
                                        <div class="row justify-content-center">
                                            <button id="save" class="btn btn-primary btn-lg" type="submit">Salvar</button>
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
            (function () {
                document.querySelectorAll(".datetimepicker-input").forEach(function (item, index) {
                    var date = moment(item.value, 'HH:mm').toDate();
                    $(item).datetimepicker({
                        date: date,
                        locale: moment.locale('pt-br'),
                        format: "HH:mm",
                        icons: {
                            time: "fas fa-clock"
                        }
                    });
                });
            })();
        </script>
    </body>
</html>
