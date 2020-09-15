<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Welcome</title>

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/res/css/style.css" rel="stylesheet">
</head>
<body>
<div class="outer-wrapper container">
    <jsp:include page="../fragments/header.jsp"/>
    <h3>Profile info of ${client.name}:
        <a href="#" onclick="document.forms['editProfileForm'].submit()">Edit</a></h3>
    <table class="table">
        <tr>
            <td>Full name</td>
            <td>${client.fullName}</td>
        </tr>
        <tr>
            <td>Birth Date</td>
            <td>${client.birthDate}</td>
        </tr>
        <tr>
            <td>Passport</td>
            <td>${client.passport}</td>
        </tr>
        <tr>
            <td>Address</td>
            <td>${client.address}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${client.email}</td>
        </tr>
        <tr>
            <td>Balance</td>
            <td>${client.balance}</td>
        </tr>
        <tr>
            <td>
                <form:form method="POST"
                           action="${contextPath}/client/addAmountToBalance"
                           enctype="application/x-www-form-urlencoded">
                <input type="number" class="input-group form-control mb-4" name="amount" value="0">
            </td>
            <td>
                <button class="btn btn-outline-primary btn-rounded waves-effect" type="submit">Replenish</button>
                </form:form>
            </td>
        </tr>
    </table>

    <form:form id="editProfileForm" method="POST"
               action="${contextPath}/client/editProfile"
               enctype="application/x-www-form-urlencoded">
    </form:form>

    <jsp:include page="../fragments/footer.jsp"/>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
</body>
</html>
