<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions"%>

<t:base>
    <jsp:attribute name="title">Meals Form</jsp:attribute>

    <jsp:body>
        <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
        <form method="POST" action="meals" accept-charset="UTF-8" class="border max-w-lg px-8 py-6 shadow-xl w-full">
            <input type="hidden" name="id" value="${meal.id}">

            <div class="md:flex md:items-center mb-6">
                <div class="md:w-1/3">
                    <label for="dateTime" class="block text-gray-500 md:text-right mb-1 md:mb-0 pr-4">Дата/Время</label>
                </div>
                <div class="md:w-2/3">
                    <input name="dateTime" type="datetime-local" class="bg-gray-100 appearance-none border border-gray-200 w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-500" id="dateTime" value="${meal.dateTime}">
                </div>
            </div>

            <div class="md:flex md:items-center mb-6">
                <div class="md:w-1/3">
                    <label for="description" class="block text-gray-500 md:text-right mb-1 md:mb-0 pr-4">Описание</label>
                </div>
                <div class="md:w-2/3">
                    <input name="description" type="text" class="bg-gray-100 appearance-none border border-gray-200 w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-500" id="description" placeholder="Описание" value="${meal.description}">
                </div>
            </div>

            <div class="md:flex md:items-center mb-6">
                <div class="md:w-1/3">
                    <label for="calories" class="block text-gray-500 md:text-right mb-1 md:mb-0 pr-4">Калории</label>
                </div>
                <div class="md:w-2/3">
                    <input name="calories" type="text" class="bg-gray-100 appearance-none border border-gray-200 w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-500" id="calories" placeholder="Калории" value="${meal.calories}">
                </div>
            </div>
            <div class="md:flex md:items-center md:justify-end">
                <a href="meals" class="mr-2 block bg-transparent hover:bg-gray-500 text-gray-700 font-semibold hover:text-white py-2 px-4 border border-gray-500 hover:border-transparent rounded">Отмена</a>
                <button type="submit" class="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded">Сохранить</button>
            </div>

        </form>
    </jsp:body>
</t:base>