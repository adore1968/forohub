create table topicos(
    id bigint auto_increment not null,
    titulo varchar(100) not null unique,
    mensaje varchar(200) not null unique,
    fecha_de_creacion datetime not null,
    status varchar(100) not null,
    nombre_de_curso varchar(100) not null,
    autor_id bigint not null,
    constraint fk_topicos_autor_id foreign key(autor_id) references autores(id),
    primary key(id)
);