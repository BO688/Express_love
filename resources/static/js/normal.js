var timer
var timerclock=0;
function loading(word) {
    $("#loading").css("display","block")
    $("#loading div label").html(word+"...")
    timer=setInterval(function () {
        if(timerclock++>=3){
            timerclock=0;
            $("#loading div label").html(word+".")
        }else{
            $("#loading div label").html($("#loading div label").html()+".")
        }
    },500)
}
function closeloading() {
    clearInterval(timer)
    $("#loading").css("display","none")
}
function tip(word) {/*提示框函数*/
    $("#tipword").show();
    $("#tipbackground").show()
    $("#tipbackground").animate({opacity:"1"},700)
    $("#tipword").animate({opacity:"1"},800)
    $("#tipword div#center_word").html("")
    $("#tipword div#center_word").append(word)
}
function closetip() {/*关闭提示框*/
    $("#tipword").animate({opacity: "0"},100,function () {
        $("#tipword").hide();
    })
    $("#tipbackground").animate({opacity: "0"},400,function () {
        $("#tipbackground").hide()
    })
}