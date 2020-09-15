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

    <title>Edit option</title>
</head>

<body>

<div class="outer-wrapper container">

    <jsp:include page="../fragments/header.jsp"/>

    <div>

        <table style="font-weight: 700">
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

        <table style="font-weight: 700">
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

    </div>

    <div >

        <form:form id="editOptionForm" method="post" action="${contextPath}/operator/updateOption"
                   enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="optionId" value="${option.id}">
            <input type="hidden" name="tariffId" value="${tariff.id}">
            <p>
            Choose dependent options:
            <c:choose>
                <c:when test="${optionsList.size() != 0}">
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
                        <c:forEach var="dependentOption" items="${optionsList}">
                            <c:if test="${option.id != dependentOption.id}">
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
                                        <c:choose>
                                            <c:when test="${option.dependentOptions.contains(dependentOption)}">
                                                <input type="checkbox" id="box1${dependentOption.id}" name="dependentOptions" value="${dependentOption.id}" checked="checked">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="checkbox" id="box1${dependentOption.id}" name="dependentOptions" value="${dependentOption.id}">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td id="inc1${dependentOption.id}" style="display: none;">
                                        (incompatible)
                                    </td>

                                </tr>

                                <SCRIPT language="JavaScript">
                                    $(function(){
                                        $("#box1${dependentOption.id}").click(function(){

                                            if($("#box1${dependentOption.id}").is(":checked")) {

                                                $("#box2${dependentOption.id}").attr("disabled", true);
                                                $("#inc2${dependentOption.id}").attr("style", "width: 100px; background: rgba(216, 255, 213, 0.38); font-size: 12px; color: #008d47");

                                            } else {

                                                $("#box2${dependentOption.id}").removeAttr("disabled");
                                                $("#inc2${dependentOption.id}").attr("style", "display: none;");
                                            }

                                        });
                                    });
                                </SCRIPT>

                            </c:if>
                        </c:forEach>
                    </table>
                    <br>
                </c:when>
                <c:otherwise>
                    empty.
                    </p>
                    <br>
                </c:otherwise>
            </c:choose>

            <hr>

            <br>

            <p>
            Choose incompatible options:
            <c:choose>
                <c:when test="${optionsList.size() != 0}">
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
                        <c:forEach var="incompatibleOption" items="${optionsList}">
                            <c:if test="${option.id != incompatibleOption.id}">
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
                                        <c:choose>
                                            <c:when test="${option.incompatibleOptions.contains(incompatibleOption)}">
                                                <input type="checkbox" id="box2${incompatibleOption.id}" name="incompatibleOptions" value="${incompatibleOption.id}" checked="checked">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="checkbox" id="box2${incompatibleOption.id}" name="incompatibleOptions" value="${incompatibleOption.id}">
                                            </c:otherwise>
                                        </c:choose>

                                    </td>

                                    <td id="inc2${incompatibleOption.id}" style="display: none;">
                                        (dependent)
                                    </td>

                                    <SCRIPT language="JavaScript">

                                        if($("#box1${incompatibleOption.id}").is(":checked")) {

                                            $("#box2${incompatibleOption.id}").attr("disabled", true);
                                            $("#inc2${incompatibleOption.id}").attr("style", "width: 100; background: rgba(216, 255, 213, 0.38); font-size: 12px; color: #008d47");

                                        }

                                        if($("#box2${incompatibleOption.id}").is(":checked")) {

                                            $("#box1${incompatibleOption.id}").attr("disabled", true);
                                            $("#inc1${incompatibleOption.id}").attr("style", "width: 100; background: rgba(255, 232, 232, 0.52); font-size: 12px; color: #C90000");

                                        }

                                    </SCRIPT>

                                </tr>

                                <SCRIPT language="JavaScript">
                                    $(function(){
                                        $("#box2${incompatibleOption.id}").click(function(){

                                            if($("#box2${incompatibleOption.id}").is(":checked")) {

                                                $("#box1${incompatibleOption.id}").attr("disabled", true);
                                                $("#inc1${incompatibleOption.id}").attr("style", "width: 100; background: rgba(255, 232, 232, 0.52); font-size: 12px; color: #C90000");

                                            } else {

                                                $("#box1${incompatibleOption.id}").removeAttr("disabled");
                                                $("#inc1${incompatibleOption.id}").attr("style", "display: none;");
                                            }

                                        });
                                    });
                                </SCRIPT>

                            </c:if>
                        </c:forEach>
                    </table>
                    <br>
                </c:when>
                <c:otherwise>
                    empty.
                    </p>
                    <br>
                </c:otherwise>
            </c:choose>

            <button type="submit" class="btn btn-outline-dark">Save</button>
        </form:form>
    </div>

    <jsp:include page="../fragments/footer.jsp"/>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>

</div>

</body>
</html>
