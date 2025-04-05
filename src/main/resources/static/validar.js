$("#frmLogin").validate({
    rules: {
        login: {
            required: true
        },
        senha: {
            required: true,
            minlength: 6,
            maxlength: 12
        }
    },
    messages: {
        login: {
            required: "O campo login é obrigatório"
        },
        senha: {
            required: "O campo senha é obrigatório",
            minlength: "A senha deve ter ao menos 6 caracteres",
            maxlength: "A senha deve ter no máximo 12 caracteres"
        }
    }
});

$("#frmUsuario").validate({
    rules: {
        nome: {
            required: true,
            maxlength: 15
        },
        nascimento: {
            required: true,
            date: true
        },
        telefone: {
            required: true,
            minlength: 14
        },
        email: {
            required: true,
            email: true
        },
        login: {
            required: true
        },
        senha: {
            required: true,
            minlength: 6,
            maxlength: 12
        }
    },
    messages: {
        nome: {
            required: "O campo nome é obrigatório",
            maxlength: "O campo deve ter no máximo 15 caracteres."
        },
        nascimento: {
            required: "O campo data de nascimento é obrigatório",
            date: "O campo deve ter formato dd/mm/aaaa"
        },
        telefone: {
            required: "O campo telefone é obrigatório",
            minlength: "O campo deve ter formato (xx) xxxxx-xxxx"
        },
        email: {
            required: "O campo e-mail é obrigatório",
            email: "O campo deve ter formato user@provedor"
        },
        login: {
            required: "O campo login é obrigatório"
        },
        senha: {
            required: "O campo senha é obrigatório",
            minlength: "A senha deve ter ao menos 6 caracteres",
            maxlength: "A senha deve ter no máximo 12 caracteres"
        }
    }
});

$("#frmProduto").validate({
    rules: {
        nome: {
            required: true,
            maxlength: 25
        },
        valorUnitario: {
            required: true,
            number: true
        },
        quantidade: {
            required: true,
            min: 0,
            max: 200
        },
        categoria: {
            required: true,
            maxlength: 15
        }
    },
    messages: {
        nome: {
            required: "O campo nome do produto é obrigatório.",
            maxlength: "O campo deve ter no máximo 25 caracteres."
        },
        valorUnitario: {
            required: "O campo valor unitário é obrigatório.",
            num: "O campo deve conter apenas números."
        },
        quantidade: {
            required: "O campo quantidade é obrigatório.",
            min: "O campo deve ter apenas números positivos.",
            max: "A quantidade maxima é de 200."
        },
        categoria: {
            required: "O campo categoria é obrigatório.",
            maxlength: "O campo deve ter no maximo 15 caracteres."
        }
    }
});

$("#frmPedido").validate({
    rules: {
        quantidade: {
            min: 0
        }
    },
    messages: {
        quantidade: {
            min: "Este campo so aceita números positivos."
        }
    }
});