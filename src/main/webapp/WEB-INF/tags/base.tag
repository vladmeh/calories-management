<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Simple Wrapper Tag" pageEncoding="UTF-8" %>
<%@ attribute name="title" fragment="true" %>
<html>
<head>
    <title><jsp:invoke fragment="title"/></title>
    <link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
<nav class="flex items-center justify-between flex-wrap bg-teal-500 p-6 shadow-lg">
    <div class="flex items-center flex-shrink-0 text-white mr-6">
        <a href="<c:url value="/"/>">Java Enterprise (Topjava)</a>
    </div>
    <div class="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
        <div class="text-sm lg:flex-grow">
            <a class="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4" href="users">Users</a>
            <a class="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4" href="meals">Meals</a>
        </div>
    </div>
</nav>
<div class="container mx-auto py-6">
    <h2 class="text-4xl"><jsp:invoke fragment="title"/></h2>
    <hr class="my-3">
    <jsp:doBody/>
</div>
</body>
</html>