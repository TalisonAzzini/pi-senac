$(document).ready(function () {
    let pedidoId = null;
    let itens = [];
    let total = 0;

    function atualizarTabelaItens() {
        const tbody = $('#itensPedido');
        tbody.empty();
        total = 0;

        itens.forEach(item => {
            tbody.append(`
                <tr>
                    <td>${item.produtoNome}</td>
                    <td>${item.quantidade}</td>
                    <td>R$ ${item.preco.toFixed(2).replace('.', ',')}</td>
                    <td>R$ ${item.subtotal.toFixed(2).replace('.', ',')}</td>
                </tr>
            `);
            total += item.subtotal;
        });

        $('#totalPedido').text('R$ ' + total.toFixed(2).replace('.', ','));
    }

    $('#btnAdicionar').click(function () {
        const produtoId = $('#produto').val();
        const produtoNome = $('#produto option:selected').text();
        const quantidade = parseInt($('#quantidade').val());

        if (!produtoId || quantidade <= 0) {
            alert('Selecione um produto e quantidade válida.');
            return;
        }

        $.ajax({
            url: '/pedidos/adicionar-item',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                produtoId: produtoId,
                quantidade: quantidade
            }),
            success: function (response) {
                const precoUnitario = response.subtotal / quantidade;

                itens.push({
                    produtoNome: produtoNome,
                    quantidade: quantidade,
                    preco: precoUnitario,
                    subtotal: response.subtotal
                });

                atualizarTabelaItens();
            },
            error: function (xhr) {
                alert('Erro ao adicionar item: ' + xhr.responseText);
            }
        });

        $('#produto').val('');
        $('#quantidade').val(1);
    });
});
