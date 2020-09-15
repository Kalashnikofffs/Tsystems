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

        <div class="form-signup">
            <form:form method="POST"
                       modelAttribute="editClient"
                       action="${contextPath}/client/updateProfile"
                       class="text-center border border-light p-5"
                       enctype="application/x-www-form-urlencoded">
                <p class="h4 mb-4">Edit profile of ${client.fullName}</p>
                <div class="form-row mb-4">
                    <div class="col">
                        <!--Name-->
                        <spring:bind path="name">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:label path="name">Name:</form:label>
                                <form:input type="text" path="name"
                                            class="form-control"
                                            placeholder="First name" autofocus="true"
                                            value="${client.name}"
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
                                            value="${client.surname}"
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
                                            value="${client.birthDate}"
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
                                            value="${client.passport}"
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
                                    value="${client.address}"
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

                <button class="btn btn-outline-dark" type="submit">Save</button>
            </form:form>
        </div>

    <jsp:include page="../fragments/footer.jsp"/>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
</body>
</html>
