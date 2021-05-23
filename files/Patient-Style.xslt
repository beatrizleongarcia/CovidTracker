<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
<title>XHTML CSS Compliant Template by 3formed.co.uk</title>
<link rel="stylesheet" href="template_style.css" type="text/css" />
</head>
<body>
	<div id="menu">
	<a href="index.html">Home</a>
	<a href="index.html">News</a>
	<a href="index.html">Doctors</a>
	<a href="index.html">Patients</a>
	<a href="index.html">Help</a>
	</div>
<div id="wrapper">

	<div id="topbar">email: CovidTrackerHelp@covidtracker.com | tel: 901 244 597 | fax: 901 243 587</div>

	<div align="center"><img src="Covpat.png" width="700"></img> </div>

	<div id="content">
   <p><b>PATIENT'S INFORMATION:</b></p>
   <table border="1">
      <th>Name</th>
      <th>Date of Birth</th>
      <th>Salary</th>
       <th>Job title</th>
      <th>Economic impact</th>
      <th>Days off work</th>
          <xsl:for-each select="Patient">
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
   </div>

</div>

<div id="footer">
template design:<a href="http://www.3formed.co.uk">3Formed.co.uk</a> | <a href="http://validator.w3.org/check/referer">Valid XHTML</a> | <a href="http://jigsaw.w3.org/css-validator/check/referer/">Valid CSS</a> | <a href="index_nocss.html">TURN OFF CSS</a>
</div>

</body>
</html>
</xsl:template>

</xsl:stylesheet>