$(document).ready(function(){
	$("#dl").click(function(){
		$("#download").click();
	});
});

function openCity(evt, cityName) {
	var i, tabcontent, tablinks;
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	document.getElementById(cityName).style.display = "block";
	evt.currentTarget.className += " active";
}

function bounceRename(obj) {
	var newName = prompt("请输入新的文件名","");
	if(!newName.trim()){
		alert("文件名不能为空！");
		return false
	}
	else if(newName.length > 50){
		alert("文件名不能超过50个字符！");
		// window.location.href="icloud_d2.jsp";
		return false;
	}
	else{
		var id = $(obj).parent().parent().find("#ID").val();
		document.getElementById("name").value = newName;    /*传值给name*/
		document.getElementById("oldId").value = id;        /*原id赋值*/
		document.getElementById("Form").action='renameFile';
		// document.getElementById("Form").submit();
		return true;
	}
}

function Search(){
	var type = parseInt($("#searchType").val());
	if(type==1)
		Layer1.style.visibility="visible";
	else
		Layer1.style.visibility="hidden";
	if(type==2)
		Layer2.style.visibility="visible";
	else
		Layer2.style.visibility="hidden";
	if(type==3)
		Layer3.style.visibility="visible";
	else
		Layer3.style.visibility="hidden";
}

function postform(obj){
	var obj2=document.getElementsByName("id");
	var count=0;
	for(var i=0;i<obj2.length;i++){
		if(obj2[i].checked==true)
			count=count+1;
	}
	if(count==0){alert("请选择文件");return false;}
	if(count==1) return true;
}



function  choose3(on) {
	var a=on;
	document.getElementById("type3").value=a;
	document.getElementById("sign3").value=1;
}

function  choose2(on) {
	var a=on;
	document.getElementById("type2").value=a;
	document.getElementById("sign2").value=1;
}
function  choose1(on) {
	document.getElementById("sign1").value=1;

}

function check() {
	var fileUp = document.getElementById("feli1").value;
	if(fileUp.length == 0){
		confirm("文件不能为空！请重新选择！");
		return false;
	}
}