# TP3 - DevCalc API

[![Deploy Multi-Environment](https://github.com/gustalgebaile/TP3_LeoGloria2/actions/workflows/deploy.yml/badge.svg)](https://github.com/gustalgebaile/TP3_LeoGloria2/actions/workflows/deploy.yml)
[![Java Matrix Build](https://github.com/gustalgebaile/TP3_LeoGloria2/actions/workflows/java-matrix.yml/badge.svg)](https://github.com/gustalgebaile/TP3_LeoGloria2/actions/workflows/java-matrix.yml)
[![Hello CI/CD](https://github.com/gustalgebaile/TP3_LeoGloria2/actions/workflows/hello.yml/badge.svg)](https://github.com/gustalgebaile/TP3_LeoGloria2/actions/workflows/hello.yml)

Aplicação de calculadora desenvolvida com Javalin e Maven, utilizando CI/CD com GitHub Actions.

## Sobre o Projeto

API REST simples para operações matemáticas, containerizada com Docker e com pipeline completo de CI/CD.

## TP3 - GitHub Actions e CI/CD

### Workflows Implementados

Este trabalho prático implementa diversos workflows do GitHub Actions para demonstrar conceitos de CI/CD, runners, variáveis de ambiente, secrets e estratégias de deploy.

#### 1. Workflows Básicos

**hello.yml**
- Dispara em qualquer push no repositório
- Exibe "Hello CI/CD" nos logs
- Demonstra conceito básico de workflow

Como executar:
```bash
git push origin master
```

**tests.yml**
- Dispara automaticamente em pull requests
- Usa `actions/checkout` para clonar o código
- Executa simulação de testes

Como executar:
```bash
# Crie um pull request no GitHub
```

**gradle-ci.yml**
- Dispara em push para a branch master
- Configura Java 17 e Maven
- Executa build completo da aplicação

Como executar:
```bash
git push origin master
```

#### 2. Runners, Variáveis e Segurança

**env-demo.yml**
- Utiliza variável de ambiente `DEPLOY_ENV=staging`
- Exibe informações do ambiente nos logs
- Demonstra uso de variáveis de ambiente

Como executar:
```bash
git push origin master
# Ou via Actions → env-demo → Run workflow
```

**secret-demo.yml**
- Utiliza secret `API_KEY` configurado no repositório
- Demonstra mascaramento automático de dados sensíveis
- Valida configuração de secrets sem expor valores

Como executar:
```bash
# 1. Configure o secret API_KEY em Settings → Secrets
# 2. Execute:
git push origin master
# Ou via Actions → secret-demo → Run workflow
```

**mask-demo.yml**
- Demonstra mascaramento automático de secrets
- Mostra uso de `::add-mask::` para valores dinâmicos
- Exemplifica boas práticas de segurança

Como executar:
```bash
# Via Actions → Mask Sensitive Data Demo → Run workflow
```

#### 3. Deploys e Estratégias

**release-deploy.yml**
- Dispara quando um release é publicado
- Exibe informações da versão
- Executa deploy simulado

Como executar:
```bash
# 1. Vá em Releases → Create a new release
# 2. Defina uma tag (ex: v1.0.0)
# 3. Publique o release
```

**java-matrix.yml**
- Usa matrix strategy para testar em Java 11 e 17
- Executa builds paralelos
- Demonstra testes de compatibilidade

Como executar:
```bash
git push origin master
# Ou via Actions → Java Matrix Build → Run workflow
```

**deploy.yml**
- Deploy multi-ambiente (dev, staging, prod)
- Usa variáveis específicas por ambiente
- Implementa secrets por ambiente
- Deploy automático em dev, manual para staging/prod

Como executar:
```bash
# Deploy automático em DEV:
git push origin master

# Deploy manual em STAGING ou PROD:
# 1. Vá em Actions → Deploy Multi-Environment
# 2. Clique em Run workflow
# 3. Selecione o ambiente desejado
# 4. Clique em Run workflow
```

### Secrets Configurados

Os seguintes secrets foram configurados no repositório:

**Development:**
- `DEV_API_KEY`
- `DEV_DB_PASSWORD`

**Staging:**
- `STAGING_API_KEY`
- `STAGING_DB_PASSWORD`

**Production:**
- `PROD_API_KEY`
- `PROD_DB_PASSWORD`

### Conceitos Implementados

#### Runners

Os runners do GitHub Actions são máquinas que executam os workflows. Existem dois tipos principais: runners hospedados pelo GitHub (ubuntu-latest, windows-latest, macos-latest) que são gerenciados pelo GitHub com manutenção zero, e runners auto-hospedados que você configura em sua própria infraestrutura. Neste projeto utilizamos runners hospedados pelo GitHub em todos os workflows, que oferecem ambientes limpos, pré-configurados com ferramentas comuns como Java, Maven e Docker, e são ideais para projetos que não precisam acessar recursos internos de uma organização.

#### Variáveis de Ambiente

As variáveis de ambiente permitem parametrizar workflows sem modificar o código. Implementamos diferenciação entre ambientes (dev, staging, prod) onde cada um possui URLs, hosts de banco de dados e configurações específicas armazenadas em variáveis. No GitHub Actions, as variáveis podem ser definidas em níveis diferentes: no workflow inteiro (`env:` no topo), em um job específico (`jobs.job_id.env:`) ou em um step individual. Isso permite reutilizar workflows para diferentes ambientes apenas alterando as variáveis.

#### Secrets

Secrets são valores sensíveis como chaves de API, senhas e tokens que precisam ser mantidos seguros. O GitHub Actions oferece mascaramento automático onde qualquer secret referenciado nos logs é substituído por `***`, impedindo que credenciais sejam expostas acidentalmente. Para valores sensíveis gerados dinamicamente durante a execução, usamos o comando `::add-mask::valor` para registrá-los como sensíveis. Separamos secrets por ambiente garantindo que credenciais de produção nunca sejam usadas em desenvolvimento.

#### Estratégias de Deploy

Implementamos conceitos de estratégias de deploy. Blue-Green é uma estratégia onde mantemos dois ambientes idênticos e redirecionamos instantaneamente o tráfego entre eles, permitindo rollback imediato mas exigindo o dobro de recursos. Rolling Update atualiza instâncias gradualmente, mantendo o serviço disponível enquanto apenas parte das máquinas está sendo atualizada, economizando recursos mas com deployment mais longo. O workflow deploy.yml implementa deploy automático em dev e manual em staging/prod, simulando um pipeline real onde produção nunca é atualizada automaticamente.

### Estrutura de Arquivos

```
.github/
└── workflows/
    ├── hello.yml              # Workflow básico
    ├── tests.yml              # Testes em PR
    ├── gradle-ci.yml          # Build com Maven
    ├── env-demo.yml           # Variáveis de ambiente
    ├── secret-demo.yml        # Demonstração de secrets
    ├── mask-demo.yml          # Mascaramento de dados
    ├── release-deploy.yml     # Deploy em releases
    ├── java-matrix.yml        # Matrix strategy
    └── deploy.yml             # Deploy multi-ambiente

src/
├── main/
│   └── java/
│       └── com/devcalc/
│           ├── CaculatorService.java
│           └── Main.java
└── test/
    └── java/
        └── com/devcalc/
            └── CalculatorServiceTest.java

pom.xml                        # Configuração Maven
Dockerfile                     # Imagem Docker
docker-compose.yml            # Orquestração Docker
README.md                      # Este arquivo
```

### Como Reexecutar os Workflows

**Via Push (Workflows que disparam em push):**
```bash
git add .
git commit -m "Trigger workflows"
git push origin master
```

**Via Interface (Workflows com workflow_dispatch):**
1. Acesse a aba **Actions** do repositório
2. Selecione o workflow desejado no menu lateral
3. Clique em **Run workflow**
4. Escolha os parâmetros (se houver)
5. Clique em **Run workflow**

**Via Pull Request (Workflows que disparam em PR):**
```bash
git checkout -b feature/test
git push origin feature/test
# Crie um PR no GitHub via interface web
```

**Via Release (Workflows que disparam em release):**
1. Vá em **Releases** no repositório
2. Clique em **Create a new release**
3. Defina uma tag (ex: v1.0.0)
4. Adicione descrição (opcional)
5. Clique em **Publish release**

### Tecnologias Utilizadas

- **Java 17**: Linguagem principal
- **Maven**: Gerenciamento de dependências e build
- **Javalin**: Framework web para API REST
- **Docker**: Containerização da aplicação
- **Docker Compose**: Orquestração de containers
- **GitHub Actions**: CI/CD e automação
- **JUnit 4**: Framework de testes

### Como Executar Localmente

**Pré-requisitos:**
- Java 17 ou superior
- Maven 3.8+
- Docker (opcional)

**Execução sem Docker:**
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.devcalc.Main"
```

A aplicação estará disponível em `http://localhost:7000`

**Endpoints disponíveis:**
- `GET /` - Retorna mensagem de confirmação
- `GET /add?a=5&b=3` - Retorna a soma de a e b

**Execução com Docker:**
```bash
docker-compose up
```
