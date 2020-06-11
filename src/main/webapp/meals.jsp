<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions"%>

<t:base>
    <jsp:attribute name="title">Meals</jsp:attribute>

    <jsp:body>
        <table class="table-auto w-full">
            <jsp:useBean id="meals" scope="request" type="java.util.List"/>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
                <tr class="${meal.excess?'text-red-500':''}">
                    <td class="border px-4 py-2">${fn:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy hh:mm')}</td>
                    <td class="border px-4 py-2">${meal.description}</td>
                    <td class="border px-4 py-2">${meal.calories}</td>
                </tr>
            </c:forEach>
        </table>
    </jsp:body>
</t:base>