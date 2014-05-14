<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/base.css" media="all" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css" media="all" />
</head>
<body>
<div class="page">
    <header id="header">
        <hgroup class="white blank">
            <h1>Welcome To Google Admin</h1>
        </hgroup>
    </header>
    <section class="demo">
        <div class="content">
          <div class="form-wrapper">
              <div class="linker">
                  <span class="ring"></span>
                  <span class="ring"></span>
                  <span class="ring"></span>
                  <span class="ring"></span>
                  <span class="ring"></span>
              </div>
              <form class="login-form" action="<%=request.getContextPath() %>/admin/doLogin" method="POST">
                 <c:if test="${null != message && '' != message}">
                    <span id="message">${message}</span>
                  </c:if>
                  <input type="text" name="username" placeholder="username" />
                  <input type="password" name="password" placeholder="password" />
                  <button type="submit">Log in</button>
              </form>
          </div>
        </div>
    </section>
    <section id="ad_w3cplus">
        <div class="grid-ad">
        </div>
        <div class="grid-ad">
        </div>
        <div class="grid-ad">
        </div>
        <p></p>
    </section>
</div>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath() %>/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath() %>/js/prefixfree.min.js"></script>
</body>
</html>
