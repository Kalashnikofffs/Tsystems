<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Show Contract</title>

    <link href="${contextPath}/res/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="outer-wrapper container">
    <jsp:include page="../fragments/header.jsp"/>
    <div align="center">
        <h2>Contract Info</h2>
    </div>
    <table class="table">
        <tr>
            <td>ID</td>
            <td>${contract.id}</td>
        </tr>
        <tr>
            <td>Number</td>
            <td>${contract.number}</td>
        </tr>
        <tr>
            <td>BlockedByClient</td>
            <td>
                <c:choose>
                    <c:when test="${contract.isBlockedByClient()}">Yes</c:when>
                    <c:otherwise>No</c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>BlockedByOperator</td>
            <td>
                <c:choose>
                    <c:when test="${contract.isBlockedByOperator()}">Yes</c:when>
                    <c:otherwise>No</c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>

    <p><h2>Connected options</h2>

    <c:choose>
        <c:when test="${optionsList.size() != 0}">
            <c:if test="${contract.isBlockedByOperator() == false && contract.isBlockedByClient() == false && contract.client.balance > 0}">
                <a title="Change tariff or options" href="#" onclick="document.forms['changeTariffForm'].submit()"><h2>Edit</h2></a>
            </c:if>
            </p>
            <br>
            <table class="table">
                <tr>
                    <th>
                        Title
                    </th>
                    <th>
                        Price
                    </th>
                    <th>
                        Cost of connection
                    </th>
                </tr>
                <c:forEach var="dependentOption" items="${optionsList}">
                    <tr>
                        <td>
                                ${dependentOption.title}
                        </td>
                        <td>
                                ${dependentOption.price}
                        </td>
                        <td>
                                ${dependentOption.costOfConnection}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h2>empty.</h2>
            <c:if test="${contract.isBlockedByOperator() == false && contract.isBlockedByClient() == false && contract.client.balance > 0}">
                <a title="Change tariff or options" href="#" onclick="document.forms['changeTariffForm'].submit()"><h2>Edit</h2></a>
            </c:if>
            </p>
        </c:otherwise>
    </c:choose>

    <form:form id="changeTariffForm" method="POST"
               action="${contextPath}/operator/changeTariff"
               enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="clientId" value=${client.id}>
        <input type="hidden" name="contractId" value=${contract.id}>
    </form:form>
    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>
</html>