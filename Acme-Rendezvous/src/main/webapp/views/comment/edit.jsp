<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="comment/user/edit.do" modelAttribute="comment">
	<security:authorize access="hasRole('USER')">
	
		<form:hidden path="momentMade" />
		<form:hidden path="replies" />
		<form:hidden path="rendezvous"/>
		<form:hidden path="parent"/>
		<acme:textbox code="comment.text" path="text" />
		<acme:textbox code="comment.picture" path="picture" />

		<acme:submit name="save" code="comment.submit" />
		<acme:cancel url="comment/list.do" code="comment.cancel" />
	</security:authorize>

</form:form>
<form:form action="comment/administrator/edit.do" modelAttribute="comment">
	<security:authorize access="hasRole('ADMIN')">
	
		<form:hidden path="momentMade" />
		<form:hidden path="replies" />
		<form:hidden path="rendezvous"/>
		<form:hidden path="parent"/>
		<form:hidden path="id"/>
		<acme:textbox code="comment.text" path="text" readonly="true"/>
		<acme:textbox code="comment.picture" path="picture" readonly="true"/>
		
		<acme:cancel url="rendezvous/list.do" code="comment.cancel" />
		<acme:delete confirmationCode="comment.confirmationCode" buttonCode="comment.delete" id="${comment.id }" />
	</security:authorize>
</form:form>

