<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">

        <title>Step 1. Choose tariff</title>

        <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
        <link type="text/css" href="${contextPath}/res/css/style.css" rel="stylesheet">
    </head>
<body>

<div class="outer-wrapper">

    <jsp:include page="../fragments/header.jsp"/>

    <div class="container">

        <header>
            Basket
        </header>
        <p>
            Contract phone number: ${contract.number}
        </p>
        <p>
            Client balance: ${client.balance}
        </p>
    </div>

    <div class="container">
        <p>
            Step 1. Choose new tariff.
        </p>
        <br>
        <p>
            Available tariffs list:
            <c:choose>
            <c:when test="${tariffsList.size() != 0}">
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
            </tr>
            <c:forEach var="tariff" items="${tariffsList}">
                <tr>
                    <td>
                        <form:form id="chooseTariffForm${tariff.id}"
                                   method="POST"
                                   action="${contextPath}/operator/chooseTariff"
                                   enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="clientId" value=${client.id}>
                            <input type="hidden" name="contractId" value=${contract.id}>
                            <input type="hidden" name="tariffId" value=${tariff.id}>
                            <a href="#" onclick="document.forms['chooseTariffForm${tariff.id}'].submit()">${tariff.title}</a>
                        </form:form>
                    </td>
                    <td>
                            ${tariff.price}
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>
            empty.
            </p>
        </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>
</html>