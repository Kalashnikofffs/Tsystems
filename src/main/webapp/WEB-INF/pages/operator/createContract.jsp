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
    <title>New contract</title>
</head>
<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>

    <div>

        <p>
            Creating of new contract for client: ${client.fullName}
        </p>
        <br>

        <form:form id="createNewContractForm" method="POST"
                   modelAttribute="newContract"
                   name="NewContractForm"
                   action="${contextPath}/operator/createContract"
                   enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="clientId" value="${client.id}">

            <!-- number -->
            <spring:bind path="number">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="number">Telephone number:</form:label>
                    <form:input type="text" path="number" class="form-control"
                                placeholder="Telephone number"
                                aria-describedby="numberHelpInline"/>
                    <form:errors path="number"/>
                    <c:choose>
                        <c:when test="${status.error}"/>
                        <c:otherwise>
                            <small id="nameHelpInline" class="text-muted">
                                Telephone number must be unique.
                            </small>
                        </c:otherwise>
                    </c:choose>
                </div>
            </spring:bind>

            <button type="submit" class="btn btn-outline-primary btn-rounded waves-effect">Create</button>

            <form:form id="viewClientForm" method="POST"
                       action="${contextPath}/operator/viewClient"
                       enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="clientId" value=${client.id}>
                <a href="#" onclick="document.forms['viewClientForm'].submit()">Back</a>
            </form:form>
        </form:form>

    </div>

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>
</html>
