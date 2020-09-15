<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Log in</title>

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${contextPath}/res/css/style.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <jsp:include page="fragments/header.jsp"/>
    <div class="row justify-content-center align-items-center">
    <form class="text-left mx-5 my-5" style="width: 500px;" method="POST" action="${contextPath}/login">
            <!-- Email -->
        <label for="email">Email:</label>
            <input id="email" name="email" type="email" class="form-control mb-4" placeholder="E-mail" autofocus="true">
            <!-- Password -->
        <label for="password">Password:</label>
            <input id="password" name = "password" type="password" class="form-control mb-4" placeholder="Password">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <!-- Sign in button -->
            <button class="btn btn-outline-dark" type="submit">Sign in</button>
        <!-- Register -->
        <p class="text-center">Not a member?
            <a href="${contextPath}/registration">Register</a>
        </p>
    </form>
    </div>
    <jsp:include page="fragments/footer.jsp"/>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</body>
</html>