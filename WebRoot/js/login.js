$(function() {
    var $dialog = $('<div/>');
    var $formBody = $('#form-body');
    $dialog.dialog({
        height: 300,
        width: 500,
        content: $formBody.show(),
        noheader: true,
        buttons: [{
                id: 'loginBtn',
                disabled: false,
                text: '登&nbsp;&nbsp;录',
                iconCls:"icon-ok",
                handler:function(){
                	var linkbutton = $(this);
                	linkbutton.linkbutton("disable");
                    $.post('um/umUserAction_login.do',$formBody.serialize(),function(rsp){
                          if(rsp.success){
                               window.location = rsp.obj;
                          }else{
                              $.messager.alert('警告',rsp.msg);
                              linkbutton.linkbutton("enable");
                          }
                    },'json').error(function(){
                        $(this).linkbutton("enable");
                        $.messager.alert('警告','系统发生错误');
                    });
                }
            }]
    });
    $formBody.after($('#logo').show());
    $(window).resize(function() {
        $dialog.dialog('center');
    });
});