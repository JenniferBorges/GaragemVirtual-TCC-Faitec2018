<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- JQuery -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<!-- Popper -->
<script type="text/javascript" src="<c:url value="/resources/js/popper.min.js"/>"></script>
<!-- Bootstrap -->
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<!-- DataTables -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dataTables.bootstrap4.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dataTables.responsive.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/responsive.bootstrap4.min.js"/>"></script>
<!-- Moment -->
<script type="text/javascript" src="<c:url value="/resources/js/moment-with-locales.js"/>"></script>
<!-- Datepicker -->
<script type="text/javascript" src="<c:url value="/resources/js/tempusdominus-bootstrap-4.min.js"/>"></script>
<!-- Noty -->
<script type="text/javascript" src="<c:url value="/resources/js/noty.min.js"/>"></script>
<!-- Custom js -->
<script type="text/javascript">
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    function increaseZoom() {
        $('body').css({'zoom': parseFloat($('body').css('zoom')) + 0.1});
    }

    function decreaseZoom() {
        $('body').css({'zoom': parseFloat($('body').css('zoom')) - 0.1});
    }

    function generate(type, text, time) {
        var n = new Noty({
            text: text,
            type: type,
            progressBar: true,
            timeout: time,
            layout: 'topRight',
            closeWith: ['button', 'click'],
            theme: 'mint',
            maxVisible: 10,
            animation: {
                open: 'animated bounceInRight',
                close: 'animated bounceOutRight'
            }
        }).show();
        return n;
    }

    function loadScript(src) {
        return new Promise(function (resolve, reject) {
            var s;
            s = document.createElement('script');
            s.src = src;
            s.onload = resolve;
            s.onerror = reject;
            document.head.appendChild(s);
        });
    }
</script>
<script>
    document.addEventListener("DOMContentLoaded", function (event) {
        var table = $('#table');
        if (table.length !== 0) {
            table.DataTable({
                "language": {
                    "sEmptyTable": "Nenhum registro encontrado",
                    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
                    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sInfoThousands": ".",
                    "sLengthMenu": "_MENU_ resultados por página",
                    "sLoadingRecords": "Carregando...",
                    "sProcessing": "Processando...",
                    "sZeroRecords": "Nenhum registro encontrado",
                    "sSearch": "Pesquisar",
                    "oPaginate": {
                        "sNext": "<i class='fas fa-angle-right'></i>",
                        "sPrevious": "<i class='fas fa-angle-left'></i>",
                        "sFirst": "Primeiro",
                        "sLast": "Último"
                    },
                    "oAria": {
                        "sSortAscending": ": Ordenar colunas de forma ascendente",
                        "sSortDescending": ": Ordenar colunas de forma descendente"
                    }
                }
            });
        }

    <c:if test="${not empty notification}">
        var text = `${notification.text}`;
        generate("${notification.type}", "<div class='activity-item'><i class='glyphicon glyphicon-ok'></i><div class='activity'>" + text + "</div></div>", ${notification.time});
    </c:if>
    <c:forEach var="error" items="${errors}">
        var text = `${error.value}`;
        generate("error", "<div class='activity-item'><i class='glyphicon glyphicon-ok'></i><div class='activity'>" + text + "</div></div>", 2000);
    </c:forEach>
    });
</script>
<!-- //Custom js --> 