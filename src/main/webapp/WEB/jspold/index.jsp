<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login</title>

    <link rel="stylesheet" type="text/css" href="${contextPath}/CSS/login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>

<div id="card">
    <div id="card-content">
        <div id="card-title">
            <h2>LOGIN</h2>
            <br>
            <div class="underline-title"></div>
        </div>
    </div>

    <form:form  action="/cekMasuk" modelAttribute="admin" method="POST">

        <label for="Username" class="form-label">Username</label>
        <form:input cssClass="form-content" id="Username" path="username" type="text" placeholder="Username"/>
        <div class="form-border"></div>
        <label for="Password">Password</label>
        <form:input id="Password" cssClass="form-content" path="password" type="password" placeholder="Password"/>
        <div class="form-border"></div>
        <br>
        <form:button class="btn btn-primary" type="submit" id="save">Simpan</form:button>

    </form:form>
</div>

</body>
</html>