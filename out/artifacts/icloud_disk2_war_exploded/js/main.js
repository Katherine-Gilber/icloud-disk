$(function () {
    load(5,1);
});

$(function () {
    $("#submit1").click(function () {
        var userId = $("#userId").val(  );
        var userName = $("#userName").val();
        var permissions = $("#permissions").val();
        var supManag = sessionStorage.getItem('permissions');
        if (userId=="" && userName==""){
            if (supManag==1)
                alert("已展示所有用户信息");
            else if (supManag==2 && permissions=="")
                alert("已展示所有用户和管理员信息");
        }
        load(5,1,userId,userName,permissions);
    });
});

function load(rows,currentPage,userId,userName,permissions){
    var supManag = sessionStorage.getItem('permissions');
    if(userId===undefined){
        userId="";
    }
    if(userName===undefined){
        userName="";
    }
    if(permissions===undefined){
        if(supManag==1)
            permissions="0";
        if (supManag==2)
            permissions="";
    }
    $.ajax({
        url:"findByPage",
        data: {currentPage:currentPage,rows:rows,userId:userId,userName:userName,permissions:permissions},
        type:"post",
        dataType:"json",
        //fp中存放的是所有的用户信息
        success:function (fp) {
            var lis='';
            if (supManag==1){
                for(var i=0;i<fp.list.length;i++){
                    if (fp.list[i].permissions==0){
                        var tr=' <tr>\n' +
                            '            <td><input type="checkbox" name="ids" value="'+fp.list[i].id+'" </td>\n</form>\n'+
                            '            <td>'+fp.list[i].id+'</td>\n' +
                            '            <td>'+fp.list[i].user+'</td>\n' +
                            '            <td>'+fp.list[i].password+'</td>\n' +
                            '            <td>\n<table>\n<tr>\n<td>\n'+
                            '            <form action="user_update.jsp" method="post">\n'+
                            '            <input type="hidden" name="id" value='+fp.list[i].id+'>\n'+
                            '            <input type="hidden" name="name" value='+fp.list[i].user+'>\n'+
                            '            <input type="hidden" name="password" value='+fp.list[i].password+'>\n'+
                            '            <input type="hidden" name="permissions" value='+fp.list[i].permissions+'>\n'+
                            '            <input type="submit" value="修改">\n</form>\n</td>'+
                            '            <td>\n'+
                            '            <form action="deleteUsers" method="post" οnsubmit="return submit1_form()">\n'+
                            '            <input type="hidden" name="users" value='+fp.list[i].user+'>\n'+
                            '            <input type="submit" onclick="alert(\'删除成功\')" value="删除">'+
                            '            </form>\n</td>\n</tr>\n</table>\n</td>'+
                            '        </tr>';
                        lis+=tr;
                    }
                }
            }
            else if (supManag==2){
                for(var i=0;i<fp.list.length;i++){
                    if (fp.list[i].permissions!=2){
                        var tr=' <tr>\n' +
                            '            <td><input type="checkbox" name="ids" value="'+fp.list[i].id+'" </td>\n</form>\n'+
                            '            <td>'+fp.list[i].id+'</td>\n' +
                            '            <td>'+fp.list[i].user+'</td>\n' +
                            '            <td>'+fp.list[i].password+'</td>\n' +
                            '            <td>'+fp.list[i].permissions+'</td>\n' +
                            '            <td>\n<table>\n<tr>\n<td>\n'+
                            '            <form action="user_update.jsp" method="post">\n'+
                            '            <input type="hidden" name="id" value='+fp.list[i].id+'>\n'+
                            '            <input type="hidden" name="name" value='+fp.list[i].user+'>\n'+
                            '            <input type="hidden" name="password" value='+fp.list[i].password+'>\n'+
                            '            <input type="hidden" name="permissions" value='+fp.list[i].permissions+'>\n'+
                            '            <input type="submit" value="修改">\n</form>\n</td>'+
                            '            <td>\n'+
                            '            <form action="deleteUsers" method="post" οnsubmit="return submit1_form()">\n'+
                            '            <input type="hidden" name="users" value='+fp.list[i].user+'>\n'+
                            '            <input type="submit" onclick="alert(\'删除成功\')" value="删除">'+
                            '            </form>\n</td>\n</tr>\n</table>\n</td>'+
                            '        </tr>';
                        lis+=tr;
                    }
                }
            }
            $("#info").html(lis);

            var pre=currentPage-1;
            if(pre<=0) {
                pre=1;
            }
            var next=currentPage+1;
            if(next>fp.totalPage){
                next=fp.totalPage;
            }

            var prePage ='      ' +
                '               <li>\n' +
                '                    <a href="javascript:load('+5+','+pre+',&quot;'+userId+'&quot;,&quot;'+userName+'&quot;,&quot;'+permissions+'&quot;)" aria-label="Previous">\n' +
                '                        <span aria-hidden="true">&laquo;</span>\n' +
                '                    </a>\n' +
                '                </li>';
            var result_lis='';
            result_lis+=prePage;
            for(var i=1;i<=fp.totalPage;i++){
                if(currentPage===i){
                    var li='<li class="active"><a href="javascript:load('+5+','+i+',&quot;'+userId+'&quot;,&quot;'+userName+'&quot;,&quot;'+permissions+'&quot;)">'+i+'</a></li>';
                }else{
                    var li='<li><a href="javascript:load('+5+','+i+',&quot;'+userId+'&quot;,&quot;'+userName+'&quot;,&quot;'+permissions+'&quot;)">'+i+'</a></li>';
                }
                result_lis+=li;
            }

            var nextPage='           <li>\n' +
                '                    <a href="javascript:load('+5+','+next+',&quot;'+userId+'&quot;,&quot;'+userName+'&quot;,&quot;'+permissions+'&quot;)" aria-label="Next">\n' +
                '                        <span aria-hidden="true">&raquo;</span>\n' +
                '                    </a>\n' +
                '                </li>';

            result_lis+=nextPage;

            var span='            <span style="font-size: 20px;margin-left: 5px;">\n' +
                '                    共'+fp.totalCount+'条记录，共'+fp.totalPage+'页\n' +
                '                </span>';
            result_lis+=span;
            $("#all").html(result_lis);

        }
    })
}

//添加用户的用户名和密码检查
function checkform(){
    var userValue = document.getElementById("user_name").value;
    if(userValue.length == 0){
        document.getElementById("user_name").value="";
        //$("#user_name").attr("placeholder","用户名不能为空！");
        confirm("用户名不能为空！请重新输入！");
        document.getElementById("user_name").focus();
        return false;
    }
    if(userValue.length < 3){
        document.getElementById("user_name").value="";
        confirm("用户名不能小于3位!请重新输入！");
        document.getElementById("user_name").focus();
        return false;
    }
    if(userValue.length > 18){
        document.getElementById("user_name").value="";
        confirm("用户名不能大于18位!请重新输入！");
        document.getElementById("user_name").focus();
        return false;
    }
    var userpass = document.getElementById("user_password").value;
    if(userpass.length == 0){
        document.getElementById("user_password").value="";
        confirm("密码不能为空!请重新输入！");
        document.getElementById("user_password").focus();
        return false;
    }
    if(userpass.length < 6){
        document.getElementById("user_password").value="";
        confirm("密码不能小于6位!请重新输入！");
        document.getElementById("user_password").focus();
        return false;
    }
    if(userpass.length > 16){
        document.getElementById("user_password").value="";
        confirm("密码不能大于16位！请重新输入！");
        document.getElementById("user_password").focus();
        return false;
    }
    return true;
}

function check(){
    var val = $('input[name="user_permission"]:checked').val();
    if(val=="管理员"){
        //alert(val);
        document.getElementById("permission").value="1";
    }
    else{
        document.getElementById("permission").value="0";
    }
}