<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="true"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/res/css/style.css" rel="stylesheet">
    <title>Creating of tariff</title>
</head>

<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>

    <div>

        <form:form method="POST"
                   modelAttribute="newTariff"
                   class="form-signin text-center border border-light p-5"
                   action="${contextPath}/operator/createTariff"
                   enctype="application/x-www-form-urlencoded">
            <!-- title -->
            <spring:bind path="title">
                <form:label path="title">Title:</form:label>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="title" class="form-control"
                                placeholder="Tariff title"/>
                    <form:errors path="title"/>
                </div>
            </spring:bind>

            <!-- price -->
            <spring:bind path="price">
                <form:label path="price">Price:</form:label>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="price" class="form-control"
                                placeholder="Tariff price"/> *
                    <form:errors path="price"/>
                </div>
            </spring:bind>
            <button type="submit" class="btn btn-outline-primary btn-rounded waves-effect">Create</button>
            <a href="#" onclick="document.forms['viewAllTariffsForm'].submit()">Back</a>
        </form:form>

        <form id="viewAllTariffsForm" method="POST"
              action="${contextPath}/operator/viewAllTariffs"
              enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="sessionRole" value=${role}>
        </form>

    </div>

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>
</html>
