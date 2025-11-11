## API de Notas - Projeto de Estudo em Spring Security

Este é um projeto de aprendizado desenvolvido em Java com Spring Boot, focado em explorar e implementar conceitos fundamentais de segurança em APIs REST.

O objetivo principal foi construir um sistema que gerencia "notas" pessoais, aplicando diferentes estratégias de autenticação e autorização para diferentes tipos de usuários.

## 🎯 Foco Principal: Autenticação Basic vs. JWT

O núcleo deste projeto é a implementação e coexistência de dois dos mais comuns mecanismos de autenticação no Spring Security:

### 1. Basic Authentication (para Usuários Padrão):

- Utilizada para autenticar usuários com ROLE_USER.
- Permite que o usuário acesse, crie e gerencie apenas as suas próprias notas.
- A autenticação é feita a cada requisição, enviando o username e password codificados em Base64.

### 2. JWT - JSON Web Tokens (para Administradores):

- Utilizada para autenticar usuários com ROLE_ADMIN.
- Admins possuem um endpoint de login dedicado (/api/auth/login) que, se o login for bem-sucedido, gera um token JWT.
- Este token deve ser enviado no cabeçalho Authorization: Bearer <token> e permite ao admin ter acesso total ao sistema, incluindo a visualização de notas de todos os usuários.

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