<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">
	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="category" requestURI="${requestURI}" id="row">

		<acme:column property="name" code="category.name" />
		<acme:column property="description" code="category.description" />
		<acme:column property="level" code="category.level" />
		<display:column>
			<acme:links url="category/administrator/edit.do?categoryId=${row.id }"
				code="category.edit" />
		</display:column>
	</display:table>
</security:authorize>


<acme:links url="category/administrator/create.do"
	code="category.create" />


