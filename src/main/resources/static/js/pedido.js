$(document).ready(function () {
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
        const produtoSelect = $('#produto');
        const produtoId = produtoSelect.val();
        const produtoNome = produtoSelect.find('option:selected').text().split(' (R$')[0];
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
                const precoUnitario = parseFloat(response.subtotal) / quantidade;

                itens.push({
                    produtoId: produtoId,
                    produtoNome: produtoNome,
                    quantidade: quantidade,
                    preco: precoUnitario,
                    subtotal: parseFloat(response.subtotal)
                });

                atualizarTabelaItens();
                $('#produto').val('');
                $('#quantidade').val(1);
            },
            error: function (xhr) {
                alert('Erro ao adicionar item: ' + xhr.responseText);
            }
        });
    });
    
    $('#frmPedido').submit(function (e) {
        e.preventDefault();

        const vendedorId = $('#vendedor').val();
        const clienteId = $('#cliente').val();

        if (!vendedorId || !clienteId || itens.length === 0) {
            alert("Preencha todos os campos e adicione ao menos um item.");
            return;
        }

        const itensParaEnvio = itens.map(item => ({
            produtoId: item.produtoId,
            quantidade: item.quantidade
        }));

        $.ajax({
            url: '/pedidos/finalizar',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                vendedorId: vendedorId,
                clienteId: clienteId,
                itens: itensParaEnvio
            }),
            success: function () {
                alert("Pedido finalizado com sucesso!");
                window.location.href = "/pedidos/lista";
            },
            error: function (xhr) {
                alert("Erro ao finalizar pedido: " + xhr.responseText);
            }
        });
    });
});