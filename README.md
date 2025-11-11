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

## 🚀 Tecnologias Utilizadas

- Java 25
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- JJWT (Java JWT)
- H2 Database (Em memória)
- Maven