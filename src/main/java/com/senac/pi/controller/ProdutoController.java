package com.senac.pi.controller;

import com.senac.pi.model.ProdutoEntity;
import com.senac.pi.model.UsuarioEntity;
import com.senac.pi.service.ListaDesejosService;
import com.senac.pi.service.ProdutoService;
import com.senac.pi.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private ListaDesejosService listaDesejosService;
    
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
    
    @PostMapping("/adicionar-lista-desejos")
    public String adicionarListaDesejos(@RequestParam Long produtoId, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/?erro=Necessario+estar+logado";
        }
        
        ProdutoEntity produto = produtoService.buscarPorId(produtoId);
        if (produto != null) {
            listaDesejosService.adicionarProduto(usuario, produto);
        }
        
        return "redirect:/produtos/lista?sucesso=Produto+adicionado+na+lista+de+desejos";
    }
    
}