<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Create an account</title>

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/res/css/style.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <jsp:include page="fragments/header.jsp"/>
        <div class="row justify-content-center align-items-center">
<form:form class="border border-light p-5"
           method="POST"
           modelAttribute="newClient"
           style="width: 700px;">

    <p class="text-center h4 mb-4">Sign up</p>
    <div class="form-row">
        <div class="col">
            <!--Name-->
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="name">Name:</form:label>
                    <form:input type="text" path="name"
                                class="form-control"
                                placeholder="First name" autofocus="true"
                                aria-describedby="nameHelpInline"/>
                    <form:errors path="name" cssClass="error" element="div"/>
                    <c:choose>
                        <c:when test="${status.error}"/>
                        <c:otherwise>
                            <small id="nameHelpInline" class="text-muted">
                                Name must be up to 60 characters.
                            </small>
                        </c:otherwise>
                    </c:choose>
                </div>
            </spring:bind>
        </div>
        <div class="col">
            <!-- Surname -->
            <spring:bind path="surname">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="surname">Surname:</form:label>
                    <form:input type="text" path="surname"
                                class="form-control"
                                placeholder="Surname"
                                aria-describedby="surnameHelpInline"/>
                    <form:errors path="surname" cssClass="error" element="div"/>
                    <c:choose>
                        <c:when test="${status.error}"/>
                        <c:otherwise>
                            <small id="surnameHelpInline" class="text-muted">
                                Surname must be up to 60 characters.
                            </small>
                        </c:otherwise>
                    </c:choose>
                </div>
            </spring:bind>
        </div>
    </div>

    <div class="form-row">
        <div class="col">
            <!-- birthDate -->
            <spring:bind path="birthDate">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="birthDate">Birth date:</form:label>
                    <form:input type="text" path="birthDate"
                                class="form-control"
                                placeholder="Your bithdate"
                                aria-describedby="birthHelpInline"/>
                    <form:errors path="birthDate" cssClass="error" element="div"/>
                    <c:choose>
                        <c:when test="${status.error}"/>
                        <c:otherwise>
                            <small id="birthHelpInline" class="text-muted">
                                Birth date must be in format YYYY-mm-dd.
                            </small>
                        </c:otherwise>
                    </c:choose>
                </div>
            </spring:bind>
        </div>
        <div class="col">
            <!-- Passport -->
            <spring:bind path="passport">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="passport">Passport:</form:label>
                    <form:input type="text" path="passport"
                                class="form-control"
                                placeholder="Your passport"
                                aria-describedby="passportHelpInline"/>
                    <form:errors path="passport" cssClass="error" element="div"/>
                    <c:choose>
                        <c:when test="${status.error}"/>
                        <c:otherwise>
                            <small id="passportHelpInline" class="text-muted">
                                Passport must be in format 9417523265.
                            </small>
                        </c:otherwise>
                    </c:choose>
                </div>
            </spring:bind>
        </div>
    </div>
    <!-- Address -->
    <spring:bind path="address">
        <div class="form-group ${status.error ? 'has-error' : ''}">
        <form:label path="address">Address:</form:label>
            <form:input type="text" path="address"
                        class="form-control"
                        placeholder="Your address"
                        aria-describedby="addressHelpInline"/>
            <form:errors path="address" cssClass="error" element="div"/>
        </div>
        <c:choose>
            <c:when test="${status.error}"/>
            <c:otherwise>
                <small id="addressHelpInline" class="text-muted">
                    This field is required.
                </small>
            </c:otherwise>
        </c:choose>
    </spring:bind>

    <!-- E-mail -->
    <spring:bind path="email">
        <div class="form-group ${status.error ? 'has-error' : ''}">
        <form:label path="email">E-mail:</form:label>
            <form:input type="email" path="email"
                        class="form-control"
                        placeholder="E-mail"
                        aria-describedby="emailHelpInline"/>
            <form:errors path="email" cssClass="error" element="div"/>
            <c:choose>
                <c:when test="${status.error}"/>
                <c:otherwise>
                    <small id="emailHelpInline" class="text-muted">
                        This field is required.
                    </small>
                </c:otherwise>
            </c:choose>
        </div>
    </spring:bind>

    <!-- Password -->
    <spring:bind path="password">
        <div class="form-group ${status.error ? 'has-error' : ''}">
        <form:label path="password">Password:</form:label>
            <form:input type="password" path="password"
                        placeholder="Password"
                        class="form-control"
                        aria-describedby="passwordHelpInline"/>
            <form:errors path="password" cssClass="error" element="div"/>
            <c:choose>
                <c:when test="${status.error}"/>
                <c:otherwise>
                    <small id="passwordHelpInline" class="text-muted">
                        Your password must be 8-32 characters long.
                    </small>
                </c:otherwise>
            </c:choose>
        </div>
    </spring:bind>

    <!-- confirmPassword -->
    <spring:bind path="confirmPassword">
        <div class="form-group ${status.error ? 'has-error' : ''}">
        <form:label path="confirmPassword">Confirm password:</form:label>
            <form:input type="password"
                        path="confirmPassword"
                        class="form-control"
                        placeholder="Confirm your password"
                        aria-describedby="passwordConfirmHelpInline"/>
            <form:errors path="confirmPassword" cssClass="error" element="div"/>
            <c:choose>
                <c:when test="${status.error}"/>
                <c:otherwise>
                    <small id="passwordConfirmHelpInline" class="text-muted">
                        Passwords must match.
                    </small>
                </c:otherwise>
            </c:choose>
        </div>
    </spring:bind>

    <!-- Sign up button -->
    <button class="btn btn-primary my-4 btn-block" type="submit">Sign up</button>
</form:form>
        </div>
    <jsp:include page="fragments/footer.jsp"/>
    <!-- Default form register -->
    <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/res/js/bootstrap.js"></script>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</div>
</body>
</html>