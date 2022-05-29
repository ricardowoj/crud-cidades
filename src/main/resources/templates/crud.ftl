<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CRUD Cidades</title>
  <link rel="stylesheet"  href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" >
</head>
<body>
    <nav class="navbar navbar-expand-sm bg-dark">
	    <span class="navbar-brand text-white">${Session.usuarioAtual} </span>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a href="/logout" class="nav-link btn btn-secondary">
                    Sair da aplicação
                </a>
            </li>
        </ul>
    </nav>
    <div class="container-fluid">
        <div class="jumbotron mt-5">
            <h1>GERENCIAMENTO DE CIDADES</h1>
            <P>UM CRUD PARA CRIAR, ALTERAR, EXCLUIR E LISTAR CIDADES</P>
        </div>

        <#if cidadeAtual??>
            <form action="/alterar"  method="POST" class="needs-validation" novalidate>
            <input type="hidden"  name="nomeAtual"  value="${(cidadeAtual.nome) !}"  class="needs-validation"  novalidate/>
            <input type="hidden"  name="estadoAtual"  value="${(cidadeAtual.estado) !}" class="needs-validation"  novalidate/>
        <#else>
            <form action="/criar" method="POST" class="needs-validation" novalidate>
        </#if>

            <div class="form-group">
                <label for="nome" class="nome">Cidade:</label>
                <input 
                    required 
                    value="${(cidadeAtual.nome) !}${nomeInformado!}" 
                    id="nome"  
                    type="text"  
                    name="nome"   
                    class="form-control ${(nome??)?then('is-invalid', '')}"  
                    placeholder="Informe o nome da cidade" >
                    
                <div class="invalid-feedback">
                    ${nome!}
                </div>
            </div>
            <div class="form-group">
                <label for="estado">Estado:</label>
                <input 
                    required 
                    maxlength="2" 
                    value="${(cidadeAtual.estado) !}${estadoinformado!}"  
                    id="estado"  
                    type="text"  
                    name="estado"  
                    class="form-control ${(nome??)?then('is-invalid', '')}"  
                        placeholder="Informe o estado ao qual a cidade pertence">
                        
                <div class="invalid-feedback">
                    ${estado!}
                </div>
            </div>
                
        <#if cidadeAtual??>
                <button type="submit" class="btn btn-warning">ALTERAR</button>
        <#else>
                <button type="submit" class="btn btn-primary">CRIAR</button>
        </#if>

        </form>
        <table class="table table-striped table-hover mt-5">
            <thead class="thead-dark">
                <tr>
                    <th>Nome</th>
                    <th>Estado</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
            <#list listaCidades as cidade >
                <tr>
                    <td>${cidade.nome}</td>
                    <td>${cidade.estado}</td>
                    <td>
                        <div class="d-flex d-justify-content-center">
                            <a href="/prepararAlterar?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-warning mr-3">ALTERAR</a>
                            <a href="/excluir?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-danger">EXCLUIR</a>
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</body>
</html>