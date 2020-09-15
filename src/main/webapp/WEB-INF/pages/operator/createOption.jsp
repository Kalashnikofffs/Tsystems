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
    <title>Creating of option</title>
</head>

<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>

    <div>

        <form:form id="createOptionForm" method="POST"
                   modelAttribute="newOption"
                   action="${contextPath}/operator/createOption"
                   enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="tariffId" value="${tariff.id}">

            <!-- title -->
            <spring:bind path="title">
                <form:label path="title">Title:</form:label>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="title" class="form-control"
                                placeholder="Option title"/>
                    <form:errors path="title"/>
                </div>
            </spring:bind>

            <!-- price -->
            <spring:bind path="price">
                <form:label path="price">Price:</form:label>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="price" class="form-control"
                                placeholder="Option price"/>
                    <form:errors path="price"/>
                </div>
            </spring:bind>

            <!-- costOfConnection -->
            <spring:bind path="costOfConnection">
                <form:label path="costOfConnection">Cost of connection:</form:label>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="costOfConnection" class="form-control"
                                placeholder="Cost of connection"/>
                    <form:errors path="costOfConnection"/>
                </div>
            </spring:bind>

            <button type="submit" class="btn btn-outline-primary btn-rounded waves-effect">Create</button>

            <form:form id="viewTariffForm${tariff.id}" method="POST"
                       action="${contextPath}/operator/viewTariff"
                       enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="tariffId" value=${tariff.id}>
                <a href="#" onclick="document.forms['viewTariffForm${tariff.id}'].submit()">Back</a>
            </form:form>

        </form:form>


    </div>

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>
</html>
