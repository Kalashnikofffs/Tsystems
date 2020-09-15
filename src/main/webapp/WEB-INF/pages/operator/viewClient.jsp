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
                           action="${contextPath}/operator/addAmountToBalance"
                           enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="clientId" value=${client.id}>
                    <input type="text" class="input-group form-control mb-4"
                           placeholder="Enter amount" name="amount">
            </td>
            <td>
                <button class="btn btn-outline-primary btn-rounded waves-effect"
                        type="submit">Replenish</button>
                </form:form>
            </td>
        </tr>
    </table>

    <form:form id="editProfileForm" method="POST"
               action="${contextPath}/operator/editProfile"
               enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="clientId" value="${client.id}">
    </form:form>

    <form:form id="createNewContractForm" method="POST"
               action="${contextPath}/operator/newContract"
               enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="clientId" value="${client.id}">
    </form:form>

    <div>
        <p>
            Contracts list:
            <c:choose>
            <c:when test="${contractsList.size() != 0}">
                <a title="Add new contract" href="#" onclick="document.forms['createNewContractForm'].submit()">Add new</a>
        </p>
        <br>
        <table class="table">
            <tr>
                <th>
                    Number
                </th>
                <th>
                    Tariff
                </th>
                <th>
                    Blocked by client
                </th>
                <th style="width: 170px">
                    Blocked by operator
                </th>
                <th style="width: 0">

                </th>
                <th style="width: 0">

                </th>
            </tr>
            <c:forEach var="contract" items="${contractsList}">
                <c:choose>
                    <c:when test="${contract.isBlockedByClient() || contract.isBlockedByOperator()}">
                        <tr style="background-color: rgba(255, 232, 232, 1);">
                    </c:when>
                    <c:otherwise>
                        <tr>
                    </c:otherwise>
                </c:choose>
                <td>

                    <form:form id="viewContractForm${contract.id}" method="POST"
                               action="${contextPath}/operator/viewContract"
                               enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="clientId" value=${client.id}>
                        <input type="hidden" name="contractId" value=${contract.id}>
                        <a href="#" onclick="document.forms['viewContractForm${contract.id}'].submit()">${contract.number}</a>
                    </form:form>

                </td>
                <td>
                        ${contract.tariff.title}
                </td>
                <td>
                    <c:choose>
                        <c:when test="${contract.isBlockedByClient()}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${contract.isBlockedByOperator()}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
                <td style="width: 0">
                    <c:choose>
                        <c:when test="${contract.isBlockedByOperator() == true}">

                            <form:form id="unblockByOperatorForm${contract.id}"
                                       method="POST"
                                       action="${contextPath}/operator/unblockByOperator"
                                       enctype="application/x-www-form-urlencoded">
                                <input type="hidden" name="contractId" value=${contract.id}>
                                <a title="Unblock contract" href="#" onclick="document.forms['unblockByOperatorForm${contract.id}'].submit()">Unblock</a>
                            </form:form>

                        </c:when>
                        <c:otherwise>

                            <form:form id="blockByOperatorForm${contract.id}"
                                       method="POST"
                                       action="${contextPath}/operator/blockByOperator"
                                       enctype="application/x-www-form-urlencoded">
                                <input type="hidden" name="contractId" value=${contract.id}>
                                <a title="Block contract" href="#" onclick="document.forms['blockByOperatorForm${contract.id}'].submit()">Block</a>
                            </form:form>

                        </c:otherwise>
                    </c:choose>

                        </td>

                        <td style="width: 0">

                        <form:form id="deleteContractForm${contract.id}" method="POST"
                                   action="${contextPath}/operator/deleteContractForClient"
                                   enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="contractId" value=${contract.id}>
                            <a title="Delete contract" href="#" onclick="document.forms['deleteContractForm${contract.id}'].submit()">Delete</a>
                        </form:form>
                </td>
                </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>
            empty.
                <a title="Add new contract" href="#" onclick="document.forms['createNewContractForm'].submit()">Add new</a>
            </p>
        </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>
</html>
