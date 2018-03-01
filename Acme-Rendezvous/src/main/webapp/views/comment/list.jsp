<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('USER')">
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="comment" requestURI="${requestURI}" id="row">
	
	<display:column> <acme:links url="user/displayByComment.do?commentId=${row.id}" code="comment.user" /> </display:column>
	<acme:column property="text" code="comment.text"/>
	<acme:column property="momentMade" code="comment.momentMade"/>
	
	<spring:message code="comment.picture" var="picture"/>
	<display:column><img class="imagenesComment" src="${row.picture}"></display:column>
	
	<display:column> <acme:links url="comment/user/createReply.do?commentId=${row.id}" code="comment.createReply" /> </display:column>
	<display:column> <acme:links url="comment/user/listReplies.do?commentId=${row.id}" code="comment.reply" /> </display:column>
	
</display:table>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="comment" requestURI="${requestUri}" id="row">
	
	<acme:column property="text" code="comment.text"/>
	<acme:column property="momentMade" code="comment.momentMade"/>
	
	<spring:message code="comment.picture" var="picture"/>
	<display:column><img class="imagenesComment" src="${row.picture}"></display:column>
	
	<display:column> <acme:links url="comment/administrator/edit.do?commentId=${row.id}" code="comment.delete" /> </display:column>
	
</display:table>
</security:authorize>

<acme:links url="rendezvous/list.do" code="comment.back" />
	
	
