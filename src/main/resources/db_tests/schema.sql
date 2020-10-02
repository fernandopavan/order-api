alter table perfis drop constraint IF EXISTS FK3w1yx5f56pouyb73r15h8w5wl;
drop table if exists perfis cascade;
drop table if exists pessoa_fisica cascade;
drop sequence if exists seq_pessoas_fisicas;

create sequence seq_pessoas_fisicas start 1 increment 1;

create table perfis (
   pessoa_fisica_id int4 not null,
   perfis int4
);

create table pessoa_fisica (
    id int4 not null,
    created_in timestamp not null,
    updated_in timestamp,
    version int4,
    cpf varchar(255),
    data_nascimento date not null,
    email varchar(255),
    nacionalidade varchar(50),
    naturalidade varchar(50),
    nome varchar(120),
    senha varchar(255),
    sexo int4,
    primary key (id)
);

alter table pessoa_fisica
    add constraint UK_cpf unique (cpf);

alter table pessoa_fisica
    add constraint UK_email unique (email);

alter table perfis
    add constraint FK3w1yx5f56pouyb73r15h8w5wl
    foreign key (pessoa_fisica_id)
    references pessoa_fisica;