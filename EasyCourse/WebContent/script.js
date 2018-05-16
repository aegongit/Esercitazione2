

var myForm = document.forms.inserisciAula;
var formDocente =document.forms.inserisciDocente;
var formCorso = document.forms.inserisciCorso;
var formOrario = document.forms.inserisciOrario;
var baseUrl = "http://localhost:8080/EasyCourse";

formOrario.addEventListener("submit", function(e) {
	
	var id_corso  = formOrario.idCorsoUpdate.value;
	var idAula = formOrario.idAulaUpdate.value;
	var giorno = formOrario.giornoUpdate.value;
	var oraInizio = formOrario.oraInizioUpdate.value;
	var oraFine = formOrario.oraFineUpdate.value;
	
	if(((oraInizio*1) < 7 || (oraInizio*1)> 19) || (oraFine*1) < 7 || (oraFine*1) > 19 ){
		alert("Intervallo orario non valido!");
		return;
	}
	if((oraInizio*1) > (oraFine*1)){
		alert("Ora inizio non può essere maggiore dell'ora fine");
		return;
	}
	
	var http = new XMLHttpRequest();
	var url = baseUrl+"/rest/unisa/corsi/"+id_corso;
	var params = "idAula="+idAula+"&giorno="+giorno+"&oraInizio="+oraInizio+"&oraFine="+oraFine;
	http.open("PUT", url, true);
	
	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {//Call a function when the state changes.
	    if(http.readyState == 4 && http.status == 200) {
	        alert(http.responseText);
	    }
	}
	http.send(params);
	e.preventDefault(); 
	return;
});

myForm.addEventListener("submit", function(e) {
	var num = Math.round(10000*Math.random());
	var nome = myForm.nome.value;
	var http = new XMLHttpRequest();
	var url = baseUrl+"/rest/unisa/aule/"+num;
	var params = "nome="+nome;
	http.open("POST", url, true);
	
	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {//Call a function when the state changes.
	    if(http.readyState == 4 && http.status == 200) {
	        alert(http.responseText);
	    }
	}
	http.send(params);
	e.preventDefault(); 
});


formDocente.addEventListener("submit", function(e) {
	
	var nome = formDocente.nome.value;
	var cognome =formDocente.cognome.value;
	var matDocente = formDocente.matricolaDocente.value;
	
	
	var http = new XMLHttpRequest();
	var url = baseUrl+"/rest/unisa/docenti/"+matDocente;
	var params = "nome="+nome+"&cognome="+cognome;
	http.open("POST", url, true);
	
	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {//Call a function when the state changes.
	    if(http.readyState == 4 && http.status == 200) {
	        alert(http.responseText);
	    }
	}
	http.send(params);
	e.preventDefault(); 
});


formCorso.addEventListener("submit", function(e) {
	var num = Math.round(10000*Math.random());
	var nome = formCorso.nome.value;
	var semestre =formCorso.semestre.value;
	var anno =formCorso.anno.value;
	var matDocente =formCorso.matDocente.value;
	var idAula = formCorso.idAula.value;
	var giorno = formCorso.giorno.value;
	var oraInizio = formCorso.oraInizio.value;
	var oraFine = formCorso.oraFine.value;
	
	
	
	var http = new XMLHttpRequest();
	var url = baseUrl+"/rest/unisa/corsi/"+num;
	var params = "nome="+nome+"&semestre="+semestre+"&anno="+anno+"&matDocente="+matDocente+"&idAula="+idAula+"&giorno="+giorno+"&oraFine="+oraFine+"&oraInizio="+oraInizio;
	http.open("POST", url, true);
	
	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {//Call a function when the state changes.
	    if(http.readyState == 4 && http.status == 200) {
	        alert(http.responseText);
	    }
	}
	http.send(params);
	e.preventDefault(); 
});



function toggler(divId) {
	$("#" + divId).toggle();

}


function visualizzaCorsi() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var obj1 = JSON.parse(this.responseText);

			console.log(JSON.stringify(obj1));

			var msg = "<tr>";
			var keys = Object.keys(obj1);
			for (var i = 0; i<  keys.length; i++) {
				var obj = obj1[keys[i]];
				console.log("Cosro con id : "+keys[i]+" "+JSON.stringify(obj));



				//msg = msg + "<th scope=\"row\">" + i + "</th><td>"+ obj.cod + "</td>"+"<td>" + obj.nome + "</td>"+"<td>" + obj.docente.nome +" "+ obj.docente.cognome + "</td>"+"</tr>";
				var keysSlot = Object.keys(obj.mappaOrario)
				tmp = "";
				msg = msg + "<th scope=\"row\">" + i + "</th><td>"+ obj.cod + "</td>"+"<td>" + obj.nome + "</td>"+"<td>" + obj.docente.nome +" "+ obj.docente.cognome + "</td>";
				for (var j = 0; j<keysSlot.length; j++){
					var msg1 = "Dalle ore: "+obj.mappaOrario[keysSlot[j]].oraInizio+" alle ore "+obj.mappaOrario[keysSlot[j]].oraFine+" in aula "+obj.mappaOrario[keysSlot[j]].aula.nome;
					tmp = tmp +"<button onclick=\"alert('"+msg1+" ');\" id=\""+keysSlot[j]+"\"  type=\"button\" class=\"btn btn-prymary\">"+obj.mappaOrario[keysSlot[j]].giorno+"</button>  "
					
				}
				msg = msg + "<td>"+tmp +"</td></tr>"
			
			}
			document.getElementById("listCorsi").innerHTML = msg;
			
		}
	};
	xhttp.open("GET", baseUrl+"/rest/unisa/corsi", true);
	xhttp.send();

}

function visualizzaCorsi1(idDocente) {
	console.log(idDocente);
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var obj1 = JSON.parse(this.responseText);
			console.log(JSON.stringify(obj1));
			var msg = "<tr>";
			var keys = Object.keys(obj1);
			for (var i = 0; i<  keys.length; i++) {

				var obj = obj1[keys[i]];
				console.log("Cosro con id : "+keys[i]+" "+JSON.stringify(obj));

				var keysSlot = Object.keys(obj.mappaOrario)
				tmp = "";
				msg = msg + "<th scope=\"row\">" + i + "</th><td>"+ obj.cod + "</td>"+"<td>" + obj.nome + "</td>"+"<td>" + obj.docente.nome +" "+ obj.docente.cognome + "</td>";
				for (var j = 0; j<keysSlot.length; j++){
					var msg1 = "Dalle ore: "+obj.mappaOrario[keysSlot[j]].oraInizio+" alle ore "+obj.mappaOrario[keysSlot[j]].oraFine+" in aula "+obj.mappaOrario[keysSlot[j]].aula.nome;
					tmp = tmp +"<button onclick=\"alert('"+msg1+"');\" id=\""+keysSlot[j]+"\"  type=\"button\" class=\"btn btn-prymary\">"+obj.mappaOrario[keysSlot[j]].giorno+"</button>  "
					
				}
				msg = msg + "<td>"+tmp +"</td></tr>"
				
			
			}
			document.getElementById("listCorsi1").innerHTML = msg;
		}
	};
	xhttp.open("GET", baseUrl+"/rest/unisa/corsi?idDocente="+idDocente, true);
	xhttp.send();

}

function getDocenti(){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var obj1 = JSON.parse(this.responseText);

			console.log(JSON.stringify(obj1));

			var msg = "";
			var keys = Object.keys(obj1);
			for (var i = 0; i<  keys.length; i++) {
				var obj = obj1[keys[i]];
				console.log("Cosro con id : "+keys[i]+" "+JSON.stringify(obj));
				msg = msg + "<option value=\""+obj.matricola+"\">"+obj.nome+" "+obj.cognome+"</option>";
			}
			document.getElementById("inlineFormCustomSelect").innerHTML = msg;
			document.getElementById("matDocente").innerHTML = msg;
		}
	};
	xhttp.open("GET", baseUrl+"/rest/unisa/docenti", true);
	xhttp.send();
}

function getCorsi(){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var obj1 = JSON.parse(this.responseText);

			console.log(JSON.stringify(obj1));

			var msg = "";
			var keys = Object.keys(obj1);
			for (var i = 0; i<  keys.length; i++) {
				var obj = obj1[keys[i]];
				console.log("Cosro con id : "+keys[i]+" "+JSON.stringify(obj));
				msg = msg + "<option value=\""+obj.cod+"\">"+obj.nome+"</option>";
			}
			//document.getElementById("inlineFormCustomSelect").innerHTML = msg;
			document.getElementById("idCorsoUpdate").innerHTML = msg;
		}
	};
	xhttp.open("GET", baseUrl+"/rest/unisa/corsi", true);
	xhttp.send();
}


function getAule(){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var obj1 = JSON.parse(this.responseText);

			console.log(JSON.stringify(obj1));

			var msg = "";
			var keys = Object.keys(obj1);
			for (var i = 0; i<  keys.length; i++) {
				var obj = obj1[keys[i]];
				console.log("Aula con id : "+keys[i]+" "+JSON.stringify(obj));
				msg = msg + "<option value=\""+obj.idAula+"\">"+obj.nome+"</option>";
			}
			//document.getElementById("inlineFormCustomSelect").innerHTML = msg;
			document.getElementById("idAula").innerHTML = msg;
			document.getElementById("idAulaUpdate").innerHTML = msg;
		}
	};
	xhttp.open("GET", baseUrl+"/rest/unisa/aule", true);
	xhttp.send();
}





