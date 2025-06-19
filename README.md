# 📊 DSMeta - Sales Report System

Um sistema completo de relatórios de vendas desenvolvido com **Spring Boot**, que permite consultar e analisar dados de vendas por período e vendedor.

## 🚀 Sobre o Projeto

O **DSMeta** é uma API REST que oferece funcionalidades avançadas para análise de vendas, incluindo:

- **Relatórios detalhados** de vendas individuais
- **Sumários consolidados** por vendedor 
- **Filtros flexíveis** por período e nome
- **Cálculo automático** dos últimos 12 meses
- **Consultas otimizadas** com SQL nativo

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (desenvolvimento)
- **PostgreSQL** (produção)
- **Maven**

## 📁 Estrutura do Projeto

```
src/main/java/com/devsuperior/dsmeta/
├── controllers/
│   └── SaleController.java          # Endpoints REST
├── services/
│   ├── SaleService.java            # Lógica de negócio - Vendas
│   └── SellerService.java          # Lógica de negócio - Vendedores
├── repositories/
│   ├── SaleRepository.java         # Queries de vendas
│   └── SellerRepository.java       # Queries de vendedores
├── entities/
│   ├── Sale.java                   # Entidade Venda
│   └── Seller.java                 # Entidade Vendedor
├── dto/
│   ├── SaleMinDTO.java            # DTO para vendas
│   └── SellerMinDTO.java          # DTO para sumário
└── projections/
    ├── SaleMinProjection.java     # Projection para vendas
    └── SellerMinProjection.java   # Projection para vendedores
```

## 🎯 Funcionalidades

### 📈 Relatório de Vendas (`/sales/report`)
- Lista vendas individuais com detalhes completos
- Filtro por período específico ou últimos 12 meses
- Filtro por nome do vendedor (busca parcial)
- Ordenação por data

### 📊 Sumário de Vendas (`/sales/summary`) 
- Consolidação de vendas por vendedor
- Total de faturamento por vendedor
- Período configurável ou últimos 12 meses
- Ordenação por maior faturamento

## 🌐 Endpoints da API

### 📋 Relatório de Vendas
```http
GET /sales/report
GET /sales/report?minDate=2022-01-01&maxDate=2022-06-30
GET /sales/report?minDate=2022-01-01&maxDate=2022-06-30&name=Loki
```

**Resposta:**
```json
[
  {
    "id": 1,
    "amount": 18196.0,
    "date": "2024-06-16",
    "sellerName": "Loki Odinson"
  }
]
```

### 📊 Sumário por Vendedor
```http
GET /sales/summary
GET /sales/summary?minDate=2022-01-01&maxDate=2022-06-30
```

**Resposta:**
```json
[
  {
    "sellerName": "Loki Odinson",
    "total": 150597.0
  },
  {
    "sellerName": "Thor Odinson", 
    "total": 144896.0
  }
]
```

### 📄 Outros Endpoints
```http
GET /sales/{id}              # Buscar venda por ID
GET /sales/all?page=0&size=10 # Listar todas (paginado)
```

## ⚙️ Regras de Negócio

### 🗓️ Cálculo de Datas
- **Data final não informada:** Usa data atual do sistema
- **Data inicial não informada:** Calcula 1 ano antes da data final  
- **Nenhuma data informada:** Últimos 12 meses automaticamente
- **Nome não informado:** Busca todos os vendedores

### 🔍 Filtros Inteligentes
- **Busca por nome:** Case-insensitive, busca parcial
- **Período flexível:** Aceita qualquer combinação de datas
- **Padrão inteligente:** Sem parâmetros = últimos 12 meses

## 🏃‍♂️ Como Executar

### Pré-requisitos
- **Java 17+**
- **Maven 3.6+**

### 🚀 Execução
```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/dsmeta.git
cd dsmeta

# Executar a aplicação
mvn spring-boot:run

# Ou compilar e executar
mvn clean package
java -jar target/dsmeta-*.jar
```

### 🌐 Acessar a API
```
http://localhost:8080/sales/report
http://localhost:8080/sales/summary
```

## 💾 Banco de Dados

### H2 (Desenvolvimento)
- **Console:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User:** `sa`
- **Password:** *(vazio)*

### PostgreSQL (Produção)
Configure no `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## 📝 Exemplos de Uso

### Relatório dos Últimos 12 Meses
```bash
curl http://localhost:8080/sales/report
```

### Sumário de Janeiro a Junho de 2022
```bash
curl "http://localhost:8080/sales/summary?minDate=2022-01-01&maxDate=2022-06-30"
```

### Vendas do Loki em Maio de 2022
```bash
curl "http://localhost:8080/sales/report?minDate=2022-05-01&maxDate=2022-05-31&name=Loki"
```

## 🏗️ Arquitetura

### Padrão MVC
- **Controllers:** Recebem requisições HTTP
- **Services:** Implementam regras de negócio  
- **Repositories:** Acessam dados com queries otimizadas
- **DTOs/Projections:** Transferem dados de forma eficiente

### Boas Práticas Implementadas
- ✅ **Separation of Concerns**
- ✅ **Repository Pattern**
- ✅ **DTO Pattern**
- ✅ **Projection Pattern**
- ✅ **Clean Code**
- ✅ **RESTful API**

## 🎨 Destaques Técnicos

### SQL Nativo Otimizado
```sql
SELECT se.name, SUM(sa.amount) AS total 
FROM tb_seller se 
INNER JOIN tb_sales sa ON se.id = sa.seller_id 
WHERE sa.date >= ? AND sa.date <= ? 
GROUP BY se.name 
ORDER BY total DESC
```

### Cálculo Inteligente de Datas
```java
LocalDate dataFinal = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
LocalDate dataInicial = dataFinal.minusYears(1L);
```

### Interface Projection para Performance
```java
public interface SaleMinProjection {
    Long getId();
    Double getAmount(); 
    LocalDate getDate();
    String getName();
}
```

## 🤝 Contribuição

1. **Fork** o projeto
2. **Crie** uma branch: `git checkout -b minha-feature`
3. **Commit** suas mudanças: `git commit -m 'Adiciona nova feature'`
4. **Push** para a branch: `git push origin minha-feature`
5. **Abra** um Pull Request


## 👨‍💻 Autor

Willian Bruno
- GitHub: (https://github.com/Sinnisterr)
- LinkedIn: (https://www.linkedin.com/in/willian-bruno-28924082/)

---

⭐ **Se este projeto te ajudou, deixe uma estrela!**
