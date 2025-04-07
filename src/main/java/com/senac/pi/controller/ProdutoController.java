package com.senac.pi.controller;

import com.senac.pi.model.ProdutoEntity;
import com.senac.pi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("produto", new ProdutoEntity());
        return "cadastroProduto";
    }

    @PostMapping("/salvar")
    public String salvarProduto(Model model, @RequestParam String nome,@RequestParam BigDecimal valorUnitario,@RequestParam int quantidade,@RequestParam String categoria) {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome(nome);
        produto.setPreco(valorUnitario);
        produto.setQuantidade(quantidade);
        produto.setCategoria(categoria);
        
        produtoService.salvar(produto);
        return "redirect:/produtos/lista?sucesso";
    }

    @GetMapping("/lista")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "listaProdutos";
    }
}