package br.edu.utfpr.cp.espjava.crudcidades.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.utfpr.cp.espjava.crudcidades.domain.Cidade;
import br.edu.utfpr.cp.espjava.crudcidades.repository.CidadeRepository;

@Controller
public class CidadeController {

    private Set<Cidade> cidades;

    @Autowired
    private CidadeRepository repository;

    public CidadeController() {
        cidades = new HashSet<Cidade>();
    }

    @GetMapping("/")
    public String listar(Model memoria, Principal usuario, HttpSession sessao, HttpServletResponse res) {
        memoria.addAttribute("listaCidades",
                repository
                        .findAll()
                        .stream()
                        .map(cidade -> new Cidade(cidade.getNome(), cidade.getEstado()))
                        .collect(Collectors.toList()));
        
        sessao.setAttribute("usuarioAtual", usuario.getName());
        
        res.addCookie(new Cookie("listar", LocalDateTime.now().toString()));
        return "/crud";
    }

    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria) {
        if (validacao.hasErrors()) {
            validacao
                    .getFieldErrors()
                    .forEach(error -> {
                        memoria.addAttribute(
                                error.getField(),
                                error.getDefaultMessage());
                    });

            memoria.addAttribute("nomeInformado", cidade.getNome());
            memoria.addAttribute("estadoinformado", cidade.getEstado());
            memoria.addAttribute("listaCidades", cidades);
            return "/crud";
        } else {
            repository.save(cidade.clonar());
        }

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam String nome, @RequestParam String estado) {
        var cidade = repository.findByNomeAndEstado(nome, estado);
        cidade.ifPresent(repository::delete);
        return "redirect:/";
    }

    @GetMapping("/prepararAlterar")
    public String prepararAlterar(@RequestParam String nome, @RequestParam String estado, Model memoria) {
        var cidadeAtual = repository.findByNomeAndEstado(nome, estado);
        cidadeAtual.ifPresent(cidade -> {
            memoria.addAttribute("cidadeAtual", cidade);
            memoria.addAttribute("listaCidades", repository.findAll());
        });

        return "/crud";
    }

    @PostMapping("/alterar")
    public String alterar(@RequestParam String nomeAtual, @RequestParam String estadoAtual, Cidade cidade) {
        var cidadeAtual = repository.findByNomeAndEstado(nomeAtual, estadoAtual);
        if (cidadeAtual.isPresent()) {
            var cidadeEncontrada = cidadeAtual.get();
            cidadeEncontrada.setNome(cidade.getNome());
            cidadeEncontrada.setEstado(cidade.getEstado());
            repository.saveAndFlush(cidadeEncontrada);
        }
        return "redirect:/";
    }
    
    @GetMapping("/mostrar")
    @ResponseBody
    public String mostraCookieAlterar(@CookieValue String listar) {
    	return "Último acesso ao método listar(): " + listar;
    }
}
