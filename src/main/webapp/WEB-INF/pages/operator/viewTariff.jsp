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
    <title>Tariff</title>
</head>

<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>

    <div>

        <table class="table" style="font-weight: 700">
            <tr>
                <td style="padding: 10px 10px 10px 20px">
                    Tariff ID:
                </td>
                <td>
                    ${tariff.id}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px">
                    Title:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${tariff.title}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px">
                    Price:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${tariff.price}
                </td>
            </tr>
        </table>

    </div>

    <div>
        <form:form id="createNewOptionForm"
                   method="POST"
                   action="${contextPath}/operator/newOption"
                   enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="tariffId" value="${tariff.id}">
        </form:form>

        Available options list:

        <c:choose>
            <c:when test="${optionsList.size() != 0}">
                <a title="Create new option" href="#" onclick="document.forms['createNewOptionForm'].submit()">New</a>
                </p>
                <br>
                <table class="table">
                    <tr>
                        <th>
                            Option ID
                        </th>
                        <th>
                            Title
                        </th>
                        <th>
                            Price
                        </th>
                        <th>
                            Cost of connection
                        </th>
                        <th style="width: 0">
                        </th>
                    </tr>
                    <c:forEach var="option" items="${optionsList}">
                        <tr>
                            <td>

                                <form:form id="viewOptionForm${option.id}" method="POST"
                                           action="${contextPath}/operator/viewOption"
                                           enctype="application/x-www-form-urlencoded">
                                    <input type="hidden" name="optionId" value=${option.id}>
                                    <input type="hidden" name="tariffId" value=${tariff.id}>
                                    <a href="#" onclick="document.forms['viewOptionForm${option.id}'].submit()">${option.id}</a>
                                </form:form>

                            </td>
                            <td>
                                    ${option.title}
                            </td>
                            <td>
                                    ${option.price}
                            </td>
                            <td>
                                    ${option.costOfConnection}
                            </td>
                            <td style="width: 0">

                                <form:form id="deleteOptionForm${option.id}" method="POST"
                                           action="${contextPath}/operator/deleteOption"
                                           enctype="application/x-www-form-urlencoded">
                                    <input type="hidden" name="optionId" value=${option.id}>
                                    <input type="hidden" name="tariffId" value=${tariff.id}>
                                    <a title="Delete option" href="#" onclick="document.forms['deleteOptionForm${option.id}'].submit()">Delete</a>
                                </form:form>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                empty. <a title="Create new option" href="#" onclick="document.forms['createNewOptionForm'].submit()">Add new option</a>
            </c:otherwise>
        </c:choose>

    </div>

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>
</html>
