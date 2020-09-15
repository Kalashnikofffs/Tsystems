<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${contextPath}/res/css/bootstrap.css">
    <link type="text/css" href="${contextPath}/res/css/style.css" rel="stylesheet">

</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand text-white" href="${contextPath}/welcome">eCare</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            aria-controls="navbarNav" aria-expanded="false">
        <span class="navbar-toggler-icon"></span>
    </button>
    <c:choose>
        <c:when test="${!isLoginOrRegistrationPage}">
        <div class="collapse navbar-collapse" id="navbarNav">

        <c:choose>
            <c:when test="${role == 'ROLE_USER'}">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" title="See your contracts" href="#"
                       onclick="document.forms['clientContractsForm'].submit()">Contracts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" title="Edit profile" href="#"
                       onclick="document.forms['profileForm'].submit()">${client.email}</a>
                </li>
            </ul>
            </c:when>
            <c:otherwise>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" title="See all clients" href="#"
                       onclick="document.forms['clientsForm'].submit()">Clients</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" title="See all tariffs" href="#"
                       onclick="document.forms['tariffForm'].submit()">Tariffs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" title="See all contracts" href="#"
                       onclick="document.forms['allContractsForm'].submit()">Contracts</a>
                </li>
            </ul>
            </c:otherwise>
        </c:choose>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <button class="btn btn-outline-light btn-rounded waves-effect" type="submit"
                            onclick="document.forms['logoutForm'].submit()">Logout</button>
                </li>
            </ul>


        </div>
        </c:when>
    </c:choose>
</nav>
<c:choose>
    <c:when test="${!isLoginOrRegistrationPage}">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <c:forEach var="page" items="${breadcrumb}">
                    <li class="breadcrumb-item"><a href="#">${page}</a></li>
                </c:forEach>
                <li class="breadcrumb-item active" aria-current="page">${currentPage}</li>
            </ol>
        </nav>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>

<form:form id="profileForm" method="POST"
           action="${contextPath}/client/profile"
           enctype="application/x-www-form-urlencoded">
</form:form>

<form:form id="clientContractsForm" method="POST"
           action="${contextPath}/client/viewAllContractsForClient"
           enctype="application/x-www-form-urlencoded">
</form:form>

<form:form id="allContractsForm" method="POST"
           action="${contextPath}/operator/viewAllContracts"
           enctype="application/x-www-form-urlencoded">
</form:form>

<form:form id="tariffForm" method="POST"
           action="${contextPath}/operator/viewAllTariffs"
           enctype="application/x-www-form-urlencoded">
</form:form>

<form:form id="clientsForm" method="POST"
           action="${contextPath}/operator/viewAllClients"
           enctype="application/x-www-form-urlencoded">
</form:form>

<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form:form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </c:if>
</div>

<c:if test="${errormessage != null}">
    <div class="alert alert-danger" role="alert">
        <p>
            Error: ${errormessage}
        </p>
    </div>

    <SCRIPT language="javascript">
        setInterval(function() {
            $("#error").attr("hidden", true);
        }, 5000);
    </SCRIPT>

</c:if>

<c:if test="${successmessage != null}">
    <div class="alert alert-success" role="alert">
        <p>
            Success: ${successmessage}
        </p>
    </div>

    <SCRIPT language="javascript">
        setInterval(function() {
            $("#success").attr("hidden", true);
        }, 5000);
    </SCRIPT>

</c:if>

    <script src="${contextPath}/res/js/bootstrap.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>
