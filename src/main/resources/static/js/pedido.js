$(document).ready(function() {
    let pedidoId = null;
    let itens = [];
    let total = 0;

    $('#btnAdicionar').click(function() {
        const produtoId = $('#produto').val();
        const produtoNome = $('#produto option:selected').text().split(' (R$')[0];
        const quantidade = parseInt($('#quantidade').val());
        const preco = parseFloat($('#produto option:selected').text().match(/R\$\s([\d,]+)/)[1].replace(',', '.'));
        const subtotal = preco * quantidade;

        itens.push({
            produtoId: produtoId,
            produtoNome: produtoNome,
            quantidade: quantidade,
            preco: preco,
            subtotal: subtotal
        });

        atualizarTabelaItens();
        
        $.post('/pedidos/adicionar-item', {
            produtoId: produtoId,
            quantidade: quantidade
        }).fail(function() {
            alert('Erro ao adicionar item');
        });

        $('#produto').val('');
        $('#quantidade').val(1);
    });

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
});