<%--
 * listByUser.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="question" requestURI="${requestUri}" id="row">
	
	<acme:column property="text" code="question.text"/>
	
	<display:column> <acme:links url="answer/list.do?questionId=${row.id }" code="question.answer"/> </display:column>
	
	<security:authorize access="hasRole('USER')">
	<display:column> <acme:links url="answer/user/create.do?questionId=${row.id}" code="question.answer.create" /> </display:column>
	</security:authorize>
	
</display:table>
	
	
