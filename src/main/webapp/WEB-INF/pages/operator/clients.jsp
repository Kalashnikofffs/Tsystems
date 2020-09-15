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
    <title>Operator's dashboard</title>
</head>
<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>
        <p>
            Clients list:
            <c:choose>
            <c:when test="${clientsList.size() != 0}">
        </p>
        <br>
        <table class="table">
            <tr>
                <th>
                    Client ID
                </th>
                <th>
                    Name
                </th>
                <th>
                    Passport
                </th>
                <th>
                    E-mail
                </th>
                <th style="width: 0">
                    <img src='${contextPath}/res/images/s.gif'>
                </th>
            </tr>
            <c:forEach var="client" items="${clientsList}">
                <tr>
                    <td>
                        <form:form id="viewClientForm${client.id}"
                                   method="POST"
                                   action="${contextPath}/operator/viewClient"
                                   enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="clientId" value=${client.id}>
                            <a href="#" onclick="document.forms['viewClientForm${client.id}'].submit()">${client.id}</a>
                        </form:form>

                    </td>
                    <td>
                            ${client.name}
                    </td>
                    <td>
                            ${client.passport}
                    </td>
                    <td>
                            ${client.email}
                    </td>
                    <td style="width: 0">

                        <form:form id="deleteClientForm${client.id}" method="POST"
                                   action="${contextPath}/operator/deleteClient"
                                   enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="clientId" value=${client.id}>
                            <a title="Delete client" href="#" onclick="document.forms['deleteClientForm${client.id}'].submit()">Delete</a>
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

    <jsp:include page="../fragments/footer.jsp"/>

</div>
</body>
</html>
