<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
  <p><b>Doctor's name: </b><xsl:value-of select="//name" /></p>
   <p><b>Hospital: </b><xsl:value-of select="//hospital" /></p>
   <p><b>Patients:</b></p>
   <table border="1">
      <th>Name</th>
      <th>Date of Birth</th>
      <th>Salary</th>
       <th>Job title</th>
      <th>Economic impact</th>
      <th>Days off work</th>
      <xsl:for-each select="Doctor/patient/Patient">
            <tr>
            <td><i><xsl:value-of select="@name" /></i></td>
            <td><xsl:value-of select="dob" /></td>
            <td><xsl:value-of select="salary" /></td>
            <td><xsl:value-of select="job_title" /></td>
            <td><xsl:value-of select="economic_impact" /></td>
            <td><xsl:value-of select="days_off_work" /></td>
            </tr>
         
      </xsl:for-each>
   </table>
   </html>
</xsl:template>

</xsl:stylesheet>