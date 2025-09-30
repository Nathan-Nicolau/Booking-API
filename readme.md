A Api foi desenvolvida utilizando:

- Scala: 2.13.16
- SBT: 1.11.6
- Play Framework: 3.0.9 
- Slick: 5.2.0
- Postgres: 15
- Docker

É possível:
- Consultar o inventário dos quartos existentes
- Adicionar novos quartos ao inventário
- Remover quarto do inventário (somente torna ele não acessível para reserva)
- Adicionar nova reserva
- Consultar reservas de um determinado período

Os arquivos init.sql e docker-compose estão na pasta - Docker-booking-api

Obs: Eu já possuía um container Docker mapeado na porta 5432, por isso o mapeamento de porta ficou para a 5433
