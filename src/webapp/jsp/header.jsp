<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Milionerzy</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Milionerzy</a>
        </div>
        <c:if test="${not empty login}">
            <ul class="nav navbar-nav">
                <li><a href="/continue">Kontynuuuj</a></li>
                <li><a href="/best">Najlepsze wyniki</a></li>
            </ul>
            <div class="navbar-header" style="float: right">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/game">Nowa gra</a>
                </div>
            </div>
            <div class="navbar-header" style="float: right">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/logout">Logout</a>
                </div>
            </div>
        </c:if>
        <c:if test="${empty login}">
            <ul class="nav navbar-nav">
                <li><a href="/login">Login</a></li>
            </ul>
            <ul class="nav navbar-nav" style="float: right">
                <li><a href="/register">Register</a></li>
            </ul>
        </c:if>
    </div>
</nav>