<%@ include file="includes/header.jsp" %>

<div class="animated bounceInDown" style="font-size:48pt; font-family:arial; color:#990000; font-weight:bold">Language Detection System</div>

</p>&nbsp;</p>&nbsp;</p>

<p><b>Language Detection:</b> Enter a string of text from a language and System will attempt to detect language</p>
<p><b>Language Existence:</b> Enter a language and system will return if it exists in the database</p>
<p><b>Database Languages:</b> Displays the list of all languages in the database - Text entered is irrelevant</p>
<p><b>Re-Populate Database:</b> Enter a URL to a file location and re-populate the database using that file</p>


<table width="600" cellspacing="0" cellpadding="7" border="0">
	<tr>
		<td valign="top">

			<form bgcolor="white" method="POST" action="doProcess">
				<fieldset>
					<legend><h3>Specify Details</h3></legend>
				
					<b>Select Option:</b>
						
					<select name="cmbOptions">
						<option selected>Language Detection</option>
						<option>Language Existence</option>
						<option>Database Languages</option>
						<option>Repopulate Database</option>
					</select>	
					<p/>
					

					<b>Enter Text :</b><br>
					<textarea name="query" rows="10" cols="100"  wrap="soft"></textarea>	
					<p/>

					<center><input type="submit" value="Process"></center>
				</fieldset>							
			</form>	

		</td>
	</tr>
</table>
<%@ include file="includes/footer.jsp" %>

