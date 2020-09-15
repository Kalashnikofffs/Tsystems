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
    <title>Option</title>
</head>

<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>

    <div>
        <table class="table" style="font-weight: 700">
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 200">
                    Tariff ID:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${tariff.id}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 200">
                    Tariff title:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${tariff.title}
                </td>
            </tr>
        </table>
        <br>
        <table class="table" style="font-weight: 700">
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 200">
                    Option ID:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${option.id}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 200">
                    Option title:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${option.title}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 200">
                    Option  price:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${option.price}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 200">
                    Cost of connection:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${option.costOfConnection}
                </td>
            </tr>
        </table>

        <form:form id="editOptionForm" method="POST"
                   action="${contextPath}/operator/editOption"
                   enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="optionId" value="${option.id}">
            <input type="hidden" name="tariffId" value="${tariff.id}">
        </form:form>

    </div>

    <div>
        <p>
            Dependent options list:
            <c:choose>
            <c:when test="${option.dependentOptions.size() != 0}">

            <a style="padding-right: 10px" title="Edit option dependencies" href="#" onclick="document.forms['editOptionForm'].submit()">Edit</a>
            <a title="Clear all dependencies" href="#" onclick="document.forms['deleteAllDependentOptionsForm'].submit()">Delete</a>

            <form:form id="deleteAllDependentOptionsForm" method="POST"
                       action="${contextPath}/operator/deleteAllDependentOptions"
                       enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="optionId" value="${option.id}">
                <input type="hidden" name="tariffId" value="${tariff.id}">
            </form:form>
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
            <c:forEach var="dependentOption" items="${option.dependentOptions}">
                <tr>
                    <td>
                            ${dependentOption.id}
                    </td>
                    <td>
                            ${dependentOption.title}
                    </td>
                    <td>
                            ${dependentOption.price}
                    </td>
                    <td>
                            ${dependentOption.costOfConnection}
                    </td>
                    <td style="width: 0">
                        <form:form id="deleteDependentOptionForm${dependentOption.id}" method="POST"
                                   action="${contextPath}/operator/deleteDependentOption"
                                   enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="optionId" value="${option.id}">
                            <input type="hidden" name="dependentOptionId" value="${dependentOption.id}">
                            <input type="hidden" name="tariffId" value="${tariff.id}">
                            <a title="Remove dependency" href="#" onclick="document.forms['deleteDependentOptionForm${dependentOption.id}'].submit()">Delete</a>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:when>
            <c:otherwise>
                empty. <a title="Edit option dependencies" href="#" onclick="document.forms['editOptionForm'].submit()">Edit</a>
            </c:otherwise>
        </c:choose>

    </div>

    <div>

        <p>
            Incompatible options list:
            <c:choose>
            <c:when test="${option.incompatibleOptions.size() != 0}">

            <a class="inline-link-edit" style="padding-right: 10px" title="Edit option dependencies" href="#" onclick="document.forms['editOptionForm'].submit()">Edit</a>
            <a class="inline-link-delete" title="Clear all incompatibilities" href="#" onclick="document.forms['deleteAllIncompatibleOptionsForm'].submit()">Delete</a>

            <form:form id="deleteAllIncompatibleOptionsForm" method="POST"
                       action="${contextPath}/operator/deleteAllIncompatibleOptions"
                       enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="optionId" value="${option.id}">
                <input type="hidden" name="tariffId" value="${tariff.id}">
            </form:form>
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
            <c:forEach var="incompatibleOption" items="${option.incompatibleOptions}">
                <tr>
                    <td>
                            ${incompatibleOption.id}
                    </td>
                    <td>
                            ${incompatibleOption.title}
                    </td>
                    <td>
                            ${incompatibleOption.price}
                    </td>
                    <td>
                            ${incompatibleOption.costOfConnection}
                    </td>
                    <td style="width: 0">
                        <form:form id="deleteIncompatibleOptionForm${incompatibleOption.id}" method="POST"
                                   action="${contextPath}/operator/deleteIncompatibleOption"
                                   enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="optionId" value="${option.id}">
                            <input type="hidden" name="incompatibleOptionId" value="${incompatibleOption.id}">
                            <input type="hidden" name="tariffId" value="${tariff.id}">
                            <a title="Remove incompatibility" href="#" onclick="document.forms['deleteIncompatibleOptionForm${incompatibleOption.id}'].submit()">Delete</a>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>
            empty. <a title="Edit option dependencies" href="#" onclick="document.forms['editOptionForm'].submit()">Edit</a>
        </c:otherwise>
        </c:choose>

    </div>

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>
</html>
