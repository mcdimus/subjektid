<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<table class="results">
			<tr>
				<td colspan="5">
					Found
					<xsl:value-of select="subjects/@quantity" />
					subjects.
				</td>
			</tr>
			<tr>
				<th>#</th>
				<th>Subject name</th>
				<th>Subject type</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<xsl:for-each select="subjects/subject">
				<tr>
					<td>
						<xsl:value-of select="position()" />
					</td>
					<td>
						<xsl:value-of select="name" />
					</td>
					<td>
						<xsl:value-of select="type" />
					</td>
					<td>
						<xsl:element name="a">
							<xsl:attribute name="href">
								<xsl:text>?mode=subject&amp;action=edit_subject&amp;subject_id=</xsl:text>
								<xsl:value-of select="id" />
								<xsl:text>&amp;subject_type=</xsl:text>
						
								<xsl:choose>
								  <xsl:when test="type='person'">1 </xsl:when>
								  <xsl:otherwise>2</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
							Edit
						</xsl:element>
					</td>
					<td>
						<xsl:element name="a">
<!-- 							<xsl:attribute name="id"> -->
<!-- 								<xsl:text>delete_</xsl:text> -->
<!-- 								<xsl:value-of select="id" />								 -->
<!-- 								<xsl:choose> -->
<!-- 								  <xsl:when test="type='person'">_1</xsl:when> -->
<!-- 								  <xsl:otherwise>_2</xsl:otherwise> -->
<!-- 								</xsl:choose> -->
<!-- 							</xsl:attribute> -->
							<xsl:attribute name="href">
								<xsl:text>#<xsl:value-of select="position()" /></xsl:text>
							</xsl:attribute>
							<xsl:attribute name="name">
								<xsl:text>deleteSubject</xsl:text>
							</xsl:attribute>
							<xsl:attribute name="data-subjectId">
								<xsl:text><xsl:value-of select="id" /></xsl:text>
							</xsl:attribute>
							<xsl:attribute name="data-subjectType">
								<xsl:text>
								<xsl:choose>
								  <xsl:when test="type='person'">1</xsl:when>
								  <xsl:otherwise>2</xsl:otherwise>
								</xsl:choose>
								</xsl:text>
							</xsl:attribute>
							Delete
						</xsl:element>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

</xsl:stylesheet>