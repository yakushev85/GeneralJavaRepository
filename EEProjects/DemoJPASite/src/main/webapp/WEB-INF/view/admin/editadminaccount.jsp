<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Administrator page</title>
<link rel="stylesheet" type="text/css" href="<jstl:url value="/resources/admin.css"/>"/>
</head>
<body>

<div id="main_head">
<a href="#">Administrator page</a>
</div>

<div id="main_text">
<div id="main_text_left">
<div id="head_menu">
<ul>
<li><a href="showmenuitems.html">Menu items</a></li>
<li><a href="showmaterials.html">Materials</a></li>
<li><a href="editadminaccount.html">Edit admin account</a></li>
<li><a href="login.html">Logout</a></li>
<ul>
</div>
</div>

<div id="main_text_right">
<form method="post" action="saveadminaccount.html">
<p><jstl:out value="${erroradminaccount}"/></p>
<p>Administrator user name:<input type="text" value="${adminusername}" disabled size=60></p>
<p>Old administrator password:<input type="password" name="oldpassword" size=60></p>
<p>New administrator password:<input type="password" name="newpassword" size=60></p>
<p>Repeat new administrator password:<input type="password" name="repeatpassword" size=60></p>
<p><input type="submit" value="OK"> <input type="button" value="Cancel" onClick="location.href='showmenuitems.html'"></p>
</form>
</div>
</div>

<div id="end_text">
<p>Created by Alex@2014.</p>
</div>

</body>
</html>