create table autores(
    id bigint auto_increment not null,
    nombre varchar(100) not null,
    correo_electronico varchar(100) not null,
    clave varchar(300) not null,
    primary key(id)
);

insert into autores(id, nombre, correo_electronico, clave)
values(1, "germán", "jg345762@gmail.com", "$2a$12$p5ZONoU0J6hepzomWJcSjul83rGbVUIXXTb/MaTp3EW2zLt4hz8gK");