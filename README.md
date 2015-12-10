# BTC Aggregator (btca)

BTCA (pronounced bitca) is a java based application for accumulating bitcoin related information.

# Development Environment

## Docker containers

The following docker containers can be used to startup a development environment:


*dockerfile/rabbitmq*
```
docker run --name rabbitmq -d -p 5672:5672 -p 15672:15672 -v /home/jaco/docker/data/rabbitmq:/data/mnesia dockerfile/rabbitmq
```

*dockerfile/mongodb*
```
docker run --name mongodb -d -p 27017:27017 -v /home/jaco/docker/data/mongodb:/data/db  dockerfile/mongodb
```

*jaconel/statsd*
```
docker run --name statsd -d -p 8125:8125/udp -e GRAPHITE_HOST=192.168.23.77 -e GRAPHITE_PORT=2003 -e STATSD_DEBUG=true -e STATSD_DUMP_MSG=true jaconel/statsd
```

*jaconel/graphite*
```
docker run --name graphite -d -p 80:80 -p 2003:2003 -p 2004:2004 -p 7002:7002 -v /home/jaco/docker/data/graphite:/var/lib/graphite/storage/whisper jaconel/graphite
```

## Environmental Varaibles

| Env Var                         | Default         |
|---------------------------------|-----------------|
| BTCA_AMQP_HOST                  | localhost       |
| BTCA_AMQP_USER                  | guest           |
| BTCA_AMQP_PASSWORD              | guest           |
| BTCA_AMQP_TRANSACTIONS_VHOST    | btca            |
| BTCA_AMQP_TRANSACTIONS_EXCHANGE | exchange_prices |
| BTCA_STATSD_PREFIX              | btca            |
| BTCA_STATSD_HOST                | 127.0.0.1       |
| BTCA_STATSD_PORT                | 8125            |
| BTCA_MONGODB_HOST               | 127.0.0.1       |
| BTCA_MONGODB_PORT               | 27017           |
| BTCA_MONGODB_DB_NAME            | transactions    | 
