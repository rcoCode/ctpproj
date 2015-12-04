# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comments (
  id                        bigserial not null,
  body                      varchar(255),
  user_id                   bigint,
  topic_id                  bigint,
  post_time                 timestamp,
  constraint pk_comments primary key (id))
;

create table pm (
  id                        bigserial not null,
  message                   varchar(255),
  sender_id                 bigint,
  receiver_id               bigint,
  constraint pk_pm primary key (id))
;

create table request (
  id                        bigserial not null,
  message                   varchar(255),
  timer                     timestamp,
  status                    boolean,
  wanted_id                 bigint,
  borrower_id               bigint,
  constraint pk_request primary key (id))
;

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

alter table comments add constraint fk_comments_topic_1 foreign key (topic_id) references tools (id);
create index ix_comments_topic_1 on comments (topic_id);
alter table pm add constraint fk_pm_sender_2 foreign key (sender_id) references users (id);
create index ix_pm_sender_2 on pm (sender_id);
alter table pm add constraint fk_pm_receiver_3 foreign key (receiver_id) references users (id);
create index ix_pm_receiver_3 on pm (receiver_id);
alter table request add constraint fk_request_wanted_4 foreign key (wanted_id) references tools (id);
create index ix_request_wanted_4 on request (wanted_id);
alter table request add constraint fk_request_borrower_5 foreign key (borrower_id) references users (id);
create index ix_request_borrower_5 on request (borrower_id);
alter table tools add constraint fk_tools_owner_6 foreign key (owner_id) references users (id);
create index ix_tools_owner_6 on tools (owner_id);
alter table tools add constraint fk_tools_category_7 foreign key (category_id) references tool_type (id);
create index ix_tools_category_7 on tools (category_id);



# --- !Downs

drop table if exists comments cascade;

drop table if exists pm cascade;

drop table if exists request cascade;

drop table if exists tool_type cascade;

drop table if exists tools cascade;

drop table if exists users cascade;

