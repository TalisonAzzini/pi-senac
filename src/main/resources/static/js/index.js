$(document).ready(function() {
    $("#frmLogin").validate({
        rules: {
            login: "required",
            senha: "required"
        },
        messages: {
            login: "Por favor, informe seu login",
            senha: "Por favor, informe sua senha"
        }
    });
});