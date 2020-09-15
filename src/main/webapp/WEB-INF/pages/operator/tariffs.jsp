<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/res/css/style.css" rel="stylesheet">
    <title>List of all tariffs</title>
</head>

<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>

    <div>

        <form:form id="createNewTariffForm" method="POST"
                   action="${contextPath}/operator/newTariff"
                   enctype="application/x-www-form-urlencoded">
            Tariffs list:
            <a title="Create new tariff" href="#" onclick="document.forms['createNewTariffForm'].submit()">Add new</a>
        </form:form>

        <c:choose>
            <c:when test="${tariffsList.size() != 0}">
                </p>
                <br>
                <table class="table">
                    <tr>
                        <th>
                            Tariff ID
                        </th>
                        <th>
                            Title
                        </th>
                        <th>
                            Price
                        </th>
                        <th style="width: 0">

                        </th>
                    </tr>
                    <c:forEach var="tariff" items="${tariffsList}">
                        <tr>
                            <td>

                                <form:form id="viewTariffForm${tariff.id}" method="POST"
                                           action="${contextPath}/operator/viewTariff"
                                           enctype="application/x-www-form-urlencoded">
                                    <input type="hidden" name="tariffId" value=${tariff.id}>
                                    <a href="#" onclick="document.forms['viewTariffForm${tariff.id}'].submit()">${tariff.id}</a>
                                </form:form>

                            </td>
                            <td>
                                    ${tariff.title}
                            </td>
                            <td>
                                    ${tariff.price}
                            </td>
                            <td style="width: 0">

                                <form:form id="deleteTariffForm${tariff.id}" method="POST"
                                           action="${contextPath}/operator/deleteTariff"
                                           enctype="application/x-www-form-urlencoded">
                                    <input type="hidden" name="tariffId" value=${tariff.id}>
                                    <a title="Delete tariff" href="#" onclick="document.forms['deleteTariffForm${tariff.id}'].submit()">Delete</a>
                                </form:form>
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

