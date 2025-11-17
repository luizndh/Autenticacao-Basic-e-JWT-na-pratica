## API de Notas - Projeto de Estudo em Spring Security

Este é um projeto de aprendizado desenvolvido em Java com Spring Boot, focado em explorar e implementar conceitos fundamentais de segurança em APIs REST.

O objetivo principal foi construir um sistema que gerencia "notas" pessoais, aplicando diferentes estratégias de autenticação e autorização para diferentes tipos de usuários.

## 🎯 Foco Principal: Autenticação Basic vs. JWT vs. OAuth 2.0

O núcleo deste projeto é a implementação e coexistência de três dos mais comuns mecanismos de autenticação no Spring Security:

### 1. Basic Authentication (Legado/Testes):
- **Público:** Usuários Padrão (`USER`).
- **Funcionamento:** Método tradicional onde o cliente envia o header `Authorization: Basic <base64>` a cada requisição.
- **Uso:** Ideal para testes rápidos via Postman ou chamadas simples de API sem interface gráfica.
- **Escopo:** Permite acessar e gerenciar apenas as notas do próprio usuário autenticado.

### 2. OAuth 2.0 com Google (Login Social):
- **Público:** Usuários Padrão (`USER`).
- **Fluxo Híbrido:** Utiliza o Google como Provedor de Identidade (Identity Provider).
- **Como funciona:**
  1. O usuário inicia o login via `/oauth2/authorization/google`.
  2. Após a autenticação no Google, o sistema intercepta o sucesso através de um `AuthenticationSuccessHandler` customizado.
  3. O sistema verifica se o e-mail já existe no banco local (H2); se não, cria um novo usuário automaticamente.
  4. Ao final, a API gera e retorna um **Token JWT** próprio da aplicação.
- **Resultado:** O usuário utiliza esse JWT para fazer chamadas autenticadas, mantendo a API *stateless*.

### 3. JWT - JSON Web Tokens (Login Administrativo):
- **Público:** Administradores (`ADMIN`).
- **Funcionamento:** Autenticação via endpoint dedicado `/api/auth/login` (envio de credenciais JSON).
- **Uso:** Gera um token com permissões elevadas.
- **Escopo:** O token deve ser enviado no cabeçalho `Authorization: Bearer <token>`. Permite acesso total ao sistema, incluindo a visualização de notas de **todos** os usuários (bypass das restrições de dono).

## Outros Conceitos Praticados
Além do foco em autenticação, o projeto serviu para praticar:

- Spring Security: Configuração de SecurityFilterChain, AuthenticationProvider e filtros customizados (JwtAuthFilter).
- Spring Data JPA: Mapeamento de entidades (@ManyToOne) e consultas customizadas (@Query).
 -Tratamento de Exceções Global: Uso de @RestControllerAdvice para retornar erros padronizados (404, 403, 500).
- HTTPS/TLS: Configuração de um certificado SSL autoassinado (via keytool) para habilitar HTTPS na aplicação.
- Simulação de DNS: Edição do arquivo hosts local para mapear um domínio customizado (minhaapi.local) para localhost, permitindo testar o certificado SSL.
- H2 Database: Uso de um banco em memória com inicialização de dados (CommandLineRunner) para testes.

## 🚀 Tecnologias Utilizadas

- Java 25
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- JJWT (Java JWT)
- H2 Database (Em memória)
- Maven