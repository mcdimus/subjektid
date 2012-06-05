<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<table class="results">
			<tr>
				<td colspan="4">
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
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

</xsl:stylesheet>