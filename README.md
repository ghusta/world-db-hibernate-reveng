# Hibernate Tools - Reverse Engineering for World DB (PostgreSQL)

[![Build Status](https://travis-ci.org/ghusta/world-db-hibernate-reveng.svg?branch=hibernate-5.2)](https://travis-ci.org/ghusta/world-db-hibernate-reveng)

## Maven command

`mvn clean generate-sources -P gen-hibernate-tools`

As a result, code will be generated in _target/generated-sources/hibernate_.

## Running the database server

You can use a docker container, like this one :

`docker run -d -p xxxx:5432 --name world-db ghusta/postgres-world-db:2.2.1`

## Templates (FreeMarker)

- pojo : **pojo/Pojo.ftl**
- dao (repository) : **dao/JpaRepository.ftl**

## Tips

- TODO: Force use of object types for @Id and not primitive types :  
  See _org.hibernate.tool.hbm2x.Cfg2JavaTool.PRIMITIVES_

## External references

- [Hibernate Tools](http://hibernate.org/tools/)
- [FreeMarker](http://freemarker.org/)
