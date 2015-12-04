# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tool_type (
  id                        bigserial not null,
  name                      varchar(255),
  constraint pk_tool_type primary key (id))
;

create table tools (
  id                        bigserial not null,
  name                      varchar(255),
  description               varchar(255),
  owner_id                  bigint,
  category_id               bigint,
  constraint pk_tools primary key (id))
;

create table users (
  id                        bigserial not null,
  email                     varchar(255),
  username                  varchar(255),
  password_hash             varchar(255),
  admin                     boolean,
  constraint uq_users_username unique (username),
  constraint pk_users primary key (id))
;

alter table tools add constraint fk_tools_owner_1 foreign key (owner_id) references users (id);
create index ix_tools_owner_1 on tools (owner_id);
alter table tools add constraint fk_tools_category_2 foreign key (category_id) references tool_type (id);
create index ix_tools_category_2 on tools (category_id);



# --- !Downs

drop table if exists tool_type cascade;

drop table if exists tools cascade;

drop table if exists users cascade;

