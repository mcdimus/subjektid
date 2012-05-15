<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="loginForm" scope="request"
	class="frontend.forms.LoginForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<title>Login</title>
</head>
<body>
	<%
		String username = "";
		String usernameError = "";
		String passwordError = "";
		String status = "WELCOME", errortxt = "";

		if (!errors.isEmpty()) {

			username = loginForm.getUsername();
			usernameError = errors.containsKey("username") ? (String) errors
					.get("username") : "";
			passwordError = errors.containsKey("password") ? (String) errors
					.get("password") : "";
			status = "ERROR";
			errortxt = errors.containsKey("reason") ? (String) errors
					.get("reason") : "";
		}
	%>
	<div class="indicator-line">
		<div class="<%=status%>"><%=status%>! <%=errortxt%></div>
	</div>
	<div id="login-form">
		<form method="post" action="?action=login">
			<table>
				<tr>
					<th>Username</th>
					<td><input type="text" name="username" value="<%=username%>" /></td>
				</tr>
				<tr>
					<td class="error" colspan="2"><%=usernameError%></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td class="error" colspan="2"><%=passwordError%></td>
				</tr>
				<tr>
					<td colspan="2">
						<button type="submit" name="LoginBtn">Login</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>