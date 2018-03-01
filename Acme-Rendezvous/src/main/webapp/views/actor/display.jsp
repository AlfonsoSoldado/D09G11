<%--
 * display.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="user/display.do" modelAttribute="user">

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.name" var="nameHeader" /> <jstl:out
			value="${nameHeader }" />:
	</span>
	<jstl:out value="${user.name }" />
</p>



<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.surname" var="surnameHeader" /> <jstl:out
			value="${surnameHeader }" />:
	</span>
	<jstl:out value="${user.surname }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.email" var="emailHeader" /> <jstl:out
			value="${emailHeader }" />:
	</span>
	<jstl:out value="${user.email }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.phoneNumber" var="phoneNumberHeader" /> <jstl:out
			value="${phoneNumberHeader }" />:
	</span>
	<jstl:out value="${user.phoneNumber }" />
</p>


<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.postalAddress" var="postalAddressHeader" /> <jstl:out
			value="${postalAddressHeader }" />:
	</span>
	<jstl:out value="${user.postalAddress }" />
</p>
</form:form>

<div>
	<a href="#" onClick="history.back();"> <spring:message
			code="actor.back" />
	</a>
</div>
