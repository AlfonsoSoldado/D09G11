<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
 <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<spring:message var="nameProject" code="legal.nameProject" />
<spring:message var="cookies" code="cookies" />


<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> <jstl:out value="${nameProject }"/> </b>
<b> <jstl:out value="${cookies }" /> <a href="misc/legal.do"> <spring:message code="legal.terms"/></a> </b>