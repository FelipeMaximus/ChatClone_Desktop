<div align="center">

# 💬 ChatClone_Desktop

### Assistente de Inteligência Artificial para Desktop desenvolvido em Java

Aplicação desktop inspirada em interfaces modernas de conversação com IA, desenvolvida com JavaFX, integração com a API da Groq e persistência de dados no MongoDB Atlas.

**Java 25 · JavaFX 25 · Maven · MongoDB Atlas · Groq API**

</div>

---

## 📌 Sobre o projeto

O **ChatClone_Desktop** é uma aplicação desktop desenvolvida em Java com o objetivo de oferecer uma experiência de conversação com Inteligência Artificial em um ambiente gráfico moderno.

O projeto integra interface gráfica, comunicação com serviços de IA, persistência de dados em banco de dados NoSQL e mecanismos de recuperação de informações por palavras-chave.

Além das funcionalidades da aplicação, o projeto explora conceitos de desenvolvimento desktop, organização de código, gerenciamento de dependências, integração com serviços externos e empacotamento de aplicações Java para Windows.

## ✨ Funcionalidades

* 💬 Interface de conversação com Inteligência Artificial.
* 🤖 Integração com a API da Groq.
* 🗄️ Persistência de dados com MongoDB Atlas.
* 👤 Sistema de autenticação de usuários.
* 🔐 Isolamento das conversas por usuário.
* 📝 Histórico de conversas salvas.
* ✏️ Renomeação de conversas.
* 🗑️ Exclusão de conversas.
* 📋 Cópia do conteúdo das mensagens.
* 🔎 Recuperação de informações por palavras-chave.
* 📚 Estrutura para consulta a uma base de conhecimento.
* 🎨 Temas visuais claro e escuro.
* 🖥️ Interface gráfica desenvolvida programaticamente com JavaFX.
* 🎞️ Animações de entrada e personalização visual.
* 📦 Geração de executável para Windows com runtime Java incluído.

> As funcionalidades disponíveis dependem da implementação e da configuração atual do projeto.

## 🛠️ Tecnologias utilizadas

| Tecnologia                 | Finalidade                                        |
| -------------------------- | ------------------------------------------------- |
| Java 25                    | Linguagem e plataforma de desenvolvimento         |
| JavaFX 25.0.4              | Construção da interface gráfica                   |
| Maven                      | Gerenciamento de dependências e compilação        |
| MongoDB Atlas              | Persistência de dados na nuvem                    |
| MongoDB Java Driver 5.12.0 | Comunicação com o banco de dados                  |
| Groq API                   | Integração com modelos de Inteligência Artificial |
| dotenv-java 3.2.0          | Leitura de variáveis de ambiente                  |
| Jackson 2.20.0             | Manipulação de dados JSON                         |
| CSS                        | Personalização visual da interface                |
| jpackage                   | Empacotamento da aplicação para Windows           |

## 🏗️ Arquitetura e organização

O projeto utiliza JavaFX para a interface gráfica e organiza responsabilidades entre componentes de interface, configuração, serviços e acesso a dados.

A estrutura exata depende dos pacotes presentes no código-fonte. A organização conceitual inclui:

* **Interface gráfica:** componentes visuais, mensagens, navegação e temas.
* **Configuração:** inicialização e configuração dos serviços utilizados pela aplicação.
* **Serviços:** regras de negócio e integração com a Inteligência Artificial.
* **Persistência:** comunicação com o MongoDB Atlas.
* **Recuperação de informações:** processamento de palavras-chave e consulta à base de conhecimento.

### Recuperação de informações

O projeto possui uma abordagem inicial de recuperação de informações baseada em palavras-chave.

Essa estratégia permite identificar termos relevantes em uma pergunta e utilizá-los para buscar conteúdos potencialmente relacionados na base de conhecimento.

A abordagem não deve ser confundida com um sistema completo de busca vetorial ou com um mecanismo avançado de geração aumentada por recuperação.

## ⚙️ Pré-requisitos para desenvolvimento

Antes de executar o projeto pelo ambiente de desenvolvimento, instale:

1. **JDK 25:** ambiente Java utilizado na compilação.
2. **Eclipse IDE:** ambiente de desenvolvimento Java.
3. **Maven:** gerenciamento de dependências e construção do projeto.
4. **MongoDB Atlas:** banco de dados remoto.
5. **Groq API:** acesso ao serviço de Inteligência Artificial.
6. **Git:** controle de versão.

A versão do Java utilizada neste projeto é a 25. O executável distribuído pelo `jpackage` inclui seu próprio runtime, portanto o usuário final não precisa instalar o JDK separadamente.

## 📥 Como obter o projeto

Clone o repositório:

```bash
git clone https://github.com/SEU-USUARIO/ChatClone_Desktop.git
```

Entre na pasta:

```bash
cd ChatClone_Desktop
```

> Substitua `SEU-USUARIO` pelo nome de usuário correspondente à conta que hospeda o repositório.

## 🔐 Configuração das variáveis de ambiente

O projeto utiliza variáveis de ambiente para configurar as conexões com serviços externos.

Crie um arquivo `.env` na raiz do projeto, seguindo os nomes de variáveis esperados pela implementação.

Exemplo ilustrativo:

```dotenv
MONGODB_URI=sua_string_de_conexao
GROQ_API_KEY=sua_chave_da_groq
```

**Importante:** os nomes e o formato devem corresponder ao código de configuração existente no projeto.

### Cuidados com as credenciais

* Não publique o arquivo `.env`.
* Não inclua chaves reais no README.
* Não envie senhas ou credenciais para o repositório.
* Utilize um arquivo `.env.example` com valores fictícios para documentar as variáveis necessárias.
* Restrinja as permissões de acesso ao MongoDB Atlas.
* Configure os acessos à API conforme as orientações dos respectivos serviços.

> A inclusão de credenciais diretamente em um aplicativo desktop distribuído também apresenta riscos. Um arquivo `.env` não é um mecanismo de proteção de segredos contra quem tem acesso ao computador e aos arquivos da aplicação.

## ▶️ Executando pelo Eclipse

1. Importe o projeto como um projeto Maven existente.
2. Aguarde a resolução das dependências.
3. Confira a configuração do arquivo `.env`.
4. Verifique a conexão com o MongoDB Atlas.
5. Execute a classe principal `br.com.chatclone.Main`.

O projeto utiliza JavaFX para apresentar a interface gráfica.

## 🔨 Compilação com Maven

Na raiz do projeto, execute:

```bash
mvn clean package
```

Esse comando limpa os artefatos anteriores, compila o código e gera o JAR da aplicação.

O arquivo resultante deverá estar em:

```text
target/chatclone-0.0.1-SNAPSHOT.jar
```

O comando `clean` remove o diretório `target`. Por isso, as bibliotecas auxiliares usadas na etapa de empacotamento precisam ser copiadas novamente.

## 📦 Preparando as bibliotecas para distribuição

Os comandos seguintes foram utilizados no ambiente Windows com PowerShell.

Copie as dependências de execução para `target/libs`:

```powershell
mvn dependency:copy-dependencies `
-DincludeScope=runtime `
-DoutputDirectory=target\libs
```

Copie o JAR gerado pelo Maven para a mesma pasta:

```powershell
Copy-Item .\target\chatclone-0.0.1-SNAPSHOT.jar `
.\target\libs\ -Force
```

Ao final, `target/libs` deverá conter o JAR da aplicação e suas dependências de execução, incluindo os componentes necessários do JavaFX.

## 🖥️ Gerando o executável para Windows

O projeto utiliza o `jpackage`, ferramenta disponibilizada pelo JDK, para criar uma imagem de aplicação com executável e runtime Java.

### 1. Pré-requisitos

* JDK 25 instalado no computador utilizado para a compilação.
* Comando `jpackage` disponível no terminal.
* Projeto compilado.
* Dependências reunidas em `target/libs`.
* Ícone `chatclone.ico` na raiz do projeto.

Confira as ferramentas:

```powershell
java --version
```

```powershell
jpackage --version
```

### 2. Gerando a aplicação

Execute na raiz do projeto:

```powershell
jpackage `
--type app-image `
--name ChatClone `
--app-version 1.0.0 `
--input target\libs `
--main-jar chatclone-0.0.1-SNAPSHOT.jar `
--main-class br.com.chatclone.Main `
--module-path target\libs `
--add-modules javafx.controls,javafx.web,java.logging,java.naming,jdk.naming.dns `
--java-options "--enable-native-access=javafx.graphics" `
--dest app `
--icon chatclone.ico
```

### 3. Entendendo os parâmetros

| Parâmetro              | Finalidade                                           |
| ---------------------- | ---------------------------------------------------- |
| `--type app-image`     | Cria uma imagem de aplicação, sem instalador         |
| `--name ChatClone`     | Define o nome da aplicação                           |
| `--app-version 1.0.0`  | Define a versão de distribuição                      |
| `--input target\libs`  | Informa onde estão o JAR e as dependências           |
| `--main-jar`           | Define o JAR principal                               |
| `--main-class`         | Define a classe inicial                              |
| `--module-path`        | Indica o caminho dos módulos Java                    |
| `--add-modules`        | Inclui os módulos necessários ao JavaFX e ao MongoDB |
| `--java-options`       | Configura o acesso nativo utilizado pelo JavaFX      |
| `--dest app`           | Define o diretório de saída                          |
| `--icon chatclone.ico` | Define o ícone do executável no Windows              |

### 4. Resultado

A estrutura gerada será semelhante a:

```text
app/
└── ChatClone/
    ├── ChatClone.exe
    ├── app/
    │   ├── chatclone-0.0.1-SNAPSHOT.jar
    │   └── bibliotecas de execução
    └── runtime/
        └── ambiente Java
```

### 5. Executando a aplicação distribuída

Execute:

```powershell
.\app\ChatClone\ChatClone.exe
```

Para distribuir a aplicação, compacte **a pasta `app/ChatClone` inteira** e envie o arquivo ZIP.

Não envie apenas o `.exe`, pois ele depende dos arquivos da aplicação e do runtime incluídos nas outras pastas.

### 6. Atualizando o executável

Sempre que modificar o código-fonte:

1. Execute `mvn clean package`.
2. Copie novamente as dependências para `target/libs`.
3. Copie o JAR atualizado para `target/libs`.
4. Remova a imagem anterior em `app/ChatClone`.
5. Execute novamente o comando `jpackage`.

O executável distribuído deverá ser gerado a partir do código atualizado.

## 🧪 Solução de problemas

### JavaFX não encontrado

Se aparecer uma mensagem indicando que os componentes do JavaFX estão ausentes, confira os parâmetros `--module-path` e `--add-modules`.

### Falha ao conectar ao MongoDB Atlas

Confira:

* Se `MONGODB_URI` está configurada corretamente.
* Se o computador possui acesso à internet.
* Se o endereço do banco está correto.
* Se as permissões de rede do MongoDB Atlas permitem a conexão.
* Se a resolução DNS está funcionando.

### Falha ao inicializar a aplicação empacotada

Verifique se o runtime contém os módulos necessários. Neste projeto, a criação do executável exigiu, entre outros, os módulos `java.logging`, `java.naming` e `jdk.naming.dns`.

### Ícone incorreto

O projeto utiliza `chatclone.ico` para o executável e `chatclone.png` como recurso da janela JavaFX.

Confira se os arquivos estão nos locais esperados e gere novamente a aplicação após atualizar os recursos.

## 🔒 Segurança

O projeto depende de serviços externos e de credenciais de acesso.

Recomendações:

* Não versionar arquivos `.env`.
* Não publicar chaves da API da Groq.
* Não publicar credenciais do MongoDB.
* Não incluir dados reais de usuários ou conversas no repositório.
* Não considerar uma chave armazenada em um aplicativo desktop como secreta.
* Revisar os arquivos antes de realizar o primeiro `git push`.

## 📚 Objetivos de aprendizagem

O desenvolvimento do ChatClone_Desktop permite explorar:

* Programação Java e orientação a objetos.
* Construção de interfaces desktop com JavaFX.
* Estilização com CSS.
* Gerenciamento de dependências com Maven.
* Persistência de dados com MongoDB.
* Integração com APIs de Inteligência Artificial.
* Recuperação de informações baseada em palavras-chave.
* Configuração de variáveis de ambiente.
* Empacotamento de aplicações Java para Windows.
* Controle de versão com Git e publicação no GitHub.

## 📄 Licença

A licença do projeto deverá ser definida pelo responsável pelo repositório.

Até que uma licença seja adicionada, não presuma que terceiros possuem autorização para redistribuir ou modificar o código.

---

<div align="center">

**ChatClone_Desktop**

Desenvolvido com Java, JavaFX e Inteligência Artificial.

</div>
