$(document).ready(function () {
    $("#frmProduto").validate({
        rules: {
            nome: {
                required: true,
                minlength: 3
            },
            valorUnitario: {
                required: true,
                min: 0.01
            },
            quantidade: {
                required: true,
                min: 1
            },
            categoria: {
                required: true,
                minlength: 3
            }
        },
        messages: {
            nome: {
                required: "Por favor, informe o nome do produto",
                minlength: "Nome deve ter pelo menos 3 caracteres"
            },
            valorUnitario: {
                required: "Informe o valor unitário",
                min: "Valor deve ser maior que zero"
            },
            quantidade: {
                required: "Informe a quantidade",
                min: "Quantidade mínima é 1"
            },
            categoria: {
                required: "Informe a categoria",
                minlength: "Categoria deve ter pelo menos 3 caracteres"
            }
        }
    });
});