# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comments (
  id                        bigserial not null,
  body                      varchar(255),
  poster_id                 bigint,
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
  start_date                timestamp,
  end_date                  timestamp,
  status                    boolean,
  dismiss                   boolean,
  wanted_id                 bigint,
  borrower_id               bigint,
  lender_id                 bigint,
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
  available                 boolean,
  owner_id                  bigint,
  category_id               bigint,
  constraint pk_tools primary key (id))
;

create table Users (
  id                        bigserial not null,
  email                     varchar(255),
  username                  varchar(255),
  password_hash             varchar(255),
  admin                     boolean,
  constraint uq_Users_username unique (username),
  constraint pk_Users primary key (id))
;

alter table comments add constraint fk_comments_poster_1 foreign key (poster_id) references Users (id);
create index ix_comments_poster_1 on comments (poster_id);
alter table comments add constraint fk_comments_topic_2 foreign key (topic_id) references tools (id);
create index ix_comments_topic_2 on comments (topic_id);
alter table pm add constraint fk_pm_sender_3 foreign key (sender_id) references Users (id);
create index ix_pm_sender_3 on pm (sender_id);
alter table pm add constraint fk_pm_receiver_4 foreign key (receiver_id) references Users (id);
create index ix_pm_receiver_4 on pm (receiver_id);
alter table request add constraint fk_request_wanted_5 foreign key (wanted_id) references tools (id);
create index ix_request_wanted_5 on request (wanted_id);
alter table request add constraint fk_request_borrower_6 foreign key (borrower_id) references Users (id);
create index ix_request_borrower_6 on request (borrower_id);
alter table request add constraint fk_request_lender_7 foreign key (lender_id) references Users (id);
create index ix_request_lender_7 on request (lender_id);
alter table tools add constraint fk_tools_owner_8 foreign key (owner_id) references Users (id);
create index ix_tools_owner_8 on tools (owner_id);
alter table tools add constraint fk_tools_category_9 foreign key (category_id) references tool_type (id);
create index ix_tools_category_9 on tools (category_id);



# --- !Downs

drop table if exists comments cascade;

drop table if exists pm cascade;

drop table if exists request cascade;

drop table if exists tool_type cascade;

drop table if exists tools cascade;

drop table if exists Users cascade;

