<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="RSVP/user/create.do" modelAttribute="RSVP">


	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="confirmed">
		<spring:message code="confirmed" />:
		</form:label>
	<form:select id="confirmed" path="confirmed">
		<form:options items="${confirmed}" />
	</form:select>
	<form:errors cssClass="error" path="confirmed" />
	<br />
	<br />
	
	<form:label path="rendezvous">
		<spring:message code="rendezvous" />:
	</form:label>
	<form:select id="rendezvouses" path="rendezvous">
		<form:options items="${rendezvouses}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="rendezvous" />
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="save" />" />
	
	<input type="button" name="cancel"
		value="<spring:message code="cancel" />"
		onclick="window.location='rendezvous/list.do';"/>
	<br />

</form:form>


