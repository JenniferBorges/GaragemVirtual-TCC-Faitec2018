<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ci" tagdir="/WEB-INF/tags/common-imports" %>
<%@ taglib prefix="mtm" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Chat"/>
        </jsp:include>  
        <link rel="stylesheet" href="<c:url value="/resources/css/chat.css"/>">
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="container-fluid clearfix">
                <div class="row row-eq-height">
                    <div class="col-lg-3 people-list" id="people-list">
                        <div class="search">
                            <div class="form-group">
                                <div class="input-group mt-4 mb-4">
                                    <input class="form-control" placeholder="Procurar" type="text">
                                    <div class="input-group-append">
                                        <span class="input-group-text"><i class="fas fa-search"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <ul class="list">
                            <c:if test="${empty receivedRentGarageUserList and empty requestRentGarageUserList}">
                                <li id="no-items-found">
                                    Nenhum contato encontrado!
                                </li>
                            </c:if>
                            <c:if test="${not empty receivedRentGarageUserList}">
                                <c:forEach items="${receivedRentGarageUserList}" var="rentGarage">
                                    <c:set var="user" value="${rentGarage.vehicleId.userId}"/>
                                    <li class="clearfix" data-userid="${user.id}" data-rentgarageid="${rentGarage.id}">
                                        <c:set var="userPhoto"><c:url value="/resources/img/no-image-profile.png"/></c:set>
                                        <c:forEach items="${user.imageList}" var="image">
                                            <c:if test="${image.type eq EImage.USER_PHOTO.type}">
                                                <c:set var="userPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                            </c:if>
                                        </c:forEach>
                                        <img class="img-fluid rounded-circle" src="${userPhoto}" alt="avatar" />
                                        <div class="about">
                                            <div class="name"><c:out value="${user.name}"/></div>
                                            <!--<div class="status">
                                                <i class="fa fa-circle online"></i> online
                                            </div>-->
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${not empty requestRentGarageUserList}">
                                <c:forEach items="${requestRentGarageUserList}" var="rentGarage">
                                    <c:set var="user" value="${rentGarage.advertisementId.garageId.userId}"/>
                                    <li class="clearfix" data-userid="${user.id}" data-rentgarageid="${rentGarage.id}">
                                        <c:set var="userPhoto"><c:url value="/resources/img/no-image-profile.png"/></c:set>
                                        <c:forEach items="${user.imageList}" var="image">
                                            <c:if test="${image.type eq EImage.USER_PHOTO.type}">
                                                <c:set var="userPhoto"><c:url value="/photo/${image.src}"/></c:set>
                                            </c:if>
                                        </c:forEach>
                                        <img class="img-fluid rounded-circle" src="${userPhoto}" alt="avatar" />
                                        <div class="about">
                                            <div class="name"><c:out value="${user.name}"/></div>
                                            <!--<div class="status">
                                                <i class="fa fa-circle online"></i> online
                                            </div>-->
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                    <div class="chat col-lg-9">
                        <div class="chat-header clearfix d-none">
                            <img class="img-fluid rounded-circle" src="" alt="avatar" />
                            <div class="chat-about" data-userId="" data-rentGarageId="">
                                <div class="chat-with"></div>
                                <!--<i class="fa fa-circle offline"></i>-->
                            </div>
                        </div>
                        <!-- end chat-header -->
                        <div class="chat-history">
                            <ul>
                            </ul>
                        </div>
                        <!-- end chat-history -->
                        <div class="chat-footer clearfix d-none">
                            <div class="chat-message">
                                <div class="input-group">
                                    <textarea class="form-control" name="message-to-send" id="message-to-send" placeholder="Type your message"></textarea>
                                    <div class="input-group-append">
                                        <button class="btn btn-primary float-right"><i class="fas fa-paper-plane"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- end chat-message -->
                    </div>
                    <!-- end chat -->
                </div>
            </div>
        </div>
        <!-- end container -->
        <script id="message-template" type="text/x-handlebars-template">
            <li class="clearfix">
            <div class="message-data text-right">
            <span class="message-data-time" >{{time}}, {{day}}</span> &nbsp; &nbsp;
            <span class="message-data-name" >Eu</span> <i class="fa fa-circle me"></i>
            </div>
            <div class="message other-message float-right">
            {{message}}
            </div>
            </li>
        </script>
        <script id="message-response-template" type="text/x-handlebars-template">
            <li>
            <div class="message-data">
            <span class="message-data-name"><i class="fa fa-circle online"></i> {{user}}</span>
            <span class="message-data-time">{{time}}, {{day}}</span>
            </div>
            <div class="message my-message">
            {{message}}
            </div>
            </li>
        </script>
        <jsp:include page="../common/wrapper.jsp"/>
        <script src="<c:url value="/resources/js/list.min.js"/>"></script>
        <script src="<c:url value="/resources/js/handlebars.min.js"/>"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function (event) {
                var chat = {
                    message: '',
                    init: function () {
                        this.cacheDOM();
                        this.bindEvents();
                        this.render();
                    },
                    cacheDOM: function () {
                        this.$chatHeader = $('.chat-header');
                        this.$chatInfo = this.$chatHeader.find('.chat-about');
                        this.$chatHistory = $('.chat-history');
                        this.$button = $('button');
                        this.$textarea = $('#message-to-send');
                        this.$chatHistoryList = this.$chatHistory.find('ul');
                    },
                    bindEvents: function () {
                        this.$button.on('click', this.addMessage.bind(this));
                        this.$textarea.on('keyup', this.addMessageEnter.bind(this));
                    },
                    render: function () {
                        this.scrollToBottom();
                        if (this.message.trim() !== '') {
                            var template = Handlebars.compile($("#message-template").html());
                            var context = {
                                message: this.message,
                                time: this.getCurrentTime(),
                                day: "Hoje"
                            };
                            this.$chatHistoryList.append(template(context));
                            this.scrollToBottom();
                            this.$textarea.val('');
                        }
                    },
                    addMessage: function () {
                        this.message = this.$textarea.val();
                        $.ajax('<c:url value="/chat/messages"/>', {
                            method: "POST",
                            data: {
                                userToId: this.$chatInfo.data("userid"),
                                rentGarageId: this.$chatInfo.data("rentgarageid"),
                                text: this.$textarea.val()
                            },
                            success: function (response) {
                                console.log(response);
                            },
                            error: function (response) {
                                console.log(response);
                            }
                        });
                        this.render();
                    },
                    addMessageEnter: function (event) {
                        // enter was pressed
                        if (event.keyCode === 13) {
                            this.addMessage();
                        }
                    },
                    scrollToBottom: function () {
                        this.$chatHistory.scrollTop(this.$chatHistory[0].scrollHeight);
                    },
                    getCurrentTime: function () {
                        return new Date().toLocaleTimeString().
                                replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
                    },
                    getRandomItem: function (arr) {
                        return arr[Math.floor(Math.random() * arr.length)];
                    }};
                chat.init();
                var searchFilter = {
                    options: {valueNames: ['name']},
                    init: function () {
                        var userList = new List('people-list', this.options);
                        var noItems = $('<li id="no-items-found">Nenhum contato encontrado!</li>');
                        userList.on('updated', function (list) {
                            if (list.matchingItems.length === 0) {
                                $(list.list).append(noItems);
                            } else {
                                noItems.detach();
                            }
                        });
                    }};
                searchFilter.init();

                var id = <c:out value="${param.id}" default="null" />;

                if (id !== null) {
                    var user = document.querySelector(".list > li[data-rentgarageid='" + id + "']");
                    user.dispatchEvent(new Event("click"));
                    console.log(user);
                }
            });

            function getDisplayDate(millis) {
                var today = new Date();
                today.setHours(0);
                today.setMinutes(0);
                today.setSeconds(0);
                today.setMilliseconds(0);
                var compDate = new Date(millis);
                compDate.setHours(0);
                compDate.setMinutes(0);
                compDate.setSeconds(0);
                compDate.setMilliseconds(0);
                var diff = today.getTime() - compDate.getTime();
                if (compDate.getTime() === today.getTime()) {
                    return "Hoje";
                } else if (diff <= (24 * 60 * 60 * 1000)) {
                    return "Ontem";
                } else {
                    return compDate.toDateString(); // or format it what ever way you want
                }
            }

            document.querySelectorAll(".list > li").forEach(function (item, index) {
                item.addEventListener("click", function (event) {
                    event.preventDefault();
                    var element = event.target;
                    if (!element.classList.contains("selected")) {
                        var chatHistory = document.querySelector(".chat-history > ul");
                        document.querySelectorAll(".list > li").forEach(function (item, index) {
                            item.classList.remove("selected");
                        });
                        var name = element.querySelector(".about > .name").innerHTML;
                        var img = element.querySelector("img").src;
                        var header = document.querySelector(".chat-header");
                        var footer = document.querySelector(".chat-footer");
                        var chatInfo = header.querySelector(".chat-about");

                        element.classList.add("selected");
                        chatHistory.innerHTML = "";
                        header.classList.remove("d-none");
                        footer.classList.remove("d-none");
                        header.querySelector("img").src = img;
                        chatInfo.dataset.userid = element.dataset.userid;
                        chatInfo.dataset.rentgarageid = element.dataset.rentgarageid;
                        chatInfo.querySelector(".chat-with").innerHTML = name;

                        $.ajax('<c:url value="/chat/messages"/>', {
                            method: "GET",
                            data: {
                                rentGarageId: element.dataset.rentgarageid
                            },
                            success: function (response) {
                                for (var item in response) {
                                    var template;
                                    var user;
                                    if (response[item].userFromId.id === ${userLogged.id}) {
                                        template = Handlebars.compile($("#message-template").html());
                                        user = response[item].userToId.name;
                                    } else {
                                        template = Handlebars.compile($("#message-response-template").html());
                                        user = response[item].userFromId.name;
                                    }
                                    var context = {
                                        message: response[item].message,
                                        time: new Date(response[item].dateTime).toLocaleTimeString().
                                                replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3"),
                                        day: getDisplayDate(response[item].dateTime),
                                        user: user
                                    };
                                    chatHistory.innerHTML += template(context);
                                }
                                chatHistory.parentNode.scrollTop = chatHistory.scrollHeight;
                            },
                            error: function (response) {
                                console.log(response);
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>