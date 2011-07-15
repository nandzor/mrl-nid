var fields;
function setData(id)
{
	alert(id);
	//for(var i=id; i<(3 + id); i++)
	//	alert(document.getElementById(i).innerHTML);
}
function setDataColumn(id,totalColumn)
{
	document.getElementById("t0").disabled=true;
	// to convert the rowNo into an integer value
	var startOfRow = id - (id % totalColumn);
	var rowNo = (startOfRow) / totalColumn;
	rowNo++;
	alert("Row: " + rowNo);
	//alert(document.getElementById(startOfRow + 1).checked);
	var j=0;
	for(var i=startOfRow+2; i<=(totalColumn + startOfRow); i++)
	document.getElementById("t"+j++).value=document.getElementById(i).innerHTML;
	fields="u";
}
function SavaData(totalfields)
{
	if(fields==null)
		fields="s";
	for(var i=0;i<totalfields;i++)
	{
		fields=fields+"?"+document.getElementById("t"+i).value;
	}
	post_to_url('delete_data.php', {'operation':fields});
	//alert("data saved");
}
function ClearAll()
{
	var m=document.getElementById("totalFields").value;
	document.getElementById("t0").disabled=false;
	for(var i=0;i<m;i++)
		{
			document.getElementById("t"+i).value="";
		}
		fields="s";
}
function getChecked(totalColumn)
{
	totalColumn=totalColumn+1;
	//var fields="Fields to be deleted are:\n";
	fields="";
	var maxFields = document.getElementById("maxField").value;
	var msg = "The following check boxes are selected : \n";
	for(var i=1; i<maxFields; i+=totalColumn)
	{
			
				if(document.getElementById(i).checked)		
				{
					// code to get the row number of selected check box
					
					/*var startOfRow = i - (i % totalColumn);
					var rowNo = (startOfRow) / totalColumn;
					rowNo++;
					msg = msg + rowNo+ ", ";*/
					fields= fields + document.getElementById(i+1).innerHTML+" ";
					
				}
	}
	alert(fields);
	fields="d"+fields;
	post_to_url('delete_data.php', {'operation':fields});
	//post_to_url('delete_data.php', {'mode':'d'});
	//window.location = "delete_data.php"
}

	function post_to_url(path, params, method) {
    method = method || "get"; // Set method to post by default, if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", params[key]);
        form.appendChild(hiddenField);
    }

    document.body.appendChild(form);    // Not entirely sure if this is necessary
    form.submit();
}

function ChangeColor(tableRow, highLight,row)
    {
    if (highLight)
    {
      tableRow.style.backgroundColor = '#dcfac9';
    }
    else
    {
	
		if(row % 2==0)
	      tableRow.style.backgroundColor = '#DDDDDD';
		  else
		  tableRow.style.backgroundColor = '#CCCCCC';
    }
  }