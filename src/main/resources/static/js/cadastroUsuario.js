$(document).ready(function () {
    $('#nascimento').mask('00/00/0000');
    $('#telefone').mask('(00) 00000-0000');

    $("#frmUsuario").validate({
        rules: {
            nome: {
                required: true,
                minlength: 3
            },
            nascimento: {
                required: true,
                minlength: 10
            },
            telefone: {
                required: true,
                minlength: 15
            },
            email: {
                required: true,
                email: true
            },
            login: {
                required: true,
                minlength: 4
            },
            senha: {
                required: true,
                minlength: 6
            }
        },
        messages: {
            nome: {
                required: "Por favor, informe seu nome completo",
                minlength: "Nome deve ter pelo menos 3 caracteres"
            },
            nascimento: {
                required: "Informe sua data de nascimento",
                minlength: "Formato: dd/mm/aaaa"
            },
            telefone: {
                required: "Telefone é obrigatório",
                minlength: "Formato: (00) 00000-0000"
            },
            email: {
                required: "E-mail é obrigatório",
                email: "Informe um e-mail válido"
            },
            login: {
                required: "Crie um login",
                minlength: "Login deve ter 4+ caracteres"
            },
            senha: {
                required: "Crie uma senha",
                minlength: "Senha deve ter 6+ caracteres"
            }
        }
    });    
});