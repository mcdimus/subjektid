 User
<strong><%=session.getAttribute("username")%></strong>
logged in.
<form method="post" action="?action=logout">
	<button type="submit">Logout</button>
</form>
<br />
<ul>
	<li><a href="s">servlet</a></li>
	<li><a href="log.txt">Log.txt</a></li>
</ul>
<hr />